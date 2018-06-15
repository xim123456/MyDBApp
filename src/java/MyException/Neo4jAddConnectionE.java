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
public class Neo4jAddConnectionE extends Exception {
    public Neo4jAddConnectionE() { super(); }
    public Neo4jAddConnectionE(String message) { super(message); }
    public Neo4jAddConnectionE(String message, Throwable cause) { super(message, cause); }
    public Neo4jAddConnectionE(Throwable cause) { super(cause); }
}
