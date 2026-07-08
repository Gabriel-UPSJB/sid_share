package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;

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
public class editarGuia_repTest {

    private editarGuia_rep activity;

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

    private Intent buildIntent(int estadoGuia) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), editarGuia_rep.class);
        Bundle bundle = new Bundle();
        bundle.putString("ID", "0001");
        bundle.putInt("Estado", estadoGuia);
        bundle.putString("Nombre_Ususario", "Cliente Test");
        bundle.putInt("DNI_cliente", 12345678);
        bundle.putString("Articulo", "Laptop");
        bundle.putString("Modelo", "XPS");
        bundle.putString("NroSerie", "SN123");
        bundle.putString("Averia", "No enciende");
        bundle.putString("Solucion", "Cambio placa");
        bundle.putString("Nota", "");
        bundle.putString("Telefono", "999999999");
        bundle.putString("Tecnico", "Tecnico 1");
        bundle.putString("FECHA", "10-10-2026");
        bundle.putString("HORA", "10:00:00");
        bundle.putString("tratado", "100");
        bundle.putString("a_cuenta", "50");
        bundle.putString("saldo", "50");
        bundle.putInt("ID_tecnico", 1);
        bundle.putString("PAGO_CERRADO", "0");
        intent.putExtras(bundle);
        return intent;
    }

    @Test
    public void testInit_EstadoMayorA80_DisablesEditing() {
        // Simulamos una guía que ya está en estado finalizado (80 o más)
        Intent intent = buildIntent(85);
        activity = Robolectric.buildActivity(editarGuia_rep.class, intent).create().get();
        activity.db = mockDb;

        Button btnActualizar = activity.findViewById(R.id.button2);
        CheckBox checkWats = activity.findViewById(R.id.checkBox_wats);
        SeekBar estadoReparacion = activity.findViewById(R.id.ESTADO_REAPARACION);

        // Verificamos que los botones de actualización desaparezcan y la barra se bloquee
        assertEquals(View.GONE, btnActualizar.getVisibility());
        assertEquals(View.GONE, checkWats.getVisibility());
        assertEquals(false, estadoReparacion.isEnabled());
    }
}