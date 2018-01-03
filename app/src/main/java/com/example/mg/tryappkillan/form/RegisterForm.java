package com.example.mg.tryappkillan.form;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mg.tryappkillan.R;
import com.example.mg.tryappkillan.logic.DataBase;
import com.example.mg.tryappkillan.logic.UsersStore;

public class RegisterForm extends AppCompatActivity {

    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText password2Edit;
    private EditText ageEdit;
    private EditText weightEdit;
    private EditText heightEdit;
    private TextView accessInform;

    private RadioButton fplec;
    private RadioButton mplec;


    private int age;
    private double weight;
    private double height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        emailEdit = (EditText) findViewById(R.id.emailEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        password2Edit = (EditText) findViewById(R.id.password2Edit);


        ageEdit = (EditText) findViewById(R.id.ageEdit);
        weightEdit = (EditText) findViewById(R.id.weightEdit);
        heightEdit = (EditText) findViewById(R.id.heightEdit);

        fplec = (RadioButton) findViewById(R.id.femaleRadioButton);
        mplec = (RadioButton) findViewById(R.id.menRadioButton);


        accessInform = (TextView) findViewById(R.id.accessInform);
    }


    public void clickRegister(View view){
        String email =  emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String password2 = password2Edit.getText().toString();
        String age = ageEdit.getText().toString();
        String weight = weightEdit.getText().toString();
        String height = heightEdit.getText().toString();
        boolean checked = true;

        UsersStore userStore = new UsersStore();
        if(checkExists(email)){
            checked = false;
            emailEdit.setError("Podany email już istnieje w bazie");
            return;
        }
        if(!checkEmail(email) ) {
            checked = false;
            emailEdit.setError("Proszę wpisać poprawny email");
        }
        if (!checkPassword(password) ) {
            checked = false;
            passwordEdit.setError("Hasło musi się składać z conajmniej 8 znaków");
        }
        if (!checkPassword(password2) ) {
            checked = false;
            password2Edit.setError("Proszę wpisać poprawnie hasło");
        }else if(!matchPassword(password,password2))
        {
            checked = false;
            password2Edit.setError("Hasła są niezgodne");
        }
        if(!checkAge(age)){
            checked = false;
            ageEdit.setError("Błędnie podany wiek");
        }
        if (!checkWeightHeight(weight)) {
            checked = false;
            weightEdit.setError("Błędnie podana waga");
            }
        if (!checkWeightHeight(height)) {
            checked = false;
            heightEdit.setError("Błędnie podana wysokość");
            }

        if(checked) {

            userStore.set_email(email);
            userStore.set_password(password);
            if(age.length()>0) userStore.set_age(Integer.valueOf(age));
            if(height.length()>0) userStore.set_height(Integer.valueOf(height));
            if(weight.length()>0)userStore.set_weight(Integer.valueOf(weight));

            if(fplec.isChecked())
            {
                userStore.set_isGirl(true);
            }
            else if(mplec.isChecked())
            {
                userStore.set_isGirl(false);
            }

            DataBase dataBase = new DataBase(this);
            dataBase.register(userStore);

            startActivity(new Intent(this, LoginForm.class));
            finish();
        }
    }






    /*    if(!isnulltext(email)|| !isnulltext(password))
        {
            if(checkEmail(email) & checkPassword(password) & checkText(email)  & checkText(password) &  checkExists(email))
            {
                UsersStore userStore = new UsersStore(email,password);
                DataBase dataBase = new DataBase(this);

                if(!isnulltext(ageEdit.getText().toString()) & checkText(ageEdit.getText().toString()) ) {
                    age = Integer.parseInt(ageEdit.getText().toString());
                    userStore.set_age(age);
                }
                if(!isnulltext(weightEdit.getText().toString()) & checkText(weightEdit.getText().toString()) ) {
                    weight = Integer.parseInt(weightEdit.getText().toString());
                    userStore.set_weight(weight);
                }
                if(!isnulltext(heightEdit.getText().toString()) & checkText(heightEdit.getText().toString()) ) {
                    height = Integer.parseInt(heightEdit.getText().toString());
                    userStore.set_height(height);
                }
                if(fplec.isChecked())
                {
                    userStore.set_isGirl(true);
                }
                else if(mplec.isChecked())
                {
                    userStore.set_isGirl(false);
                }

                dataBase.register(userStore);
                startActivity(new Intent(this, LoginForm.class));
                finish();
            }
            else
            {
                alert2();
            }
        }
        else
        {
            alert();
        }*/

    private boolean checkWeightHeight(String height) {
        //Sprawdzić czy jest liczbą  lub 3 miejsca przed przecinkiem lub kropką
        //Sprawdzić czy jest mniejsze od 230 ( 299)
        //Sprawdzić czy jest większe od 0
        //Przyjmować puste pola
        //Dopuszczać tylko 2 miejsca po przecinku lub kropką

        //problem 000.00, 00.00

            String hPattern = "^([0-9]{0,1}[0-9]([.][0-9][0-9]{0,1}){0,1}){0,1}$";
           // String hPattern = "^[0-3][1-9][0-9][.|,][0-9][0-9]$";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(hPattern);
            java.util.regex.Matcher m = p.matcher(height);
            return  m.matches();
    }


    private boolean checkAge(String age) {
        String aPattern = "^[1-3]{0,1}[0-9]{0,1}[0-9]{0,1}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(aPattern);
        java.util.regex.Matcher m = p.matcher(age);
        return  m.matches();
    }

    /*
    * Check methods
    * */
    public boolean checkEmail(String email)
    {
        String ePattern = "^[A-Za-z0-9](([_.\\-]?[a-zA-Z0-9]+)*)@([A-Za-z0-9]+)(([\\.\\-]?[a-zA-Z0-9]+)*)\\.([A-Za-z]{2,})$";
       // String ePattern = "^[a-zA-Z0-9.]{0,126}+@+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]{1,25}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean checkPassword(String password) {
        String  pPattern = "^([a-zA-Z0-9@*#]{8,15})$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pPattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();
    }
    private boolean matchPassword(String password, String password2) {
        if(password.equals(password2))
        {
            return true;
        }else {
            return  false;
        }
    }

    public boolean checkNumber(String number){
        return true;
    }
    public boolean isnulltext(String text)
    {
        if(text.length() == 0)
        {
            return  true;
        }
        return false;
    }
    public  boolean checkText(String text){

        if(text.length() > 20 || text.length() < 2 ) return false;

        return true;
    }

    public boolean checkExists(String email){
        DataBase db = new DataBase(this);
        Cursor result = db.query("SELECT email FROM USERS WHERE email='"+email+"'");
        if(result.getCount() >= 1)
        {
            return true;
        }
        else
        {
        //    alertExist();
            return false;
        }

    }
    /*
    * Alerts
    * */
    private void alert() {
        accessInform.setVisibility(View.VISIBLE);
        accessInform.setText("Nie wszystkie wymagane pola zostały wypełnione!");
    }
    private void alert2() {
        accessInform.setVisibility(View.VISIBLE);
        accessInform.setText("Wymagane pola zostały wypełnione nie prawidłowo");
    }
    private void alertExist()
    {
        accessInform.setVisibility(View.VISIBLE);
        accessInform.setText("Podany użytkownik jest już zarejestrowany");
    }
}