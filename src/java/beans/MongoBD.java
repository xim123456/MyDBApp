/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import MyException.*;

import MyClass.User;
import MyClass.Chat;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;

import java.util.ArrayList;
import java.util.Collection;
import org.bson.Document;

/**
 *
 * @author Feda
 */
public class MongoBD {
    MongoClient mongoClient;
    
    public MongoBD(){}
    
    public MongoDatabase CreateConnection(String ConnectionUrl, String DB) throws MongoCreateConnectionE{
        try {
            MongoClientURI connectionString = new MongoClientURI(ConnectionUrl);
            mongoClient = new MongoClient(connectionString);
            MongoDatabase database = mongoClient.getDatabase(DB);
            return database;
        }
        catch (Exception e) {
            throw new MongoCreateConnectionE(e);
        }
    }
    
    public MongoDatabase CreateConnection(MongoClientURI ConnectionUrl) throws MongoCreateConnectionE{
        try {
            mongoClient = new MongoClient(ConnectionUrl);
            MongoDatabase database = mongoClient.getDatabase("Mdb");
            return database;
        }
        catch (Exception e) {
            throw new MongoCreateConnectionE(e);
        }
    }
    public MongoDatabase CreateConnection() throws MongoCreateConnectionE{
        try {
            mongoClient = new MongoClient("192.168.0.106",27029);
            MongoDatabase database = mongoClient.getDatabase("Mdb");
            return database;
        }
        catch (Exception e) {
            throw new MongoCreateConnectionE(e);
        }
    }
    
    public void CloseConnection() throws MongoCloseConnectionE {
        try {
            mongoClient.close();
            mongoClient = null;
        }
        catch (Exception e) {
            throw new MongoCloseConnectionE(e);
        }
    } 
    
    public MongoCollection<Document> getCollection(MongoDatabase database,String CollectionName){
        MongoCollection<Document> Collection;
        
        if(database != null) {
            Collection = database.getCollection(CollectionName);
        }
        else {
            Collection = null;
        }
        
        return Collection;
    }
    
    public void InsertUser(User user,MongoCollection<Document> Collection) throws MongoInsertE {
        try {
        Document doc = new Document("NickName", user.getNickName())
	                .append("FullName", user.getFullName())
	                .append("Email", user.getEmail())
	                .append("Password", user.getPassword())
	                .append("Date", user.getDate());
        
        Collection.insertOne(doc);
        }
        catch (Exception e) {
            throw new MongoInsertE(e);
        }
    }
    
    public void InsertChat(Chat chat,MongoCollection<Document> Collection) throws MongoInsertE {
        try {
        Document doc = new Document("Title", chat.getTitle())
	                .append("Members", chat.getMembers())
	                .append("Date", chat.getData());
        
        Collection.insertOne(doc);
        }
        catch (Exception e) {
            throw new MongoInsertE(e);
        }
    }
    
    public ArrayList<User> SelectUser(MongoCollection<Document> Collection, String Param, String Content) throws MongoSelectE {
        MongoCursor<Document> cursor = null;
        ArrayList<User> List;
        try {
            cursor = Collection.find(eq(Param, Content)).iterator();
            List = new ArrayList<User>();
            Document buf;
            while (cursor.hasNext()) {
                buf = cursor.next();
                List.add(new User(buf.getString("NickName"), buf.getString("FullName"), buf.getString("Email"), buf.getString("Password"), buf.getString("Date"), buf.get("_id").toString()));
            }
        }
        catch (Exception e) {
          throw new MongoSelectE(e);          
        }
        finally {
        //    cursor.close();
        }
        return List;
    }
   
    public ArrayList<Chat> SelectChat(MongoCollection<Document> Collection, String Param, String Content) throws MongoSelectE {
        MongoCursor<Document> cursor = null;
        ArrayList<Chat> List;
        try {
            List = new ArrayList<Chat>();
            Document buf;
            cursor = Collection.find(eq(Param, Content)).iterator();
            while (cursor.hasNext()) {
                buf = cursor.next();
                if(buf.get("Members") == null)
                    List.add(new Chat(buf.getString("Title"), new ArrayList<String>(), buf.getString("Data"),buf.get("_id").toString()));
                else 
                    List.add(new Chat(buf.getString("Title"), new ArrayList<String>((Collection<? extends String>) buf.get("Members")), buf.getString("Data"),buf.get("_id").toString()));
            }
        }
        catch (Exception e) {
            throw new MongoSelectE(e);
        }
        finally {
            cursor.close();
        }
        return List;
    }

    public Long Remove(MongoCollection<Document> Collection,String Param,String Content) throws MongoDeleteE{
        try {
            DeleteResult deleteResult = Collection.deleteMany(eq(Param,Content));
            Long Count = deleteResult.getDeletedCount();
            return Count;
        }
        catch (Exception e) {
            throw new MongoDeleteE(e);
        }
    }
}