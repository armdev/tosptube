/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author armena
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserData implements Serializable {

    private String userId;

    private String firstName;
    private String lastName;

}
