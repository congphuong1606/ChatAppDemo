package ominext.android.vn.androidchatexample.AddContact.Event;

/**
 * Created by MyPC on 18/07/2017.
 */

public class AddContactEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
