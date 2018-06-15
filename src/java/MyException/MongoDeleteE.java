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
public class MongoDeleteE extends Exception {
    public MongoDeleteE() { super(); }
    public MongoDeleteE(String message) { super(message); }
    public MongoDeleteE(String message, Throwable cause) { super(message, cause); }
    public MongoDeleteE(Throwable cause) { super(cause); }
}
