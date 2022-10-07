package edu.jdrouin.eesc;

import javax.swing.*;
import java.awt.*;

public class Champs {

    public static Box generate(String label, JComponent... listeComposants) {
        Box ligne = Box.createHorizontalBox();

        ligne.setMaximumSize(new Dimension(500, 30));
        ligne.add(Box.createRigidArea(new Dimension(1, 50)));

        JLabel jlabel = new JLabel(label);
        jlabel.setPreferredSize(new Dimension(100, 30));
        ligne.add(jlabel);

        for (Component composant : listeComposants) {
            ligne.add(composant);
        }

        return ligne;
    }
}