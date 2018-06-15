/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servises;

import MyClass.ChatRecord;
import MyException.*;
import beans.CassandraBD;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Feda
 */
@Path("Casandra")
public class CasandraRest {
    
    CassandraBD cas = new CassandraBD();
        
    @GET
    @Path("CreateR:{Title}/{Nickname}/{Date}/{Message}")
    public String CreateRecord(@PathParam("Nickname") String Nickname,@PathParam("Title") String Title,@PathParam("Date") String Date,@PathParam("Message") String Message) {
        String BuffDS;
        int BuffDI;
        if(Date.split("\\.").length  != 5)
            return "Invalid date";
        try {
            BuffDS = Date.split("\\.")[0];  // Day
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI <= 0 || BuffDI > 31)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[1];  // Month
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI <= 0 || BuffDI > 12)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[2];  // Year
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() != 4 || BuffDI < 0)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[3];  // Hours
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI < 0 || BuffDI > 23)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[4];  // Min
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI < 0 || BuffDI > 59)
                return "Invalid date";
        }
        catch (NumberFormatException e) {
            return "Invalid date";
        }
        
        try {
            if(!MongoRest.ChtckChat(Title))
                return "Chat does not exist";
            
            if(!MongoRest.ChtckUser(Nickname))
                return "User does not exist";
        }
        catch (MongoSelectE e) {
            return "Connection with Mongodb could not be established";
        }
     
        try {
            cas.CreateConnectionToClaster("192.168.0.90","192.168.0.91","192.168.0.92","chats");
        }
        catch (CasandraCreateConnectionE e) {
            return "Connection with Casandra could not be established";
        }
        
        ChatRecord Record = new ChatRecord(Message,Title,Nickname,Date);
        
        try {
            cas.InsertRecord(Record);
        }
        catch (CasandraInsertE e) {
            return "Add to Casandra failed";
        }
        
        try {
            cas.CloseConnection();
        }
        catch (CasandraCloseConnectionE e) {
            return "Closeed Casandra faled";
        }
        
        return "Record added";
    }
    
    @GET
    @Path("DeleteR:{Title}/{Date}/{Message}")
    public String DeleteRecord(@PathParam("Title") String Title,@PathParam("Date") String Date,@PathParam("Message") String Message) {
        String BuffDS;
        int BuffDI;
        if(Date.split("\\.").length  != 5)
            return "Invalid date";
        try {
            BuffDS = Date.split("\\.")[0];  // Day
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI <= 0 || BuffDI > 31)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[1];  // Month
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI <= 0 || BuffDI > 12)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[2];  // Year
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() != 4 || BuffDI < 0)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[3];  // Hours
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI < 0 || BuffDI > 23)
                return "Invalid date";
        
            BuffDS = Date.split("\\.")[4];  // Min
            BuffDI = Integer.parseInt(BuffDS);
            if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI < 0 || BuffDI > 59)
                return "Invalid date";
        }
        catch (NumberFormatException e) {
            return "Invalid date";
        }
        
        try {
            if(!MongoRest.ChtckChat(Title))
                return "Chat does not exist";
        }
        catch (MongoSelectE e) {
            return "Connection with Mongodb could not be established";
        }
     
        try {
            cas.CreateConnectionToClaster("192.168.0.90","192.168.0.91","192.168.0.92","chats");
        }
        catch (CasandraCreateConnectionE e) {
            return "Connection with Casandra could not be established";
        }
        
        
        try {
            cas.RemoveRecord(Title, Date);
        }
        catch (CasandraDeleteE e) {
            return "Delete to Casandra failed";
        }
        
        try {
            cas.CloseConnection();
        }
        catch (CasandraCloseConnectionE e) {
            return "Closeed Casandra faled";
        }
        
        return "Record deleted";
    }
    
    @GET
    @Path("SelectLastR:{Title}/{Count}")
    public String SelectLastRecord(@PathParam("Title") String Title,@PathParam("Count") String Count) {
        String Answer = null;
        List<ChatRecord> buff;
        
       /* try {
            if(!MongoRest.ChtckChat(Title))
                return "Chat does not exist";
        }
        catch (MongoSelectE e) {
            return "Connection with Mongodb could not be established";
        }
        */
        try {
            cas.CreateConnectionToClaster("192.168.0.90","192.168.0.91","192.168.0.92","chats");
        }
        catch (CasandraCreateConnectionE e) {
            return "Connection with Casandra could not be established";
        }
        
        try {
            buff = cas.SelectLastRecord(Title, Count);
        }
        catch (CasandraSelectE e) {
            return "Select faled";
        }
        
        try {
            cas.CloseConnection();
        }
        catch (CasandraCloseConnectionE e) {
            return "Closeed Casandra faled";
        }
        Answer = "Id : Title : Nickname : Date : Message";
        for(int i = 0;i < buff.size();i++) {
            Answer = Answer + "<p>" +  buff.get(i).getId() + " : " + buff.get(i).getTitle_Chat() + " : " +  buff.get(i).getNickname() + " : " + buff.get(i).getData() + " : " + buff.get(i).getMessage();
        }
        return Answer;
    }
}
