package com.example.mg.tryappkillan.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.example.mg.tryappkillan.form.ListOfHistories;
import com.example.mg.tryappkillan.form.ListOfStatistics;
import com.example.mg.tryappkillan.form.LoginForm;
import com.example.mg.tryappkillan.form.StartActivity;
import com.example.mg.tryappkillan.form.StartActivity2;




public class MyMenu {


    private Context classContext;

    public void Menu(Context classContext)
    {
       this.classContext = classContext;
    }

    public boolean clickHistory() {
        classContext.startActivity(new Intent(classContext,ListOfHistories.class));
        ((Activity) classContext).finish();
        return true;
    }
    public boolean clickStatistics()
    {
        classContext.startActivity(new Intent(classContext,ListOfStatistics.class));
        ((Activity) classContext).finish();
        return true;
    }
    public boolean clickActivity() {
       // classContext.startActivity(new Intent(classContext, StartActivity.class));
        Intent intent = new Intent(classContext, StartActivity.class);
        intent.putExtra("nameOfActivity",0);
        classContext.startActivity(intent);
        ((Activity) classContext).finish();
        return true;
    }
    public boolean clickActivity2(){
        Intent intent = new Intent(classContext, StartActivity2.class);
        intent.putExtra("nameOfActivity",1);
        classContext.startActivity(intent);
        ((Activity) classContext).finish();
        return true;
    }
    public boolean clickLogout(Sessions sessions){
        Intent intent = new Intent(classContext, LoginForm.class);
        classContext.startActivity(intent);
        ((Activity) classContext).finish();
        sessions.clearSession();
        return true;
    }

}
