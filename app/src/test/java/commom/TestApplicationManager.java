package commom;

import android.app.Application;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

/**
 * 替代 ApplicationManager
 * Created by ring
 * on 16/7/19.
 */
public class TestApplicationManager extends Application implements TestLifecycleApplication {
    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {

    }

    @Override
    public void afterTest(Method method) {

    }
}
