package com.example.mg.tryappkillan.interf;

import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mg.tryappkillan.logic.MyMenu;
import com.example.mg.tryappkillan.logic.Sessions;


public interface clickMenu {

    boolean clickHistory(MenuItem item);
    boolean clickStatistics(MenuItem item);
    boolean clickActivity(MenuItem item);
    boolean clickActivity2(MenuItem item);
    boolean clickLogout(MenuItem item);

}
