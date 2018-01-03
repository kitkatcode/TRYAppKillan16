package com.example.mg.tryappkillan.form;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mg.tryappkillan.R;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public boolean clickHistory(MenuItem item) {
        startActivity(new Intent(this,ListOfHistories.class));
        finish();
        return true;
    }
    public boolean clickStatistics(MenuItem item)
    {
        startActivity(new Intent(this,ListOfStatistics.class));
        finish();
        return true;
    }
    public boolean clickActivity(MenuItem item) {
     //   startActivity(new Intent(this, StartActivity.class));
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
    }

}
