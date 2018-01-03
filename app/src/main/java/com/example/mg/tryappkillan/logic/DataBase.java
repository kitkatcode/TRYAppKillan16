package com.example.mg.tryappkillan.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mg on 26/09/16.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "KILLAN.db";
    private SQLiteDatabase db;


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*==============================================================*/
        /* Table: USERS                                   */
        /*==============================================================*/

        db.execSQL("CREATE TABLE USERS ( " +
                "idUSER INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT, " +
                "password TEXT, " +
               /* "name TEXT, " +
                "surname TEXT, " +*/
                "weight DOUBLE, " +
                "height DOUBLE, " +
                "age INT, " +
                "gender BOOLEAN " +
                "); "
        );
        /*==============================================================*/
        /* Table: RUNS                                                */
        /*==============================================================*/
        db.execSQL("CREATE TABLE RUNS ( " +
                "idRUN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "datetime DATETIME," +
                "distance DOUBLE," +
                "stoppertime INT," +
                "maxspeed DOUBLE," +
             /*   "avgspeed DOUBLE," +*/
                /*"chose_activity TEXT, " +*/
                "idUSER INT, " +
                "FOREIGN KEY(idUSER) REFERENCES USERS(idUSER)" +
                ");"
        );
        //
        /*==============================================================*/
        /* Table: GPS                                       */
        /*==============================================================*/
        db.execSQL("CREATE TABLE GPS ( " +
                "idGPS INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "latitude DOUBLE, " +
                "longitude DOUBLE, " +
               /* "altitude DOUBLE," +*/
                "datetime DATETIME, " +
               "idRUN INT," +
               "FOREIGN KEY(idRUN) REFERENCES RUNS(idRUN)" +
                ");"
        );
        /*==============================================================*/
        /* Insert Section                                 */
        /*==============================================================*/
        db.execSQL("INSERT INTO RUNS(" +
                "idRUN,datetime,distance,stoppertime,maxspeed,idUSER" +
                ")" +
                "VALUES ("  +
                "1,'2017-08-06 07:42:00',275, 108760, 27, 1" +
                ");");
      /*  db.execSQL("INSERT INTO USERS (" +
                "idUSER,email,password,weight,height,age,gender" +
                ")" +
                "VALUES ("  +
                "1,'mcom', 'mcom', 56, 180, 20, 1" +
                ");"); */


     /*   db.execSQL("INSERT INTO RUNS(" +
                "idRUN,datetime,distance,stoppertime,maxspeed,idUSER" +
                ")" +
                "VALUES ("  +
                "1,'test',10, 65000, 100, 1" +
                ");");
        db.execSQL("INSERT INTO RUNS(" +
                "idRUN,datetime,distance,stoppertime,maxspeed,idUSER" +
                ")" +
                "VALUES ("  +
                "2,'test2',10, 65000, 100, 1" +
                ");");*/

 /*    db.execSQL("INSERT INTO RUNS(" +
             "idRUN,datetime,distance,stoppertime,maxspeed,idUSER" +
             ")" +
             "VALUES ("  +
             "1,'test',25.49, 9360007, 4.45, 1" +
             ");");
*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS RUNS");
        db.execSQL("DROP TABLE IF EXISTS SUBRUNS");
        onCreate(db);
    }
    public Cursor query(String text) {
       db = this.getReadableDatabase();
      return  db.rawQuery(text, null);
    }
    public void execute(String text) {
        db = this.getWritableDatabase();
        db.execSQL(text);
    }


    public void register(UsersStore userStore){

        execute("INSERT INTO USERS (" +
                "idUSER,email,password,weight,height,age,gender" +
                ")" +
                "VALUES (" +
                "null," +
                "'" + userStore.get_email()+ "'," +
                "'" + userStore.get_password() + "',"
                + userStore.get_weight() + ","
                + userStore.get_height() + ","
                + userStore.get_age() + ","
                + userStore.get_isGirl() +
                ")");
    }
    public boolean login( String email, String password, Context context) {

        String sql = "SELECT idUSER, email, password FROM USERS WHERE email == '" + email + "' AND password == '" + password + "' ";

        Cursor result = query(sql);
        // jeśli istnieje  wynik w bazie danych
        if( result.getCount() == 1) {
            result.moveToFirst();

            //send idUSER
            SharedPreferences shp = context.getSharedPreferences("SessionPreference",0);
            SharedPreferences.Editor shpE = shp.edit();
            shpE.putInt("idUSER",result.getInt(0));
            shpE.commit();
            return true;
        }
        return false;
    }
}
