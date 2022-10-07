package edu.jdrouin.eesc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Fenetre extends JFrame {

    protected static int largeurEcran = 800;
    protected static int hauteurEcran = 600;

    public Fenetre() {
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panneau = new JPanel();

        setContentPane(panneau);

        JButton bouton = new JButton("Fermer l'application");

        panneau.add(bouton);

        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(largeurEcran, this.hauteurEcran));
        setBounds(0, 0, this.largeurEcran, this.hauteurEcran);


//        ActionListener evt = new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("clic");
//            }
//        };

        bouton.addActionListener(e -> {
            Object[] choix = {"Oui", "Nope :("};
            int reponse = JOptionPane.showOptionDialog(this, "Voulez vous fermer l'application ?", "Confirmer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);

            if (reponse == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        //-----------COMBOBOX-------------
        String[] listeCivilite = {"M.", "Me.", "Mlle.", "Non précisé"};
        JComboBox<Object> selectCivilite = new JComboBox<>(listeCivilite);
        panneau.add(selectCivilite);

        selectCivilite.addActionListener((ActionEvent e) -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            System.out.println(comboBox.getSelectedItem());
        });

        //----------Autre exemple
        Utilisateur[] listeUtilisateurs = {
                new Utilisateur("DROUIN", "Jessy"),
                new Utilisateur("BOB", "Paul"),
                new Utilisateur("MORANO", "Nadine"),
        };

        JComboBox<Utilisateur> selectUtilisateur = new JComboBox<>(listeUtilisateurs);
        selectUtilisateur.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                Utilisateur utilisateur = (Utilisateur) value;
                setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
                if (isSelected) {
                    setBackground(Color.PINK);
                }
                setFont(new Font("Roboto", Font.BOLD, 15));
                return this;
            }
        });
        panneau.add(selectUtilisateur);

        //------------BOUTON FORMULAIRE------------

        JButton boutonFormulaire = new JButton("Envoyer");
        boutonFormulaire.addActionListener(e -> {
            System.out.println(selectCivilite.getSelectedItem() + ((Utilisateur) selectUtilisateur.getSelectedItem()).getNom());
        });

        panneau.add(boutonFormulaire);

        setVisible(true);

    }

    public static void main(String[] args) {
        new Fenetre();
    }
}