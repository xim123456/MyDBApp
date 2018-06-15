/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servises;

import MyClass.User;
import MyClass.Chat;
import MyException.*;
import beans.MongoBD;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import org.bson.Document;
/**
 *
 * @author Feda
 */
@Path("Mongo")
public class MongoRest {
        
    MongoBD bd1 = new MongoBD() ;
    public static ServerAddress seed1 = new ServerAddress("192.168.0.106", 27027);
    public static ServerAddress seed2 = new ServerAddress("192.168.0.106", 27028);
    public static ServerAddress seed3 = new ServerAddress("192.168.0.106", 27029);
    public static String ReplSetName = "Myrs";
    @GET
    @Path("CreateU:{nickname}/{fullname}/{email}/{passwd}/{date}")
    public String CreateUser(@PathParam("nickname") String Nickname,@PathParam("fullname") String Full_Name,@PathParam("email") String Email,@PathParam("password") String Password,@PathParam("date") String Date) {
        User buff = new User(Nickname,Full_Name,Email,Password,Date);
        MongoDatabase db;
        ArrayList<User> ResList = null;
        String BuffDS;
        int BuffDI;
        
        if(Date.split("\\.").length  != 3)
            return "Invalid date 1";
        try {
        BuffDS = Date.split("\\.")[0];  // Day
        BuffDI = Integer.parseInt(BuffDS);
        if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI <= 0 || BuffDI > 31)
            return "Invalid date 2";
        
        BuffDS = Date.split("\\.")[1];  // Month
        BuffDI = Integer.parseInt(BuffDS);
        if(BuffDS.length() > 2 || BuffDS.length() < 1 || BuffDI <= 0 || BuffDI > 12)
            return "Invalid date 3";
        
        BuffDS = Date.split("\\.")[2];  // Year
        BuffDI = Integer.parseInt(BuffDS);
        if(BuffDS.length() != 4 || BuffDI < 0)
            return "Invalid date 4";
        }
        catch (NumberFormatException e) {
            return "Invalid date 5";
        }
        
        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        try {
            db = bd1.CreateConnection(connectionString);
        }
        catch (MongoCreateConnectionE e) {
            return "Connection with Mongodb could not be established";
        }
        
        MongoCollection<Document> Collection = bd1.getCollection(db,"Users");
        
        try {
            ResList = bd1.SelectUser(Collection,"NickName", buff.getNickName());
        }
        catch (MongoSelectE e) {
            return "Find a record in Mongo failed";
        }
        
        if(ResList != null && ResList.size() >= 1)
            return "NickName already exists";
        
        try {
            bd1.InsertUser(buff,Collection);
        }
        catch (MongoInsertE e) {
            return "Add to Mongo failed";
        }
        
        try {
            bd1.CloseConnection();
        }
        catch (MongoCloseConnectionE e) {
            return "Closeed Mongo faled";
        }
       
        return "User added";
    }
    
    @GET
    @Path("DeleteU:{nickname}")
    public String DeleteUser(@PathParam("nickname") String Nickname) {
        MongoDatabase db;
        ArrayList<User> ResList = null;
        Long res = 0L;
        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        try {
            db = bd1.CreateConnection(connectionString);
        }
        catch (MongoCreateConnectionE e) {
            return "Connection with Mongodb could not be established";
        }
        
        MongoCollection<Document> Collection = bd1.getCollection(db,"Users");
        
        try {
            res = bd1.Remove(Collection,"NickName",Nickname);
        }
        catch (MongoDeleteE e) {
            return "Delete to Mongo failed";
        }
        
        try {
            bd1.CloseConnection();
        }
        catch (MongoCloseConnectionE e) {
            return "Closeed Mongo faled";
        }
        
        if(res == 1L) {
            return "User deleted";
        }
        else if(res > 1L) {
            return "Users deleted";
        }
        else 
            return "User does not exist";
    }
    
    @GET
    @Path("CreateC:{Title}/{Data}")
    public String CreateChat(@PathParam("Title") String Title,@PathParam("Data") String Date) {
        Chat buff = new Chat(Title, Date);
        MongoDatabase db;
        ArrayList<Chat> ResList = null;
        String BuffDS;
        int BuffDI;
        
        if(Date.split("\\.").length  != 3)
            return "Invalid date";
        try {
        BuffDS = Date.split("\\.")[0];  // Day
        BuffDI = Integer.parseInt(BuffDS);
        if(BuffDS.length() > 2 || BuffDS.length() < 1)
            return "Invalid date";
        
        BuffDS = Date.split("\\.")[1];  // Month
        BuffDI = Integer.parseInt(BuffDS);
        if(BuffDS.length() > 2 || BuffDS.length() < 1)
            return "Invalid date";
        
        BuffDS = Date.split("\\.")[2];  // Year
        BuffDI = Integer.parseInt(BuffDS);
        if(BuffDS.length() != 4 || BuffDI < 0)
            return "Invalid date";
        }
        catch (NumberFormatException e) {
            return "Invalid date";
        }
        
        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        try {
            db = bd1.CreateConnection(connectionString);
        }
        catch (MongoCreateConnectionE e) {
            return "Connection with Mongodb could not be established";
        }
        
        MongoCollection<Document> Collection = bd1.getCollection(db,"Chats");
        
        try {
            ResList = bd1.SelectChat(Collection,"Title", buff.getTitle());
        }
        catch (MongoSelectE e) {
            return "Find a record in Mongo failed";
        }
        
        if(ResList != null && ResList.size() >= 1)
            return "Chat already exists";
        
        try {
            bd1.InsertChat(buff,Collection);
        }
        catch (MongoInsertE e) {
            return "Add to Mongo failed";
        }
        
        try {
            bd1.CloseConnection();
        }
        catch (MongoCloseConnectionE e) {
            return "Closeed Mongo faled";
        }
        
        return "Chat added";
    }

    @GET
    @Path("AddMemberC:{Title}/{NickName}")
    public String AddMemberChat(@PathParam("Title") String Title,@PathParam("NickName") String NickName){
       Chat buff;
        MongoDatabase db;
        ArrayList<Chat> ResList = null;
        ArrayList<User> ResList2 = null;
        
        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        try {
            db = bd1.CreateConnection(connectionString);
        }
        catch (MongoCreateConnectionE e) {
            return "Connection with Mongodb could not be established";
        }
        
        MongoCollection<Document> Collection = bd1.getCollection(db,"Chats");
        
        try {
            ResList = bd1.SelectChat(Collection,"Title", Title);
        }
        catch (MongoSelectE e) {
            return "Find a record in Mongo failed";
        }
        
        if(ResList == null || ResList.size() == 0)
            return "Chat is not exists";
        
        buff = new Chat(ResList.get(0).getTitle(),ResList.get(0).getMembers(),ResList.get(0).getData(),ResList.get(0).getTitle());

        MongoCollection<Document> Collection2 = bd1.getCollection(db,"Users");
        
        try {
            ResList2 = bd1.SelectUser(Collection2,"NickName", NickName);
        }
        catch (MongoSelectE e) {
            return "Find a record in Mongo failed";
        }
        
        if(ResList2 == null || ResList2.size() == 0)
            return "NickName is not exists";
        
        for(int i = 0;i< buff.getMembers().size();i++) {
            if(buff.getMembers().get(i).equals(NickName))
                return "User already added to chat";
        }
        
        buff.getMembers().add(NickName);
        
        try {
            bd1.Remove(Collection,"Title", buff.getTitle().toString());
        }
        catch (MongoDeleteE e) {
            return "Delete to Mongo failed";
        }
        
        try {
            bd1.InsertChat(buff,Collection);
        }
        catch (MongoInsertE e) {
            return "Add to Mongo failed";
        }
        
        try {
            bd1.CloseConnection();
        }
        catch (MongoCloseConnectionE e) {
            return "Closeed Mongo faled";
        }
        
        return "Memebers added in " + buff.getTitle();
    }

    @GET
    @Path("DeleteC:{Title}")
    public String DeleteChat(@PathParam("Title") String Title) {
        MongoDatabase db;
        ArrayList<Chat> ResList = null;
        Long res = 0L;
       
        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        try {
            db = bd1.CreateConnection(connectionString);
        }
        catch (MongoCreateConnectionE e) {
            return "Connection with Mongodb could not be established";
        }
        
        MongoCollection<Document> Collection = bd1.getCollection(db,"Chats");
        
        try {
            res = bd1.Remove(Collection,"Title",Title);
        }
        catch (MongoDeleteE e) {
            return "Delete to Mongo failed";
        }
        
        try {
            bd1.CloseConnection();
        }
        catch (MongoCloseConnectionE e) {
            return "Closeed Mongo faled";
        }
        
        if(res == 1L) {
            return "Chat deleted";
        }
        else if(res > 1L) {
            return "Chats deleted";
        }
        else 
            return "Chat does not exist";
    }

    static boolean ChtckChat(String Title) throws MongoSelectE{
        MongoDatabase db;
        MongoBD bd2 = new MongoBD() ;
        ArrayList<Chat> ResList = null; 
        
        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        try {
            db = bd2.CreateConnection(connectionString);
            MongoCollection<Document> Collection = bd2.getCollection(db,"Chats");
            ResList = bd2.SelectChat(Collection,"Title", Title);
            bd2.CloseConnection();
            if(ResList != null && ResList.size() >= 1)
                return true;
            else
                return false;
        }
        catch (MongoCloseConnectionE | MongoCreateConnectionE | MongoSelectE e) {
            throw new MongoSelectE(e);
        }
    }
    
    static boolean ChtckUser(String NickName) throws MongoSelectE {
       MongoDatabase db;
       MongoBD bd2 = new MongoBD() ;
       ArrayList<User> ResList = null; 
      
        try {
            db = bd2.CreateConnection("mongodb://localhost:27017", "Mdb");
            MongoCollection<Document> Collection = bd2.getCollection(db,"Users");
            ResList = bd2.SelectUser(Collection,"NickName", NickName);
            bd2.CloseConnection();
            if(ResList != null && ResList.size() >= 1)
                return true;
            else
                return false;
        }
        catch (MongoCloseConnectionE | MongoCreateConnectionE | MongoSelectE e) {
            throw new MongoSelectE();
        }
    }
}
