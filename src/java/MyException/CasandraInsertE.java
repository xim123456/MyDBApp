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
public class CasandraInsertE extends Exception {
    public CasandraInsertE() { super(); }
    public CasandraInsertE(String message) { super(message); }
    public CasandraInsertE(String message, Throwable cause) { super(message, cause); }
    public CasandraInsertE(Throwable cause) { super(cause); }
}
