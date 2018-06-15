/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import MyClass.ChatRecord;
import MyException.*;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Feda
 */
public class CassandraBD {
    
    Cluster cluster = null;
    Session session = null;
    
    public Session getSession(){
    return this.session;
    }
    //chats
    public void CreateConnection(String ip, String table) throws CasandraCreateConnectionE {
        try {
            cluster = Cluster.builder().addContactPoint(ip).build();  
            session = cluster.connect(table);
        }
        catch (Exception e) {
            throw new CasandraCreateConnectionE(e);
        }
    }
    
    public void CreateConnectionToClaster(String ip1,String ip2, String ip3, String table) throws CasandraCreateConnectionE {
        try {
            cluster = Cluster.builder().addContactPoints(ip1,ip2,ip3).build();  
            session = cluster.connect(table);
        }
        catch (Exception e) {
            throw new CasandraCreateConnectionE(e);
        }
    }
    
    public void CloseConnection() throws CasandraCloseConnectionE {
        try {
            session.close();
            cluster.close();
        }
        catch (Exception e) {
            throw new CasandraCloseConnectionE(e);
        }
    }
    
    public void InsertRecord(ChatRecord Record) throws CasandraInsertE {
        try {
            ResultSet result = session.execute("SELECT next_id FROM ids WHERE id_name = 'record_id'");
        
            Record.setId(result.all().get(0).getInt(0));
        
            String insert = "INSERT INTO message_chat (message_id, text_message, title_chat, nickname,  date_message)"			
   
                    + " VALUES(" + Record.getId() + ",'"+Record.getMessage()+"', '"+Record.getTitle_Chat()+"', '"+Record.getNickname()+"','"+Record.getData()+"');" ;
            
            session.execute(insert);
        
            String update = "UPDATE ids SET next_id = "+ (Record.getId()+ 1) +" WHERE id_name = 'record_id' IF next_id = "+ Record.getId();
        
            session.execute(update);
        }
        catch (Exception e) {
            throw new CasandraInsertE(e);
        }
    }
    
    public ArrayList<ChatRecord> SelectRecord(String Param,String Content) throws CasandraSelectE {
        ArrayList<ChatRecord> List = null;
        try {
            List = new ArrayList<ChatRecord>();
         
            String select = "SELECT * FROM message_chat WHERE "+Param+" = '"+Content+"' ALLOW FILTERING;";
         
            ResultSet result = session.execute(select);
         
            List<Row> RowList = result.all();
         
            for(int i=0;i<RowList.size();i++){
                List.add(new ChatRecord(RowList.get(i).getInt("message_id"),RowList.get(i).getString("text_message"),RowList.get(i).getString("title_chat"),RowList.get(i).getString("nickname"),RowList.get(i).getString("date_message")));
            }
        }
        catch (Exception e) {
            throw new CasandraSelectE(e);
        }
        
        return List;
    }
    
    public ArrayList<ChatRecord> SelectLastRecord(String Title,String Count) throws CasandraSelectE {
        ArrayList<ChatRecord> List = null;
        try {
            List = new ArrayList<ChatRecord>();
         
            String select = "SELECT * FROM message_chat WHERE title_chat = '"+Title+"' ORDER BY message_id DESC LIMIT "+Count+" ALLOW FILTERING;";
         
            ResultSet result = session.execute(select);
         
            List<Row> RowList = result.all();
         
            for(int i=0;i<RowList.size();i++){
                List.add(new ChatRecord(RowList.get(i).getInt("message_id"),RowList.get(i).getString("text_message"),RowList.get(i).getString("title_chat"),RowList.get(i).getString("nickname"),RowList.get(i).getString("date_message")));
            }
        }
        catch (Exception e) {
            throw new CasandraSelectE(e);
        }
        
        return List;
    }
    
    public Integer RemoveRecord(String Title,String Date) throws CasandraDeleteE {
        int Count = 0;
        try {
            ResultSet result = session.execute("SELECT * FROM message_chat WHERE title_chat = '"+Title+"' and date_message = '"+Date+"' ALLOW FILTERING;");
         
            List<Row> RowList = result.all();
        
            for(int i=0;i<RowList.size();i++){
                session.execute("DELETE FROM message_chat WHERE message_id ="+ RowList.get(i).getInt(0)+";");
                Count++;
            }
        }
        catch(Exception e) {
            throw new CasandraDeleteE(e);
        }
        return Count;
    }
}