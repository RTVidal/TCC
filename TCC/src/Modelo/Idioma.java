/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ResourceBundle;

/**
 *
 * @author Rafael
 */
public class Idioma {
    
    private ResourceBundle resourceBundle;
    
    //1. Por, 2. Eng, 3. Esp
    private int idioma;

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
                
        this.resourceBundle = resourceBundle;
    }
    
}
