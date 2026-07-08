package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
public class GESTOR_PAGOSTest {

    private GESTOR_PAGOS activity;

    @Mock
    private FirebaseFirestore mockDb;
    @Mock
    private DocumentReference mockDocRef;
    @Mock
    private Task<Void> mockUpdateTask;

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

        // Mock de base de datos
        when(mockDb.collection("Guias")).thenReturn(mock(com.google.firebase.firestore.CollectionReference.class));
    }

    private Intent buildMockIntent(boolean isVisualMap, String montoPagado, String montoTotal, String pagosCerrado) {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GESTOR_PAGOS.class);
        Bundle bundle = new Bundle();
        bundle.putString("ID", "999");
        bundle.putString("MontoPagado_Adelanto", montoPagado);
        bundle.putString("MONTO_TOTAL", montoTotal);
        bundle.putString("Monto_a_pagar", "50");
        bundle.putInt("tipo_Comprobante", 0);
        bundle.putString("COGIGO_RESTANTE", "");
        bundle.putString("PAGOS_CERRADO", pagosCerrado);
        bundle.putBoolean("IS_VISUAL_MAP", isVisualMap);
        bundle.putString("CodigoAdelanto", "ADEL-123");
        intent.putExtras(bundle);
        return intent;
    }

    @Test
    public void testInit_VisualMapTrue_HidesUIElements() {
        Intent intent = buildMockIntent(true, "50", "100", "0");
        activity = Robolectric.buildActivity(GESTOR_PAGOS.class, intent).create().get();
        activity.db = mockDb;

        Button finalizarBtn = activity.findViewById(R.id.Finalizar_Gestor_pagos_button);
        TextView montoRestante = activity.findViewById(R.id.Monto_restante_value);

        // Si es visual map, estos elementos deben estar ocultos (GONE)
        assertEquals(View.GONE, finalizarBtn.getVisibility());
        assertEquals(View.GONE, montoRestante.getVisibility());
    }

    @Test
    public void testInit_PagoTotalCompletado_DisablesInputs() {
        // Simular que el adelanto es igual al total (pago completado)
        Intent intent = buildMockIntent(false, "100", "100", "0");
        activity = Robolectric.buildActivity(GESTOR_PAGOS.class, intent).create().get();
        activity.db = mockDb;

        Button finalizarBtn = activity.findViewById(R.id.Finalizar_Gestor_pagos_button);
        android.widget.Spinner tipoComprobante = activity.findViewById(R.id.TIPO_COMPROBANTE);

        // El botón debe desaparecer y el spinner deshabilitarse
        assertEquals(View.GONE, finalizarBtn.getVisibility());
        assertEquals(false, tipoComprobante.isEnabled());
    }
}