package com.froggame.sid_share;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.froggame.sid_share.recursos.Guia;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import android.Manifest;
import java.util.HashMap;
import java.util.Map;

public class status_product_rep extends AppCompatActivity {
    
    private static final int REQUEST_CODE_PERMISSION = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status_product_rep);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        
        /*
        user.put("FECHA", String.valueOf(fecha));
        user.put("Articulo", Articulo);
        user.put("Averia", Averia);
        user.put("DNI_cliente", String.valueOf(DNI_cliente));
        user.put("Estado",String.valueOf(Estado));
        user.put("ID",String.valueOf(ID));
        user.put("ID_tecnico", String.valueOf(ID_tecnico));
        user.put("Modelo", Modelo);
        user.put("Nombre_cliente", Nombre_Ususario);
        user.put("Nota", Nota);
        user.put("NroSerie", NroSerie);
        user.put("Solucion", Solucion);
        user.put("Tecnico", Tecnico);
        user.put("tratado", String.valueOf(tratado));
        user.put("a_cuenta", String.valueOf(a_cuenta));
        user.put("saldo", String.valueOf(saldo));
        user.put("Telefono", String.valueOf(Telefono));
         */

        String ID = extras.getString("ID");
        int Estado = extras.getInt("Estado");
        String Nombre_Ususario = extras.getString("Nombre_Ususario");
        int DNI_cliente = extras.getInt("DNI_cliente");
        String Articulo = extras.getString("Articulo");
        String Modelo = extras.getString("Modelo");
        String NroSerie = extras.getString("NroSerie");
        String Averia = extras.getString("Averia");
        String Solucion = extras.getString("Solucion");
        String Nota = extras.getString("Nota");
        String Telefono = extras.getString("Telefono");
        String Tecnico = extras.getString("Tecnico");
        String FECHA = extras.getString("FECHA");
        String HORA = extras.getString("HORA");
        String tratado = extras.getString("tratado");
        String a_cuenta = extras.getString("a_cuenta");
        String saldo = extras.getString("saldo");
        int ID_tecnico = extras.getInt("ID_tecnico");
        String TOKEN_APP = extras.getString("TOKEN_APP");

        Toast.makeText(status_product_rep.this, "Guia " + String.valueOf(ID) + " a sido encontrado...", Toast.LENGTH_SHORT).show();
        //      String nombre = document.getString("nombre");
        //      int edad = document.getLong("edad").intValue();

        Guia guia = new Guia(Integer.parseInt(ID),Estado,Nombre_Ususario,DNI_cliente,Articulo,Modelo,NroSerie,Averia,Solucion,Nota,Tecnico,Telefono,ID_tecnico,tratado,a_cuenta,saldo,FECHA,HORA);

        TextView ID_GIA = findViewById(R.id.ID_GIA);
        TextView NOM_USUAR = findViewById(R.id.Nombre_Ususario);
        SeekBar ESTADO_REAPARACION = findViewById(R.id.ESTADO_REAPARACION);
        TextView TIPO_ARTICULO = findViewById(R.id.TIPO_ARTICULO);
        TextView MODELO_ARTICULO = findViewById(R.id.MODELO_ARTICULO);
        TextView NUMERO_DE_SERIE_ARTICULO = findViewById(R.id.NUMERO_DE_SERIE_ARTICULO);
        TextView DNI = findViewById(R.id.dni);
        TextView AVERIA_ARTICULO = findViewById(R.id.AVERIA_ARTICULO);
        TextView SOLUCION_ARTICULO = findViewById(R.id.SOLUCION_ARTICULO);
        TextView NOTA_ARTICULO = findViewById(R.id.NOTA_ARTICULO);
        TextView TECNICO_REPARACION = findViewById(R.id.TECNICO_REPARACION);
        TextView telefono = findViewById(R.id.telefono);
        TextView T_tratado = findViewById(R.id.tratado);
        TextView T_a_cuenta = findViewById(R.id.a_cuenta);
        TextView T_saldo = findViewById(R.id.saldo);
        TextView T_FECHA = findViewById(R.id.FECHA);
        TextView T_HORA = findViewById(R.id.HORA);
        ImageView imageView4 = findViewById(R.id.imageView4);
        Button Button_TOKEN_APP = findViewById(R.id.Recibir_ntf);

        //ESTADOS...
        TextView Estado1 = findViewById(R.id.Estado1);
        TextView Estado2 = findViewById(R.id.Estado2);
        TextView Estado3 = findViewById(R.id.Estado3);
        TextView Estado4 = findViewById(R.id.Estado4);



        NOM_USUAR.setText("Nombres: " + guia.getNombre_cliente());
        ID_GIA.setText("ID: " + ID);
        ESTADO_REAPARACION.setProgress(guia.getEstado());
        TIPO_ARTICULO.setText("Articulo: " + guia.getArticulo());
        MODELO_ARTICULO.setText("Modelo: " + guia.Modelo);
        NUMERO_DE_SERIE_ARTICULO.setText("N° de serie: " + guia.getNroSerie());
        AVERIA_ARTICULO.setText("Avería: " + guia.getAveria());
        SOLUCION_ARTICULO.setText("Solución: " + guia.getSolucion());
        NOTA_ARTICULO.setText("NOTA: \n" + guia.getNota());
        TECNICO_REPARACION.setText("Tecnico: " + guia.getTecnico());
        telefono.setText("Telefono: " + guia.getTelefono());
        DNI.setText("DNI: " + guia.getDNI_cliente());
        T_tratado.setText(guia.getTratado());
        T_a_cuenta.setText(guia.getA_cuenta());
        T_saldo.setText(guia.getSaldo());
        T_FECHA.setText(guia.getFECHA());
        T_HORA.setText("HORA: " + guia.getHORA());

        if(ESTADO_REAPARACION.getProgress() <= 24)
        {
            Estado1.setVisibility(View.VISIBLE);
          //  Estado1.setEnabled(true);
            Estado2.setVisibility(View.GONE);
          //  Estado2.setEnabled(false);
            Estado3.setVisibility(View.GONE);
          //  Estado3.setEnabled(false);
            Estado4.setVisibility(View.GONE);
         //   Estado4.setEnabled(false);
        }
        else if(ESTADO_REAPARACION.getProgress() >= 25 && ESTADO_REAPARACION.getProgress() <= 39
        )
        {
            Estado1.setVisibility(View.GONE);
         //   Estado1.setEnabled(false);
            Estado2.setVisibility(View.VISIBLE);
         //   Estado2.setEnabled(true);
            Estado3.setVisibility(View.GONE);
         //   Estado3.setEnabled(false);
            Estado4.setVisibility(View.GONE);
         //   Estado4.setEnabled(false);
        }
        else if(ESTADO_REAPARACION.getProgress() >= 40 && ESTADO_REAPARACION.getProgress() <= 79)
        {
            Estado1.setVisibility(View.GONE);
       //     Estado1.setEnabled(false);
            Estado2.setVisibility(View.GONE);
       //     Estado2.setEnabled(false);
            Estado3.setVisibility(View.VISIBLE);
       //     Estado3.setEnabled(true);
            Estado4.setVisibility(View.GONE);
       //     Estado4.setEnabled(false);
        }
        else if(ESTADO_REAPARACION.getProgress() >= 80)
        {
            Estado1.setVisibility(View.GONE);
        //    Estado1.setEnabled(false);
            Estado2.setVisibility(View.GONE);
        //    Estado2.setEnabled(false);
            Estado3.setVisibility(View.GONE);
         //   Estado3.setEnabled(false);
            Estado4.setVisibility(View.VISIBLE);
        //    Estado4.setEnabled(true);
        }
        ESTADO_REAPARACION.setEnabled(false);
        switch(Articulo)
        {
            case "Laptop":
            {
                imageView4.setImageResource(R.mipmap.laptop_icon);
                break;
            }
            case "Impresora":
            {
                imageView4.setImageResource(R.mipmap.impresora_icon);
                break;
            }
            case "Computadora":
            {
                imageView4.setImageResource(R.mipmap.pc_icon);
                break;
            }
        }
        Button_TOKEN_APP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                requestNotificationPermission();

                Map<String, Object> data = new HashMap<>();
                data.put("Token_FCM", TOKEN_APP);

                db.collection("Guias").document(ID)
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "actualizado!");
                                Toast.makeText(status_product_rep.this, "Se te notificará cuando esta guía se actualize...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });
    }
    

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {

            } else {
                // El usuario denegó el permiso
                Toast.makeText(this, "Se necesita el permiso de notificaciones para continuar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_PERMISSION);
        } else {
            // El permiso ya ha sido concedido
        }
    }
}