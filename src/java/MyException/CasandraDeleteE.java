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
public class CasandraDeleteE extends Exception {
    public CasandraDeleteE() { super(); }
    public CasandraDeleteE(String message) { super(message); }
    public CasandraDeleteE(String message, Throwable cause) { super(message, cause); }
    public CasandraDeleteE(Throwable cause) { super(cause); }
}
