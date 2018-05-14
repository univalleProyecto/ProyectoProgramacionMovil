package com.example.javi.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.javi.myapplication.data.ContratoWar.PersonajeEntry;

/**
 * Created by JAvi on 06/05/2018.
 * * {@link PersonajeCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.

 */

public class PersonajeCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link PersonajeCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public PersonajeCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nombreTextView = view.findViewById(R.id.tvNombre);
        TextView descripcionTextView = view.findViewById(R.id.tvDescripion);

        // Find the columns of pet attributes that we're interested in
        int nombreColumnIndex = cursor.getColumnIndex(PersonajeEntry.COLUMN_PERSONAJE_NOMBRE);
        int descripcionColumnIndex = cursor.getColumnIndex(PersonajeEntry.COLUMN_PERSONAJE_DESCRIPCION);

        // Read the pet attributes from the Cursor for the current pet
        String personajeNombre = cursor.getString(nombreColumnIndex);
        String personajeDescripcion = cursor.getString(descripcionColumnIndex);

        // Update the TextViews with the attributes for the current pet
        nombreTextView.setText(personajeNombre);
        descripcionTextView.setText(personajeDescripcion);
    }
}
