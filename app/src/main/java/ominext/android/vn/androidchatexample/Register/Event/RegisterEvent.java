package ominext.android.vn.androidchatexample.Register.Event;

/**
 * Created by MyPC on 18/07/2017.
 */

public class RegisterEvent {
    public final static int onSignUpSuccess = 1;
    public final static int onSignUpError = 0;
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
