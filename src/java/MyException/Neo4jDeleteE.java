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
public class Neo4jDeleteE extends Exception {
    public Neo4jDeleteE() { super(); }
    public Neo4jDeleteE(String message) { super(message); }
    public Neo4jDeleteE(String message, Throwable cause) { super(message, cause); }
    public Neo4jDeleteE(Throwable cause) { super(cause); }
}
