package com.example.javi.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.javi.myapplication.data.ContratoWar;
import com.example.javi.myapplication.data.WarHelper;

public class Insertar extends AppCompatActivity {

    private WarHelper mDbHelper;
    //Crear view del activity:
    private EditText nombre;
    private EditText vida;
    private EditText descripcion;
    private Spinner genero;
    private int mGender = ContratoWar.PersonajeEntry.GENERO_PERSONAJE_HOMBRE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);

        //nuevo
        Intent intent = getIntent();
        Uri currentUri=intent.getData();
        if (currentUri==null){
            setTitle("Agregar un Personaje");
        }else {
            setTitle(getString(R.string.titulo_editar_personaje));
        }
        //nota quitar de manifest el titulo de editar personaje en activity insertar



        mDbHelper=new WarHelper(this);
        nombre=findViewById(R.id.etNombre);
        vida=findViewById(R.id.etVida);
        descripcion=findViewById(R.id.etDescripcion);
        genero=findViewById(R.id.spGenero);
        setupSpiner();

    }

    private void setupSpiner() {
        //antes de hacer esto hay que crear EL ARRAY Y LOS STRING

        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        genero.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.genero_hombre))) {
                        mGender = ContratoWar.PersonajeEntry.GENERO_PERSONAJE_HOMBRE;
                    } else {
                        mGender = ContratoWar.PersonajeEntry.GENERO_PERSONAJE_MUJER;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = ContratoWar.PersonajeEntry.GENERO_PERSONAJE_HOMBRE;
            }
        });
    }

    public void insertarPersonaje(){
        String nombreString= nombre.getText().toString().trim();
        String vidaString=vida.getText().toString().trim();
        int vidaInt=Integer.parseInt(vidaString);
        String descripcionString=descripcion.getText().toString().trim();

        //ahora lo demas es igual que los datos de relleno que usamos
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_NOMBRE, nombreString);
        values.put(ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_VIDA,vidaInt);
        values.put(ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_GENERO, mGender);
        values.put(ContratoWar.PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION, descripcionString);

        /**
         * Antes de provider:
         * long newRowId=db.insert(ContratoWar.PersonajeEntry.TABLE_NAME,null,values);
        **/
        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
        Uri newUri = getContentResolver().insert(ContratoWar.PersonajeEntry.CONTENT_URI, values);

        /*
        //verificar que la insercion exitosa
        if (newRowId==-1 )
        {
            Toast.makeText(this,"Ocurrio un error al intentar guardar",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Personaje guardado correctamente con el id: "+newRowId,Toast.LENGTH_SHORT).show();
        }
        */
        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.insercion_personaje_fallo),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.insercion_personaje_exito),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_insertar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.accion_guardar:
                // Save pet to database
                insertarPersonaje();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.accion_eliminar:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
