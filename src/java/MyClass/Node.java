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
public class Node {
    private String Id;
    private String Type;
    private String Fild;
    private String NameFild;
    
    public Node() {
        Id = Type = Fild = NameFild = "";
    }
    
    public Node(String Type, String Fild) {
        this.Type = Type;
        this.Fild = Fild;
        switch(Type) {
            case "Main":
                NameFild = "Count";
                break;
            case "Bot":
                NameFild = "Name";
                break;
            case "KeyWord":
                NameFild = "Word";
                break;
            case "States":
                NameFild = "States";
                break;
            case "BotAnswer":
                NameFild = "Answer";
            break;
            case "LogDialog":
                NameFild = "Chat";
                break;
        }
    }
    
    public Node(String Id, String Type, String Fild) {
        this.Id = Id;
        this.Type = Type;
        this.Fild = Fild;
        switch(Type) {
            case "Main":
                NameFild = "Count";
                break;
            case "Bot":
                NameFild = "Name";
                break;
            case "KeyWord":
                NameFild = "Word";
                break;
            case "States":
                NameFild = "States";
                break;
            case "BotAnswer":
                NameFild = "Answer";
            break;
            case "LogDialog":
                NameFild = "Chat";
                break;
        }
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getFild() {
        return Fild;
    }

    public void setFild(String Fild) {
        this.Fild = Fild;
    }
    
    public String getNameFild() {
        return NameFild;
    }

    public void setNameFild(String NameFild) {
        this.NameFild = NameFild;
    }
}
