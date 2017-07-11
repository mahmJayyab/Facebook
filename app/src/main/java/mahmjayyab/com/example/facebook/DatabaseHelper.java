    package mahmjayyab.com.example.facebook;


    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;


    public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FacebookVideos.db";
    public static final String TABLE_NAME = "Pages_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PAGENAME";
    public static final String COL_3 = "CHEACK";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
        // id INTEGER PRIMARY KEY AUTOINCREMENT
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PAGENAME TEXT,CHEACK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id,String pagename,String cheack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,pagename);
        contentValues.put(COL_3,cheack);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
        {
            Log.d("aa","Faild");
            return false;
        }
        else
        {
            Log.d("aa","Suc");

            return true;
        }
    }
    public boolean insertData(String pagename,String cheack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1,id);
        contentValues.put(COL_2,pagename);
        contentValues.put(COL_3,cheack);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
        {
            Log.d("aa","Faild");
            return false;
        }
        else
        {
            Log.d("aa","Suc");

            return true;
        }
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String pagename,String cheack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,id);
        contentValues.put(COL_2,pagename);
        contentValues.put(COL_3,cheack);
        db.update(TABLE_NAME, contentValues, "PAGENAME = ?",new String[] { pagename });
        return true;
    }
    /*public boolean updateData(String id,String cheack) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
       // contentValues.put(COL_2,pagename);
        contentValues.put(COL_3,cheack);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }*/

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}