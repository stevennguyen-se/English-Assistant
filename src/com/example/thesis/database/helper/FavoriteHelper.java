package com.example.thesis.database.helper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SdCardPath")
public class FavoriteHelper extends SQLiteOpenHelper{

	private static String DATABASE_PATH = "/data/data/com.example.thesis/databases/";
	private static String DATABASE_NAME = "FavoriteDatabase";
	
	public static final String TABLE_NAME = "FavoriteTable";
	private static final int DATABASE_VERSION = 12;
	
	public static final String ID = "ID";
	public static final String WORD = "WORD";
	public static final String PRONUNCIATION = "PRONUNCIATION";
	public static final String TYPE = "TYPE";
	public static final String CONTENT = "CONTENT";
	
	public static final String DAYS_OF_WEEK = "DAYS_OF_WEEK";
	public static final String WEEKS_OF_YEAR = "WEEK_OF_YEAR";
	public static final String DAY = "DAY";
	public static final String MONTH = "MONTH";
	public static final String YEAR = "YEAR";
	
	public static final String NUMBER_OF_GUESSING_WRONG = "NUMBER_OF_GUESSING_WRONG";
	
//	private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("+ID+" INTEGER, "
//																					+DAYS_OF_WEEK+" VARCHAR(10), "+WEEKS_OF_YEAR+" INTEGER, "
//																					+DAY+" INTEGER, "+MONTH+" INTEGER, "+YEAR+" INTEGER, "
//																					+WORD+" VARCHAR(255), "+PRONUNCIATION+" VARCHAR(255), "+TYPE+" VARCHAR(255), "+CONTENT+" VARCHAR(255), "
//																					+NUMBER_OF_GUESSING_WRONG+" INTEGER, "
//																					+"PRIMARY KEY ("+ID+", "+WORD+"));";
	
//	private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	private Context mContext;
	private SQLiteDatabase mSQLiteDatabase;
	
	public FavoriteHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
		
	}
	
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created
			// into the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}
	
	/**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
	private boolean checkDataBase(){
		 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DATABASE_PATH + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
	
	/**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
	private void copyDataBase() throws IOException{
		 
    	//Open your local db as the input stream
    	InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DATABASE_PATH + DATABASE_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
	
	public void openDataBase() throws SQLException{
		 
    	//Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
    	mSQLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(mSQLiteDatabase != null)
    		    mSQLiteDatabase.close();
 
    	    super.close();
 
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
