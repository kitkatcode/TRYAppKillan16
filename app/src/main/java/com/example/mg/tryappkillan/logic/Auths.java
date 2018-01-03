package com.example.mg.tryappkillan.logic;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mg on 23/09/16.
 */
public class Auths {

    private DataBase dataBase;
    private UsersStore users;
    private SharedPreferences sharedPreference;


    public Auths(Context context)
    {
        //TODO: CHECK IF DATABASE EXISTS?
        dataBase = new DataBase(context);
        sharedPreference = context.getSharedPreferences("LoginPreference",0);
    }

    public void setLoginUser()
    {
        SharedPreferences.Editor spEditor = sharedPreference.edit();
        if(!sharedPreference.getBoolean("isLogin",false))
        {
            spEditor.putBoolean("isLogin",true);
        }
        else
        {
            spEditor.putBoolean("isLogin",false);
        }
        spEditor.commit();

    }
    public  boolean getLoginUser()
    {
        SharedPreferences.Editor spEditor = sharedPreference.edit();
        if(sharedPreference.getBoolean("isLogin",false)) {
            return true;
        }
        else {
            return false;
        }
    }
    public  void clearLoginUser()
    {
        SharedPreferences.Editor spEditor = sharedPreference.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
