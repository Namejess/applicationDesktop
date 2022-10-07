package edu.jdrouin.eesc;

import javax.swing.*;
import java.awt.*;

public class Champs {

    public static Box generate(String label, JComponent... listeComposants) {
        Box ligne = Box.createHorizontalBox();
        ligne.setMaximumSize(new Dimension(500, 30));
        ligne.add(new JLabel(label));

        for (Component composant : listeComposants) {
            ligne.add(composant);

        }

        return ligne;
    }
}