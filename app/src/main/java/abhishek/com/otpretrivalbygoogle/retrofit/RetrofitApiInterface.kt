package abhishek.com.otpretrivalbygoogle.retrofit

import abhishek.com.otpretrivalbygoogle.model.D
import abhishek.com.otpretrivalbygoogle.model.OTPRequestModel
import abhishek.com.otpretrivalbygoogle.model.OTPResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApiInterface {

    @POST("/Your URL End Point")
    fun CustomerLoginRequest(@Body otpRequestModel: OTPRequestModel?): Call<OTPResponseModel?>?
}