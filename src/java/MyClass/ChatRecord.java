/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClass;

/**
 *
 * @author Feda
 */
public class ChatRecord {
    Integer Id;
    String Message;
    String Title_Chat;
    String Nickname;
    String Data;

    public ChatRecord(Integer Id, String Message, String Title_Chat, String Nickname, String Data) {
        this.Id = Id;
        this.Message = Message;
        this.Title_Chat = Title_Chat;
        this.Nickname = Nickname;
        this.Data = Data;
    }

    public ChatRecord(String Message, String Title_Chat, String Nickname, String Data) {
        this.Message = Message;
        this.Title_Chat = Title_Chat;
        this.Nickname = Nickname;
        this.Data = Data;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getTitle_Chat() {
        return Title_Chat;
    }

    public void setTitle_Chat(String Title_Chat) {
        this.Title_Chat = Title_Chat;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
    
}
