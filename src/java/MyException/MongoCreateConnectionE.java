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
public class MongoCreateConnectionE extends Exception {
    public MongoCreateConnectionE() { super(); }
    public MongoCreateConnectionE(String message) { super(message); }
    public MongoCreateConnectionE(String message, Throwable cause) { super(message, cause); }
    public MongoCreateConnectionE(Throwable cause) { super(cause); }
}
