package com.actvity;

import android.content.Intent;

import com.lyc.study.BuildConfig;
import com.lyc.study.R;
import com.lyc.study.go019.GoRobolectricAct;
import com.lyc.study.go019.RobolectricSecondActivity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import commom.TestApplicationManager;

/**
 * Created by lyc on 17/9/7.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,manifest="app/src/main/AndroidManifest.xml",application = TestApplicationManager.class)
public class ActivityTest {
    @Test
    public void testMainActivity() {
        GoRobolectricAct mainActivity = Robolectric.setupActivity(GoRobolectricAct.class);
        mainActivity.findViewById(R.id.textView1).performClick();

        Intent expectedIntent = new Intent(mainActivity, RobolectricSecondActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(mainActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(expectedIntent, actualIntent);
    }
}
