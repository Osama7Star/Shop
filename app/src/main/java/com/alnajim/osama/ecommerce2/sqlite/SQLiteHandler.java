/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.alnajim.osama.ecommerce2.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.alnajim.osama.ecommerce2.Model.mAddress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper
{

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "localDB";

	//  table name
	private static final String TABLE_USER = "user";
	private static final String TABLE_USER_WISHLIST = "WISHLIST";
	private static final String TABLE_USER_BASKET   = "BASKET";
	private static final String TABLE_USER_ADDRESS   = "ADDRESS";






	// Login Table Columns names
	private static final String User_KEY_ID 	     = "id";
	private static final String User_KEY_NAME        = "name";
	private static final String User_KEY_EMAIL       = "email";
	private static final String User_KEY_UID		 = "uid";
	private static final String User_KEY_CREATED_AT  = "created_at";


	// USER SLECTIONS COLUMNS NAME
	private static final String UserSelection_KEY_ID 	      = "id";
	private static final String UserSelection_KEY_PRODUCT_ID  = "productId";
	private static final String UserSelection_KEY_CREATED_AT  = "created_at";

	// ADDRESS COLUMN NAME
	private static final String ADRESS_KEY_ID 	      = "id";
	private static final String ADRESS_CITY 	      = "city";
	private static final String ADRESS_AREA 	      = "area";
	private static final String ADRESS_DISTRICT	      = "district";
	private static final String ADRESS_NEIGHBORHOOD	     	  = "neighborhood";
	private static final String ADRESS_BUILDING_NUMBER	      = "buildingnumber";
	private static final String ADRESS_HOUSE_NUMBER	     	  = "housenumber";
	private static final String ADRESS_FULL_ADDRESS	     	  = "fulladress";






	String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
			+ User_KEY_ID    	  + " INTEGER PRIMARY KEY,"
			+ User_KEY_NAME  	  + " TEXT,"
			+ User_KEY_EMAIL 	  + " TEXT UNIQUE,"
			+ User_KEY_UID   	  + " TEXT,"
			+ User_KEY_CREATED_AT + " TEXT" 						 + ")";

	String CREATE_BASKET_TABLE = "CREATE TABLE " + TABLE_USER_BASKET + "("
			+ UserSelection_KEY_ID    	      + " INTEGER PRIMARY KEY,"
			+ UserSelection_KEY_PRODUCT_ID    + " TEXT UNIQUE ,"
			+ UserSelection_KEY_CREATED_AT 	  + " TEXT "
			+ ")";

	String CREATE_WISHLIST_TABLE = "CREATE TABLE " + TABLE_USER_WISHLIST + "("
			+ UserSelection_KEY_ID    	      + " INTEGER PRIMARY KEY,"
			+ UserSelection_KEY_PRODUCT_ID    + " TEXT  ,"
			+ UserSelection_KEY_CREATED_AT 	  + " TEXT "
			+ ")";

	String CREATE_ADRESS_TABLE = "CREATE TABLE " + TABLE_USER_ADDRESS + "("
			+ ADRESS_KEY_ID    	      + " INTEGER PRIMARY KEY,"
			+ ADRESS_CITY     		  + " TEXT , "
			+ ADRESS_AREA 	          + " TEXT, "
			+ ADRESS_DISTRICT 		  + " TEXT ,"
			+ ADRESS_NEIGHBORHOOD 	  + " TEXT, "
			+ ADRESS_BUILDING_NUMBER  + " TEXT, "
			+ ADRESS_HOUSE_NUMBER 	  + " TEXT ,"
			+ ADRESS_FULL_ADDRESS 	  + " TEXT "

			+ ")";
	public SQLiteHandler(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db)
	{


		db.execSQL(CREATE_LOGIN_TABLE);
		db.execSQL(CREATE_BASKET_TABLE);
		db.execSQL(CREATE_WISHLIST_TABLE);
		db.execSQL(CREATE_ADRESS_TABLE);

		Log.d("message", "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_WISHLIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_BASKET);






		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email, String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();


		ContentValues values = new ContentValues();
		values.put(User_KEY_NAME, name); // Name
		values.put(User_KEY_EMAIL, email); // Email
		values.put(User_KEY_UID, uid); // Email
		values.put(User_KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("created_at", cursor.getString(4));
		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}



	////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////// USER SELECTIONS //////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////

	/// ADD PORDUCT TO WISHLIST TABLE
	public void addToWishList (String productId, String date  )
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UserSelection_KEY_PRODUCT_ID, productId); // PRODUCT ID
		values.put(UserSelection_KEY_CREATED_AT, date); // ADDED DATE


		// Inserting Row
		long id = db.insert(TABLE_USER_WISHLIST, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/// ADD PORDUCT TO BASKET TABLE
	public void addToBasket (String productId, String date  )
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(UserSelection_KEY_PRODUCT_ID, productId); // PRODUCT ID
		values.put(UserSelection_KEY_CREATED_AT, date); // ADDED DATE


		// Inserting Row
		long id = db.insert(TABLE_USER_BASKET, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}

	/// GET VALUE FROM WISHLIST

	public ArrayList<String> getAllWishlistItems()
	{
		ArrayList<String> wishlistProducts = new ArrayList<String>();

		try{
			String selectQuery = "SELECT  * FROM " + TABLE_USER_WISHLIST;


			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {

					// adding to todo list
					wishlistProducts.add(c.getString((c.getColumnIndex(UserSelection_KEY_PRODUCT_ID))));
				} while (c.moveToNext());
			}

		}
		catch (Exception e)
		{
			Log.i("Errorindatabase","Errorindatabase") ;
		}
		return wishlistProducts;
	}


	/////// GET PRODCUTS FROM BASKETS


	public ArrayList<String> getAllBasketItem()
	{
		ArrayList<String> wishlistProducts = new ArrayList<String>();

		try {
			String selectQuery = "SELECT  * FROM " + TABLE_USER_BASKET;


			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {

					// adding to todo list
					wishlistProducts.add(c.getString((c.getColumnIndex(UserSelection_KEY_PRODUCT_ID))));
				} while (c.moveToNext());
			}
		}
		catch (Exception e )
		{
			Log.i("ErrorInDatabae","ErrorInDatabae");
		}

		return wishlistProducts;
	}

	public int  GetWishListItemsCount()
	{
		String selectQuery = "SELECT  * FROM " + TABLE_USER_WISHLIST;


		int count = 0 ;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
			count = c.getCount() ;
		}
		catch (SQLException e )
		{}


		return count;
	}
	public int  GetBasketItemsCount()
	{
		List<Integer> wishlistProducts = new ArrayList<Integer>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER_BASKET;
		int count = 0 ;
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);
			count = c.getCount() ;
		}
		catch (SQLException e )
		{}


		return count;
	}


	public boolean   getProductByIdBasket(String productId)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			Cursor c = db.rawQuery("SELECT * FROM "+TABLE_USER_BASKET+" WHERE "+UserSelection_KEY_PRODUCT_ID+" = ?", new String[] {productId});
			if (c.getCount()>0)
				return  true ;
			else
				return false ;

		}
		catch (Exception e )
		{
			Log.i("ErroinDatabse","Error In Database ");

		}
	return false ;


	}

	public boolean   getProductByIdWishList(String productId)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		try
		{
			Cursor c = db.rawQuery("SELECT * FROM "+TABLE_USER_WISHLIST+" WHERE "+UserSelection_KEY_PRODUCT_ID+" = ?", new String[] {productId});


			if (c.getCount()>0)
				return  true ;
			else
				return false ;

		}
		catch (Exception e )
		{
			Log.i("ErroinDatabse","Error In Database ");
		}
		return  false ;

	}
	public Boolean deleteBasketProductById (String productId )
	{
		SQLiteDatabase db = this.getReadableDatabase();

		return db.delete(TABLE_USER_BASKET, UserSelection_KEY_PRODUCT_ID + "=" + productId, null) > 0;

	}
	public Boolean deleteWishListProductById (String productId )
	{
		SQLiteDatabase db = this.getReadableDatabase();

		return db.delete(TABLE_USER_WISHLIST, UserSelection_KEY_PRODUCT_ID + "=" + productId, null) > 0;

	}
	public    void deleteBasket() {
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL("delete from "+ TABLE_USER_BASKET);

	}
	public    void deleteWish() {
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL("delete from "+ TABLE_USER_WISHLIST);

	}

	public void deleteBasketTable()
	{
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USER_BASKET);

	}
	public void deltewishlistTable()
	{
		SQLiteDatabase db = this.getReadableDatabase();

		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USER_WISHLIST);
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////
	//////ADD ADDRESS
	public void AddAddress(String city, String area, String district, String neighborhood,String buildingNumber,String houseNumber ,String fullAdres) {
		SQLiteDatabase db = this.getWritableDatabase();


		ContentValues values = new ContentValues();
		values.put(ADRESS_CITY, city);
		values.put(ADRESS_AREA, area);
		values.put(ADRESS_DISTRICT, district);
		values.put(ADRESS_NEIGHBORHOOD, neighborhood);
		values.put(ADRESS_BUILDING_NUMBER, buildingNumber);
		values.put(ADRESS_HOUSE_NUMBER, houseNumber);
		values.put(ADRESS_FULL_ADDRESS, fullAdres);




		// Inserting Row
		long id = db.insert(TABLE_USER_ADDRESS, null, values);
		db.close(); // Closing database connection

		Log.d("ADDRESS ADDEED", "ADDRESS ADDEED " + id);
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////
	////// GET ADRESS

	public  mAddress getAddress ( )
	{

		mAddress Address = null ;
		try {
			String selectQuery = "SELECT  * FROM " + TABLE_USER_ADDRESS;


			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst()) {
				do {
					Address = new mAddress();

					Address.setCity(c.getString((c.getColumnIndex(ADRESS_CITY))));
					Address.setArea(c.getString((c.getColumnIndex(ADRESS_AREA))));
					Address.setDiscrit(c.getString((c.getColumnIndex(ADRESS_DISTRICT))));
					Address.setNeighborhood(c.getString((c.getColumnIndex(ADRESS_NEIGHBORHOOD))));
					Address.setBuildingNumber(c.getString((c.getColumnIndex(ADRESS_BUILDING_NUMBER))));
					Address.setHouseNumber(c.getString((c.getColumnIndex(ADRESS_HOUSE_NUMBER))));
					Address.setFullAddress(c.getString((c.getColumnIndex(ADRESS_FULL_ADDRESS))));






					// adding to todo list
				} while (c.moveToNext());
			}
		}
		catch (Exception e )
		{
			Log.i("ErrorInDatabae","ErrorInGettingAddress");
		}

		return Address;

	}

	public Boolean DeleteAddress()
	{
		SQLiteDatabase db = this.getReadableDatabase();

	int isDeleted =	 db.delete(TABLE_USER_ADDRESS,null,null);
	if (isDeleted>0)
		return true ;

	return false ;

	}
}
