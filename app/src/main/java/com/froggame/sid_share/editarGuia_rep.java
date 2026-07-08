package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.froggame.sid_share.recursos.Guia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class editarGuia_rep extends AppCompatActivity {

    FirebaseFirestore db;
    CheckBox checkBox_wats;
    Button button2;
    TextView ID_GIA;
    TextView NOM_USUAR;
    SeekBar ESTADO_REAPARACION; //mod
    TextView TIPO_ARTICULO;
    TextView MODELO_ARTICULO;
    TextView NUMERO_DE_SERIE_ARTICULO;
    EditText AVERIA_ARTICULO; //mod
    EditText SOLUCION_ARTICULO; //mod
    EditText NOTA_ARTICULO; //mod
    Spinner TECNICO_REPARACION; //mod
    EditText telefono; //mod
    EditText T_tratado; //mod
    EditText T_a_cuenta; //mod
    TextView T_saldo;
    TextView T_FECHA;
    TextView T_HORA;
    ImageView imageView4 ;
    TextView DNI ;
    Button gestor_pagos;
    String ID_Tecnico;
    //ESTADOS...
    TextView Estado1;
    TextView Estado2;
    TextView Estado3;
    TextView Estado4;

    int Saldo_data;
    String base_id;
    String base_aticulo;
    String base_nombre;
    String base_dni;
    String base_modelo;
    String base_numeroserie;
    int tec_select;
    String TOKEN_APP_SERVER;
    String PAGO_CERRADO = "0";
    String CodigoAdelanto;
    String COGIGO_RESTANTE;
    private int ESTADO_BASE_DE_EMISOR;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_guia_rep);
        db = FirebaseFirestore.getInstance();
        checkBox_wats = findViewById(R.id.checkBox_wats);
        button2 = findViewById(R.id.button2);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

         ID_GIA = findViewById(R.id.ID_GIA);
         NOM_USUAR = findViewById(R.id.Nombre_Ususariojhj);
         ESTADO_REAPARACION = findViewById(R.id.ESTADO_REAPARACION); //mod
         TIPO_ARTICULO = findViewById(R.id.TIPO_ARTICULO);
         MODELO_ARTICULO = findViewById(R.id.MODELO_ARTICULO);
         NUMERO_DE_SERIE_ARTICULO = findViewById(R.id.NUMERO_DE_SERIE_ARTICULO);
         AVERIA_ARTICULO = findViewById(R.id.averia_camp); //mod
         SOLUCION_ARTICULO = findViewById(R.id.solucion_camp); //mod
         NOTA_ARTICULO = findViewById(R.id.nota_camp); //mod
         TECNICO_REPARACION = findViewById(R.id.Trabajadores); //mod
         telefono = findViewById(R.id.telefono_camp);
        gestor_pagos = findViewById(R.id.gestor_pagos);
        //mod
         T_tratado = findViewById(R.id.tratado); //mod
         T_a_cuenta = findViewById(R.id.a_cuenta); //mod
         T_saldo = findViewById(R.id.saldo);
         T_FECHA = findViewById(R.id.FECHA);
        T_HORA = findViewById(R.id.HORA);
         imageView4 = findViewById(R.id.imageView4);
         DNI = findViewById(R.id.DNI);
        tec_select = 0;
        //ESTADOS...
         Estado1 = findViewById(R.id.Estado1);
         Estado2 = findViewById(R.id.Estado2);
         Estado3 = findViewById(R.id.Estado3);
         Estado4 = findViewById(R.id.Estado4);

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
        base_id = String.valueOf(ID);
        int Estado = extras.getInt("Estado");

        ESTADO_BASE_DE_EMISOR = Estado;

        if(ESTADO_BASE_DE_EMISOR >= 80)
        {
            button2.setVisibility(View.GONE);
            checkBox_wats.setVisibility(View.GONE);
            ESTADO_REAPARACION.setEnabled(false);
            AVERIA_ARTICULO.setEnabled(false);
            SOLUCION_ARTICULO.setEnabled(false);
            NOTA_ARTICULO.setEnabled(false);
            TECNICO_REPARACION.setEnabled(false);
            telefono.setEnabled(false);
            T_tratado.setEnabled(false);
            T_a_cuenta.setEnabled(false);
            T_saldo.setEnabled(false);
            new AlertDialog.Builder(editarGuia_rep.this)
                    .setTitle("ADVERTENCIA")
                    .setMessage("ESTA GUIA YA ESTÁ FINALIZADA, SERÁ IMPOSIBLE EDITARLA...")
                    .show();
        }
        String Nombre_Ususario = extras.getString("Nombre_Ususario");
        base_nombre = Nombre_Ususario;
        int DNI_cliente = extras.getInt("DNI_cliente");
        base_dni = String.valueOf(DNI_cliente);
        String Articulo = extras.getString("Articulo");
        base_aticulo = Articulo;
        String Modelo = extras.getString("Modelo");
        base_modelo = Modelo;
        String NroSerie = extras.getString("NroSerie");
        base_numeroserie = NroSerie;
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
        PAGO_CERRADO = extras.getString("PAGO_CERRADO");
        CodigoAdelanto = extras.getString("CodigoAdelanto");
        COGIGO_RESTANTE = extras.getString("COGIGO_RESTANTE");
         TOKEN_APP_SERVER = extras.getString("TOKEN_APP_SERVER");


        Toast.makeText(editarGuia_rep.this, "Guia " + String.valueOf(ID) + " a sido encontrado...", Toast.LENGTH_SHORT).show();
        Toast.makeText(editarGuia_rep.this, PAGO_CERRADO, Toast.LENGTH_SHORT).show();

        //      String nombre = document.getString("nombre");
        //      int edad = document.getLong("edad").intValue();

        List<String> Tecnicos = new ArrayList<>();
        List<String> IDs_Tecnicos = new ArrayList<>();
        List<String> Productos = new ArrayList<>();

        Productos.add("Laptop");
        Productos.add("Impresora");
        Productos.add("Computadora");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(editarGuia_rep.this, android.R.layout.simple_spinner_item, Productos);

        Guia guia = new Guia(Integer.parseInt(ID),Estado,Nombre_Ususario,DNI_cliente,Articulo,Modelo,NroSerie,Averia,Solucion,Nota,Tecnico,Telefono,ID_tecnico,tratado,a_cuenta,saldo,FECHA,HORA);

        String ID_REAL = String.format("%06d", guia.getID()); // 5 dígitos, rellenando con ceros a la izquierda

        ID_GIA.setText(String.valueOf(ID_REAL));
        NOM_USUAR.setText("Nombres: " + guia.getNombre_cliente());
        ESTADO_REAPARACION.setProgress(guia.getEstado());
        TIPO_ARTICULO.setText("Articulo: " + guia.getArticulo());
        MODELO_ARTICULO.setText("Modelo: " + guia.Modelo);
        NUMERO_DE_SERIE_ARTICULO.setText("N° de serie: " + guia.getNroSerie());
        AVERIA_ARTICULO.setText(guia.getAveria());
        SOLUCION_ARTICULO.setText(guia.getSolucion());
        NOTA_ARTICULO.setText(guia.getNota());


        telefono.setText(guia.getTelefono());
        DNI.setText("DNI: " + guia.getDNI_cliente());
        T_tratado.setText(guia.getTratado());
        T_a_cuenta.setText(guia.getA_cuenta());
        T_saldo.setText(guia.getSaldo());
        T_FECHA.setText(guia.getFECHA());
        T_HORA.setText(guia.getHORA());

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

        ArrayAdapter<String> tecnicos_array = new ArrayAdapter<String>(editarGuia_rep.this, android.R.layout.simple_spinner_item, Tecnicos);
        ArrayAdapter<String> ID_Tecnicos_Array = new ArrayAdapter<String>(editarGuia_rep.this, android.R.layout.simple_spinner_item, IDs_Tecnicos);


        db.collection("Usuarios")
                .whereIn("user_type",  Arrays.asList("1", "2"))
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
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                String nombreProducto = document.getString("Nombre");
                                String ID = document.getString("ID");
                                tecnicos_array.add(nombreProducto);
                                ID_Tecnicos_Array.add(ID);

                            }
                            for(int i = 0; i< tecnicos_array.getCount(); i++)
                            {
                                if (tecnicos_array.getItem(i).equals(Tecnico))
                                {
                                    TECNICO_REPARACION.setSelection(i);
                                    break;
                                }
                            }
                        }
                        else
                        {
                            Log.w("TAG", "Error al obtener los tecnicos para seleccionar...", task.getException());
                        }
                    }
                });

        TECNICO_REPARACION.setAdapter(tecnicos_array);



        TECNICO_REPARACION.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ID_Tecnico = ID_Tecnicos_Array.getItem(TECNICO_REPARACION.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //XD...
            }
        });

        ESTADO_REAPARACION.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
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
                    ESTADO_REAPARACION.setEnabled(false);
                    new AlertDialog.Builder(editarGuia_rep.this)
                            .setTitle("Finalizar Guia")
                            .setMessage("¿Estás seguro de que deseas finalizar la guia? no se podrá editar mas adelante")
                            .setPositiveButton("Sí", (dialog, which) ->
                                    ProcedimientoEsclavo1()
                            )
                            .setNegativeButton("no", (dialog, which) ->
                                    ProcedimientoEsclavo2()
                            ).show();
                    Estado1.setVisibility(View.GONE);
                    //    Estado1.setEnabled(false);
                    Estado2.setVisibility(View.GONE);
                    //    Estado2.setEnabled(false);
                    Estado3.setVisibility(View.GONE);
                    //   Estado3.setEnabled(false);
                    Estado4.setVisibility(View.VISIBLE);
                    //    Estado4.setEnabled(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        T_tratado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {
                int valor = 0;
                int valor2 = 0;

                if(T_tratado.getText().toString() == "")
                {
                    valor = 0;
                }
                if(T_a_cuenta.getText().toString() == "")
                {
                    valor2 = 0;
                }
            /*   if(TRATADO.getText().toString() == "")
                {
                    TRATADO.setText("0");
                }
                if(Integer.parseInt(A_CUENTA.getText().toString()) > Integer.parseInt(TRATADO.getText().toString()))
                {
                    A_CUENTA.setText("0");
                    TRATADO.setText("0");
                    Log.d(TAG, "A cuenta no puede ser mayor al precio tratado...");
                }
                else
                {
                    Saldo_data = Integer.parseInt(TRATADO.getText().toString()) - Integer.parseInt(A_CUENTA.getText().toString());
                    SALDO.setText(String.valueOf(Saldo_data));
                }*/
                try {
                    valor = Integer.parseInt(T_tratado.getText().toString());
                }
                catch (Exception e)
                {
                    valor = 0;
                    //       A_CUENTA.setText("0");
                }
                try {
                    valor2 = Integer.parseInt(T_a_cuenta.getText().toString());
                }
                catch (Exception e)
                {
                    valor2 = 0;
                    //       A_CUENTA.setText("0");
                }


                Saldo_data = valor - valor2;
                T_saldo.setText(String.valueOf(Saldo_data));
            }
        });

        T_a_cuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
            @Override
            public void afterTextChanged(Editable s)
            {
                int valor = 0;
                int valor2 = 0;

                if(T_tratado.getText().toString() == "")
                {
                    valor = 0;
                }
                if(T_a_cuenta.getText().toString() == "")
                {
                    valor2 = 0;
                }
             /*   if(A_CUENTA.getText().toString() == "")
                {
                    A_CUENTA.setText("0");
                }
                if(Integer.parseInt(A_CUENTA.getText().toString()) > Integer.parseInt(TRATADO.getText().toString()))
                {
                    A_CUENTA.setText("");
                    Log.d(TAG, "A cuenta no puede ser mayor al precio tratado...");
                }
                else
                {
                    Saldo_data = Integer.parseInt(TRATADO.getText().toString()) - Integer.parseInt(A_CUENTA.getText().toString());
                    SALDO.setText(Saldo_data);
                }*/
                try {
                    valor = Integer.parseInt(T_tratado.getText().toString());
                }
                catch (Exception e)
                {
                    valor = 0;
                    //       A_CUENTA.setText("0");
                }
                try {
                    valor2 = Integer.parseInt(T_a_cuenta.getText().toString());
                }
                catch (Exception e)
                {
                    valor2 = 0;
                    //       A_CUENTA.setText("0");
                }


                Saldo_data = valor - valor2;
                T_saldo.setText(String.valueOf(Saldo_data));
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Actualizar_Guia();
            }
        });

        gestor_pagos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(editarGuia_rep.this, GESTOR_PAGOS.class);

                Bundle bundle = new Bundle();
                bundle.putString("ID", base_id);
                bundle.putString("MontoPagado_Adelanto", T_a_cuenta.getText().toString());
                bundle.putString("MONTO_TOTAL", T_tratado.getText().toString());
                bundle.putString("Monto_a_pagar", T_saldo.getText().toString());
                bundle.putString("CodigoAdelanto", CodigoAdelanto);
                bundle.putString("COGIGO_RESTANTE", COGIGO_RESTANTE);
                bundle.putBoolean("IS_VISUAL_MAP", false);


                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    public void ProcedimientoEsclavo1()
    {
        ESTADO_REAPARACION.setProgress(100);
        ESTADO_REAPARACION.setEnabled(true);
    }
    public void ProcedimientoEsclavo2()
    {
        ESTADO_REAPARACION.setProgress(45);
        ESTADO_REAPARACION.setEnabled(true);
    }


    private void Actualizar_Guia()
    {

        String ID = base_id;
        int Estado = ESTADO_REAPARACION.getProgress();

        String Nombre_Ususario = base_nombre;
        String DNI_cliente = base_dni;
        String Articulo = base_aticulo;
        String Modelo = base_modelo;
        String NroSerie = base_numeroserie;
        String Averia = AVERIA_ARTICULO.getText().toString();
        String Solucion = SOLUCION_ARTICULO.getText().toString();
        String Telefono = telefono.getText().toString();
        String Tecnico = TECNICO_REPARACION.getSelectedItem().toString();

        String tratado = T_tratado.getText().toString();
        String a_cuenta = T_a_cuenta.getText().toString();
        String saldo = T_saldo.getText().toString();

        String fecha = T_FECHA.getText().toString();

        String Nota = NOTA_ARTICULO.getText().toString();



        String ID_tecnico = ID_Tecnico.toString();

        if(PAGO_CERRADO.equals("0") && Estado >= 80)
        {
            new AlertDialog.Builder(editarGuia_rep.this)
                    .setTitle("ADVERTENCIA")
                    .setMessage("PARA FINALIZAR LA GUIA, PRIMERO CIERRA EL GESTOR DE PAGOS PRIMERO...")
                    .show();
            Toast.makeText(editarGuia_rep.this, "NO PUEDE FINALIZAR LA GUIA, CIERRA EL GESTOR DE PAGOS PRIMERO", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(base_nombre) || TextUtils.isEmpty(base_dni) || TextUtils.isEmpty(base_aticulo) ||
                TextUtils.isEmpty(base_modelo) || TextUtils.isEmpty(AVERIA_ARTICULO.getText()) ||
                TextUtils.isEmpty(SOLUCION_ARTICULO.getText()))
        {
            Toast.makeText(editarGuia_rep.this, "FALTAN COMPLETAR CAMPOS OBLIGATORIOS...", Toast.LENGTH_SHORT).show();
            return;
        }


        Map<String, Object> user = new HashMap<>();
        user.put("FECHA", String.valueOf(fecha));
        user.put("HORA", String.valueOf(T_HORA.getText().toString())); // XD...
        user.put("Articulo", Articulo);
        user.put("Averia", Averia);
        user.put("DNI_cliente", String.valueOf(DNI_cliente));
        user.put("Estado",Estado);
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
        user.put("CodigoAdelanto", CodigoAdelanto);

        db.collection("Guias").document(String.valueOf(base_id))
                .update(user)
                .addOnSuccessListener(documentReference ->
                {
                    Log.d(TAG, "Se anadio la guia exitosamente");

                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    Toast.makeText(editarGuia_rep.this, "Actualizado exitosamente", Toast.LENGTH_SHORT).show();

                    if(checkBox_wats.isChecked())
                    {
                        intent.setData(Uri.parse("https://wa.me/" + Telefono + "?text=Hola%20hemos%20actualizado%20tu%20pedido%2C%20verifique%20la%20aplicaci%C3%B3n%20para%20ver%20mas%20detalles..."));
                        startActivity(intent);
                        Toast.makeText(editarGuia_rep.this, "Actualizado y Enviando a WhatsApp", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }

                    finish();
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "Error al anadir el documento", e);
                    }
                });
    }


}