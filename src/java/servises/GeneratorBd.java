/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servises;

import beans.*;
import MyException.*;
import MyClass.*;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ServerAddress;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import static servises.MongoRest.ReplSetName;
import static servises.MongoRest.seed1;
import static servises.MongoRest.seed2;
import static servises.MongoRest.seed3;

/**
 *
 * @author Feda
 */
@Path("Gen")
public class GeneratorBd {
    MongoBD mongo = new MongoBD();
    CassandraBD cas = new CassandraBD();
    Neo4jBD neo = new Neo4jBD();
    
    public static ServerAddress seed1 = new ServerAddress("192.168.0.106", 27027);
    public static ServerAddress seed2 = new ServerAddress("192.168.0.106", 27028);
    public static ServerAddress seed3 = new ServerAddress("192.168.0.106", 27029);
    public static String ReplSetName = "Myrs";
    
        boolean CreateConnectForClaster(String Addres,String Port1, String Port2, String Port3, String Log, String Pass) throws Neo4jCreateConnectionE{
        String Answer = "";
        for (int i = 0 ;i < 3; i++){
            try {
                switch(i) {
                    case 0:
                        neo.CreateConnection(Addres + Port1, Log, Pass);
                        break;
                    case 1:
                        neo.CreateConnection(Addres + Port2, Log, Pass);
                        break;
                    case 2:
                        neo.CreateConnection(Addres + Port3, Log, Pass);
                        break;
                }   
            }
            catch (Neo4jCreateConnectionE e) {
                continue;
            }
            
            try {
                Node test = new Node("Bot","Test");
                test.setId(neo.Insert_Node("Bot", "Test"));
                
                neo.Delete_Node(test);
                return true;
            }
            catch(Exception e) {
                Answer = "Select to Neo4j failed";
            }
            
            
            try {
                neo.CloseConnection();
            }   
            catch(Neo4jCloseConnectionE e) {
                Answer =  "Closeed Neo4j faled";
            }
        }
    return false;
    }
    
    
    @GET
    @Path("test1")
    public String MongoGen() throws Exception {
        MongoDatabase db;

        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        db = mongo.CreateConnection(connectionString);
        
        MongoCollection<Document> Collection = mongo.getCollection(db,"Users");
        
        for(int i = 0;i < 1923074;i++) {  //1923074
            User buff = new User("Nick" + i,"blabla lablab " + i,"blablabla@mail.ru","123456","10.10.1996");
            
            mongo.InsertUser(buff,Collection);
        }
        
        MongoCollection<Document> Collection2 = mongo.getCollection(db,"Chats");
        int j = 0;
        
        for(int i = 0;i < 76926;i++) {  //76926
            ArrayList<String> buff2 = new ArrayList<String>();
            buff2.add("Nick" + j);
            j++;
            buff2.add("Nick" + j);
            j++;
            buff2.add("Bot" + i);

            Chat buff = new Chat("Chat " + i, buff2, "10.10.2010");
            
            mongo.InsertChat(buff,Collection2);
        }
        
        mongo.CloseConnection();
        return "Ok";
    }
    
    @GET
    @Path("test5")
    public String test() throws Exception {
        MongoDatabase db;

        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        db = mongo.CreateConnection();
        
        ArrayList<User> a = new ArrayList<User>();    
        ArrayList<Chat> b = new ArrayList<Chat>();
        MongoCollection<Document> Collection = mongo.getCollection(db,"Users");
        a = mongo.SelectUser(Collection, "NickName", "Nick1");
        MongoCollection<Document> Collection2 = mongo.getCollection(db,"Chats");
        b = mongo.SelectChat(Collection2, "Members", a.get(0).getNickName());
        
        Chat c = new Chat("Title",new ArrayList<String>(),"10");
        mongo.InsertChat(c, Collection);
        cas.CreateConnectionToClaster("192.168.0.90","192.168.0.91","192.168.0.92","chats");
        List<ChatRecord> buff;
        buff = cas.SelectLastRecord(b.get(0).getTitle(), "4");
        cas.CloseConnection();
      
        String Answer = "Id : Title : Nickname : Date : Message";
        for(int i = 0;i < buff.size();i++) {
            Answer = Answer + "<p>" +  buff.get(i).getId() + " : " + buff.get(i).getTitle_Chat() + " : " +  buff.get(i).getNickname() + " : " + buff.get(i).getData() + " : " + buff.get(i).getMessage();
        }
        
        Node MainNode;
        
        neo.CreateConnection("bolt://127.0.0.1:" + "7688", "neo4j", "ew23ew23");
        MainNode = neo.Select_Fild("Bot", b.get(0).getMembers().get(2)).get(0);
        return  "NickName: " + a.get(0).getNickName() + " Full Name: " + a.get(0).getFullName() + " Title Chat: " + b.get(0).getTitle() + "Chat Members:" + b.get(0).getMembers().toString() + "<p> Coments <p>" +  Answer + "<p> Bot id <p>" + MainNode.getId();
        
    }
    
    /*
    
      MongoDatabase db;

        MongoClientURI connectionString = new MongoClientURI("mongodb://@" + seed1 + "," + seed2 + "," + seed3 + "/" +
                                                             "Mdb" +  "?replicaSet=" + ReplSetName);
        
        db = mongo.CreateConnection(connectionString);
        
        MongoCollection<Document> Collection = mongo.getCollection(db,"Users");
        ArrayList<User> a = new ArrayList<User>();    
        ArrayList<Chat> b = new ArrayList<Chat>();
        a = mongo.SelectUser(Collection, "NickName", "NickName1");
        MongoCollection<Document> Collection2 = mongo.getCollection(db,"Chats");
        b = mongo.SelectChat(Collection2, "Members", a.get(0).getNickName());
    return  "NickName: " + a.get(0).getNickName() + "Full Name" + a.get(0).getFullName() + "Title Chat" + b.get(0).getTitle();
    }
    
    */
    
    @GET
    @Path("test2")
    public String CasandraGen() throws Exception {
        cas.CreateConnectionToClaster("192.168.0.90","192.168.0.91","192.168.0.92","chats");
        
        ChatRecord Record;
        int j = 0, z = 0,a = 1;
        for(int i = 0;i < 76926;i++) { //76926
            //dialog1
            Record = new ChatRecord("Hello","Chat " + j,"Nick"+ z,"10.10.2010.12.00");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Hello","Chat " + j,"Bot"+ j,"10.10.2010.12.01");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Hello","Chat " + j,"Nick"+ a,"10.10.2010.12.02");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bot , how are you ?","Chat " + j,"Nick"+ z,"10.10.2010.12.03");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Well, how about you?","Chat " + j,"Bot"+ j,"10.10.2010.12.04");
            cas.InsertRecord(Record);
            Record = new ChatRecord("And I`m well.","Chat " + j,"Nick"+ z,"10.10.2010.12.05");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Nick" + (z+1) + " how are you?","Chat " + j,"Nick"+ z,"10.10.2010.12.06");
            cas.InsertRecord(Record);
            Record = new ChatRecord("I`m well","Chat " + j,"Nick"+ a,"10.10.2010.12.07");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Well","Chat " + j,"Nick"+ z,"10.10.2010.12.08");
            cas.InsertRecord(Record);
            z = z + 2;
            a = a + 2;
            j++;
            //dialog2
            Record = new ChatRecord("Bot , say your favorite color ?","Chat " + j,"Nick"+ z,"10.10.2010.12.10");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Blue.","Chat " + j,"Bot"+ j,"10.10.2010.12.11");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bot , say your favorite fruit ?","Chat " + j,"Nick"+ a,"10.10.2010.12.12");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Banana.","Chat " + j,"Bot"+ j,"10.10.2010.12.13");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bot , say your favorite size ?","Chat " + j,"Nick"+ z,"10.10.2010.12.14");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Big.","Chat " + j,"Bot"+ j,"10.10.2010.12.15");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bot , say your favorite number ?","Chat " + j,"Nick"+ a,"10.10.2010.12.16");
            cas.InsertRecord(Record);
            Record = new ChatRecord("21.","Chat " + j,"Bot"+ j,"10.10.2010.12.17");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bye","Chat " + j,"Nick"+ z,"10.10.2010.12.18");
            cas.InsertRecord(Record);
            z = z + 2;
            a = a + 2;
            j++;
            //dialog3
            Record = new ChatRecord("Hello","Chat " + j,"Nick"+ z,"10.10.2010.12.30");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Hello","Chat " + j,"Bot"+ j,"10.10.2010.12.31");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Hello","Chat " + j,"Nick"+ a,"10.10.2010.12.32");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bot , tell me your name ?","Chat " + j,"Nick"+ z,"10.10.2010.12.33");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bot"+ j,"Chat " + j,"Bot"+ j,"10.10.2010.12.34");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bye","Chat " + j,"Nick"+ z,"10.10.2010.12.35");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bye","Chat " + j,"Bot"+ j,"10.10.2010.12.36");
            cas.InsertRecord(Record);
            Record = new ChatRecord("Bye","Chat " + j,"Nick"+ a,"10.10.2010.12.37");
            cas.InsertRecord(Record);
            z = z + 2;
            a = a + 2;
            j++;
        }

        cas.CloseConnection();
        return "Ok";
    }
    
    @GET
    @Path("test3")
    public String Neo4jGen() throws Exception {
        Node MainNode;
        int z = 0, j = 0;
        CreateConnectForClaster("bolt://127.0.0.1:","7692","7688","7689","neo4j", "ew23ew23");
        MainNode = neo.Select_Type("Main").get(0);

        for(int i = 0;i < 76926;i++) {
            Node Bot = new Node("Bot","Bot" + i);
            Bot.setId(neo.Insert_Node("Bot", "Bot" + i));
            
            Node Key1 = new Node("KeyWord","Bot");
            Key1.setId(neo.Insert_Node(Key1.getType(), Key1.getFild()));
            Node Key2 = new Node("KeyWord","how are you?");
            Key2.setId(neo.Insert_Node(Key2.getType(), Key2.getFild()));
            Node Key3 = new Node("KeyWord","your favorite color ?");
            Key3.setId(neo.Insert_Node(Key3.getType(), Key3.getFild()));
            Node Key4 = new Node("KeyWord","your favorite fruit ?");
            Key4.setId(neo.Insert_Node(Key4.getType(), Key4.getFild()));
            Node Key5 = new Node("KeyWord","your favorite size ?");
            Key5.setId(neo.Insert_Node(Key5.getType(), Key5.getFild()));
            Node Key6 = new Node("KeyWord","your favorite number ?");
            Key6.setId(neo.Insert_Node(Key6.getType(), Key6.getFild()));
            Node Key7 = new Node("KeyWord","your name ?");
            Key7.setId(neo.Insert_Node(Key7.getType(), Key7.getFild()));

            Node Key8 = new Node("KeyWord","Hello");
            Key8.setId(neo.Insert_Node(Key8.getType(), Key8.getFild()));
            Node Key9 = new Node("KeyWord","Bye");
            Key9.setId(neo.Insert_Node(Key9.getType(), Key9.getFild()));

            Node Answer1 = new Node("BotAnswer","Well, how about you?");
            Answer1.setId(neo.Insert_Node(Answer1.getType(), Answer1.getFild()));
            Node Answer2 = new Node("BotAnswer","Blue.");
            Answer2.setId(neo.Insert_Node(Answer2.getType(), Answer2.getFild()));
            Node Answer3 = new Node("BotAnswer","Banana.");
            Answer3.setId(neo.Insert_Node(Answer3.getType(), Answer3.getFild()));
            Node Answer4 = new Node("BotAnswer","Big.");
            Answer4.setId(neo.Insert_Node(Answer4.getType(), Answer4.getFild()));
            Node Answer5 = new Node("BotAnswer","21.");
            Answer5.setId(neo.Insert_Node(Answer5.getType(), Answer5.getFild()));
            Node Answer6 = new Node("BotAnswer","Bot" + i);
            Answer6.setId(neo.Insert_Node(Answer6.getType(), Answer6.getFild()));

            Node Answer7 = new Node("BotAnswer","Hello");
            Answer7.setId(neo.Insert_Node(Answer7.getType(), Answer7.getFild()));
            Node Answer8 = new Node("BotAnswer","Bye");
            Answer8.setId(neo.Insert_Node(Answer8.getType(), Answer8.getFild()));

            neo.Cteate_Connect(MainNode, Bot);

            neo.Cteate_Connect(Bot,Key1);
            neo.Cteate_Connect(Bot,Key8);
            neo.Cteate_Connect(Bot,Key9);

            neo.Cteate_Connect(Key1,Key2);
            neo.Cteate_Connect(Key1,Key3);
            neo.Cteate_Connect(Key1,Key4);
            neo.Cteate_Connect(Key1,Key5);
            neo.Cteate_Connect(Key1,Key6);

            neo.Cteate_Connect(Key2,Answer1);
            neo.Cteate_Connect(Key3,Answer2);
            neo.Cteate_Connect(Key4,Answer3);
            neo.Cteate_Connect(Key5,Answer4);
            neo.Cteate_Connect(Key6,Answer5);
            neo.Cteate_Connect(Key7,Answer6);
            neo.Cteate_Connect(Key8,Answer7);
            neo.Cteate_Connect(Key9,Answer8);


            Node Log = new Node("LogDialog","Chat" + i);
            Log.setId(neo.Insert_Node(Log.getType(), Log.getFild()));
            neo.Cteate_Connect(Bot,Log);

            if(i % 3 == 0) {
                Node States1 = new Node("States","Hello");
                States1.setId(neo.Insert_Node(States1.getType(), States1.getFild()));
                Node States2 = new Node("States","Well, how about you?");
                States2.setId(neo.Insert_Node(States2.getType(), States2.getFild()));

                neo.Cteate_Connect(Log,States1);
                neo.Cteate_Connect(States1,States2);
            }
            else if(i % 3 == 1) {
                Node States1 = new Node("States","Blue");
                States1.setId(neo.Insert_Node(States1.getType(), States1.getFild()));
                Node States2 = new Node("States","Banana.");
                States2.setId(neo.Insert_Node(States2.getType(), States2.getFild()));
                Node States3 = new Node("States","Big.");
                States3.setId(neo.Insert_Node(States3.getType(), States3.getFild()));
                Node States4 = new Node("States","21.");
                States4.setId(neo.Insert_Node(States4.getType(), States4.getFild()));

                neo.Cteate_Connect(Log,States1);
                neo.Cteate_Connect(States1,States2);
                neo.Cteate_Connect(States2,States3);
                neo.Cteate_Connect(States3,States4);
            }
            else if(i % 3 == 2) {
                Node States1 = new Node("States","Hello");
                States1.setId(neo.Insert_Node(States1.getType(), States1.getFild()));
                Node States2 = new Node("States","Bot" + i); 
                States2.setId(neo.Insert_Node(States2.getType(), States2.getFild()));
                Node States3 = new Node("States","Bye");
                States3.setId(neo.Insert_Node(States3.getType(), States3.getFild()));

                neo.Cteate_Connect(Log,States1);
                neo.Cteate_Connect(States1,States2);
                neo.Cteate_Connect(States2,States3);
            }   
        }
        neo.CloseConnection();
    return"Ok";
    }
}