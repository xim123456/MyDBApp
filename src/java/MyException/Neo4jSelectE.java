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
public class Neo4jSelectE extends Exception {
    public Neo4jSelectE() { super(); }
    public Neo4jSelectE(String message) { super(message); }
    public Neo4jSelectE(String message, Throwable cause) { super(message, cause); }
    public Neo4jSelectE(Throwable cause) { super(cause); }
}
