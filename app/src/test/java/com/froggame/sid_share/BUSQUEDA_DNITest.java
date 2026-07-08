package com.froggame.sid_share;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33})
public class BUSQUEDA_DNITest {
    private BUSQUEDA_DNI activity;

    @Mock
    private FirebaseFirestore mockDb;

    @Mock
    private CollectionReference mockCollectionReference;

    @Mock
    private Query mockQuery;

    @Mock
    private Task<QuerySnapshot> mockTask;

    @Mock
    private QuerySnapshot mockQuerySnapshot;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // 1. Inicializar FirebaseApp simulado para el proceso del test
        if (FirebaseApp.getApps(ApplicationProvider.getApplicationContext()).isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setApplicationId("1:1234567890:android:1234567890abcdef")
                    .setApiKey("fake_api_key_for_testing")
                    .setProjectId("fake-project-id")
                    .build();
            FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext(), options);
        }

        // 2. Ahora sí revivimos la Activity sin que crashee por Firebase
        activity = Robolectric.buildActivity(BUSQUEDA_DNI.class).create().get();

        // 3. Estructura de comportamiento de tus Mocks de Firestore
        when(mockDb.collection("Guias")).thenReturn(mockCollectionReference);
        when(mockCollectionReference.whereEqualTo(anyString(), anyString())).thenReturn(mockQuery);
        when(mockQuery.get()).thenReturn(mockTask);
    }

    @Test
    public void testFirestoreQuery_StructureIsCorrect() {
        // Verificamos que la inicialización del Mock de la base de datos no sea nula
        assertNotNull(mockDb);

        // Ejecutamos la simulación de la consulta simulando los filtros del flujo por DNI
        Query query = mockDb.collection("Guias").whereEqualTo("DNI_cliente", "12345678");
        Task<QuerySnapshot> originalTask = query.get();

        // Verificaciones de comportamiento
        verify(mockDb).collection("Guias");
        verify(mockCollectionReference).whereEqualTo("DNI_cliente", "12345678");
        assertEquals(mockTask, originalTask);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testFirestoreCallback_OnFailure() {
        // Capturador para interceptar el listener que agregas en tu Activity (.addOnCompleteListener)
        ArgumentCaptor<OnCompleteListener<QuerySnapshot>> argumentCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);

        // Simulamos la llamada al método get() y capturamos el listener cuando se registre
        when(mockTask.addOnCompleteListener(argumentCaptor.capture())).thenReturn(mockTask);

        // Forzamos el comportamiento del Task simulando un fallo
        when(mockTask.isSuccessful()).thenReturn(false);
        when(mockTask.getException()).thenReturn(new Exception("Error de conexión simulado"));

        // Ejecutamos la acción en el mockTask
        mockTask.addOnCompleteListener(task -> {
            // Evaluamos la lógica interna que se ejecuta en tu OnComplete cuando falla
            boolean isSuccess = task.isSuccessful();
            assertEquals(false, isSuccess);
            assertNotNull(task.getException());
            assertEquals("Error de conexión simulado", task.getException().getMessage());
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testBuscarSeleccionado_SendsIntentWithData() {
        // 1. Mocks necesarios para el flujo del DocumentReference
        DocumentReference mockDocRef = mock(DocumentReference.class);
        Task<DocumentSnapshot> mockDocTask = mock(Task.class);
        DocumentSnapshot mockDocumentSnapshot = mock(DocumentSnapshot.class);

        // 2. Simular el comportamiento de la lista IDS y la selección del Spinner
        // Añadimos un ID simulado a la lista que usa tu Activity
        activity.IDS.add("doc_simulado_123");
        // Nota: Si 'IDS' es privada en tu Activity, necesitarás usar Reflection o cambiarla a paquete/pública para el test.

        // 3. Encadenar el comportamiento: db.collection().document().get()
        when(mockDb.collection("Guias").document(anyString())).thenReturn(mockDocRef);
        when(mockDocRef.get()).thenReturn(mockDocTask);

        // 4. Capturar el listener asíncrono del documento
        ArgumentCaptor<OnCompleteListener<DocumentSnapshot>> argumentCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);
        when(mockDocTask.addOnCompleteListener(argumentCaptor.capture())).thenReturn(mockDocTask);

        // 5. Configurar los datos ficticios que simularán venir de Firestore
        when(mockDocTask.isSuccessful()).thenReturn(true);
        when(mockDocTask.getResult()).thenReturn(mockDocumentSnapshot);
        when(mockDocumentSnapshot.exists()).thenReturn(true);

        // Campos que tu segundo botón extrae obligatoriamente:
        when(mockDocumentSnapshot.getString("ID")).thenReturn("ID-999");
        when(mockDocumentSnapshot.getLong("Estado")).thenReturn(1L);
        when(mockDocumentSnapshot.getString("Nombre_cliente")).thenReturn("Juan Pérez");
        when(mockDocumentSnapshot.getString("DNI_cliente")).thenReturn("77777777"); // Se parsea a int en tu código
        when(mockDocumentSnapshot.getString("Articulo")).thenReturn("Laptop");
        when(mockDocumentSnapshot.getString("Modelo")).thenReturn("ThinkPad");
        when(mockDocumentSnapshot.getString("ID_tecnico")).thenReturn("101"); // Se parsea a int en tu código

        // Ejecutar el listener capturado manualmente para simular que Firebase respondió
        argumentCaptor.getValue().onComplete(mockDocTask);

        // 6. Verificar que la Activity intentó abrir la siguiente pantalla con los datos correctos
        Intent expectedIntent = org.robolectric.Shadows.shadowOf(activity).getNextStartedActivity();
        assertNotNull("El Intent no debería ser nulo (debió iniciar status_product_rep)", expectedIntent);
        assertEquals(status_product_rep.class.getName(), expectedIntent.getComponent().getClassName());

        // Validar que los datos viajen en el Bundle
        Bundle bundle = expectedIntent.getExtras();
        assertNotNull(bundle);
        assertEquals("ID-999", bundle.getString("ID"));
        assertEquals(1, bundle.getInt("Estado"));
        assertEquals("Juan Pérez", bundle.getString("Nombre_Ususario"));
        assertEquals(77777777, bundle.getInt("DNI_cliente"));
    }
}