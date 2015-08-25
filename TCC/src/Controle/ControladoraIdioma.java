/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class ControladoraIdioma {

    private ResourceBundle resourceBundle;
    private static ControladoraIdioma instancia;
    private String idiomaAtual;

    public void DefineIdioma(String idioma) {
        switch (idioma) {
            case "Português":
                idiomaAtual = "Português";
                resourceBundle = ResourceBundle.getBundle("Internacionalizacao/portugues");
                break;
            case "English":
                idiomaAtual = "English";
                resourceBundle = ResourceBundle.getBundle("Internacionalizacao/english");
                break;
            case "Español":
                idiomaAtual = "Español";
                resourceBundle = ResourceBundle.getBundle("Internacionalizacao/espanol");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Idioma desconhecido pelo sistema. Sem alterações.");
                break;
        }
    }

    public String Valor(String valor) {
        try {
            return resourceBundle.getString(valor);
        } catch (Exception e) {
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

    public String getIdiomaAtual() {
        return idiomaAtual;
    }

}
