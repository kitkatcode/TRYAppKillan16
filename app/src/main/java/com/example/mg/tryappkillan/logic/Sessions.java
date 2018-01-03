package com.example.mg.tryappkillan.logic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mg on 19/02/17.
 * This class supports login system.
 */

public class Sessions {

    SharedPreferences shp;
    SharedPreferences.Editor shpE;
    private int UserID;

    public Sessions(Context conext){
        shp = conext.getSharedPreferences("SessionPreference",0);
        shpE = shp.edit();
        shpE.commit();
    }


    public void setLoginUser(boolean loggedIn)
    {
        shpE.putBoolean("isLogin",loggedIn);
        shpE.commit();
    }
    public  boolean logginIN()
    {
        return shp.getBoolean("isLogin",false);
    }


  /*  public void setUserID(int userID) {
         shpE.putInt("idUser",userID);
         shpE.commit();
    }

    public int getUserID() {
        return shp.getInt("idUser",0);
    }

    public  void clearSession() {
        shpE.clear();
        shpE.commit();
    }*/
  public  void clearSession() {
      shpE.clear();
      shpE.commit();
  }
}


