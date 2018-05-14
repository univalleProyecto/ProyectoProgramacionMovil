package com.example.javi.myapplication.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JAvi on 24/04/2018.
 */

public class ContratoWar {
    //crear un constructor por defecto vacio y privado
    private ContratoWar() {
    }

/**
     //clase que va a servir como auxiliar para definir el esquema de nuestra tabla Personaje
     //Antiguo:
    public static final class PersonajeEntry implements BaseColumns {

        public static final String TABLE_NAME = "personaje";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PERSONAJE_NOMBRE = "nombre";
        public static final String COLUMN_PERSONAJE_VIDA = "vida";
        public static final String COLUMN_PERSONAJE_GENERO = "genero";
        public static final String COLUMN_PERSONAJE_DESCRIPCION = "descripcion";

        public static final int GENERO_PERSONAJE_HOMBRE = 0;
        public static final int GENERO_PERSONAJE_MUJER = 1;

 **/

        /**
         * The "Content authority" is a name for the entire content provider, similar to the
         * relationship between a domain name and its website.  A convenient string to use for the
         * content authority is the package name for the app, which is guaranteed to be unique on the
         * device.
         */
        public static final String CONTENT_AUTHORITY = "com.example.javi.myapplication";

        /**
         * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
         * the content provider.
         */
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        /**
         * Possible path (appended to base content URI for possible URI's)
         * For instance, content://com.example.android.pets/pets/ is a valid path for
         * looking at pet data. content://com.example.android.pets/staff/ will fail,
         * as the ContentProvider hasn't been given any information on what to do with "staff".
         */
        public static final String PATH_PERSONAJE = "personaje";

        /**
         * Inner class that defines constant values for the personaje database table.
         * Each entry in the table represents a single personaje.
         */
        public static final class PersonajeEntry implements BaseColumns {

            /** The content URI to access the personaje data in the provider */
            public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PERSONAJE);

            /**
             * The MIME type of the {@link #CONTENT_URI} for a list of personajes.
             */
            public static final String CONTENT_LIST_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSONAJE;

            /**
             * The MIME type of the {@link #CONTENT_URI} for a single personaje.
             */
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSONAJE;

            /** Name of database table for personaje */
            public final static String TABLE_NAME = "personaje";

            /**
             * Unique ID number for the personaje (only for use in the database table).
             *
             * Type: INTEGER
             */
            public final static String _ID = BaseColumns._ID;

            /**
             * Name of the personaje.
             *
             * Type: TEXT
             */
            public final static String COLUMN_PERSONAJE_NOMBRE ="nombre";

            /**
             * Descripcion of the personaje.
             *
             * Type: TEXT
             */
            public final static String COLUMN_PERSONAJE_DESCRIPCION = "descripcion";

            /**
             * Gender of the personaje
             *
             * The only possible values are {@link #GENERO_PERSONAJE_DESCONOCIDO}, {@link #GENERO_PERSONAJE_HOMBRE},
             * or {@link #GENERO_PERSONAJE_MUJER}.
             *
             * Type: INTEGER
             */
            public final static String COLUMN_PERSONAJE_GENERO = "genero";

            /**
             * Weight of the pet.
             *
             * Type: INTEGER
             */
            public final static String COLUMN_PERSONAJE_VIDA = "vida";

            /**
             * Possible values for the gender of the pet.
             */
            public static final int GENERO_PERSONAJE_DESCONOCIDO = 3;
            public static final int GENERO_PERSONAJE_HOMBRE = 0;
            public static final int GENERO_PERSONAJE_MUJER = 1;

            /**
             * Returns whether or not the given gender is {@link #GENERO_PERSONAJE_DESCONOCIDO}, {@link #GENERO_PERSONAJE_HOMBRE},
             * or {@link #GENERO_PERSONAJE_MUJER}.
             */
            public static boolean isValidGender(int genero) {
                if (genero == GENERO_PERSONAJE_DESCONOCIDO || genero == GENERO_PERSONAJE_HOMBRE || genero == GENERO_PERSONAJE_MUJER) {
                    return true;
                }
                return false;
            }
        }
}
