package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33})
public class MainActivityTest {

    private MainActivity activity;

    @Before
    public void setUp() {
        if (FirebaseApp.getApps(ApplicationProvider.getApplicationContext()).isEmpty()) {
            FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext(),
                    new FirebaseOptions.Builder().setApplicationId("1:123:android:abc").setProjectId("test").build());
        }
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
    }

    @Test
    public void testLoginButton_OpensLoginActivity() {
        Button btnLogin = activity.findViewById(R.id.button);
        btnLogin.performClick();

        Intent expectedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertNotNull("Debe iniciar una nueva Activity", expectedIntent);
        assertEquals(login_rep.class.getName(), expectedIntent.getComponent().getClassName());
    }
}