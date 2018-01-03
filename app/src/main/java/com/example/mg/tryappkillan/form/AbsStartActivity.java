package com.example.mg.tryappkillan.form;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.mg.tryappkillan.interf.clickMenu;
import com.example.mg.tryappkillan.logic.MyMenu;
import com.example.mg.tryappkillan.logic.Sessions;

/**
 * Created by mg on 06/07/17.
 */

public abstract class AbsStartActivity extends AppCompatActivity implements clickMenu {
private  MyMenu myMenu;
private Sessions session;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
         myMenu = new MyMenu();
        session = new Sessions(this);
        if (!session.logginIN()) {
            Log.e("SESSION", String.valueOf(session.logginIN()));
            finish();
            session.clearSession();
            startActivity(new Intent(this, LoginForm.class));
        }

    }

    @Override
    public boolean clickActivity(MenuItem item) {
        myMenu.clickActivity();
        return true;
    }
    public boolean clickActivity2(MenuItem item){
        myMenu.clickActivity2();
        return true;
    }

    //
    public boolean clickHistory(MenuItem item) {

        myMenu.clickHistory();
        return true;
    }

    public boolean clickStatistics(MenuItem item)
    {
        myMenu.clickStatistics();
        return true;
    }
    public boolean clickLogout(MenuItem item) {
        myMenu.clickLogout(session);
        return true;
    }
}
