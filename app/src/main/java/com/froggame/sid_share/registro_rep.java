package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class registro_rep extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Random random = new Random();
    String ID_User;
    String TOKEN_APP = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_rep);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();




        Button REGISTRAR_SESION = findViewById(R.id.registrar);
        Button Iniciarsession = findViewById(R.id.Iniciarsession);

        EditText NOMBRE = findViewById(R.id.name_Text);
        EditText EMAIL = findViewById(R.id.Email_Text);
        EditText DNI = findViewById(R.id.dni);
        EditText CONTRASENA = findViewById(R.id.Contrasena);
        EditText CONF_CONTRASENA = findViewById(R.id.Contrasena_confirmacion);

        REGISTRAR_SESION.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Registro(NOMBRE, EMAIL,DNI,CONTRASENA,CONF_CONTRASENA);
            }
        });

        Iniciarsession.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(registro_rep.this, login_rep.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void Registro(EditText _nombre,EditText _correo,EditText _DNI,EditText _password,EditText _confpass)
    {

        String correo = _correo.getText().toString().trim();
        String password = _password.getText().toString().trim();
        String confirm_password = _confpass.getText().toString().trim();
        String nombre = _nombre.getText().toString().trim();
        String DNI = _DNI.getText().toString().trim();

        //Validamos que el valor correo no este vacio
        if (TextUtils.isEmpty(nombre))
        {
            Toast.makeText(this, "El campo nombre esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(correo))
        {
            Toast.makeText(this, "El campo correo electronico esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(DNI))
        {
            Toast.makeText(this, "El campo DNI esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        //Validamos que el valor password no este vacio porque si xd.
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "El campo contraseña esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirm_password))
        {
            Toast.makeText(this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
            return;
        }

        //Mostramos un ProgresDialog para esperar la rpta del login Firebase
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Registrando");
        dialog.setMessage("Espere por favor...");
        dialog.setCancelable(false);
        dialog.show();
        ID_User = String.valueOf(random.nextInt(999999));
        mAuth.createUserWithEmailAndPassword(correo,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    dialog.dismiss();

                    Toast.makeText(registro_rep.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();

                    Map<String, Object> user = new HashMap<>();
                    user.put("Nombre", nombre);
                    user.put("Gmail", correo);
                    user.put("ID", ID_User);
                    user.put("DNI", DNI);
                    user.put("Lista_IDS","");
                    user.put("user_type","0");

                    db.collection("Usuarios").document(String.valueOf(ID_User))
                            .set(user)
                            .addOnSuccessListener(documentReference ->
                            {
                                Log.d(TAG, "Se anadio usuario");
                                dialog.setMessage("Preparando todo...");

                                Intent intent = new Intent(registro_rep.this, menuprincipal_rep.class);
                                Bundle bundle = new Bundle();


                                bundle.putString("user", ID_User);
                                bundle.putString("nombre", nombre);
                                bundle.putString("tipo", "0");
                                bundle.putString("lista", "");
                                bundle.putString("TOKEN_APP",TOKEN_APP);
                                intent.putExtras(bundle);

                                Toast.makeText(registro_rep.this, nombre , Toast.LENGTH_SHORT).show();
                                Toast.makeText(registro_rep.this, "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure( Exception e)
                                {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                }else
                {
                    dialog.dismiss();
                    Toast.makeText(registro_rep.this, "No se ha podido registrar al usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}