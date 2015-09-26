/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Suporte;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Classe responsável por limitar a quantidade de caracteres nos campos de texto
 *
 * @author Rafael
 */
public class LimiteCaracteres extends PlainDocument {

    private final int iMaxLength;

    public LimiteCaracteres(int maxlen) {
        super();
        iMaxLength = maxlen;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        //if (s == null) return;  

        if (iMaxLength <= 0) // aceitara qualquer no. de caracteres  
        {
            super.insertString(offset, str, attr);
            return;
        }

        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength) // se o comprimento final for menor...  
        {
            super.insertString(offset, str, attr);   // ...aceita str
            
        } else {
            
            if (getLength() == iMaxLength) {
                return; // nada a fazer  
            }
            String newStr = str.substring(0, (iMaxLength - getLength()));

            super.insertString(offset, newStr, attr);
        }
    }
}
