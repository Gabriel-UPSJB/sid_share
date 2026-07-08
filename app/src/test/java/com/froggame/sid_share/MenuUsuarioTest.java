package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
public class MenuUsuarioTest {

    private MenuUsuario activity;

    @Mock
    private FirebaseFirestore mockDb;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        if (FirebaseApp.getApps(ApplicationProvider.getApplicationContext()).isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApplicationId("1:1234567890:android:testing")
                    .setProjectId("sid-share-test")
                    .build();
            FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext(), options);
        }
    }

    private Intent buildMockIntent(String userId, String userType) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MenuUsuario.class);
        Bundle bundle = new Bundle();
        bundle.putString("user", userId);
        bundle.putString("nombre", "Usuario Prueba");
        bundle.putString("tipo", userType);
        bundle.putString("lista", "123|456");
        intent.putExtras(bundle);
        return intent;
    }

    @Test
    public void testInit_UserType0_HidesAdminControls() {
        // Tipo 0 = Usuario normal
        Intent intent = buildMockIntent("USR-1", "0");
        activity = Robolectric.buildActivity(MenuUsuario.class, intent).create().get();
        activity.db = mockDb;

        TextView tipoTxt = activity.findViewById(R.id.textView6);
        Spinner spinnerRoles = activity.findViewById(R.id.spinner2);
        Button btnActualizar = activity.findViewById(R.id.button4);

        assertEquals("Usuario", tipoTxt.getText().toString());
        assertEquals(View.GONE, spinnerRoles.getVisibility());
        assertEquals(View.GONE, btnActualizar.getVisibility());
    }

    @Test
    public void testInit_UserType2_ShowsAdminControls() {
        // Tipo 2 = Administrador
        Intent intent = buildMockIntent("ADM-1", "2");
        activity = Robolectric.buildActivity(MenuUsuario.class, intent).create().get();
        activity.db = mockDb;

        TextView tipoTxt = activity.findViewById(R.id.textView6);
        Spinner spinnerRoles = activity.findViewById(R.id.spinner2);
        Button btnActualizar = activity.findViewById(R.id.button4);

        assertEquals("Administrador (CONTROL TOTAL)", tipoTxt.getText().toString());
        assertEquals(View.VISIBLE, spinnerRoles.getVisibility());
        assertEquals(View.VISIBLE, btnActualizar.getVisibility());
    }
}