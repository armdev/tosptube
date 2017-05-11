/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.jsf.utils;

/**
 *
 * @author Armen
 */
public class ConfigUtil {

    public static String getFileHome() {
        String home = System.getProperty("java.io.tmpdir");
        if (home == null) {
            home = System.getProperty("java.io.tmpdir");
        }
        if (home == null) {
            throw new RuntimeException("FILE_HOME is not defined in ENV or java start up.");
        }
        return home;
    }

}
