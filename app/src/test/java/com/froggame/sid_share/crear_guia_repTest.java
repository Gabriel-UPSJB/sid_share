package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33})
public class crear_guia_repTest {

    private crear_guia_rep activity;

    @Mock
    private FirebaseFirestore mockDb;
    @Mock
    private DocumentReference mockDocRef;
    @Mock
    private Task<Void> mockSetTask;

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

        activity = Robolectric.buildActivity(crear_guia_rep.class).create().get();
        activity.db = mockDb; // Inyectar DB simulada
    }

    @Test
    public void testSeekBarLogic_ChangesStateVisibility() {
        SeekBar estadoReparacion = activity.findViewById(R.id.ESTADO_REAPARACION);
        TextView estado1 = activity.findViewById(R.id.Estado1);
        TextView estado2 = activity.findViewById(R.id.Estado2);
        TextView estado3 = activity.findViewById(R.id.Estado3);

        // Simular progreso menor a 24 (Estado 1 visible)
        estadoReparacion.setProgress(15);
        assertEquals(View.VISIBLE, estado1.getVisibility());
        assertEquals(View.GONE, estado2.getVisibility());

        // Simular progreso entre 25 y 39 (Estado 2 visible)
        estadoReparacion.setProgress(30);
        assertEquals(View.GONE, estado1.getVisibility());
        assertEquals(View.VISIBLE, estado2.getVisibility());

        // Simular progreso entre 40 y 79 (Estado 3 visible)
        estadoReparacion.setProgress(50);
        assertEquals(View.GONE, estado2.getVisibility());
        assertEquals(View.VISIBLE, estado3.getVisibility());
    }

    @Test
    public void testA_Cuenta_MathLogic_UpdatesSaldo() {
        android.widget.EditText tratado = activity.findViewById(R.id.tratado);
        android.widget.EditText a_cuenta = activity.findViewById(R.id.a_cuenta);
        TextView saldo = activity.findViewById(R.id.saldo);

        // Configurar valores
        tratado.setText("150");
        a_cuenta.setText("50");

        // El TextWatcher debería calcular 150 - 50 = 100
        assertEquals("100", saldo.getText().toString());
    }
}