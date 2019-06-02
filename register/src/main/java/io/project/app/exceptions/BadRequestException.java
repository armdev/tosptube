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
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -433185572876737517L;

    public BadRequestException(String message) {
        super(message);
    }

}
