package abhishek.com.otpretrivalbygoogle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPRequestModel {

    // replace this model with your own model
    @SerializedName("phoneNo")
    @Expose
    private String phoneNumber;

    public OTPRequestModel(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
