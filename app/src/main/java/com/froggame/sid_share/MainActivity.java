package com.froggame.sid_share;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String TOKEN_APP;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button BT_Seguimiento;
        Button BT_Login;

        BT_Login = findViewById(R.id.button);
        BT_Seguimiento = findViewById(R.id.button3);


        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Get new Instance ID token

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();
                    Log.d("TAG", "Refreshed token: " + token);

                    // Muestra el token en un TextView (opcional)
                    /*
                    Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    TOKEN_APP = token.toString();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("simple text", token);
                    clipboard.setPrimaryClip(clip);

                     */
                });

        Toast.makeText(MainActivity.this, "El Cliente nunca a sido añadido ...", Toast.LENGTH_SHORT).show();

       BT_Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, login_rep.class);

                Bundle bundle = new Bundle();
                bundle.putString("TOKEN_APP", TOKEN_APP);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        BT_Seguimiento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(MainActivity.this, panel_seguimiento_rep.class);
                Bundle bundle = new Bundle();

                bundle.putBoolean("IsLocal", true);
                bundle.putString("TOKEN_APP", TOKEN_APP);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}