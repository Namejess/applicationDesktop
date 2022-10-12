package edu.jdrouin.eesc.exempleFormulaire;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ChampsSaisie extends Box {

    protected JTextField textField = new JTextField();
    protected JLabel icone = new JLabel();
    protected JLabel message = new JLabel();

    protected Border borderOriginale;

    public ChampsSaisie (){
        super(BoxLayout.Y_AXIS);
        Box ligne1 = Box.createHorizontalBox();
        ligne1.add(textField);
        ligne1.add(icone);
        ligne1.add(Box.createHorizontalGlue());
        add(ligne1);

        Box ligne2 = Box.createHorizontalBox();
        ligne2.add(message);
        ligne2.add(Box.createHorizontalGlue());
        add(ligne2);

        borderOriginale = textField.getBorder();
    }

    public void erreur(String texte){
        textField.setBorder(BorderFactory.createLineBorder(Color.RED));
        message.setForeground(Color.RED);
        message.setFont(new Font("Arial", 12, 9));
        message.setText(texte);
    }

    public void resetMessage(){
        textField.setBorder(borderOriginale);
        message.setText("");
    }



    public String getText(){
        return textField.getText();
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JLabel getIcone() {
        return icone;
    }

    public void setIcone(JLabel icone) {
        this.icone = icone;
    }

    public JLabel getMessage() {
        return message;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }
}
