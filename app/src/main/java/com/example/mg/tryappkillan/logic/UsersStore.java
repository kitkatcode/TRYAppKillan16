package com.example.mg.tryappkillan.logic;

/**
 * Created by mg on 31/01/17.
 */

public class UsersStore {

    private DataBase dataBase;
   /* private int _idUsers;*/
    private String _email;
    private String _password;
    private String _name;
    private String _surname;
    private int _age;
    private double _weight;
    private double _height;
    private boolean _isGirl;

    public UsersStore(){
     /*   this._email = email;
        this._password = password;*/
    }
    /*
     * Getters
     * */
     String get_email() { return _email; }
     String get_password() {
        return _password;
    }
    public String get_name() {
        return _name;
    }
    public String get_surname() {
        return _surname;
    }
     int get_age() { return _age; }
     double get_height() {
        return _height;
    }
     double get_weight() {
        return _weight;
    }
     int get_isGirl(){
        return _isGirl?1:0;
    }

    /*
    * Setters
    * */
    public void set_name(String _name) { this._name = _name;}
    public void set_surname(String _surname) { this._surname = _surname; }

    public void set_email(String _email) {
        this._email = _email;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_age(int _age) { this._age = _age; }
    public void set_height(double _height) {
        this._height = _height;
    }
    public void set_weight(double _weight) {
        this._weight = _weight;
    }
    public void set_isGirl(boolean _isGirl) { this._isGirl = _isGirl; }





}
