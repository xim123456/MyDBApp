/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import MyException.*;
import MyClass.Node;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementRunner;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

public class Neo4jBD {
    
    Driver driver;
    Session session;
    
    public void CreateConnection( String uri, String user, String password ) throws Neo4jCreateConnectionE  {
        try {
            driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
            session = driver.session();
        }
        catch(Exception e) {
            throw new Neo4jCreateConnectionE(e);
        }
    }
    
    public void CloseConnection() throws Neo4jCloseConnectionE {
        try {
            session.close();
            driver.close();
        }
        catch (Exception e) {
            throw new Neo4jCloseConnectionE(e);
        }
    }
    
    public String Insert_Node(String Type, String Fild) throws Neo4jInsertE {
        try { 
        String Answer = session.writeTransaction( new TransactionWork<String>()  {
                @Override
                public String execute( Transaction tx )  {
                    StatementResult result = null;
                    switch(Type) {
                        case "Main":
                            result = tx.run( "CREATE (a:Main  { Count: '" + Fild + "'}) RETURN id(a)");
                            break;
                        case "Bot":
                            result = tx.run( "CREATE (a:Bot { Name: '" + Fild + "'}) RETURN id(a)");
                            break;
                        case "KeyWord":
                            result = tx.run( "CREATE (a:KeyWord { Word: '" + Fild + "'}) RETURN id(a)");
                            break;
                        case "States":
                            result = tx.run( "CREATE (a:States { States: '" + Fild + "'}) RETURN id(a)");
                            break;
                        case "BotAnswer":
                            result = tx.run( "CREATE (a:BotAnswer { Answer: '" + Fild + "'}) RETURN id(a)");
                            break;
                        case "LogDialog":
                            result = tx.run( "CREATE (a:LogDialog { Chat: '" + Fild + "'}) RETURN id(a)");
                            break;
                    }
                    return String.valueOf(result.single().get(0).asInt());
                }
            } );
                return Answer;
        }
        catch (Exception e) {
            throw new Neo4jInsertE(e);
        }
    }
    
    public void Cteate_Connect(Node Node1, Node Node2) throws Neo4jAddConnectionE {
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run(    "MATCH (a:" + Node1.getType() + " { " + Node1.getNameFild() + " : '"+ Node1.getFild() +"'})," +
                            " (b:" + Node2.getType() + " {" + Node2.getNameFild() + ": '" + Node2.getFild() + "'})" +
                            "WHERE id(a) = " + Node1.getId() + " AND id(b) = " + Node2.getId() + "  MERGE (a)-[C:C1]->(b) RETURN 'Node insert!'");
                    return result.single().get( 0 ).asString();
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jAddConnectionE(e);
        }
    }

    public void Delete_Node(Node node) throws Neo4jDeleteE {
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run(    "MATCH (a:" + node.getType() + " { " + node.getNameFild() + " : '"+ node.getFild() +"'}) WHERE id(a) = " + node.getId() + " DETACH DELETE a");
                    return "";
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jDeleteE(e);
        }
    }
    
    public void Delete_Node_Id(String id) throws Neo4jDeleteE {
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run(    "MATCH (a) WHERE id(a) = " + id + " DETACH DELETE a");
                    return "";
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jDeleteE(e);
        }
    }

    public void Update_Fild(Node node, String NewFild) throws Neo4jUpdateE {
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run("MATCH (a:" + node.getType() + ") WHERE id(a) = " + node.getId() + " SET a." + node.getNameFild() + " = '" + NewFild + "' RETURN a");
                    return "";
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jUpdateE(e);
        }
    }

    public ArrayList<Node> Select_Type(String Type) throws Neo4jSelectE {
        ArrayList<Node> res = new ArrayList<Node>();
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    Node buff = new Node(Type,"123");
                    StatementResult result = null;
                    result = tx.run("MATCH (a:" + Type + ")  RETURN a." + buff.getNameFild() + ", id(a)");
                    List<Record> buff2 = result.list();
                    for(int i = 0;i < buff2.size();i++) {
                        res.add(new Node(String.valueOf(buff2.get(i).get(1).asInt()),Type,buff2.get(i).get(0).asString()));
                    }
                    return "Ok";
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jSelectE(e);
        }
        return res;
    }

    public ArrayList<Node> Select_Fild(String Type, String Fild) throws Neo4jSelectE{
        ArrayList<Node> res = new ArrayList<Node>();
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    Node buff = new Node(Type,Fild);
                    StatementResult result = null;
                    result = tx.run("MATCH (a:" + Type + " {" + buff.getNameFild() + ": '"+ buff.getFild() + "'})  RETURN a." + buff.getNameFild() + ", id(a)");
                    List<Record> buff2 = result.list();
                    for(int i = 0;i < buff2.size();i++) {
                        res.add(new Node(String.valueOf(buff2.get(i).get(1).asInt()),Type,buff2.get(i).get(0).asString()));
                    }
                    return "Ok";
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jSelectE(e);
        }
        return res;
    }

    public ArrayList<Node> Select_From_Connect(Node node, String Type) throws Neo4jSelectE{
        ArrayList<Node> res = new ArrayList<Node>();
        try {
        String Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    Node buff = new Node(Type,"123");
                    StatementResult result = null;
                    result = tx.run("Match (a:" + node.getType() + " {" + node.getNameFild() + ":'" + node.getFild() + "'}) -[:C1]- (b:" + Type + ") WHERE id(a) = " + node.getId() + "  return id(b), b." + buff.getNameFild()+ "");
                    List<Record> buff2 = result.list();
                    for(int i = 0;i < buff2.size();i++) {
                        res.add(new Node(String.valueOf(buff2.get(i).get(0).asInt()),Type,buff2.get(i).get(1).asString()));
                    }
                    return "Ok";
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jSelectE(e);
        }
        return res;
    }

    public String Select_Role() throws Neo4jSelectE{
        String Answer = "";
        try {
        Answer = session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = null;
                    result = tx.run("CALL dbms.cluster.role()");
                    return result.list().get(0).get(0).asString();
                }
            } );
        }
        catch (Exception e) {
            throw new Neo4jSelectE(e);
        }
        return Answer;
    }
}
