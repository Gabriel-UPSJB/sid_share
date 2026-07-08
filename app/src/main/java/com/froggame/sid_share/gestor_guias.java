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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class gestor_guias extends AppCompatActivity {
    FirebaseFirestore db;
    EditText editTextText;
    Spinner spinner_pendientes;
    Spinner spinner;
    Button editar_guia_central;
    Button buscar;
    Button editar_guia_base;
    Button editar_guia;

    CheckBox checkBox_mi_nombre;
    CheckBox checkBox_finalizadas;

    String USER_ID;
    String NOMBRE_USER;
    String Tipo_user;
    String LISTA_USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestor_guias);
        spinner = findViewById(R.id.spinner);
  //      spinner_pendientes = findViewById(R.id.spinner_pendientes);
        editar_guia_central = findViewById(R.id.editar_guia_central);
        editar_guia_base = findViewById(R.id.editar_guia_base);
      //  editar_guia = findViewById(R.id.editar_guia);
        checkBox_mi_nombre = findViewById(R.id.checkBox_mi_nombre);
        checkBox_finalizadas = findViewById(R.id.checkBox_finalizadas);
        editTextText = findViewById(R.id.editTextText);
        buscar = findViewById(R.id.buscar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        USER_ID = extras.getString("user");
        NOMBRE_USER = extras.getString("nombre");
        Tipo_user = extras.getString("tipo");
        LISTA_USER = extras.getString("lista");

        editar_guia_central.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.setTitle("Buscando");
                dialog.setMessage("Espere por favor...");
                dialog.setCancelable(false);
                dialog.show();

                if (TextUtils.isEmpty(editTextText.getText())) {
                    Toast.makeText(gestor_guias.this, "El campo esta vacio", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                DocumentReference docRef = db.collection("Guias").document(String.valueOf(editTextText.getText()));

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
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
                                String TOKEN_APP_SERVER = document.getString("Token_FCM");
                                String PAGO_CERRADO = document.getString("PAGOS_CERRADO");
                                String CodigoAdelanto = document.getString("CodigoAdelanto");
                                String COGIGO_RESTANTE = document.getString("COGIGO_RESTANTE");

                                Intent intent = new Intent(gestor_guias.this, editarGuia_rep.class);
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
                                bundle.putString("TOKEN_APP_SERVER", TOKEN_APP_SERVER);
                                bundle.putString("PAGO_CERRADO",PAGO_CERRADO);
                                bundle.putString("CodigoAdelanto",CodigoAdelanto);
                                bundle.putString("COGIGO_RESTANTE",COGIGO_RESTANTE);
                                bundle.putBoolean("IS_VISUAL_MAP",false);

                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Log.d(TAG, "DOCUMENTO NO ENCONTRADO");
                                Toast.makeText(gestor_guias.this, "El ducmento no ha sido encontrado ó se a anulado...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                            Log.d(TAG, "Se encontró un error: ", task.getException());
                            Toast.makeText(gestor_guias.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });

        List<String> Descripcion = new ArrayList<>();
        List<String> IDS = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(gestor_guias.this, android.R.layout.simple_spinner_item, Descripcion);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Descripcion.clear();
                IDS.clear();
                Toast.makeText(gestor_guias.this, USER_ID, Toast.LENGTH_SHORT).show();

                if (checkBox_mi_nombre.isChecked() && !checkBox_finalizadas.isChecked()) {
                    dialog.setTitle("Buscando");
                    dialog.setMessage("Espere por favor...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Descripcion.clear();
                    IDS.clear();

                    db.collection("Guias")
                            .whereEqualTo("ID_tecnico", USER_ID.toString())
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
                                        spinner.setAdapter(adapter);
                                    } else {
                                        dialog.dismiss();
                                        Log.w("TAG", "Error al obtener los tecnicos para seleccionar...", task.getException());
                                    }
                                }
                            });
                } else if (!checkBox_mi_nombre.isChecked() && checkBox_finalizadas.isChecked()) {
                    dialog.setTitle("Buscando");
                    dialog.setMessage("Espere por favor...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Descripcion.clear();
                    IDS.clear();

                    db.collection("Guias")
                            .whereLessThan("Estado", 80)
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
                                        spinner.setAdapter(adapter);
                                    } else {
                                        dialog.dismiss();
                                        Log.w("TAG", "fghdfg...", task.getException());
                                    }
                                }
                            });
                } else if (!checkBox_mi_nombre.isChecked() && !checkBox_finalizadas.isChecked()) {
                    dialog.setTitle("Buscando");
                    dialog.setMessage("Espere por favor...");
                    dialog.setCancelable(false);
                    dialog.show();


                    db.collection("Guias").get()
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
                                        spinner.setAdapter(adapter);
                                    } else {
                                        dialog.dismiss();
                                        Log.w("TAG", "Edfedf...", task.getException());
                                    }
                                }
                            });
                } else {
                    dialog.setTitle("Buscando");
                    dialog.setMessage("Espere por favor...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Descripcion.clear();
                    IDS.clear();

                    db.collection("Guias")
                            .whereEqualTo("ID_tecnico", USER_ID).whereLessThan("Estado", 80)
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
                                        spinner.setAdapter(adapter);
                                    } else {
                                        Exception exception = task.getException();
                                        Log.e("FirestoreError", "Error al obtener documentos: " + exception.getMessage(), exception);
                                        // Mostrar un mensaje de error al usuario o tomar alguna otra acción
                                        Toast.makeText(gestor_guias.this, "Ocurrió un error al cargar los datos" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });

        editar_guia_base.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


                if (spinner.getSelectedItem() == null)
                {
                    Toast.makeText(gestor_guias.this, "Seleccione la guia a editar...", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setTitle("Buscando");
                dialog.setMessage("Espere por favor...");
                dialog.setCancelable(false);
                dialog.show();


                DocumentReference docRef = db.collection("Guias").document(IDS.get(spinner.getSelectedItemPosition()));

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
                                String TOKEN_APP_SERVER = document.getString("Token_FCM");
                                String PAGO_CERRADO = document.getString("PAGOS_CERRADO");
                                String CodigoAdelanto = document.getString("CodigoAdelanto");
                                String COGIGO_RESTANTE = document.getString("COGIGO_RESTANTE");

                                Intent intent = new Intent(gestor_guias.this, editarGuia_rep.class);
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
                                bundle.putString("TOKEN_APP_SERVER", TOKEN_APP_SERVER);
                                bundle.putString("PAGO_CERRADO",PAGO_CERRADO);
                                bundle.putString("CodigoAdelanto",CodigoAdelanto);
                                bundle.putString("COGIGO_RESTANTE",COGIGO_RESTANTE);
                                bundle.putBoolean("IS_VISUAL_MAP",false);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                Log.d(TAG, "DOCUMENTO NO ENCONTRADO");
                                Toast.makeText(gestor_guias.this, "El ducmento no ha sido encontrado ó se a anulado...", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dialog.dismiss();
                            Log.d(TAG, "Se encontró un error: ", task.getException());
                            Toast.makeText(gestor_guias.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}