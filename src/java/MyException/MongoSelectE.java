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
public class MongoSelectE extends Exception  {
    public MongoSelectE() { super(); }
    public MongoSelectE(String message) { super(message); }
    public MongoSelectE(String message, Throwable cause) { super(message, cause); }
    public MongoSelectE(Throwable cause) { super(cause); }
}
