/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

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
                resourceBundle = ResourceBundle.getBundle("Internacionalizacao/portugues");
                break;
            case 2:
                //Eng
                resourceBundle = ResourceBundle.getBundle("Internacionalizacao/english");
                break;
            case 3:
                //Esp
                resourceBundle = ResourceBundle.getBundle("Internacionalizacao/espanol");
                break;
        }
    }

    public String Valor(String valor) {
        try {
            return resourceBundle.getString(valor);
        } catch(Exception e)
        {
            return valor;
        }
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
