package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
public class menuprincipal_repTest {

    private menuprincipal_rep activity;

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

    private Intent buildIntent(String userType) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), menuprincipal_rep.class);
        Bundle bundle = new Bundle();
        bundle.putString("user", "1");
        bundle.putString("nombre", "Test");
        bundle.putString("tipo", userType);
        bundle.putString("lista", "");
        bundle.putString("TOKEN_APP", "token");
        intent.putExtras(bundle);
        return intent;
    }

    @Test
    public void testRole_UserType0_HidesManagementButtons() {
        Intent intent = buildIntent("0"); // Rol Usuario
        activity = Robolectric.buildActivity(menuprincipal_rep.class, intent).create().get();
        activity.db = mockDb;

        Button crearGuiaBtn = activity.findViewById(R.id.crear_guia);
        Button gestionarGuiaBtn = activity.findViewById(R.id.modificar_guia);

        // Un usuario normal no debería ver estos botones
        assertEquals(View.GONE, crearGuiaBtn.getVisibility());
        assertEquals(View.GONE, gestionarGuiaBtn.getVisibility());
    }

    @Test
    public void testRole_UserType2_ShowsAllButtons() {
        Intent intent = buildIntent("2"); // Rol Admin
        activity = Robolectric.buildActivity(menuprincipal_rep.class, intent).create().get();
        activity.db = mockDb;

        Button crearGuiaBtn = activity.findViewById(R.id.crear_guia);
        Button gestionarGuiaBtn = activity.findViewById(R.id.modificar_guia);

        // Un admin debe ver todo
        assertEquals(View.VISIBLE, crearGuiaBtn.getVisibility());
        assertEquals(View.VISIBLE, gestionarGuiaBtn.getVisibility());
    }
}