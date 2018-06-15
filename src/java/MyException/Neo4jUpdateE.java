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
public class Neo4jUpdateE extends Exception  {
    public Neo4jUpdateE() { super(); }
    public Neo4jUpdateE(String message) { super(message); }
    public Neo4jUpdateE(String message, Throwable cause) { super(message, cause); }
    public Neo4jUpdateE(Throwable cause) { super(cause); }
}
