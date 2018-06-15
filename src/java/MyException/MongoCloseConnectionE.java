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
public class MongoCloseConnectionE extends Exception {
    public MongoCloseConnectionE() { super(); }
    public MongoCloseConnectionE(String message) { super(message); }
    public MongoCloseConnectionE(String message, Throwable cause) { super(message, cause); }
    public MongoCloseConnectionE(Throwable cause) { super(cause); }
}
