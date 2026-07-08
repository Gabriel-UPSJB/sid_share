package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33})
public class guiasConsultadasTest {

    private guiasConsultadas activity;

    @Mock
    private FirebaseFirestore mockDb;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        if (FirebaseApp.getApps(ApplicationProvider.getApplicationContext()).isEmpty()) {
            FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext(),
                    new FirebaseOptions.Builder().setApplicationId("1:123:android:abc").setProjectId("test").build());
        }
    }

    @Test
    public void testInit_ExtractsUserIdFromIntent() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), guiasConsultadas.class);
        Bundle bundle = new Bundle();
        bundle.putString("user", "USER-404");
        intent.putExtras(bundle);

        activity = Robolectric.buildActivity(guiasConsultadas.class, intent).create().get();
        activity.db = mockDb;

        assertEquals("USER-404", activity.USER_ID);
    }
}