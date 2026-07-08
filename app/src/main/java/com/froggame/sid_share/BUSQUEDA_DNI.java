package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BUSQUEDA_DNI extends AppCompatActivity
{

    List<String> Descripcion = new ArrayList<>();
    List<String> IDS = new ArrayList<>();

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busqueda_dni);

        db = FirebaseFirestore.getInstance();

        Button Bsucar = findViewById(R.id.button3);
        Button BuscarSelec = findViewById(R.id.Buscar);
        TextInputEditText IDseg = findViewById(R.id.idseg);
        Spinner spinner3 = findViewById(R.id.spinner3);

        ProgressDialog dialog = new ProgressDialog(this);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BUSQUEDA_DNI.this, android.R.layout.simple_spinner_item, Descripcion);

        Bsucar.setOnClickListener(new View.OnClickListener()
        {
            @Override

            public void onClick(View view) {

                {
                    if (TextUtils.isEmpty(IDseg.getText().toString()))
                    {
                        Toast.makeText(BUSQUEDA_DNI.this, "INGRESE EL DNI...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog.setTitle("Buscando");
                    dialog.setMessage("Espere por favor...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Descripcion.clear();
                    IDS.clear();

                    db.collection("Guias")
                            .whereEqualTo("DNI_cliente", IDseg.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String Modelo = document.getString("Modelo");
                                            String FECHA = document.getString("FECHA");
                                            String ID = document.getString("ID");

                                            Descripcion.add(Modelo + "|" + FECHA);
                                            IDS.add(ID);
                                        }
                                        dialog.dismiss();
                                        spinner3.setAdapter(adapter);
                                    } else {
                                        Exception exception = task.getException();
                                        Log.e("FirestoreError", "Error al obtener documentos: " + exception.getMessage(), exception);
                                        // Mostrar un mensaje de error al usuario o tomar alguna otra acción
                                        Toast.makeText(BUSQUEDA_DNI.this, "Ocurrió un error al cargar los datos" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });
        BuscarSelec.setOnClickListener(new View.OnClickListener()
        {
            @Override

            public void onClick(View view)
            {
                if (spinner3.getSelectedItem() == null)
                {
                    Toast.makeText(BUSQUEDA_DNI.this, "Seleccione la guia a editar...", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setTitle("Buscando");
                dialog.setMessage("Espere por favor...");
                dialog.setCancelable(false);
                dialog.show();


                DocumentReference docRef = db.collection("Guias").document(IDS.get(spinner3.getSelectedItemPosition()));

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
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
                                String TOKEN_APP = document.getString("Token_FCM");
                                String PAGO_CERRADO = document.getString("PAGO_CERRADO");
                                String CodigoAdelanto = document.getString("CodigoAdelanto");
                                String COGIGO_RESTANTE = document.getString("COGIGO_RESTANTE");

                                Intent intent = new Intent(BUSQUEDA_DNI.this, status_product_rep.class);
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
                                bundle.putString("PAGO_CERRADO",PAGO_CERRADO);
                                bundle.putString("CodigoAdelanto",CodigoAdelanto);
                                bundle.putString("COGIGO_RESTANTE",COGIGO_RESTANTE);
                                bundle.putBoolean("IS_VISUAL_MAP",true);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Log.d(TAG, "DOCUMENTO NO ENCONTRADO");
                                Toast.makeText(BUSQUEDA_DNI.this, "El ducmento no ha sido encontrado ó se a anulado...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                            Log.d(TAG, "Se encontró un error: ", task.getException());
                            Toast.makeText(BUSQUEDA_DNI.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
