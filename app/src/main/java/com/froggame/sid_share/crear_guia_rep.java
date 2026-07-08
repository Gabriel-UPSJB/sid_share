package com.froggame.sid_share;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class crear_guia_rep extends AppCompatActivity {

    FirebaseFirestore db;
  //  FirebaseAuth mAuth;
  //  Random random = new Random();

    int ID_BASE_GUIA;

    TextView ID_GIA;
    EditText NOM_USUAR;
    EditText DNI_USUAR;
    EditText Adelanto;
    SeekBar ESTADO_REAPARACION;
    Spinner TIPO_ARTICULO;
    Spinner TECNICOS;
    String ID_Tecnico;
    EditText MODELO_ARTICULO;
    EditText NUMERO_DE_SERIE_ARTICULO;
    AutoCompleteTextView AVERIA_ARTICULO;
    AutoCompleteTextView SOLUCION_ARTICULO;
    EditText NOTA_ARTICULO;
    EditText TELEFONO;
    EditText TRATADO;
    EditText A_CUENTA;
    int Saldo_data;
    TextView SALDO;
    TextView FECHA;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;

    Button buscar_por_dni;
    Button CrearGuia;
    ImageView imageView4;
    int tec_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_guia_rep);
        db = FirebaseFirestore.getInstance();

         ID_GIA = findViewById(R.id.ID_GIA);
         Adelanto = findViewById(R.id.Adelanto);
         NOM_USUAR = findViewById(R.id.nombre_camp);
         DNI_USUAR = findViewById(R.id.dni_camp);
         ESTADO_REAPARACION = findViewById(R.id.ESTADO_REAPARACION);
         MODELO_ARTICULO = findViewById(R.id.modelo_camp);
         NUMERO_DE_SERIE_ARTICULO = findViewById(R.id.nroserie_camp);
         AVERIA_ARTICULO = findViewById(R.id.averia_camp);
         SOLUCION_ARTICULO = findViewById(R.id.solucion_camp);
         NOTA_ARTICULO = findViewById(R.id.nota_camp);
         TELEFONO = findViewById(R.id.telefono_camp);
         CrearGuia = findViewById(R.id.button2);
         imageView4 = findViewById(R.id.imageView4);
         TIPO_ARTICULO = findViewById(R.id.spinner2);
         TECNICOS = findViewById(R.id.Trabajadores);
         TRATADO = findViewById(R.id.tratado);
         A_CUENTA = findViewById(R.id.a_cuenta);
         SALDO = findViewById(R.id.saldo);
         FECHA = findViewById(R.id.FECHA);
        buscar_por_dni = findViewById(R.id.buscar_por_dni);
        TextView Estado1 = findViewById(R.id.Estado1);
        TextView Estado2 = findViewById(R.id.Estado2);
        TextView Estado3 = findViewById(R.id.Estado3);
        TextView Estado4 = findViewById(R.id.Estado4);

        Spinner TIPO_COMPROBANTES = findViewById(R.id.Tipo_comprobante);


        String[] listaAverias = getResources().getStringArray(R.array.ListaAveria);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaAverias);
        AVERIA_ARTICULO.setAdapter(arrayAdapter);

        String[] listaSolucion = getResources().getStringArray(R.array.ListaSolucion);
        arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaSolucion);
        SOLUCION_ARTICULO.setAdapter(arrayAdapter2);

        tec_select = 0;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = sdf.format(calendar.getTime());

        FECHA.setText(fechaActual);

        List<String> Productos = new ArrayList<>();

        Productos.add("Laptop");
        Productos.add("Impresora");
        Productos.add("Computadora");

        TIPO_COMPROBANTES.setSelection(0);
        TIPO_COMPROBANTES.setEnabled(false);

        List<String> Tecnicos = new ArrayList<>();
        List<String> IDs_Tecnicos = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(crear_guia_rep.this, android.R.layout.simple_spinner_item, Productos);

        List<String> TIPOS_COMPROBANTES = new ArrayList<>();
        TIPOS_COMPROBANTES.add("NV");
        TIPOS_COMPROBANTES.add("BD");
        TIPOS_COMPROBANTES.add("FC");
        ArrayAdapter<String> add = new ArrayAdapter<String>(crear_guia_rep.this, android.R.layout.simple_spinner_item, TIPOS_COMPROBANTES);
        TIPO_COMPROBANTES.setAdapter(add);

        TIPO_ARTICULO.setAdapter(adapter);
        CrearGuia.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Crear_Guia();
            }
        });
        buscar_por_dni.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Buscar_usuario_DNI();
            }
        });

        ArrayAdapter<String> tecnicos_array = new ArrayAdapter<String>(crear_guia_rep.this, android.R.layout.simple_spinner_item, Tecnicos);
        ArrayAdapter<String> ID_Tecnicos_Array = new ArrayAdapter<String>(crear_guia_rep.this, android.R.layout.simple_spinner_item, IDs_Tecnicos);

        Estado1.setVisibility(View.VISIBLE);
        Estado2.setVisibility(View.GONE);
        Estado3.setVisibility(View.GONE);
        Estado4.setVisibility(View.GONE);

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
                        }
                        else
                        {
                            Log.w("TAG", "Error al obtener los tecnicos para seleccionar...", task.getException());
                        }
                    }
                });
        db.collection("Guias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            ID_BASE_GUIA = task.getResult().size();

                            String ID_REAL = String.format("%06d", ID_BASE_GUIA); // 5 dígitos, rellenando con ceros a la izquierda XD

                            ID_GIA.setText(String.valueOf(ID_REAL));
                        }
                        else
                        {
                            Toast.makeText(crear_guia_rep.this, "ERROR", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error XD...", task.getException());
                        }
                    }
                });

        TECNICOS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ID_Tecnico = ID_Tecnicos_Array.getItem(TECNICOS.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //XD...
            }
        });

        TIPO_ARTICULO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch(TIPO_ARTICULO.getSelectedItem().toString())
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //XD...
            }
        });

        TECNICOS.setAdapter(tecnicos_array);

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
                    new AlertDialog.Builder(crear_guia_rep.this)
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


        TRATADO.addTextChangedListener(new TextWatcher() {
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

                if(TRATADO.getText().toString() == "")
                {
                    valor = 0;
                }
                if(A_CUENTA.getText().toString() == "")
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
                    valor = Integer.parseInt(TRATADO.getText().toString());
                }
                catch (Exception e)
                {
                    valor = 0;
                    //       A_CUENTA.setText("0");
                }
                try {
                    valor2 = Integer.parseInt(A_CUENTA.getText().toString());
                }
                catch (Exception e)
                {
                    valor2 = 0;
                    //       A_CUENTA.setText("0");
                }


                Saldo_data = valor - valor2;
                SALDO.setText(String.valueOf(Saldo_data));

                if(SALDO.getText().equals("0"))
                {
                    TIPO_COMPROBANTES.setEnabled(true);
                }
                else
                {
                    TIPO_COMPROBANTES.setSelection(0);
                    TIPO_COMPROBANTES.setEnabled(false);
                }
            }
        });

        A_CUENTA.addTextChangedListener(new TextWatcher() {
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

                if(TRATADO.getText().toString() == "")
                {
                    valor = 0;
                }
                if(A_CUENTA.getText().toString() == "")
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
                    valor = Integer.parseInt(TRATADO.getText().toString());
                }
                catch (Exception e)
                {
                    valor = 0;
                    //       A_CUENTA.setText("0");
                }
                try {
                    valor2 = Integer.parseInt(A_CUENTA.getText().toString());
                }
                catch (Exception e)
                {
                    valor2 = 0;
                    //       A_CUENTA.setText("0");
                }


                Saldo_data = valor - valor2;
                SALDO.setText(String.valueOf(Saldo_data));

                if(SALDO.getText().equals("0"))
                {
                    TIPO_COMPROBANTES.setEnabled(true);
                }
                else
                {
                    TIPO_COMPROBANTES.setSelection(0);
                    TIPO_COMPROBANTES.setEnabled(false);
                }
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
    private void Buscar_usuario_DNI()
    {
        if(TextUtils.isEmpty(DNI_USUAR.getText()))
        {
            Toast.makeText(crear_guia_rep.this, "MI ESTIMADO, PODRÍA SER TAN AMABLE DE PONER EL DNI ANTES DE HACER LA BUSQUEDA...", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference docRef = db.collection("Usuarios_DNI").document(DNI_USUAR.getText().toString());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        String Nombre = document.getString("Nombre");
                        String Telefono = document.getString("Telefono");

                        NOM_USUAR.setText(Nombre);
                        TELEFONO.setText(Telefono);
                    }
                    else
                    {
                        Log.d(TAG, "USUARIO NO ENCONTRADO");
                        Toast.makeText(crear_guia_rep.this, "El Cliente nunca a sido añadido ...", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Log.d(TAG, "Se encontró un error: ", task.getException());
                    Toast.makeText(crear_guia_rep.this, "No se pudo buscar, verifique su conexión a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void Crear_Guia()
    {
        if(TextUtils.isEmpty(DNI_USUAR.getText()) || TextUtils.isEmpty(NOM_USUAR.getText()) || TextUtils.isEmpty(TELEFONO.getText()) || TextUtils.isEmpty(NUMERO_DE_SERIE_ARTICULO.getText()) || TextUtils.isEmpty(AVERIA_ARTICULO.getText()) || TextUtils.isEmpty(SOLUCION_ARTICULO.getText()))
        {
            Toast.makeText(crear_guia_rep.this, "FALTAN COMPLETAR CAMPOS OBLIGATORIOS...", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Adelanto.getText()))
        {
            Toast.makeText(crear_guia_rep.this, "PRIMERO GENERA LA NV DEL ADELANTO Ó EL PAGO TOTAL Y ENLAZALO CON SU CODIGO EN ESTA GUIA...", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(crear_guia_rep.this)
                    .setTitle("NO SE PUEDE CREAR")
                    .setMessage("SI EL CLIENTE DEJÓ ADELANTO Ó PAGÓ COMPLETO, PRIMERO GENERA LA BOLETA E INGRESA SU CODIGO EN LA GUIA A CREAR...")
                    .show();
            return;
        }
        String ID = ID_GIA.getText().toString();
        int Estado = ESTADO_REAPARACION.getProgress();
        String Nombre_Ususario = NOM_USUAR.getText().toString();
        String DNI_cliente = DNI_USUAR.getText().toString();
        String Articulo = TIPO_ARTICULO.getSelectedItem().toString();
        String Modelo = MODELO_ARTICULO.getText().toString();
        String NroSerie = NUMERO_DE_SERIE_ARTICULO.getText().toString();
        String Averia = AVERIA_ARTICULO.getText().toString();
        String Solucion = SOLUCION_ARTICULO.getText().toString();
        String Telefono = TELEFONO.getText().toString();
        String fecha = FECHA.getText().toString();
        String Nota = NOTA_ARTICULO.getText().toString();
        String Tecnico = TECNICOS.getSelectedItem().toString();
        String ID_tecnico = ID_Tecnico.toString();
        String tratado = TRATADO.getText().toString();
        String a_cuenta = A_CUENTA.getText().toString();
        String saldo = SALDO.getText().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = sdf.format(calendar.getTime());

        Map<String, Object> user = new HashMap<>();
        user.put("FECHA", String.valueOf(fecha));
        user.put("HORA", formattedDate);
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

        if(Integer.parseInt(a_cuenta) == Integer.parseInt(tratado)) {
            user.put("CodigoAdelanto", String.valueOf(Adelanto.getText()));
            user.put("COGIGO_RESTANTE", String.valueOf(Adelanto.getText()));
            user.put("PAGOS_CERRADO", String.valueOf("1"));
        }
        else
        {
            user.put("CodigoAdelanto", String.valueOf(Adelanto.getText()));
            user.put("COGIGO_RESTANTE", String.valueOf(""));
            user.put("PAGOS_CERRADO", String.valueOf("0"));
        }


        db.collection("Guias").document(String.valueOf(ID))
                .set(user)
                .addOnSuccessListener(documentReference ->
                {
                    Log.d(TAG, "Se anadio la guia exitosamente");
                    Anadir_Actualizar_DNI_Usuarios();
                    Toast.makeText(crear_guia_rep.this, "Enviando a WhatsApp", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse("https://wa.me/" + Telefono + "?text=Hola%2C%20somos%20Soluciones%20Inform%C3%A1ticas%20Digitales%20SAC%2C%20te%20enviamos%20tu%20c%C3%B3digo%20seguimiento%3A%20" + ID + "%20%2C%20Puedes%20hacer%20seguimiento%20en%20tiempo%20real%20descargando%20la%20aplicaci%C3%B3n%20aqu%C3%AD%3A%20https%3A%2F%2Fplay.google.com%2Fstore%2Fapps%2Fdetails%3Fid%3Dcom.froggame.sid_share"));
                    startActivity(intent);

                    finish();
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    private void Anadir_Actualizar_DNI_Usuarios()
    {
        Map<String, Object> user = new HashMap<>();
        user.put("Nombre", NOM_USUAR.getText().toString());
        user.put("Telefono", TELEFONO.getText().toString());

        db.collection("Usuarios_DNI").document(String.valueOf(DNI_USUAR.getText().toString()))
                .set(user)
                .addOnSuccessListener(documentReference ->
                {
                    Log.d(TAG, "Se anadio ó actualizó usuario");
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.w(TAG, "Error al añadir o actualizar el usuario", e);
                    }
                });
    }
}