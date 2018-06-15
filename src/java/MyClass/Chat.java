/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClass;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Feda
 */
public class Chat {
    String Title;
    String Data;
    ArrayList<String> Members;
    String Token;
    
    public Chat(String Title,ArrayList<String> Members,String Data){
    this.Title = Title;
    this.Members = Members;
    this.Data = Data;
    }
    
    public Chat(String Title,ArrayList<String> Members,String Data,String Token){
    this.Title = Title;
    this.Members = Members;
    this.Data = Data;
    this.Token = Token;
    }
    
    public Chat(String Title, String Data) {
    this.Title = Title;
    this.Data = Data;   
    this.Members = new ArrayList<String>();
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
    
    public ArrayList<String> getMembers() {
        return Members;
    }

    public void setMembers(ArrayList<String> Members) {
        this.Members = Members;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
