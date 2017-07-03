package com.example.mg.tryappkillan.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.mg.tryappkillan.form.ListOfHistories;


/**
 * Created by mg on 28/05/17.
 */

public class Menu {


    private Context classContext;

    public void Menu(Context classContext)
    {
       this.classContext = classContext;
    }

    public boolean clickHistory(MenuItem item) {
        classContext.startActivity(new Intent(classContext,ListOfHistories.class));
        ((Activity) classContext).finish();
        return true;
    }
   /* public boolean clickStatistics(MenuItem item)
    {
        startActivity(new Intent(this,ListOfStatistics.class));
        finish();
        return true;
    }
    public boolean clickActivity(MenuItem item) {
        startActivity(new Intent(this, StartActivity.class));
        Intent intent = new Intent(getBaseContext(), StartActivity.class);
        intent.putExtra("nameOfActivity",0);
        startActivity(intent);
        finish();
        return true;
    }
    public boolean clickActivity2(MenuItem item){
        Intent intent = new Intent(getBaseContext(), StartActivity.class);
        intent.putExtra("nameOfActivity",1);
        startActivity(intent);
        finish();
        return true;
    }*/

}
