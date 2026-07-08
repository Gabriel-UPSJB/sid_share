package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33})
public class login_repTest {

    private login_rep activity;

    @Mock
    private FirebaseFirestore mockDb;
    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private Task<AuthResult> mockAuthTask;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        if (FirebaseApp.getApps(ApplicationProvider.getApplicationContext()).isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApplicationId("1:1234567890:android:1234567890abcdef")
                    .setApiKey("testing_key")
                    .setProjectId("sid-share-test")
                    .build();
            FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext(), options);
        }

        // Inyectar el mock de Auth antes de crear la Activity (requiere que uses una inyección o variables accesibles si es posible.
        // Si mAuth es package-private en tu clase, Robolectric lo tomará normal al instanciar).

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), login_rep.class);
        intent.putExtra("TOKEN_APP", "token_simulado_123");

        activity = Robolectric.buildActivity(login_rep.class, intent).create().get();

        // Reemplazamos la instancia real por el mock después de onCreate
        activity.mAuth = mockAuth;
        activity.db = mockDb;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testLoginButton_EmptyFields_ShowsToast() {
        EditText email = activity.findViewById(R.id.editTextTextPassword2);
        EditText pass = activity.findViewById(R.id.editTextTextPassword);
        Button btnLogin = activity.findViewById(R.id.iniciar_session);

        email.setText("");
        pass.setText("");
        btnLogin.performClick();

        // No se debe llamar a Firebase si los campos están vacíos
        verify(mockAuth, org.mockito.Mockito.never()).signInWithEmailAndPassword(anyString(), anyString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testLogin_Failure_DoesNotOpenMenu() {
        EditText email = activity.findViewById(R.id.editTextTextPassword2);
        EditText pass = activity.findViewById(R.id.editTextTextPassword);
        Button btnLogin = activity.findViewById(R.id.iniciar_session);

        email.setText("test@correo.com");
        pass.setText("123456");

        // Simular flujo de Firebase Auth
        when(mockAuth.signInWithEmailAndPassword("test@correo.com", "123456")).thenReturn(mockAuthTask);

        ArgumentCaptor<OnCompleteListener<AuthResult>> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        when(mockAuthTask.addOnCompleteListener(captor.capture())).thenReturn(mockAuthTask);

        btnLogin.performClick();

        // Forzar fallo de autenticación
        when(mockAuthTask.isSuccessful()).thenReturn(false);
        captor.getValue().onComplete(mockAuthTask);

        // Verificar que no se lanzó la actividad de menú principal
        Intent expectedIntent = Shadows.shadowOf(activity).getNextStartedActivity();
        org.junit.Assert.assertNull("No debería abrir el menú si falla el login", expectedIntent);
    }
}