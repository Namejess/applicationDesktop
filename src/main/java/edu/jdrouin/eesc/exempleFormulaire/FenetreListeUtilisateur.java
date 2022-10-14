package edu.jdrouin.eesc.exempleFormulaire;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import edu.jdrouin.eesc.exempleFormulaire.model.Pays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FenetreListeUtilisateur extends JFrame implements WindowListener {

    protected boolean themeSombreActif = true;
    protected DefaultTableModel model;
    protected ArrayList<Utilisateur> listeUtilisateur;


    public FenetreListeUtilisateur () {

        //--------- CREATION ECRAN -----------
        setSize(800,500);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("Application Formulaire");

        addWindowListener(this);

        //ajout du panneau principal avec un layout de 5 zones
        // (NORTH, SOUTH, EAST, WEST, CENTER)
        JPanel panneau = new JPanel(new BorderLayout());
        setContentPane(panneau);
        setLocationRelativeTo(null);

        ChampsSaisie boite = new ChampsSaisie();


        //--------- BOUTON THEME -----------
        JButton boutonTheme = new JButton("Changer le theme");

        boutonTheme.addActionListener(
                e -> {
                    try {
                        if(themeSombreActif) {
                            UIManager.setLookAndFeel(new FlatLightLaf());
                        } else {
                            UIManager.setLookAndFeel(new FlatDarculaLaf());
                        }
                        SwingUtilities.updateComponentTreeUI(this);
                        themeSombreActif = !themeSombreActif;

                    } catch (UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );


        //---------- BOUTON DU HAUT -------

        JButton boutonAjoutUtilisateur = new JButton("Ajouter un utilisateur");

        boutonAjoutUtilisateur.addActionListener(
                e -> {
                    JOptionPane.showOptionDialog(
                            null,
                            new FenetreFormulaire(listeUtilisateur),
                            "Ajouter un utilisateur",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            new Object[]{},
                            null);
                });







        //-----------------------------------
        Box boxBoutonHaut = Box.createVerticalBox();

        boxBoutonHaut.add(
                HelperForm.generateRow(boutonTheme,10,10,0,0, HelperForm.ALIGN_RIGHT)
        );

        boxBoutonHaut.add(
                HelperForm.generateRow(boutonAjoutUtilisateur,10,0,0,10, HelperForm.ALIGN_LEFT)
        );

        //---------- BOUTON HAUT GAUCHE-------
        panneau.add(boxBoutonHaut, BorderLayout.NORTH );






        //---------------- FORMULAIRE ------------------
        Box boxFormulaireListeUtilisateur = Box.createVerticalBox();
        //formulaire.setBorder(BorderFactory.createLineBorder(Color.RED));

        panneau.add(boxFormulaireListeUtilisateur, BorderLayout.CENTER);


        //---------------- TABLE UTILISATEUR ------------------
        model = new DefaultTableModel();
        model.addColumn("Civilité");
        model.addColumn("Nom");
        model.addColumn("Prénom");
        model.addColumn("Pays");
        model.addColumn("Email");
        model.addColumn("Age");
        model.addColumn("Marié/Pacsé");
        model.addColumn("Actions");

        JTable tableUtilisateur = new JTable(model);
        tableUtilisateur.setEnabled(false);

        JScrollPane scrollPaneTableUtilisateur = new JScrollPane(tableUtilisateur);

        panneau.add(scrollPaneTableUtilisateur, BorderLayout.CENTER);


        ouvrirFichier();

        setVisible(true);

    }

    public void ouvrirFichier() {

        //--------- GESTION ERREUR -----------
        ObjectInputStream ois = null;

        try {
            final FileInputStream fichier = new FileInputStream("personne.eesc");
            ois = new ObjectInputStream(fichier);
            listeUtilisateur = (ArrayList<Utilisateur>) ois.readObject();

            for(Utilisateur utilisateurFichier : listeUtilisateur){
                model.addRow(utilisateurFichier.getLigneTableau());
            }


            ois.close();

        } catch (FileNotFoundException e) {
            System.out.println("Premiere fois qu'on ouvre l'application");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Impossible d'ouvrir le fichier");

        } catch (ClassNotFoundException | ClassCastException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fichier corrompu");
        }
    }


    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new FenetreListeUtilisateur();
    }



    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        String[] choix = {"Oui", "Ne pas fermer l'application"};
        int choixUtilisateur = JOptionPane.showOptionDialog(
                this,
                "Voulez-vous vraiment fermer l'application",
                "Confirmer",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                choix[1]);

        if(choixUtilisateur == JOptionPane.YES_OPTION) {
            System.exit(1);
        }

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
