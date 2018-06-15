/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClass;

import java.util.Date;
/**
 *
 * @author Feda
 */
public class User {
    String NickName;
    String FullName;
    String Date;
    String Email;
    String Password;
    String Token;
    
    public User(String NickName, String FullName, String Email, String Password, String Date) {
    this.NickName = NickName;
    this.FullName = FullName;
    this.Email = Email;
    this.Password = Password;
    this.Date =  Date;
    }
    
    public User(String NickName, String FullName, String Email, String Password, String Date,String Token) {
    this.NickName = NickName;
    this.FullName = FullName;
    this.Email = Email;
    this.Password = Password;
    this.Date =  Date;
    this.Token = Token;
    }
    
    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }    
}
