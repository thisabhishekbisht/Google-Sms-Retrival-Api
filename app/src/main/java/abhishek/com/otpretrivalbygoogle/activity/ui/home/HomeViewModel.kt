package abhishek.com.otpretrivalbygoogle.activity.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value =
            "Congratulations ! You have sucessfully integrated Google OTP Retrival API .Cheers !"
    }
    
    val text: LiveData<String> = _text
}