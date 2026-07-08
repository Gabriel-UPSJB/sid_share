package com.froggame.sid_share;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GESTOR_PAGOS extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestor_pagos);
        EditText cogido_restante_value = findViewById(R.id.cogido_restante_value);
        Button Finalizar_Gestor_pagos_button = findViewById(R.id.Finalizar_Gestor_pagos_button);
        TextView cogido_Acuenta_value = findViewById(R.id.COMPROBANTE_ACUENTA);
        TextView SSMonto_pagado_value = findViewById(R.id.Monto_pagado_value);

        TextView Comentario = findViewById(R.id.text_re);
        TextView Monto_restante_value = findViewById(R.id.Monto_restante_value);
        Spinner TIPO_COMPROBANTE = findViewById(R.id.TIPO_COMPROBANTE);



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        List<String> TIPOS_COMPROBANTES = new ArrayList<>();
        TIPOS_COMPROBANTES.add("NV");
        TIPOS_COMPROBANTES.add("BD");
        TIPOS_COMPROBANTES.add("FC");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GESTOR_PAGOS.this, android.R.layout.simple_spinner_item, TIPOS_COMPROBANTES);
        TIPO_COMPROBANTE.setAdapter(adapter);

        String MontoPagado_Adelanto = extras.getString("MontoPagado_Adelanto"); //--
        String CodigoAdelanto = extras.getString("CodigoAdelanto"); //--

        String MONTO_TOTAL = extras.getString("MONTO_TOTAL"); //--

        String ID = extras.getString("ID"); //--
        String Monto_a_pagar = extras.getString("Monto_a_pagar"); //--
        int tipo_Comprobante = extras.getInt("tipo_Comprobante"); //--
        String COGIGO_RESTANTE = extras.getString("COGIGO_RESTANTE"); //--
        String PAGOS_CERRADO = extras.getString("PAGOS_CERRADO"); //--
        boolean IS_VISUAL_MAP = extras.getBoolean("IS_VISUAL_MAP");//--

        SSMonto_pagado_value.setText(MontoPagado_Adelanto);
        cogido_Acuenta_value.setText(CodigoAdelanto);

        Monto_restante_value.setText(Monto_a_pagar);
        cogido_restante_value.setText(COGIGO_RESTANTE);
        TIPO_COMPROBANTE.setSelection(tipo_Comprobante);

        if (IS_VISUAL_MAP == true) {
            Finalizar_Gestor_pagos_button.setVisibility(View.GONE);
            TIPO_COMPROBANTE.setVisibility(View.GONE);
            cogido_restante_value.setVisibility(View.GONE);
            Monto_restante_value.setVisibility(View.GONE);
            Comentario.setVisibility(View.GONE);
            SSMonto_pagado_value.setVisibility(View.GONE);
            cogido_Acuenta_value.setVisibility(View.GONE);
        }

        if (Integer.parseInt(MontoPagado_Adelanto) == Integer.parseInt(MONTO_TOTAL)) {
            TextView TEXTO_TOTAL = findViewById(R.id.pago_total);
            TEXTO_TOTAL.setText("Codigo del comprobanta del pago total: " + CodigoAdelanto);

            new AlertDialog.Builder(GESTOR_PAGOS.this)
                    .setTitle("GESTOR CERRADO")
                    .setMessage("YA SE REALIZÓ EL PAGO TOTAL DEL SERVICIO...")
                    .show();

            Finalizar_Gestor_pagos_button.setVisibility(View.GONE);
            TIPO_COMPROBANTE.setEnabled(false);
            cogido_restante_value.setEnabled(false);
            Monto_restante_value.setEnabled(false);
            Comentario.setEnabled(false);
            SSMonto_pagado_value.setEnabled(false);
            cogido_Acuenta_value.setEnabled(false);
        } else {
            TextView TEXTO_TOTAL = findViewById(R.id.pago_total);
            TEXTO_TOTAL.setText("gestor de pago (beta 1.0)" );

            if (PAGOS_CERRADO == "1") {
                Finalizar_Gestor_pagos_button.setVisibility(View.GONE);
                TIPO_COMPROBANTE.setEnabled(false);
                cogido_restante_value.setEnabled(false);
                Monto_restante_value.setEnabled(false);
                Comentario.setEnabled(false);
                SSMonto_pagado_value.setEnabled(false);
                cogido_Acuenta_value.setEnabled(false);

                new AlertDialog.Builder(GESTOR_PAGOS.this)
                        .setTitle("GESTOR CERRADO")
                        .setMessage("YA SE A CERRADO EL GESTOR DE PAGOS Y NO SE PUEDE MODIFICAR...")
                        .show();
            }

        }
        Finalizar_Gestor_pagos_button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                if(TextUtils.isEmpty(cogido_restante_value.getText()))
                {
                    new AlertDialog.Builder(GESTOR_PAGOS.this)
                            .setTitle("COLOQUE EL CODIGO")
                            .setMessage("Para finalizar el gestor de pagos, debe de ingresar el codigo del PAGO TOTAL...")
                            .show();
                    return;
                }
                DocumentReference documentReference = db.collection("Guias").document(ID);

                // Mapa de datos para actualizar
                Map<String, Object> data = new HashMap<>();
                data.put("COGIGO_RESTANTE", TIPO_COMPROBANTE.getSelectedItem().toString() + cogido_restante_value.getText()); // Reemplaza con tu campo y nuevo valor
                data.put("PAGOS_CERRADO", "1");
                // Actualizar el documento
                documentReference.update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Firebase", "Documento actualizado correctamente");
                                Toast.makeText(GESTOR_PAGOS.this, "Actualizado...", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {
                                Log.w("Firebase", "Error al actualizar el documento", e);
                            }
                        });
            }

        });


    }
}