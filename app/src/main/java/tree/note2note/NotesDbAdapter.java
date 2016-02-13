package tree.note2note;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * Created by regina on 2/13/16.
 */
public class NotesDbAdapter {
    public static final String TBL_INFO = "tbl_info";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_COURSE = "course";
    public static final String COL_OWNER = "owner";

    // projection on all columns
    private static final String[] PROJECTION_ALL = new String[] {
            COL_ID, COL_NAME, COL_COURSE, COL_OWNER
    };

    public static final int QUERY_TYPE_STRING_ARRAY = 0x01;
    public static final int QUERY_TYPE_NoteINFO_OBJ = 0x02;

    // declared fields
    private Context mContext;
    private SQLiteDatabase mDb;
    private NotesDbHelper mDbHelper;

    public NotesDbAdapter(Context c) {
        mContext = c;
    }

    // close db connection
    public NotesDbAdapter close() {
        mDbHelper.close();
    }
    // open db connection
    public NotesDbAdapter open() throws SQLException {
        mDbHelper = new NotesDbHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    // insert record to db
    public long insertNote(String name, String course, String owner) {
        return mDb.insert(TBL_INFO, null, createContentValues(name, course, owner));
    }
    public long deleteNote(int id) {
        return mDb.delete(TBL_INFO, COL_ID + "=" + id, null);
    }
    // update record to db
    public long updateNote(int id, String name, String course, String owner) {
        return mDb.update(TBL_INFO, createContentValues(name, course, owner), COL_ID + "=" + id, null);
    }

    // query all records
    public List<NoteInfo> fetchAllNotes() {
        // Cursor interface provides random r/w access to result set returned by db query
        Cursor queryCursor = mDb.query(TBL_INFO, PROJECTION_ALL, null, null, null, null, null);
        if(queryCursor == null) {
            Log.d(MainActivity.TAG, "NoteDbAdapter.fetchAllNotes(): queryCursor = null ");
            return null;
        }
        // init list to hold Note info
        List<NoteInfo> listNotes = new ArrayList<>();
        // set cursor to the first element
        queryCursor.moveToFirst();
        // if cursor is not the last element
        while(queryCursor.isAfterLast() == false) {
            // add new Note info
            listNotes.add(new NoteInfo(
                            queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_ID)),
                            queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_NAME)),
                            queryCursor.getInt(queryCursor.getColumnIndexOrThrow(COL_COURSE)),
                            queryCursor.getString(queryCursor.getColumnIndexOrThrow(COL_OWNER))
                    )
            );
            // move cursor to next item
            queryCursor.moveToNext();
        }
        // check if cursor is still opened and not null
        if(queryCursor != null && !queryCursor.isClosed()) {
            // close it to avoid memory leak
            queryCursor.close();
        }
        Log.d(MainActivity.TAG, "NoteDbAdapter.fetchAllNotes(): listNotes.size() = " + listNotes.size());
        // return Note list
        return listNotes;
    }

    // query one record
    public Object fetchSingleNote(int id, int type) {
        // query a cursor on identified note
        Cursor c = mDb.query(true, TBL_INFO, PROJECTION_ALL, COL_ID + "=" + id, null, null, null, null, null);
        // return null if no record avaiable
        if(c == null) {
            return null;
        }

        Object objOut = null;

        if(type == QUERY_TYPE_STRING_ARRAY) {
            // create array to hold note info
            String[] note_info = new String[4];
            note_info[0] = String.valueOf(id);
            note_info[1] = c.getString(c.getColumnIndexOrThrow(COL_NAME));
            note_info[2] = c.getString(c.getColumnIndexOrThrow(COL_COURSE));
            note_info[3] = c.getString(c.getColumnIndexOrThrow(COL_OWNER));
            objOut = note_info;
        } else {
            // create noteInfo object
            NoteInfo note_info = new NoteInfo (
                    id,
                    c.getString(c.getColumnIndexOrThrow(COL_NAME)),
                    c.getInt(c.getColumnIndexOrThrow(COL_COURSE)),
                    c.getString(c.getColumnIndexOrThrow(COL_OWNER))
            );
            objOut = note_info;
        }
        // close cursor
        c.close();

        // return note info
        return objOut;
    }

    //create ContentValues object to use for db transaction
    private ContentValues createContentValues(String name, String course, String owner) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_COURSE, course);
        cv.put(COL_OWNER, owner);
        return cv;
    }
}
