/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Rafael
 */
public class ControladoraIdioma {

    private ResourceBundle resourceBundle;
    private static ControladoraIdioma instancia;

    public void DefineIdioma(int idioma) {
        switch (idioma) {
            case 1:
                //Por
                break;
            case 2:
                //Eng
                resourceBundle = ResourceBundle.getBundle("Idioma/english");
                break;
            case 3:
                //Esp
                break;
        }
    }

    public String Valor(String valor) {
        return resourceBundle.getString(valor);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public static ControladoraIdioma getInstancia() {
        if (instancia == null) {
            instancia = new ControladoraIdioma();
        }
        return instancia;
    }

    public static void setInstancia(ControladoraIdioma instancia) {
        ControladoraIdioma.instancia = instancia;
    }

    

}
