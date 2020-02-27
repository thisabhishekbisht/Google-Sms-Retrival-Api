package abhishek.com.otpretrivalbygoogle.activity


import abhishek.com.otpretrivalbygoogle.R
import abhishek.com.otpretrivalbygoogle.fragment.UserDetails_Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var UserDetails_Fragment: UserDetails_Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Attach User Fragment from main activity
        UserDetails_Fragment = UserDetails_Fragment()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(android.R.id.content, UserDetails_Fragment)
        fragmentTransaction.commit()

    }


    override fun onRestart() {
        super.onRestart()
    }


    override fun onResume() {
        super.onResume()
    }
}
