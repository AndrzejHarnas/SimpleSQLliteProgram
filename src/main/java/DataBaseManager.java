import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseManager {

    private static final String DEBUG_TAG="DataBaseMANAGER";

    private static final int DB_VERSION = 1;
    private static final String DB_Name = "database.db";
    private static final String DB_USERS_TABLE = "users";

    public static final String KEY_ID = "_id";
    public static final String ID_OPTION = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String USER_NAME = "Name";
    public static final String USER_OPTION = "TEXT NOT NULL";
    public static final int USER_NAME_COLUMN = 1;
    public static final String PASSWORD = "Password";
    public static final String PASSWORD_OPTION = "TEXT NOT NULL";
    public static final int PASSWORD_COLUMN = 2;

    private static final String DB_CREATE_USERS_TABLE = "CREATE TABLE " + DB_USERS_TABLE + "( " + KEY_ID +
            " " + ID_OPTION + ", " + USER_NAME + " " + USER_OPTION + ", " + PASSWORD + " " + PASSWORD_OPTION + ");";

    private static final String DB_DROP_USERS_TABLE = "DROP TABLE IF EXISTS " +DB_USERS_TABLE;

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;


    public DataBaseManager(Context context){

        this.context = context;

    }

    public DataBaseManager open() {
        dbHelper= new DatabaseHelper(context, DB_Name,null,DB_VERSION);

        try {

            db=dbHelper.getWritableDatabase();

        } catch(SQLException e){

            db=dbHelper.getReadableDatabase();

        }

        return this;
    }

    public void close() {
        dbHelper.close();
    }


    public long insertTodo(String name, String password) {
        ContentValues newTodoValues = new ContentValues();
        newTodoValues.put(USER_NAME, name);
        newTodoValues.put(USER_NAME, password);
        return db.insert(DB_USERS_TABLE,null,newTodoValues);
    }


    public boolean updateTodo(TodoTask task) {
        long id = task.getId();
        String name = task.getName();
        String password = task.getPassword();
        return updateTodo(id,name,password);
    }

    public boolean updateTodo(long id, String name, String password){
        String where = KEY_ID +"="+id;
        ContentValues updateTodoValues = new ContentValues();
        updateTodoValues.put(USER_NAME,name);
        updateTodoValues.put(PASSWORD,password);
        return db.update(DB_USERS_TABLE,updateTodoValues,where,null) > 0;

    }

    public boolean deleteTodo(long id){
        String where = KEY_ID + "="+ id;
        return db.delete(DB_USERS_TABLE,where,null) >0;
    }

    



    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

            super(context, name, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DB_CREATE_USERS_TABLE);

            Log.d(DEBUG_TAG,"Database creating...");
            Log.d(DEBUG_TAG,"Table"+ DB_USERS_TABLE + "ver. "+DB_VERSION +" created");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DB_DROP_USERS_TABLE);
            Log.d(DEBUG_TAG,"Database updating...");
            Log.d(DEBUG_TAG,"Table "+ DB_DROP_USERS_TABLE+ "updated from ver. "+ oldVersion+ " to ver."+ newVersion);
            Log.d(DEBUG_TAG,"All data is lost");

            onCreate(db);

        }


    }
}