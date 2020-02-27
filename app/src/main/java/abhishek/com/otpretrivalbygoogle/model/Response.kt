package abhishek.com.otpretrivalbygoogle.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Response {
    // replace this model with your own model
    @SerializedName("__type")
    @Expose
    var type: String? = null
    @SerializedName("Message")
    @Expose
    var message: String? = null
    @SerializedName("Status")
    @Expose
    var status: Int? = null

}