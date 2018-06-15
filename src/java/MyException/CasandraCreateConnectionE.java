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
public class CasandraCreateConnectionE extends Exception {
    public CasandraCreateConnectionE() { super(); }
    public CasandraCreateConnectionE(String message) { super(message); }
    public CasandraCreateConnectionE(String message, Throwable cause) { super(message, cause); }
    public CasandraCreateConnectionE(Throwable cause) { super(cause); }
}
