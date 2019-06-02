/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.exceptions;

/**
 *
 * @author Admin
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6936410732203003964L;

    public NotFoundException(String message) {
        super(message);
    }

}
