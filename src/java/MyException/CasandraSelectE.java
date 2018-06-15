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
public class CasandraSelectE extends Exception {
    public CasandraSelectE() { super(); }
    public CasandraSelectE(String message) { super(message); }
    public CasandraSelectE(String message, Throwable cause) { super(message, cause); }
    public CasandraSelectE(Throwable cause) { super(cause); }
}
