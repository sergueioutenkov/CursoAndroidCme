package com.serguei.cursos.cursoandroidcme;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //Codigo que identifica nuestra request para pedir permiso
    final private int WRITE_CONTACTS_REQUEST_CODE = 1;

    private Button saveContactButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveContactButton = (Button) findViewById(R.id.save_contact_button);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                insertContact();
                insertContactWithPermission();
            }
        });
    }

    /**
     * Metodo que pregunta por el permiso antes de insertar un contacto
     */
    private void insertContactWithPermission() {

        //Chequeamos si el permiso WRITE_CONTACTS no ha sido otorgado
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

            // Con lo cual chequeamos si hace flata un mensaje para orientar al usuario de porque
            // deberia darnos permiso
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_CONTACTS)) {
                //Mostrar el mensaje "orientador"
                showMessageOKCancel("You need to allow access to Contacts in order to save a new contact",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si hace click en "OK", pedir el permiso
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.WRITE_CONTACTS},
                                        WRITE_CONTACTS_REQUEST_CODE);
                            }
                        });
            } else {

                //Directamente pedimos el permiso, con el nombre del permiso, y el codigo identificatorio
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_CONTACTS},
                        WRITE_CONTACTS_REQUEST_CODE);

            }
        } else {
            //El permiso fue otorgado, podemos insertar el contacto sin problemas
            insertContact();
        }
    }

    /**
     * Metodo que genera un contacto y lo guarda
     * Requiere el permiso WRITE_CONTACTS
     */
    private void insertContact() {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "Sample contact");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
            Toast.makeText(MainActivity.this, "Contact inserted!", Toast.LENGTH_SHORT)
                    .show();
        } catch (RemoteException | OperationApplicationException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }

    /**
     * Metodo de utileria para mostrar un dialog de tipo OK-Cancel
     *
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * Metodo que lleva al usuario a la pagina de App Settings
     *
     * @param context
     */
    private void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    /**
     * Callback que se ejecuta cuando el usuario acepta o declina un permiso
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Hacemos switch, porque podriamos pedir distintos permisos en una misma activity
        switch (requestCode) {
            case WRITE_CONTACTS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso otorgado, insertamos el contacto
                    insertContact();
                } else {
                    //Aunque android no quiera mostrar mas info, podemos forzarlo a mostrar un dialogo al usuario explicando que necesitamos si o si el permiso
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_CONTACTS)) {
                        showMessageOKCancel("Please give the app permission from settings in order to add a contact", new Dialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Si el usuario hace click en OK, lo redirigimos a la pagina de las settings de nuestra app.
                                startInstalledAppDetailsActivity(MainActivity.this);
                            }
                        });
                    } else {
                        //Permiso negado, mostramos un mensaje o hacemos lo que queremos.
                        Toast.makeText(MainActivity.this, "WRITE_CONTACTS permission denied :(", Toast.LENGTH_SHORT)
                                .show();

                    }
                }
                break;
        }
    }
}
