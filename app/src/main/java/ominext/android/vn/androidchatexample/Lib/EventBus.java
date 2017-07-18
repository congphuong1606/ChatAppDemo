package ominext.android.vn.androidchatexample.Lib;

/**
 * Created by MyPC on 18/07/2017.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
