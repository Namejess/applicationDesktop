package edu.jdrouin.eesc.exempleFormulaire;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import edu.jdrouin.eesc.exempleFormulaire.model.Pays;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

public class FenetrePrincipale extends JFrame implements WindowListener {

    protected boolean themeSombreActif = true;
    protected int defaultMargin = 10;

    public FenetrePrincipale() {
        setSize(600,600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(this);

        //ajout du panneau principal avec un layout de 5 zones
        // (NORTH, SOUTH, EAST, WEST, CENTER)
        JPanel panneau = new JPanel(new BorderLayout());
        setContentPane(panneau);
        setLocationRelativeTo(null);
        setTitle("Application Desktop" );

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

        //---------- DISPOSITION DES COMPOSANTS -------

        panneau.add(
                HelperForm.generateRow(boutonTheme, 10,
                        10,
                        0,
                        0,
                        HelperForm.ALIGN_RIGHT),
                BorderLayout.NORTH);




        //---------------FORMULAIRE-----------------
        Box formulaire = Box.createVerticalBox();
        panneau.add(formulaire, BorderLayout.CENTER);


        //---------------LISTE CIVILITE-----------------
        String[] listeCivilite = {"Monsieur", "Madame", "Mademoiselle", "Autre"};
        JComboBox<String> selectCivilite = new JComboBox<>(listeCivilite);
        formulaire.add(HelperForm.generateField("Civilité", selectCivilite));


        //---------- CHAMPS TEXT : NOM-------
        ChampsSaisie champsNom = new ChampsSaisie();
        formulaire.add(HelperForm.generateField("Nom", champsNom));

        //---------- CHAMPS TEXT : PRENOM-------
        ChampsSaisie champsPrenom = new ChampsSaisie();
        formulaire.add(HelperForm.generateField("Prenom", champsPrenom));

        //---------------LISTE PAYS-----------------
        Pays[] listePays = {
                new Pays("France", "FR", "fr.png"),
                new Pays("Royaume-unis", "GBR", "gb.png"),
                new Pays("Allemagne", "DE", "de.png")
        };

        JComboBox<Pays> selectPays = new JComboBox<>(listePays);

        selectPays.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Pays pays = (Pays) value;

                try {
                    Image image = ImageIO.read(new File("src/main/resources/drapeaux/" + pays.getImage()));

                    Image resizedImage = image.getScaledInstance(20, 16, Image.SCALE_SMOOTH);

                    setIcon(new ImageIcon(resizedImage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                setIconTextGap(10);

                setText(pays.getIso() + "-" + pays.getNom());
                setFont(new Font("Roboto", Font.BOLD, 11));

                return this;
            }
        });

        formulaire.add(HelperForm.generateField("Pays", selectPays));

        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> {

            boolean erreurNom = false;
            boolean erreurPrenom = false;

            String message = "Le formulaire comporte des erreurs :";

            champsNom.resetMessage();
            champsPrenom.resetMessage();
//            champsNom.setBorder(BorderFactory.createEmptyBorder());
//            champsPrenom.setBorder(BorderFactory.createEmptyBorder());


            if (champsNom.getText().equals("")){
                erreurNom = true;
                message += "\n - Nom obligatoire";
                champsNom.erreur("Champs obligatoire");
            }

            if(champsPrenom.getText().equals("")){
                erreurPrenom = true;
                message += "\n - Prenom obligatoire";
                champsPrenom.erreur("Champs obligatoire");

            }

//            message = message.substring(0, message.length()-2);

            if (erreurNom ||erreurPrenom){
                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Erreur de saisie",
                        JOptionPane.WARNING_MESSAGE);
            }

        });
        boutonValider.setSize(new Dimension(100, 30));

        //--------- BOUTON DU BAS -----------

        panneau.add(
                HelperForm.generateRow(boutonValider, 0,
                        10,
                        10,
                        0,
                        HelperForm.ALIGN_RIGHT),
                BorderLayout.SOUTH);

        //---------------VISIBLE TRUE-----------------
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        new FenetrePrincipale();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        String[] choix = {"Oui","Ne pas fermer l'application"};
        int choixUtilisateur = JOptionPane.showOptionDialog(
                this,
                "Voulez-vous vraiment fermer l'application",
                "Confirmer",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choix,
                choix[0]);

        if (choixUtilisateur == JOptionPane.YES_OPTION){
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
