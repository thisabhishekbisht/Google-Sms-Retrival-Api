package abhishek.com.otpretrivalbygoogle.fragment

import abhishek.com.otpretrivalbygoogle.R
import abhishek.com.otpretrivalbygoogle.activity.DrawerActivity
import abhishek.com.otpretrivalbygoogle.reciever.SMSBroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.goodiebag.pinview.Pinview
import com.google.android.gms.auth.api.phone.SmsRetriever
import java.util.regex.Pattern


class OTP_Verfication_Fragment : Fragment() {
    private var otpActual: String? = null
    private var etd_OTP: Pinview? = null
    private var myContext: FragmentActivity? = null
    private var otp_recieved: String? = null
    private var progressBar: ProgressBar? = null
    private var progressStatus: Int? = 0
    private val handler = Handler()
    private var tvTime: TextView? = null
    private var tvNote: TextView? = null
    private var userNumber: String? = null
    // SMS Retrival Listner
    var receiver: SMSBroadcastReceiver = SMSBroadcastReceiver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.layout_otp_verification, container, false)
        // Sign your app for hash key uncomment it for one time
        /*   val appSignatureHelper = AppSignatureHelper(myContext)
           appSignatureHelper.appSignatures*/
        // get retrival client
        getSimsRetrievalClient()

        // Start long running operation in a background thread
        // Start long running operation in a background thread

        tvTime = view.findViewById(R.id.tv_timer)
        tvNote = view.findViewById(R.id.tv_note)
        etd_OTP = view.findViewById(R.id.etd_OTP)

        progressBar = view.findViewById(R.id.progressBar)

        tvNote!!.setText("Please sit back and relax we have sent OTP message on mobile number " + userNumber!!.substring(4) + "****")

        // Start long running operation in a background thread
        // Start long running operation in a background thread
        Thread(Runnable {
            while (progressStatus!! < 60) {
                progressStatus = progressStatus!! + 1
                // Update the progress bar and display the
                //current value in the text view
                handler.post {
                    progressBar!!.progress = progressStatus!!
                    tvTime!!.text = progressStatus.toString()
                }

                try { // Sleep for 200 milliseconds...
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()

        // here bind reciever for recieving sms from phone
        SMSBroadcastReceiver.bindListener { message ->
            run {
                Log.e("meaasge", message);
                // here retrieve otp from recieved msg on device
                getOTP(message);
                // check if otp is same a s received from Api and from device message
                if (compairOtp(otpActual.toString(), otp_recieved.toString())) {
                    etd_OTP!!.setValue(otp_recieved.toString());
                    etd_OTP!!.setOnKeyListener(null);
                    // on otp recieved is same as otp recieved in api response then pass user to your next activity.
                    var intent: Intent = Intent(myContext, DrawerActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        return view;
    }

    override fun onAttach(context: Context) {
        myContext = activity as FragmentActivity
        // get bundled otp and usernumber from bundlled
        arguments?.getString("OTP")?.let { otpActual = it }
        arguments?.getString("userNumber")?.let { userNumber = it }

        Log.e("otp abu ", " $otpActual")
        super.onAttach(context)
    }

    override fun onStart() {
        // register reciever dynamically
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        myContext!!.registerReceiver(receiver, intentFilter)
        super.onStart()
    }


    override fun onStop() {
        // unregister reciever
        myContext!!.unregisterReceiver(receiver)
        super.onStop()
    }

    fun getSimsRetrievalClient() {
        // Get an instance of SmsRetrieverClient, used to start listening for a matching
        // SMS message
        val client = SmsRetriever.getClient(myContext!!/* context */)
        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        val task = client.startSmsRetriever()
        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener { aVoid: Void? -> }
        task.addOnFailureListener { e: Exception ->
            // Failed to start retriever, inspect Exception for more details
            // Log.e("error", "" + e.message)
        }
    }

    // get otp from message
    fun getOTP(message: String?) { /*
         * Now extract the otp*/
        if (message != null) {
            val p = Pattern.compile("(|^)\\d{4}")
            val m = p.matcher(message)
            if (m.find()) {
                Log.e("otp", "" + m.group(0))
                otp_recieved = m.group(0)
            } else {
                Log.e("wrong ", message.toString())
            }
        }
    }

    private fun compairOtp(actual_api_otp: String, otp_recieved: String): Boolean {
        var isOTPSame = false
        Log.e("otp_recieved", otp_recieved)
        if (actual_api_otp == otp_recieved) { isOTPSame = true }
        return isOTPSame
    }
}