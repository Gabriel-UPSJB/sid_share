package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuUsuario extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_usuario);

        ProgressDialog dialog = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        TextView nombre_user = findViewById(R.id.textView5);
        TextView tipo = findViewById(R.id.textView6);
        TextView texto1 = findViewById(R.id.textView234324);
        TextView texto2 = findViewById(R.id.textView7);
        TextView idd = findViewById(R.id.idd);
        String USER_ID;
        String NOMBRE_USER;
        String Tipo_user;
        String LISTA_USER;
        Button button4 = findViewById(R.id.button4);
        TextInputEditText user_id = findViewById(R.id.id_user);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Spinner spinner2 = findViewById(R.id.spinner2);
        USER_ID =  extras.getString("user");
        NOMBRE_USER = extras.getString("nombre");
        Tipo_user = extras.getString("tipo");
        LISTA_USER = extras.getString("lista");

        idd.setText( "ID: " + USER_ID);



        if(Integer.parseInt(Tipo_user) == 0)
        {
            tipo.setText("Usuario");
            spinner2.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);
            user_id.setVisibility(View.GONE);
            user_id.setVisibility(View.GONE);
            texto1.setVisibility(View.GONE);
            texto2.setVisibility(View.GONE);
        }
        else if(Integer.parseInt(Tipo_user) == 1)
        {
            tipo.setText("Tecnico");
            spinner2.setVisibility(View.GONE);
            button4.setVisibility(View.GONE);
            user_id.setVisibility(View.GONE);
            user_id.setVisibility(View.GONE);
            texto1.setVisibility(View.GONE);
            texto2.setVisibility(View.GONE);
        }
        else
        {
            tipo.setText("Administrador (CONTROL TOTAL)");
            spinner2.setVisibility(View.VISIBLE);
            button4.setVisibility(View.VISIBLE);
            user_id.setVisibility(View.VISIBLE);
            user_id.setVisibility(View.VISIBLE);
            texto1.setVisibility(View.VISIBLE);
            texto2.setVisibility(View.VISIBLE);
        }

        List<String> USUARIOS_TIPOS = new ArrayList<>();

        USUARIOS_TIPOS.add("Usuario");
        USUARIOS_TIPOS.add("Tecnico");
        USUARIOS_TIPOS.add("Administrador");

        nombre_user.setText(NOMBRE_USER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuUsuario.this, android.R.layout.simple_spinner_item, USUARIOS_TIPOS);
        spinner2.setAdapter(adapter);
        button4.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                if(TextUtils.isEmpty(user_id.getText()))
                {
                    Toast.makeText(MenuUsuario.this, "INGRESA LA ID PUES PAPI...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(USER_ID.equals(user_id.getText().toString()))
                {
                    Toast.makeText(MenuUsuario.this, "solo otro administrador puede hacer eso...", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setTitle("Buscando");
                dialog.setMessage("Espere por favor...");
                dialog.setCancelable(false);
                dialog.show();
                DocumentReference docRef = db.collection("Usuarios").document(String.valueOf(user_id.getText()));

                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists())
                            {

                                String DNI = document.getString("DNI");
                                String Gmail = document.getString("Gmail");
                                String ID = document.getString("ID");
                                String Lista_IDS = document.getString("Lista_IDS");
                                String Nombre = document.getString("Nombre");
                                String user_type = document.getString("user_type");

                                dialog.setMessage("Añadiendo rol a " + Nombre + "...");
                                Map<String, Object> user = new HashMap<>();
                                user.put("DNI", DNI);
                                user.put("Gmail", Gmail); // XD...
                                user.put("ID", ID);
                                user.put("Lista_IDS", Lista_IDS);
                                user.put("Nombre", String.valueOf(Nombre));

                                if(spinner2.getSelectedItem().toString() == "Usuario")
                                    user.put("user_type","0");
                                 else if(spinner2.getSelectedItem().toString() == "Tecnico")
                                    user.put("user_type", "1");
                                else
                                    user.put("user_type", "2");


                                db.collection("Usuarios").document(String.valueOf(ID))
                                        .set(user)
                                        .addOnSuccessListener(documentReference ->
                                        {
                                            Toast.makeText(MenuUsuario.this, "Se anadio el rol exitosamente a " + Nombre, Toast.LENGTH_SHORT).show();
                                            finish();
                                            dialog.dismiss();
                                        })
                                        .addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {
                                                Log.w(TAG, "Error AL AÑADIR ATRIBUTOS AL USUARIO...", e);
                                                dialog.dismiss();
                                            }
                                        });
                            } else
                            {
                                dialog.dismiss();
                                Log.d(TAG, "DOCUMENTO NO ENCONTRADO");
                                Toast.makeText(MenuUsuario.this, "El usuario no ha sido encontrado ...", Toast.LENGTH_SHORT).show();
                            }
                        } else
                        {
                            dialog.dismiss();
                            Log.d(TAG, "Se encontró un error: ", task.getException());
                            Toast.makeText(MenuUsuario.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}