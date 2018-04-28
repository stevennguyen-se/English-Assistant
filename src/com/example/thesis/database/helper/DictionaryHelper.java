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
import android.util.Log;

public class DictionaryHelper extends SQLiteOpenHelper {
	
	@SuppressLint("SdCardPath")
	private static String DATABASE_PATH = "/data/data/com.example.thesis/databases/";
	private static String DATABASE_NAME = "DictionaryDatabase";
	
	public static final String TABLE_NAME_NONE = "WordNONE";
	public static final String TABLE_NAME_A = "WordA";
	public static final String TABLE_NAME_B = "WordB";
	public static final String TABLE_NAME_C = "WordC";
	public static final String TABLE_NAME_D = "WordD";
	public static final String TABLE_NAME_E = "WordE";
	public static final String TABLE_NAME_F = "WordF";
	public static final String TABLE_NAME_G = "WordG";
	public static final String TABLE_NAME_H = "WordH";
	public static final String TABLE_NAME_I = "WordI";
	public static final String TABLE_NAME_J = "WordJ";
	public static final String TABLE_NAME_K = "WordK";
	public static final String TABLE_NAME_L = "WordL";
	public static final String TABLE_NAME_M = "WordM";
	public static final String TABLE_NAME_N = "WordN";
	public static final String TABLE_NAME_O = "WordO";
	public static final String TABLE_NAME_P = "WordP";
	public static final String TABLE_NAME_Q = "WordQ";
	public static final String TABLE_NAME_R = "WordR";
	public static final String TABLE_NAME_S = "WordS";
	public static final String TABLE_NAME_T = "WordT";
	public static final String TABLE_NAME_U = "WordU";
	public static final String TABLE_NAME_V = "WordV";
	public static final String TABLE_NAME_W = "WordW";
	public static final String TABLE_NAME_X = "WordX";
	public static final String TABLE_NAME_Y = "WordY";
	public static final String TABLE_NAME_Z = "WordZ";
	
	
	public static final String MAJOR_NONE = "MAJOR_NONE";
	
	public static final String MAJOR_ECONOMY_NONE = "ECONOMY_NONE";
	public static final String MAJOR_ECONOMY_A = "ECONOMY_A";
	public static final String MAJOR_ECONOMY_B = "ECONOMY_B";
	public static final String MAJOR_ECONOMY_C = "ECONOMY_C";
	public static final String MAJOR_ECONOMY_D = "ECONOMY_D";
	public static final String MAJOR_ECONOMY_E = "ECONOMY_E";
	public static final String MAJOR_ECONOMY_F = "ECONOMY_F";
	public static final String MAJOR_ECONOMY_G = "ECONOMY_G";
	public static final String MAJOR_ECONOMY_H = "ECONOMY_H";
	public static final String MAJOR_ECONOMY_I = "ECONOMY_I";
	public static final String MAJOR_ECONOMY_J = "ECONOMY_J";
	public static final String MAJOR_ECONOMY_K = "ECONOMY_K";
	public static final String MAJOR_ECONOMY_L = "ECONOMY_L";
	public static final String MAJOR_ECONOMY_M = "ECONOMY_M";
	public static final String MAJOR_ECONOMY_N = "ECONOMY_N";
	public static final String MAJOR_ECONOMY_O = "ECONOMY_O";
	public static final String MAJOR_ECONOMY_P = "ECONOMY_P";
	public static final String MAJOR_ECONOMY_Q = "ECONOMY_Q";
	public static final String MAJOR_ECONOMY_R = "ECONOMY_R";
	public static final String MAJOR_ECONOMY_S = "ECONOMY_S";
	public static final String MAJOR_ECONOMY_T = "ECONOMY_T";
	public static final String MAJOR_ECONOMY_U = "ECONOMY_U";
	public static final String MAJOR_ECONOMY_V = "ECONOMY_V";
	public static final String MAJOR_ECONOMY_W = "ECONOMY_W";
	public static final String MAJOR_ECONOMY_X = "ECONOMY_X";
	public static final String MAJOR_ECONOMY_Y = "ECONOMY_Y";
	public static final String MAJOR_ECONOMY_Z = "ECONOMY_Z";
	
	public static final String MAJOR_TECHNOLOGY_NONE = "TECHNOLOGY_NONE";
	public static final String MAJOR_TECHNOLOGY_A = "TECHNOLOGY_A";
	public static final String MAJOR_TECHNOLOGY_B = "TECHNOLOGY_B";
	public static final String MAJOR_TECHNOLOGY_C = "TECHNOLOGY_C";
	public static final String MAJOR_TECHNOLOGY_D = "TECHNOLOGY_D";
	public static final String MAJOR_TECHNOLOGY_E = "TECHNOLOGY_E";
	public static final String MAJOR_TECHNOLOGY_F = "TECHNOLOGY_F";
	public static final String MAJOR_TECHNOLOGY_G = "TECHNOLOGY_G";
	public static final String MAJOR_TECHNOLOGY_H = "TECHNOLOGY_H";
	public static final String MAJOR_TECHNOLOGY_I = "TECHNOLOGY_I";
	public static final String MAJOR_TECHNOLOGY_J = "TECHNOLOGY_J";
	public static final String MAJOR_TECHNOLOGY_K = "TECHNOLOGY_K";
	public static final String MAJOR_TECHNOLOGY_L = "TECHNOLOGY_L";
	public static final String MAJOR_TECHNOLOGY_M = "TECHNOLOGY_M";
	public static final String MAJOR_TECHNOLOGY_N = "TECHNOLOGY_N";
	public static final String MAJOR_TECHNOLOGY_O = "TECHNOLOGY_O";
	public static final String MAJOR_TECHNOLOGY_P = "TECHNOLOGY_P";
	public static final String MAJOR_TECHNOLOGY_Q = "TECHNOLOGY_Q";
	public static final String MAJOR_TECHNOLOGY_R = "TECHNOLOGY_R";
	public static final String MAJOR_TECHNOLOGY_S = "TECHNOLOGY_S";
	public static final String MAJOR_TECHNOLOGY_T = "TECHNOLOGY_T";
	public static final String MAJOR_TECHNOLOGY_U = "TECHNOLOGY_U";
	public static final String MAJOR_TECHNOLOGY_V = "TECHNOLOGY_V";
	public static final String MAJOR_TECHNOLOGY_W = "TECHNOLOGY_W";
	public static final String MAJOR_TECHNOLOGY_X = "TECHNOLOGY_X";
	public static final String MAJOR_TECHNOLOGY_Y = "TECHNOLOGY_Y";
	public static final String MAJOR_TECHNOLOGY_Z = "TECHNOLOGY_Z";
	
	
	public static final String MAJOR_ENGINEERING_AND_CONSTRUCTION = "ENGINEERING_AND_CONSTRUCTION";
	public static final String MAJOR_TEXTILES = "TEXTILES";
	public static final String MAJOR_ELECTRONIC = "ELECTRONIC";
	public static final String MAJOR_REFRIGERATION = "REFRIGERATION";
	public static final String MAJOR_ELECTRONICS_AND_TELECOMMUNICATIONS = "ELECTRONICS_AND_TELECOMMUNICATIONS";
	public static final String MAJOR_MEASUREMENT_AND_CONTROL = "MEASUREMENT_AND_CONTROL";
	public static final String MAJOR_TRANSPORTATION = "TRANSPORTATION";
	public static final String MAJOR_CHEMISTRY_AND_MATERIALS = "CHEMISTRY_AND_MATERIALS";
	public static final String MAJOR_ENVIRONMENT = "ENVIRONMENT";
	public static final String MAJOR_CAR = "CAR";
	public static final String MAJOR_THTP = "THTP";
	public static final String MAJOR_FOOD = "FOOD";

	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_NONE = "MATHEMATICS_AND_INFORMATION_NONE";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_A = "MATHEMATICS_AND_INFORMATION_A";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_B = "MATHEMATICS_AND_INFORMATION_B";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_C = "MATHEMATICS_AND_INFORMATION_C";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_D = "MATHEMATICS_AND_INFORMATION_D";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_E = "MATHEMATICS_AND_INFORMATION_E";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_F = "MATHEMATICS_AND_INFORMATION_F";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_G = "MATHEMATICS_AND_INFORMATION_G";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_H = "MATHEMATICS_AND_INFORMATION_H";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_I = "MATHEMATICS_AND_INFORMATION_I";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_J = "MATHEMATICS_AND_INFORMATION_J";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_K = "MATHEMATICS_AND_INFORMATION_K";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_L = "MATHEMATICS_AND_INFORMATION_L";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_M = "MATHEMATICS_AND_INFORMATION_M";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_N = "MATHEMATICS_AND_INFORMATION_N";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_O = "MATHEMATICS_AND_INFORMATION_O";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_P = "MATHEMATICS_AND_INFORMATION_P";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_Q = "MATHEMATICS_AND_INFORMATION_Q";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_R = "MATHEMATICS_AND_INFORMATION_R";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_S = "MATHEMATICS_AND_INFORMATION_S";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_T = "MATHEMATICS_AND_INFORMATION_T";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_U = "MATHEMATICS_AND_INFORMATION_U";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_V = "MATHEMATICS_AND_INFORMATION_V";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_W = "MATHEMATICS_AND_INFORMATION_W";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_X = "MATHEMATICS_AND_INFORMATION_X";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_Y = "MATHEMATICS_AND_INFORMATION_Y";
	public static final String MAJOR_MATHEMATICS_AND_INFORMATION_Z = "MATHEMATICS_AND_INFORMATION_Z";
	
	public static final String MAJOR_PHYSICS = "PHYSICS";
	
	public static final String MAJOR_CONSTRUCTION_NONE = "CONSTRUCTION_NONE";
	public static final String MAJOR_CONSTRUCTION_A = "CONSTRUCTION_A";
	public static final String MAJOR_CONSTRUCTION_B = "CONSTRUCTION_B";
	public static final String MAJOR_CONSTRUCTION_C = "CONSTRUCTION_C";
	public static final String MAJOR_CONSTRUCTION_D = "CONSTRUCTION_D";
	public static final String MAJOR_CONSTRUCTION_E = "CONSTRUCTION_E";
	public static final String MAJOR_CONSTRUCTION_F = "CONSTRUCTION_F";
	public static final String MAJOR_CONSTRUCTION_G = "CONSTRUCTION_G";
	public static final String MAJOR_CONSTRUCTION_H = "CONSTRUCTION_H";
	public static final String MAJOR_CONSTRUCTION_I = "CONSTRUCTION_I";
	public static final String MAJOR_CONSTRUCTION_J = "CONSTRUCTION_J";
	public static final String MAJOR_CONSTRUCTION_K = "CONSTRUCTION_K";
	public static final String MAJOR_CONSTRUCTION_L = "CONSTRUCTION_L";
	public static final String MAJOR_CONSTRUCTION_M = "CONSTRUCTION_M";
	public static final String MAJOR_CONSTRUCTION_N = "CONSTRUCTION_N";
	public static final String MAJOR_CONSTRUCTION_O = "CONSTRUCTION_O";
	public static final String MAJOR_CONSTRUCTION_P = "CONSTRUCTION_P";
	public static final String MAJOR_CONSTRUCTION_Q = "CONSTRUCTION_Q";
	public static final String MAJOR_CONSTRUCTION_R = "CONSTRUCTION_R";
	public static final String MAJOR_CONSTRUCTION_S = "CONSTRUCTION_S";
	public static final String MAJOR_CONSTRUCTION_T = "CONSTRUCTION_T";
	public static final String MAJOR_CONSTRUCTION_U = "CONSTRUCTION_U";
	public static final String MAJOR_CONSTRUCTION_V = "CONSTRUCTION_V";
	public static final String MAJOR_CONSTRUCTION_W = "CONSTRUCTION_W";
	public static final String MAJOR_CONSTRUCTION_X = "CONSTRUCTION_X";
	public static final String MAJOR_CONSTRUCTION_Y = "CONSTRUCTION_Y";
	public static final String MAJOR_CONSTRUCTION_Z = "CONSTRUCTION_Z";
			
	public static final String MAJOR_MEDICINE = "MEDICINE";
	
	private static final int DATABASE_VERSION = 52;
	
	public static final String ID = "ID";
	public static final String WORD = "WORD";
	public static final String PRONUNCIATION = "PRONUNCIATION";
	public static final String TYPE = "TYPE";
	public static final String CONTENT = "CONTENT";
	
	private Context mContext;
	private SQLiteDatabase mSQLiteDatabase;
	
	public DictionaryHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		this.mContext = context;
	}
	
	/**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
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
    		Log.v("Cannot open Dictionary Database", "Cannot open Dictionary Database");
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
