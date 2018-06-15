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
public class Neo4jInsertE extends Exception {
    public Neo4jInsertE() { super(); }
    public Neo4jInsertE(String message) { super(message); }
    public Neo4jInsertE(String message, Throwable cause) { super(message, cause); }
    public Neo4jInsertE(Throwable cause) { super(cause); }
}
