package ominext.android.vn.androidchatexample.Lib;

import ominext.android.vn.androidchatexample.Register.Event.RegisterEvent;

/**
 * Created by MyPC on 18/07/2017.
 */

public class Event {
    public void postEvent(int type) {
        postEvent(type, null);
    }
    public void postEvent(int type, String errorMessage) {
        RegisterEvent event = new RegisterEvent();
        event.setEventType(type);
        if (errorMessage != null) {
            event.setErrorMesage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(event);
    }
}
