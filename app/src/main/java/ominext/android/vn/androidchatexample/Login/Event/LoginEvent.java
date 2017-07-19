package ominext.android.vn.androidchatexample.Login.Event;

/**
 * Created by MyPC on 18/07/2017.
 */

public class LoginEvent {
   public final static int onSignInSuccess=1;
   public final static int onSignInFail=0;
    public static final int onFailedToRecoverSession=2;
    private int eventType;
    private String errorMesage;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getErrorMesage() {
        return errorMesage;
    }

    public void setErrorMesage(String errorMesage) {
        this.errorMesage = errorMesage;
    }
}
