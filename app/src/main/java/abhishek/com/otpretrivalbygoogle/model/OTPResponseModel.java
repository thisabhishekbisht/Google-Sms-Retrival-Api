package abhishek.com.otpretrivalbygoogle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPResponseModel {
    // replace this model with your own model
    @SerializedName("d")
    @Expose
    private D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

}