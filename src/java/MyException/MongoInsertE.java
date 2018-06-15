/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyException;

import java.lang.Exception;
/**
 *
 * @author Feda
 */
public class MongoInsertE extends Exception {
    public MongoInsertE() { super(); }
    public MongoInsertE(String message) { super(message); }
    public MongoInsertE(String message, Throwable cause) { super(message, cause); }
    public MongoInsertE(Throwable cause) { super(cause); }
}
