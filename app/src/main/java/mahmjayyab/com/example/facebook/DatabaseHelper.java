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
    public static final String TABLE_HISTORY = "History_table";
    public static final String TABLE_FAV = "Favourite_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PAGENAME";
    public static final String COL_3 = "CHEACK";
    public static final String COL_4 = "PAGEPIC";
    public static final String COL_5 = "PAGECOVER";
    public static final String COL_6 = "LINK";
    public static final String COLL_2 = "PAGENAME";
    public static final String COLL_3 = "TITLE";
    public static final String COLL_4 = "SOURCE";
    public static final String COLL_5 = "PICTURE";
    public static final String COLL_6 = "FAV";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
        // id INTEGER PRIMARY KEY AUTOINCREMENT
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PAGENAME TEXT,CHEACK TEXT,PAGEPIC TEXT,PAGECOVER TEXT,LINK TEXT)");
        db.execSQL("create table " + TABLE_HISTORY +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PAGENAME TEXT,TITLE TEXT,SOURCE TEXT,PICTURE TEXT)");
        db.execSQL("create table " + TABLE_FAV + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,PAGENAME TEXT,TITLE TEXT,SOURCE TEXT,PICTURE TEXT,FAV TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
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
    public  Cursor test(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT name FROM sqlite_master  WHERE type='table'", null);
    }
    public boolean insertData_Pages(String pageLink,String cheack, String pic, String cover,String pageName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,pageLink);
        contentValues.put(COL_3,cheack);
        contentValues.put(COL_4,pic);
        contentValues.put(COL_5,cover);
        contentValues.put(COL_6,pageName);
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
        public boolean insertDataPageInf(String pagename,String cheack,String pagePic,String pageCover) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            // contentValues.put(COL_1,id);
            contentValues.put(COL_2,pagename);
            contentValues.put(COL_3,cheack);
            contentValues.put(COL_4,pagePic);
            contentValues.put(COL_5,pageCover);
            long result = db.insert(TABLE_NAME,null ,contentValues);
            if(result == -1)
            {
                Log.d("ccc","Faild");
                return false;
            }
            else
            {
                Log.d("ccc","Suc");

                return true;
            }
        }
        public boolean insertData(String pagename,String title,String source,String picture) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
             //contentValues.put(COL_1,id);
            contentValues.put(COLL_2,pagename);
            contentValues.put(COLL_3,title);
            contentValues.put(COLL_4,source);
            contentValues.put(COLL_5,picture);
            long result = db.insert(TABLE_HISTORY,null ,contentValues);
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

        public boolean insertDataFave(String pagename, String title, String source, String picture, String fav) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //contentValues.put(COL_1,id);
            contentValues.put(COLL_2, pagename);
            contentValues.put(COLL_3, title);
            contentValues.put(COLL_4, source);
            contentValues.put(COLL_5, picture);
            contentValues.put(COLL_6, fav);
            long result = db.insert(TABLE_FAV, null, contentValues);
            if (result == -1) {
                Log.d("aa", "Faild");
                return false;
            } else {
                Log.d("aa", "Suc");

                return true;
            }
        }


    public Cursor getAllData(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+table,null);
        return res;
    }

    public Cursor getData(String table, String title) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select * from " + table + " where TITLE = ?", new String[]{title}, null);

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
        public boolean updateDataByLink(String link,String cheack) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //contentValues.put(COL_1,id);
            contentValues.put(COL_3,cheack);
            //contentValues.put(COL_4,pagePic);
           // contentValues.put(COL_5,pageCover);
            contentValues.put(COL_6,link);
            db.update(TABLE_NAME, contentValues, "LINK = ?",new String[] { link });
            return true;
        }
        public boolean updateDataByID(String id,String cheack,String pagePic,String pageCover,String link) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,id);
            //contentValues.put(COL_2,pagename);
            contentValues.put(COL_3,cheack);
            contentValues.put(COL_4,pagePic);
            contentValues.put(COL_5,pageCover);
            contentValues.put(COL_6,link);
            db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
            return true;
        }
        public boolean updateDataPic(String id,String pagePic, String cheack) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_1,id);
            //contentValues.put(COL_2,pagename);
            contentValues.put(COL_3,cheack);
            contentValues.put(COL_4,pagePic);
            db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
            return true;
        }

   public Integer deleteData(String title,String table) {
            SQLiteDatabase db = this.getWritableDatabase();
       // db.e
       return db.delete(table, "TITLE = ?", new String[]{title});

        }
   public Integer deleteData_P(String link,String table) {
            SQLiteDatabase db = this.getWritableDatabase();
            // db.e
            return db.delete(table, "PAGENAME = ?", new String[]{link});

        }
    public void deleteDataAll (String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+table);
    }
}