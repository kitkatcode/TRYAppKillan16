package com.example.mg.tryappkillan.form;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mg.tryappkillan.R;
import com.example.mg.tryappkillan.logic.DataBase;
import com.example.mg.tryappkillan.logic.Sessions;

public class LoginForm extends AppCompatActivity {

    private EditText emailEdit;
    private EditText passwordEdit;
    private TextView accessInform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        emailEdit = (EditText) findViewById(R.id.editText);
        passwordEdit = (EditText) findViewById(R.id.editText2);
        accessInform = (TextView) findViewById(R.id.accessInform);

    }

    public void clickLogin(View view) {


        //Check correctness of Strings
        if (emailEdit != null || passwordEdit != null) {
            String email = emailEdit.getText().toString();
            String password = passwordEdit.getText().toString();

            if (email.isEmpty() || email.length() > 20) {
                emailEdit.setError("Nazwa emaila została wpisana niepoprawnie!");
            } else if (password.isEmpty() || password.length() > 100) {
                passwordEdit.setError("Hasło zostało wpisane niepoprawnie!");
            } else if (!email.isEmpty() && !password.isEmpty()) {
                DataBase dataBase = new DataBase(this);
                if (dataBase.login(email,password, this) == true) {
                    Sessions session = new Sessions(this);
                    session.setLoginUser(true);

                    startActivity(new Intent(this, StartActivity.class));
                    finish();
                } else {
                   alert();
                    Log.e("LOG","Test");
                }
            } else {
                alert();
            }
        } else {
          alert();
        }
        return;
    }

    public void clickRegister(View view) {
        startActivity(new Intent(this, RegisterForm.class));
        finish();
    }

    public void forgetPassword() {

        // TODO: Complete forgetPassword
    }
     private void alert() {
         accessInform.setVisibility(View.VISIBLE);
         accessInform.setText("Logowanie odbyło się nieprawidłowo. Popraw błędy lub skorzystaj z opcji odzyskiwania hasła");

    }
    private void alert2() {
        accessInform.setVisibility(View.VISIBLE);
        accessInform.setText("chrum - chrum");

    }
}

