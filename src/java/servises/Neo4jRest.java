/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servises;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import beans.Neo4jBD;
import MyClass.Node;
import java.util.ArrayList;

import MyException.*;
/**
 *
 * @author Feda
 */

@Path("Neo4j")
public class Neo4jRest {
    Neo4jBD neo = new Neo4jBD();
    
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
    @Path("CreateB:{Name}")
    public String CreateBot(@PathParam("Name") String Name) throws Neo4jCreateConnectionE {
        Node mainNode, myNode = new Node("Bot",Name);
        try {
            if(!CreateConnectForClaster("bolt://127.0.0.1:","7692","7688","7689","neo4j", "ew23ew23")){
                return "LEADER is not find";
            }
        }
        catch(Neo4jCreateConnectionE e) {
            return "Connection with Neo4j could not be established";
        }
        
        try {
            myNode.setId(neo.Insert_Node("Bot", Name));
        }
        catch(Neo4jInsertE e) {
            return "Add to Neo4j failed";
        }
        
        try {
            mainNode = neo.Select_Type("Main").get(0);
        }
        catch(Neo4jSelectE e) {
            return "Select to Neo4j failed 2";
        }
        
        try {
            neo.Cteate_Connect(mainNode, myNode);
        }
        catch(Neo4jAddConnectionE e) {
            return "Add connection failed";
        }
        
        try {
            int buff = Integer.valueOf(mainNode.getFild());
            mainNode.setFild(String.valueOf(buff+1));
            neo.Update_Fild(mainNode, mainNode.getFild());
        }
        catch(Neo4jUpdateE e) {
            return "Update failed";
        }
        
        try {
            neo.CloseConnection();
        }
        catch(Neo4jCloseConnectionE e) {
            return "Closeed Neo4j faled";
        }
        return "Create Bot complited";
    }
    
    @GET
    @Path("SelectR:{Port}") 
    public String SelectRole(@PathParam("Port") String Port){
        String Answer = "";
        try {
            neo.CreateConnection("bolt://127.0.0.1:" + Port, "neo4j", "ew23ew23");
        }
        catch(Neo4jCreateConnectionE e) {
            return "Connection with Neo4j could not be established";
        }
        
        try {
            Answer = neo.Select_Role();
        }
        catch(Neo4jSelectE e) {
            return "Select to Neo4j failed";
        }
        
        try {
            neo.CloseConnection();
        }
        catch(Neo4jCloseConnectionE e) {
            return "Closeed Neo4j faled";
        }
        return Answer;
    }
            
    @GET
    @Path("DeleteB:{Name}")
    public String DeleteBot(@PathParam("Name") String Name) throws Neo4jCreateConnectionE {
        Node mainNode, myNode;
        //try {
            if(!CreateConnectForClaster("bolt://127.0.0.1:","7692","7688","7689","neo4j", "ew23ew23")){
                return "LEADER is not find2";
            }
        //}
       // catch(Neo4jCreateConnectionE e) {
        //    return "Connection with Neo4j could not be established";
        //}
        
        try {
            myNode = neo.Select_Fild("Bot",Name).get(0);
        }
        catch(Neo4jSelectE e) {
            return "Select to Neo4j failed";
        }
        
        try {
            neo.Delete_Node(myNode);
        }
        catch(Neo4jDeleteE e) {
            return "Delete failed";
        }
        
        try {
            mainNode = neo.Select_Type("Main").get(0);
        }
        catch(Neo4jSelectE e) {
            return "Select to Neo4j failed";
        }
        
        try {
            int buff = Integer.valueOf(mainNode.getFild());
            mainNode.setFild(String.valueOf(buff-1));
            neo.Update_Fild(mainNode, mainNode.getFild());
        }
        catch(Neo4jUpdateE e) {
            return "Update failed";
        }
        
        try {
            neo.CloseConnection();
        }
        catch(Neo4jCloseConnectionE e) {
            return "Closeed Neo4j faled";
        }
        return "Delete Bot complited ";
    }

    @GET
    @Path("AnswerB:{Name}/{Message}")
    public String AnswerBot(@PathParam("Name") String Name, @PathParam("Message") String Message) throws Neo4jCreateConnectionE {
        String Answer = "";
        Node myNode, Level1 = null, Level2 = null;
        ArrayList<Node> buff;
        //try {
            if(!CreateConnectForClaster("bolt://127.0.0.1:","7692","7688","7689","neo4j", "ew23ew23")){
                return "LEADER is not find3";
            }
        //}
        //catch(Neo4jCreateConnectionE e) {
        //    return "Connection with Neo4j could not be established";
        //}
        
        try {
            myNode = neo.Select_Fild("Bot",Name).get(0);
        }
        catch(Neo4jSelectE e) {
            return "Select to Neo4j failed";
        }
        
        try {
            buff = neo.Select_From_Connect(myNode, "KeyWord");
        }
        catch(Neo4jSelectE e) {
            return "Select to Neo4j failed 2";
        }
        
        int find = -1;
        for(int i=0;i < buff.size();i++) {
            if(Message.contains(buff.get(i).getFild())) {
                find = i;
                Level1 = buff.get(i);
                break;
            }
        }
        if(find == -1)
            return "Not answer";
        else {
            try {
                buff = neo.Select_From_Connect(buff.get(find), "BotAnswer");
            }
            catch(Neo4jSelectE e) {
                return "Select to Neo4j failed 2";
            }
            
            if(buff.size() == 0) {
                try {
                    buff = neo.Select_From_Connect(Level1, "KeyWord");
                }
                catch(Neo4jSelectE e) {
                    return "Select to Neo4j failed 2";
                }
                for(int i=0;i < buff.size();i++) {
                    String buff3 = buff.get(i).getFild().replace(" ?", "");
                    if(Message.contains(buff3)) {
                        Level2 = buff.get(i);
                        break;
                    }
                }
                
                try {
                    buff = neo.Select_From_Connect(Level2, "BotAnswer");
                }
                catch(Neo4jSelectE e) {
                    return "Select to Neo4j failed 2";
                }
                
                Answer = buff.get(0).getFild(); 
            }
            else {
                Answer = buff.get(0).getFild();
            }
        }
        try {
            neo.CloseConnection();
        }
        catch(Neo4jCloseConnectionE e) {
            return "Closeed Neo4j faled";
        }
        
        return Answer;
    }
}