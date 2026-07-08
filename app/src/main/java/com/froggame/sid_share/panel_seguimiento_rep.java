package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.froggame.sid_share.recursos.Guia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class panel_seguimiento_rep extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    boolean IsLocal;
    String USER_ID = "";
    String NOMBRE_USER = "";
    String Tipo_user = "";
    String LISTA_USER = "";
    String Token_APP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_panel_seguimiento_rep);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ProgressDialog dialog = new ProgressDialog(this);

        Button buss;
        TextInputEditText input_id_seguimiento;
        buss = findViewById(R.id.button3);
        input_id_seguimiento = findViewById(R.id.IDseg);

        Button buttonDNI = findViewById(R.id.buttonDNI);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();



        IsLocal = extras.getBoolean("IsLocal");
        Token_APP = extras.getString("Token_APP");

        if(!IsLocal)
        {
            USER_ID = extras.getString("user");
            NOMBRE_USER = extras.getString("nombre");
            Tipo_user = extras.getString("tipo");


            db.collection("Usuarios").document(USER_ID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot>
                                                       task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "Recuperado");
                                    LISTA_USER = document.getString("Lista_IDS");
                                 //   Toast.makeText(panel_seguimiento_rep.this, LISTA_USER, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d(TAG, "Error al obtener el documento", task.getException());
                            }
                        }
                    });
        }
        buttonDNI.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(panel_seguimiento_rep.this, BUSQUEDA_DNI.class);
                startActivity(intent);
            }
        });
        buss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                dialog.setTitle("Buscando");
                dialog.setMessage("Espere por favor...");
                dialog.setCancelable(false);
                dialog.show();

                if(TextUtils.isEmpty(input_id_seguimiento.getText()))
                {
                    Toast.makeText(panel_seguimiento_rep.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                DocumentReference docRef = db.collection("Guias").document(String.valueOf(input_id_seguimiento.getText()));

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {
                                if(!IsLocal)
                                {
                                    db.collection("Usuarios").document(USER_ID)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot>
                                                        task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists())
                                                                {
                                                                    Log.d(TAG, "Recuperado");
                                                                    Toast.makeText(panel_seguimiento_rep.this, LISTA_USER, Toast.LENGTH_SHORT).show();

                                                                    LISTA_USER  = document.getString("Lista_IDS");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        Log.d(TAG, "Error al obtener el documento", task.getException());
                                                    }
                                                }
                                            });

                                    if (!LISTA_USER.equals(""))
                                        LISTA_USER += "|" + input_id_seguimiento.getText().toString();
                                    else
                                        LISTA_USER += input_id_seguimiento.getText().toString();

                                    db.collection("Usuarios").document(USER_ID)
                                            .update("Lista_IDS", LISTA_USER);

                                }
                                dialog.dismiss();

                                // Accede a los datos del documento
                                String ID = document.getString("ID");
                                Long est = document.getLong("Estado");
                                int Estado = est.intValue();
                                String Nombre_Ususario = document.getString("Nombre_cliente");
                                int DNI_cliente = Integer.parseInt(document.getString("DNI_cliente"));
                                String Articulo = document.getString("Articulo");
                                String Modelo = document.getString("Modelo");
                                String NroSerie = document.getString("NroSerie");
                                String Averia = document.getString("Averia");
                                String Solucion = document.getString("Solucion");
                                String Nota = document.getString("Nota");
                                String Tecnico = document.getString("Tecnico");
                                String tratado = document.getString("tratado");
                                String a_cuenta = document.getString("a_cuenta");
                                String saldo = document.getString("saldo");
                                String FECHA = document.getString("FECHA");
                                String HORA = document.getString("HORA");
                                int ID_tecnico = Integer.parseInt(document.getString("ID_tecnico"));
                                String telefono = document.getString("Telefono");
                                String Token_APP_SERVER = document.getString("Token_FCM");
                                String PAGO_CERRADO = document.getString("PAGO_CERRADO");
                                String CodigoAdelanto = document.getString("CodigoAdelanto");
                                String COGIGO_RESTANTE = document.getString("COGIGO_RESTANTE");


                                Intent intent = new Intent(panel_seguimiento_rep.this, status_product_rep.class);
                                Bundle bundle = new Bundle();

                                bundle.putString("ID", ID);
                                bundle.putInt("Estado", Estado);
                                bundle.putString("Nombre_Ususario", Nombre_Ususario);
                                bundle.putInt("DNI_cliente", DNI_cliente);
                                bundle.putString("Articulo", Articulo);
                                bundle.putString("Modelo", Modelo);
                                bundle.putString("NroSerie", NroSerie);
                                bundle.putString("Averia", Averia);
                                bundle.putString("Solucion", Solucion);
                                bundle.putString("Nota", Nota);
                                bundle.putString("Tecnico", Tecnico);
                                bundle.putInt("ID_tecnico", ID_tecnico);
                                bundle.putString("FECHA", FECHA);
                                bundle.putString("tratado", tratado);
                                bundle.putString("a_cuenta", a_cuenta);
                                bundle.putString("saldo", saldo);
                                bundle.putString("Telefono", telefono);
                                bundle.putString("HORA", HORA);
                                bundle.putString("TOKEN_APP", Token_APP);
                                bundle.putString("PAGO_CERRADO",PAGO_CERRADO);
                                bundle.putString("CodigoAdelanto",CodigoAdelanto);
                                bundle.putString("COGIGO_RESTANTE",COGIGO_RESTANTE);
                                bundle.putBoolean("IS_VISUAL_MAP",true);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else
                            {
                                dialog.dismiss();
                                Log.d(TAG, "DOCUMENTO NO ENCONTRADO");
                                Toast.makeText(panel_seguimiento_rep.this, "El ducmento no ha sido encontrado ...", Toast.LENGTH_SHORT).show();
                            }
                        } else
                        {
                            dialog.dismiss();
                            Log.d(TAG, "Se encontró un error: ", task.getException());
                            Toast.makeText(panel_seguimiento_rep.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}