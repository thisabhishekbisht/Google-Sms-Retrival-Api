package abhishek.com.otpretrivalbygoogle.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class D {
// replace this model with your own model
@SerializedName("__type")
@Expose
private String type;
@SerializedName("OTP")
@Expose
private String oTP;
@SerializedName("Response")
@Expose
private Response response;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getOTP() {
return oTP;
}

public void setOTP(String oTP) {
this.oTP = oTP;
}

public Response getResponse() {
return response;
}

public void setResponse(Response response) {
this.response = response;
}

}