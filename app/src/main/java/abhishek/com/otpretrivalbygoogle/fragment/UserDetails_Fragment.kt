package abhishek.com.otpretrivalbygoogle.fragment


import abhishek.com.otpretrivalbygoogle.R
import abhishek.com.otpretrivalbygoogle.model.OTPRequestModel
import abhishek.com.otpretrivalbygoogle.model.OTPResponseModel
import abhishek.com.otpretrivalbygoogle.retrofit.RetrofitClient
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserDetails_Fragment : Fragment(), View.OnClickListener {
    // finding the edit text
    private var progressBar: ProgressBar? = null
    private lateinit var userNumber: EditText
    private lateinit var tvNext: TextView
    private var mGoogleApiClient: GoogleApiClient? = null
    private var myContext: FragmentActivity? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.layout_user_details, container, false)

        // initialize views for fragment
        userNumber = view.findViewById(R.id.userNumber)
        tvNext = view.findViewById(R.id.tv_next)
        progressBar = view.findViewById(R.id.progressBar)


        // set Listner
        tvNext.setOnClickListener(this)
        //  make google api client instance for use
        mGoogleApiClient = activity?.let {
            GoogleApiClient.Builder(it)
                .enableAutoManage(
                    activity!! /* FragmentActivity */
                    /* OnConnectionFailedListener */
                ) { connectionResult: ConnectionResult? -> }
                .addApi(Auth.CREDENTIALS_API).enableAutoManage(
                    activity!!
                ) { connectionResult: ConnectionResult? -> }
                .build()
        }


// this method will allow user to choosse its its existing mobile number
        requestHint()

        // Inflate the layout for this fragment
        return view;

    }

    // Construct a request for phone numbers and show the picker
    private fun requestHint() {
        val hintRequest: HintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()

        val intent: PendingIntent =
            Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest)
        startIntentSenderForResult(intent.intentSender, 101, null, 0, 0, 0, Bundle())
    }

    override fun onAttach(context: Context) {
        myContext = activity as FragmentActivity
        super.onAttach(context)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // get result phone number her or you can input manually too
        when (requestCode) {
            101 -> try {
                val credential: Credential = data!!.getParcelableExtra(Credential.EXTRA_KEY)
                // credential.getId();  <-- will need to process phone number string
                val id = credential.id
                userNumber.setText(id.substring(3))
            } catch (e: Exception) {

            }

        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_next -> {

                // Hit  your api in this code
                callYourApiForSmsWithYourPhoneNumber()

            }
        }
    }

    private fun callYourApiForSmsWithYourPhoneNumber() {
        var userNun: String = userNumber.text.toString();
        val retrofitApiInterface: Call<OTPResponseModel?>? =
            RetrofitClient.apiService.CustomerLoginRequest(OTPRequestModel(userNun))

        retrofitApiInterface!!.enqueue(object : Callback<OTPResponseModel?> {
            override fun onResponse(
                call: Call<OTPResponseModel?>,
                response: Response<OTPResponseModel?>
            ) {
                if (response.isSuccessful) {
                    // on succesfull api call get OTP that has been sent from server to user number and match with the otp recieved via api call
                    var OTP = response.body()!!.d.otp
                    val fragmentManager = myContext!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    val OTP_Verfication_Fragment = OTP_Verfication_Fragment()

                    var args = Bundle();
                    args.putString("OTP", OTP)
                    args.putString("userNumber", userNun)
                    OTP_Verfication_Fragment.setArguments(args)
                    // pass data to next fragment OTP , usernumber
                    fragmentTransaction.replace(
                        android.R.id.content,
                        OTP_Verfication_Fragment
                    )
                    fragmentTransaction.commit()

                } else {
                    Log.e("otp", "message  else ")
                }
            }

            override fun onFailure(call: Call<OTPResponseModel?>, t: Throwable) {
                //
                Log.e("otp ", "failed")
            }
        })
    }
}