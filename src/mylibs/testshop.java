package mylibs;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class testshop {

	// the Activity or Application that is creating an object from this class.
	Context context;

	// a reference to the database used by this application/object
	public static SQLiteDatabase db;
	public static  CustomSQLiteOpenHelper helper;

	// These constants are specific to the database. They should be
	// changed to suit your needs.
	private final String DB_NAME = "ShoppingList";
	private final int DB_VERSION = 1;

	// These constants are specific to the database table. They should be
	// changed to suit your needs.
	public final static String TABLE_NAME = "items";
	public final static String TABLE_ROW_ID = "id";
	public final static String TABLE_ROW_ONE = "table_row_one";
	public final static String TABLE_ROW_TWO = "table_row_two";

	public testshop(Context context) {
		this.context = context;

		// create or open the database
		helper = new CustomSQLiteOpenHelper(context);
		this.db = helper.getWritableDatabase();
		
	}

	// This(addRow()) is the only method I'm using so far as Im only trying to
	// add data to the database
	public void addRow(String rowStringOne, String rowStringTwo) {
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_ONE, rowStringOne);
		values.put(TABLE_ROW_TWO, rowStringTwo);

		// ask the database object to insert the new data
		try {db = helper.getWritableDatabase();
			db.insert(TABLE_NAME, null, values);
		} catch (Exception e) {
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}
	}

	/*
	 * public Cursor getRowValues(String tableName, String[] columns) {
	 * 
	 * columns = new String [] {TABLE_ROW_ID,TABLE_ROW_ONE,TABLE_ROW_TWO};
	 * Cursor cursor = db.rawQuery(tableName, columns); return cursor; } 
	 * 
	 * public String getWhereClause(String compareColumn, String compareValue) { String
	 * whereClause = null; if (compareColumn == null || compareColumn == "") { }
	 * else if (compareValue == null || compareColumn == "") { } else {
	 * whereClause = compareColumn + "=\"" + compareValue + "\""; } return
	 * whereClause; } */
	 

	public static void deleteRow(String tableName,String compareColumn, String compareValue) {
		// ask the database manager to delete the row of given id
		try{
			db = helper.getWritableDatabase();
			String whereClause = getWhereClause(compareColumn, compareValue);
		
			db.delete(tableName, whereClause, null);
			//db.delete(TABLE_NAME, TABLE_ROW_ID + "=" + rowID, null);
		} catch (Exception e) {
			Log.e("DB DELETE ERROR", e.toString());
			e.printStackTrace();
			db.close();
		}
	}

	private static String getWhereClause(String compareColumn, String compareValue) {
		String whereClause = null;
		if (compareColumn == null || compareColumn == "") { }
		else if (compareValue == null || compareColumn == "") { }
		else { whereClause = compareColumn + "=\"" + compareValue + "\""; }
		
		return whereClause;
		
	}
 
	public void updateRow(long rowID, String rowStringOne, String rowStringTwo) {
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_ONE, rowStringOne);
		values.put(TABLE_ROW_TWO, rowStringTwo);

		// ask the database object to update the database row of given rowID
		try {
			db.update(TABLE_NAME, values, TABLE_ROW_ID + "=" + rowID, null);
		} catch (Exception e) {
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}
	}

	public ArrayList<Object> getRowAsArray(long rowID) {
		ArrayList<Object> rowArray = new ArrayList<Object>();
		Cursor cursor;

		try {
			// this is a database call that creates a "cursor" object.
			// the cursor object stores the information collected from the
			// database and is used to iterate through the data.
			cursor = db.query(TABLE_NAME, new String[] { TABLE_ROW_ID,
					TABLE_ROW_ONE, TABLE_ROW_TWO }, TABLE_ROW_ID + "=" + rowID,
					null, null, null, null, null);

			// move the pointer to position zero in the cursor.
			cursor.moveToFirst();

			// if there is data available after the cursor's pointer, add
			// it to the ArrayList that will be returned by the method.
			if (!cursor.isAfterLast()) {
				do {
					rowArray.add(cursor.getLong(0));
					rowArray.add(cursor.getString(1));
					rowArray.add(cursor.getString(2));
				} while (cursor.moveToNext());
			}

			// let java know that you are through with the cursor.
			cursor.close();
		} catch (SQLException e) {
			Log.e("DB ERROR", e.toString());
			e.printStackTrace();
		}

		// return the ArrayList containing the given row from the database.
		return rowArray;
	}

	public ArrayList<ArrayList<Object>> getAllRowsAsArrays() {
		this.db = helper.getReadableDatabase();
		// create an ArrayList that will hold all of the data collected from
		// the database.
		ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();

		// this is a database call that creates a "cursor" object.
		// the cursor object store the information collected from the
		// database and is used to iterate through the data.
		Cursor cursor;

		try {
			// ask the database object to create the cursor.
			cursor = db.query(TABLE_NAME, new String[] { TABLE_ROW_ID,
					TABLE_ROW_ONE, TABLE_ROW_TWO }, null, null, null, null,
					null);

			// move the cursor's pointer to position zero.
			try {
				if (cursor != null & cursor.getCount() > 0) {
					cursor.moveToFirst();
				}
			} catch (Exception e) {
				Log.e("CURSOR ERROR", e.toString());
				e.printStackTrace();
			}

			// if there is data after the current cursor position, add it
			// to the ArrayList.
			if (!cursor.isAfterLast()) {
				do {
					ArrayList<Object> dataList = new ArrayList<Object>();

					dataList.add(cursor.getLong(0));
					dataList.add(cursor.getString(1));
					dataList.add(cursor.getString(2));

					dataArrays.add(dataList);
				}
				// move the cursor's pointer up one position.
				while (cursor.moveToNext());
			}
		} catch (SQLException e) {
			Log.e("DB Error", e.toString());
			e.printStackTrace();
		}

		// return the ArrayList that holds the data collected from
		// the database.
		return dataArrays;
	}
 
	// the SQLiteOpenHelper class
	public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
		public CustomSQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// This string is used to create the database. It should
			// be changed to suit your needs.
			String newTableQueryString = "create table " + TABLE_NAME + " ("
					+ TABLE_ROW_ID
					+ " integer primary key autoincrement not null,"
					+ TABLE_ROW_ONE + " text," + TABLE_ROW_TWO + " text" + ");";
			// execute the query string to the database.
			db.execSQL(newTableQueryString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// NOTHING TO DO HERE. THIS IS THE ORIGINAL DATABASE VERSION.
			// OTHERWISE, YOU WOULD SPECIFIY HOW TO UPGRADE THE DATABASE.
		}
	}

}