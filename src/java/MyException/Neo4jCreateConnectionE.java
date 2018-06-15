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
public class Neo4jCreateConnectionE extends Exception {
    public Neo4jCreateConnectionE() { super(); }
    public Neo4jCreateConnectionE(String message) { super(message); }
    public Neo4jCreateConnectionE(String message, Throwable cause) { super(message, cause); }
    public Neo4jCreateConnectionE(Throwable cause) { super(cause); }
}
