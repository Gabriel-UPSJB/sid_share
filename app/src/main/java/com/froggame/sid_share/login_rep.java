package com.froggame.sid_share;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class login_rep extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    String USER_ID;
    String NOMBRE_USER;
    String Tipo_user;
    String LISTA_USER;
    String TOKEN_APP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_rep);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        TOKEN_APP = extras.getString("TOKEN_APP");


        EditText editTextTextPassword2 = findViewById(R.id.editTextTextPassword2); //CORREO...
        EditText editTextTextPassword = findViewById(R.id.editTextTextPassword); // CONTRASENA...

        Button iniciar_session =  findViewById(R.id.iniciar_session);
        Button registrarse =  findViewById(R.id.button3);

        iniciar_session.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String correo = editTextTextPassword2.getText().toString().trim();
                String password = editTextTextPassword.getText().toString().trim();
                IniciarSesion(correo, password);

            }
        });

        registrarse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(login_rep.this, registro_rep.class);
                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("TOKEN_APP",TOKEN_APP);
                intent.putExtras(bundle);
                finish();
            }
        });
    }

    private void IniciarSesion(String txt_correo, String txt_password)
    {
        String correo = txt_correo;
        String password = txt_password;

        //Validamos que el valor correo no este vacio
        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "El campo correo electronico esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validamos que el valor password no este vacio
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "El campo contraseña esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }

        //Mostramos un ProgresDialog para esperar la rpta del login Firebase
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Autentificando");
        dialog.setMessage("Espere por favor...");
        dialog.setCancelable(false);
        dialog.show();

        //Invocamos al metodo de autentificacion del servicio Firebase "Authentification"
        mAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    db.collection("Usuarios")
                            .whereEqualTo("Gmail", correo)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        for (QueryDocumentSnapshot document : task.getResult())
                                        {

                                            dialog.dismiss();
                                            Intent intent = new Intent(login_rep.this, menuprincipal_rep.class);
                                            Bundle bundle = new Bundle();

                                             USER_ID = document.getString("ID");
                                             NOMBRE_USER = document.getString("Nombre");
                                             Tipo_user = document.getString("user_type");
                                             LISTA_USER = document.getString("Lista_IDS");

                                            bundle.putString("user", USER_ID);
                                            bundle.putString("nombre", NOMBRE_USER);
                                            bundle.putString("tipo", Tipo_user);
                                            bundle.putString("lista", LISTA_USER);
                                            bundle.putString("TOKEN_APP",TOKEN_APP);
                                            intent.putExtras(bundle);

                                            Toast.makeText(login_rep.this, NOMBRE_USER , Toast.LENGTH_SHORT).show();
                                            Toast.makeText(login_rep.this, "Has iniciado sesion correctamente", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else
                                    {
                                        dialog.dismiss();
                                        Log.w("TAG", "Error al obtener la data...", task.getException());
                                    }
                                }
                            });

                }else
                {
                    dialog.dismiss();
                    Toast.makeText(login_rep.this, "El usuario y/o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}