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
public class CasandraCloseConnectionE extends Exception {
    public CasandraCloseConnectionE() { super(); }
    public CasandraCloseConnectionE(String message) { super(message); }
    public CasandraCloseConnectionE(String message, Throwable cause) { super(message, cause); }
    public CasandraCloseConnectionE(Throwable cause) { super(cause); }
}
