package com.example.javi.myapplication;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
//importamos la clase PersonajeEntry para facilitar
import com.example.javi.myapplication.data.ContratoWar.PersonajeEntry;
import com.example.javi.myapplication.data.WarHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    //nuevo
    private static final int PERSONAJE_LOADER = 0;
    PersonajeCursorAdapter mCursorAdapter;

    private WarHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configurando FAB para abrir Insertar:
        FloatingActionButton fab = findViewById(R.id.fabInsertar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Insertar.class);
                startActivity(intent);
            }
        });
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper=new WarHelper(this);

        // Find the ListView which will be populated with the personaje data
        ListView personajeListView = findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        personajeListView.setEmptyView(emptyView);

        //nuevo
        mCursorAdapter=new PersonajeCursorAdapter(this,null);
        personajeListView.setAdapter(mCursorAdapter);
        //Nuevo
        getLoaderManager().initLoader(PERSONAJE_LOADER,null,this);


        //nuevo
        //setup item click listener (implementar metodo)
        /*
            personajeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            personajeListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, Insertar.class);
            Uri currentPersonajeUri = ContentUris.withAppendedId(PersonajeEntry.CONTENT_URI, id);
            intent.setData(currentPersonajeUri);
            startActivity(intent);
        }); */
        personajeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,Insertar.class);
                Uri currentPersonajeUri = ContentUris.withAppendedId(PersonajeEntry.CONTENT_URI, id);
                intent.setData(currentPersonajeUri);
                startActivity(intent);
            }
        });

    }

    /* nuevo ya no se usara
    //actualizar lista personajes
    @Override
    protected void onStart() {
        super.onStart();
        displayConsulta();
        displayDatabaseInfo();
    }
    */

    //Metodo para insertar datos a la tabla
    public void datosPrueba(){

        SQLiteDatabase db=mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PersonajeEntry.COLUMN_PERSONAJE_NOMBRE,"paladin");
        values.put(PersonajeEntry.COLUMN_PERSONAJE_VIDA,100);
        values.put(PersonajeEntry.COLUMN_PERSONAJE_GENERO, PersonajeEntry.GENERO_PERSONAJE_HOMBRE);
        values.put(PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION,"gerrero humano de fuerza");

        /*
        //Forma antigua
        long newRowId=db.insert(PersonajeEntry.TABLE_NAME,null,values);
        */
        Uri newUri = getContentResolver().insert(PersonajeEntry.CONTENT_URI, values);


        //Metodo para mostrar el numero de filas en la tabla personaje en el text view
        displayDatabaseInfo();
        //Metodo para mostrar la consulta en el text view "etConsulta"
        //displayConsulta();
    }

    /* nuevo ya no se usara
    private void displayConsulta() {

        //Se usaba para mostrar los personajes en un textView:
        //TextView displayView = findViewById(R.id.tvConsulta);

        // Crear y/o abrir la BD para leer desde ella:
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Definir una proyeccion que especifique cuales columnas desde la BD
        // se debeeria usar en la consulta

        String[] projection = {
                PersonajeEntry._ID,
                PersonajeEntry.COLUMN_PERSONAJE_NOMBRE,
                PersonajeEntry.COLUMN_PERSONAJE_VIDA,
                PersonajeEntry.COLUMN_PERSONAJE_GENERO,
                PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION };

        // Realizando la consulta en la tabla personaje
        Cursor cursor = db.query(
                PersonajeEntry.TABLE_NAME,   // Nombre de la tabla de la consulta
                projection,                 // Las columnas a retornar
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order


        // Find the ListView which will be populated with the personaje data
        ListView personajeListView = (ListView) findViewById(R.id.list);

        // Setup an Adapter to create a list item for each row of personaje data in the Cursor.
        PersonajeCursorAdapter adapter = new PersonajeCursorAdapter(this, cursor);

        // Attach the adapter to the ListView.
        personajeListView.setAdapter(adapter);

      /*Esta era la forma en la que cargabamos los personajes en el textView
        try {
            // Se va a crear una cabezera en el Text View:
            // La tabla personaje contiene  <numero de filas en el cursor> personaje.
            //
            // _id - nombre - vida - genero - descripcion
            //
            // En el while, se va iterar mientrar haya filas en el cursor, y se va mostrar las filas
            // en el orden descrito:

            displayView.setText("La tabla personaje contiene:  " + cursor.getCount() + " personajes.\n\n");
            displayView.append(PersonajeEntry._ID + " - " +
                    PersonajeEntry.COLUMN_PERSONAJE_NOMBRE + " - " +
                    PersonajeEntry.COLUMN_PERSONAJE_VIDA+" - "+
                    PersonajeEntry.COLUMN_PERSONAJE_GENERO+ " - "+
                    PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION+"\n");

            // Asignando los indices:
            int idColumnIndex = cursor.getColumnIndex(PersonajeEntry._ID);
            int nombreColumnIndex = cursor.getColumnIndex(PersonajeEntry.COLUMN_PERSONAJE_NOMBRE);
            int vidaColumnIndex = cursor.getColumnIndex(PersonajeEntry.COLUMN_PERSONAJE_VIDA);
            int generoColumnIndex = cursor.getColumnIndex(PersonajeEntry.COLUMN_PERSONAJE_GENERO);
            int descripcionClomunIndex = cursor.getColumnIndex(PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION);

            // Se va iterar mientrar existe un retorno de filas en el cursor
            // moveToNext devuelve false si ya no hay filas
            while (cursor.moveToNext()) {
                // Se va usar los indices para extraer el string o int
                // en la fila actual donde el cursor esta
                int currentID = cursor.getInt(idColumnIndex);
                String currentNombre = cursor.getString(nombreColumnIndex);
                int currentVida = cursor.getInt(vidaColumnIndex);
                int currentGenero = cursor.getInt(generoColumnIndex);
                String currentDescripcion = cursor.getString(descripcionClomunIndex);

                // Se muestran los valores desde cada colunda de la fila actual en el cursor en el
                // edit text
                displayView.append(("\n"
                        + currentID + " - "
                        + currentNombre + " - "
                        + currentVida + " - "
                        + currentGenero + " - "
                        + currentDescripcion ));
            }
        } finally {
            // Siempre cierre el cursor cuando termine de leer de él.
            // Esto libera todos sus recursos y lo invalida.
            cursor.close();
        }

    }
    */
    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM " + PersonajeEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.tvTexto);
            displayView.setText("Numero total de personajes: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    //Metodo para abrir otra activity
    public void insertarDatos(View v){
        Intent intent = new Intent(this, Insertar.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.accion_insertar_datos_ejemplo:
                datosPrueba();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.accion_borrar_todos_personajes:
                // Por ahora nada
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        // Definir una proyeccion que especifique cuales columnas desde la BD
        // se debeeria usar en la consulta

        String[] projection = {
                PersonajeEntry._ID,
                PersonajeEntry.COLUMN_PERSONAJE_NOMBRE,
                PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION };

        // Realizando la consulta en la tabla personaje
        return new CursorLoader(this,
                PersonajeEntry.CONTENT_URI,   // Nombre de la tabla de la consulta
                projection,                 // Las columnas a retornar
                null,                  // No selection clause
                null,                  // No selection arguments
                null);                   // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //update {@link PersonajeCursorAdapter} with this new cursor containing update personaje data
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);

    }
}
