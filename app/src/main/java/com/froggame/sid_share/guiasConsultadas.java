package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class guiasConsultadas extends AppCompatActivity {
    FirebaseFirestore db;
private String[] BASE_IDS;
    String LISTA_USER;
    Spinner spinner3;
    String  USER_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guias_consultadas);

        spinner3 = findViewById(R.id.spinner3);
        Button Eliminar = findViewById(R.id.Eliminar);
        Button Buscar = findViewById(R.id.Buscar);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        db = FirebaseFirestore.getInstance();
        USER_ID =  extras.getString("user");



        //desmonte...
        List<String> IDS_USUARIO = new ArrayList<>();

        db.collection("Usuarios").document(USER_ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete( Task<DocumentSnapshot>
                                                   task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {
                                LISTA_USER  = document.getString("Lista_IDS");
                                BASE_IDS = LISTA_USER.split("\\|");
                                Log.d(TAG, "Recuperado");

                                for(int i = 0; i< BASE_IDS.length; i++) //entrada...
                                {
                                    IDS_USUARIO.add(BASE_IDS[i]);
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(guiasConsultadas.this, android.R.layout.simple_spinner_item, IDS_USUARIO);

                                spinner3.setAdapter(adapter);

                            }
                        }
                        else
                        {
                            Log.d(TAG, "Error al obtener el documento", task.getException());
                        }
                    }
                });




        Eliminar.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(spinner3.getSelectedItem().toString() == "")
            {
                Toast.makeText(guiasConsultadas.this, "No hay nada que eliminar...", Toast.LENGTH_SHORT).show();
                return;
            }
            new AlertDialog.Builder(guiasConsultadas.this)
                    .setTitle("Eliminar")
                    .setMessage("¿Estás seguro de que deseas Eliminarlo?")
                    .setPositiveButton("Sí", (dialog, which) ->

                            eliminar()
                    )
                    .setNegativeButton("no)", null)
                    .show();

        }

    });
        Buscar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(spinner3.getSelectedItem().toString()))
                {
                    Toast.makeText(guiasConsultadas.this, "Seleccione la ID para buscar...", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docRef = db.collection("Guias").document(String.valueOf(spinner3.getSelectedItem().toString()));
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                        if (document.exists())
                        {
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
                            String TOKEN_APP = document.getString("Token_FCM");

                            Intent intent = new Intent(guiasConsultadas.this, status_product_rep.class);
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
                            bundle.putString("TOKEN_APP", TOKEN_APP);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else
                        {
                            Log.d(TAG, "DOCUMENTO NO ENCONTRADO");
                           // Toast.makeText(guiasConsultadas.this, "El ducmento no ha sido encontrado ...", Toast.LENGTH_SHORT).show();
                        }
                        } else
                        {
                            Log.d(TAG, "Se encontró un error: ", task.getException());
                            Toast.makeText(guiasConsultadas.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    });
                Toast.makeText(guiasConsultadas.this, "El ducmento no ha sido encontrado ...", Toast.LENGTH_SHORT).show();
            }


        });
    }
    void eliminar()
    {

        String BASE = "";
        for(int i = 0; i < BASE_IDS.length; i++) //entrada...
        {
            if(BASE_IDS[i] != spinner3.getSelectedItem().toString()) //reconstruccion.
            {
                if(i + 1 >= BASE_IDS.length)
                {
                    BASE += BASE_IDS[i];
                }
                else
                {
                    BASE += BASE_IDS[i] + "|";
                }
            }
        }
        LISTA_USER = BASE;

        BASE_IDS = LISTA_USER.split("\\|"); //rereconstrucnion.
        List<String> IDS_USUARIO = new ArrayList<>();

        for(int i = 0; i< BASE_IDS.length; i++) //entrada...
            IDS_USUARIO.add(BASE_IDS[i]);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(guiasConsultadas.this, android.R.layout.simple_spinner_item, IDS_USUARIO);
        spinner3.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        db.collection("Usuarios").document(USER_ID)
                .update("Lista_IDS", LISTA_USER);
    }
}