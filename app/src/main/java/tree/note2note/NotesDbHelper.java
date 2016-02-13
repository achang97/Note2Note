package tree.note2note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by regina on 2/13/16.
 * Class to obtain references to database in
 * more time-efficient way by inheriting from SQLiteOpenHelper
 */
public class NotesDbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "notes_db";
    private static final int DB_VERSION = 1;

    private static final String DB_CREATE =
            "CREATE TABLE tbl_info ( " +
                    "_id integer PRIMARY KEY AUTOINCREMENT, " +
                    "name text NOT NULL, " +
                    "class text NOT NULL, " +
                    "owner text NOT NULL" +
                    ");";

    private static final String DB_DESTROY =
            "DROP TABLE IF EXISTS tbl_info";

    public NotesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DB_DESTROY);
        onCreate(db);
    }
//
//    public final class NotesDataContract {
//
//        public NotesDataContract() {}
//
//        /* Inner class that defines the table contents */
//        public static abstract class NotesData implements BaseColumns {
//            public static final String TABLE_NAME = "notes";
//            public static final String NOTE_ID = "noteid";
//            public static final String NOTE_NAME = "name";
//            public static final String NOTE_CLASS = "class";
//            public static final String NOTE_OWNER = "owner";
//        }
//
//        private static final String TEXT_TYPE = " TEXT";
//        private static final String COMMA_SEP = ",";
//        private static final String SQL_CREATE_ENTRIES =
//                "CREATE TABLE " + NotesData.TABLE_NAME + " (" +
//                        NotesData._ID + " INTEGER PRIMARY KEY," +
//                        NotesData.NOTE_ID + TEXT_TYPE + COMMA_SEP +
//                        NotesData.NOTE_NAME + TEXT_TYPE + COMMA_SEP +
//                        NotesData.NOTE_CLASS + TEXT_TYPE + COMMA_SEP +
//                        NotesData.NOTE_OWNER + TEXT_TYPE + COMMA_SEP +
//                        " )";
//
//        private static final String SQL_DELETE_ENTRIES =
//                "DROP TABLE IF EXISTS " + NotesData.TABLE_NAME;
//
//
//
//        public class NotesDataDbHelper extends SQLiteOpenHelper {
//            public static final int DATABASE_VERSION = 1;
//            public static final String DATABASE_NAME = "NotesData.db";
//
//            public NotesDataDbHelper(Context context) {
//                super(context, DATABASE_NAME, null, DATABASE_VERSION);
//            }
//
//            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//                onUpgrade(db, oldVersion, newVersion);
//            }
//        }
//
//    }

}
