package com.froggame.sid_share;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class menuprincipal_rep extends AppCompatActivity {
    FirebaseFirestore db;

    TextView SALUDO;
    String USER_ID;
    String NOMBRE_USER;
    String Tipo_user;
    String LISTA_USER;

    Button CREARGUIA;
    Button GUIASCONSULTADAS;
    Button GESTION;
    Button REVICION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menuprincipal_rep);
        SALUDO = findViewById(R.id.textView2DD);

        Button buscar_guia = findViewById(R.id.buscar_guia);
        Button crear_guia = findViewById(R.id.crear_guia);

        CREARGUIA = crear_guia;
        Button guias_consultada = findViewById(R.id.guias_consultada);
        GUIASCONSULTADAS = guias_consultada;
        Button modificar_guia = findViewById(R.id.modificar_guia);
        GESTION = modificar_guia;
        Button CUENTA = findViewById(R.id.revicion_guias);
        REVICION = CUENTA;
        Button cerrar_session = findViewById(R.id.cerrar_session);
        TextView textView211 = findViewById(R.id.textView211);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String TOKEN_APP;
        USER_ID =  extras.getString("user");
        NOMBRE_USER = extras.getString("nombre");
        Tipo_user = extras.getString("tipo");
        LISTA_USER = extras.getString("lista");
        TOKEN_APP = extras.getString("TOKEN_APP");
        switch (Tipo_user)
            {
                case "0" :
                {
                    CREARGUIA.setVisibility(View.GONE);
                    GESTION.setVisibility(View.GONE);
                    REVICION.setVisibility(View.VISIBLE);
                    GESTION.setVisibility(View.GONE);
                    textView211.setText("");
                    break;
                }
                case  "1":
                {
                    CREARGUIA.setVisibility(View.VISIBLE);
                    GESTION.setVisibility(View.VISIBLE);
                    REVICION.setVisibility(View.VISIBLE);
                    textView211.setText("Modo Tecnico");
                    break;
                }
                case  "2":
                {
                    CREARGUIA.setVisibility(View.VISIBLE);
                    GESTION.setVisibility(View.VISIBLE);
                    REVICION.setVisibility(View.VISIBLE);
                    textView211.setText("Modo Admin");
                    break;
                }
        }

        SALUDO.setText("Bienvenido... " + NOMBRE_USER);
        db = FirebaseFirestore.getInstance();
        buscar_guia.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(menuprincipal_rep.this, panel_seguimiento_rep.class);

                Bundle bundle = new Bundle();
                bundle.putBoolean("IsLocal", false);
                bundle.putString("user", USER_ID);
                bundle.putString("nombre", NOMBRE_USER);
                bundle.putString("tipo", Tipo_user);
                bundle.putString("lista", LISTA_USER);
                bundle.putString("TOKEN_APP", TOKEN_APP);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        crear_guia.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menuprincipal_rep.this, crear_guia_rep.class);
                startActivity(intent);
            }
        });
        modificar_guia.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menuprincipal_rep.this, gestor_guias.class);
                Bundle bundle = new Bundle();

                bundle.putString("user", USER_ID);
                bundle.putString("nombre", NOMBRE_USER);
                bundle.putString("tipo", Tipo_user);
                bundle.putString("lista", LISTA_USER);

                intent.putExtras(bundle);
                startActivity(intent);

            }

        });
        cerrar_session.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new AlertDialog.Builder(menuprincipal_rep.this)
                        .setTitle("Salir")
                        .setMessage("¿Estás seguro de que deseas cerrar session?")
                        .setPositiveButton("Sí", (dialog, which) ->

                                CerrarSession()
                        )
                        .setNegativeButton("Toqué el botón de casualidad (no)", null)
                        .show();
            }

        });
        guias_consultada.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menuprincipal_rep.this, guiasConsultadas.class);
                Bundle bundle = new Bundle();

                bundle.putString("user", USER_ID);
                bundle.putString("nombre", NOMBRE_USER);
                bundle.putString("tipo", Tipo_user);
                bundle.putString("lista", LISTA_USER);
                bundle.putString("TOKEN_APP", TOKEN_APP);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
        CUENTA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menuprincipal_rep.this, MenuUsuario.class);
                Bundle bundle = new Bundle();

                bundle.putString("user", USER_ID);
                bundle.putString("nombre", NOMBRE_USER);
                bundle.putString("tipo", Tipo_user);
                bundle.putString("lista", LISTA_USER);

                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onBackPressed()
    {
        // No hacer nada para que no se escape xd.
    }

    public void CerrarSession()
    {
        Intent intent = new Intent(menuprincipal_rep.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}