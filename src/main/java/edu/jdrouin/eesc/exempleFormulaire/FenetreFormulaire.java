package edu.jdrouin.eesc.exempleFormulaire;

import edu.jdrouin.eesc.exempleFormulaire.model.Pays;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FenetreFormulaire extends JPanel  {

    protected boolean themeSombreActif = true;

    protected int defaultMargin = 10;
    protected JCheckBox champsMarie;
    protected ChampsSaisie champsAge;
    protected JComboBox<String> selectCivilite;
    protected ChampsSaisie champsNom;
    protected JComboBox<Pays> selectPays;
    protected ChampsSaisie champsPrenom;
    protected ChampsSaisie champsEmail;


    public FenetreFormulaire (OnClickAjouter callback) {


        //--------- CREATION ECRAN -----------
        setSize(500,500);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //ajout du panneau principal avec un layout de 5 zones
        // (NORTH, SOUTH, EAST, WEST, CENTER)
        //JPanel panneau = new JPanel(new BorderLayout());
        //setContentPane(panneau);
       // setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setToolTipText("Formulaire");

        ChampsSaisie boite = new ChampsSaisie();

//        //--------- BOUTON THEME -----------
//
//        JButton boutonTheme = new JButton("Changer le theme");
//
//        boutonTheme.addActionListener(
//                e -> {
//                    try {
//                        if(themeSombreActif) {
//                            UIManager.setLookAndFeel(new FlatLightLaf());
//                        } else {
//                            UIManager.setLookAndFeel(new FlatDarculaLaf());
//                        }
//                        SwingUtilities.updateComponentTreeUI(this);
//                        themeSombreActif = !themeSombreActif;
//
//                    } catch (UnsupportedLookAndFeelException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//        );



        //---------- BOUTONS DU HAUT -------
//        add(
//                HelperForm.generateRow(boutonTheme,10,10,0,0, HelperForm.ALIGN_RIGHT),
//                BorderLayout.NORTH);



        //---------------- FORMULAIRE ------------------


        Box boxFormulaire = Box.createVerticalBox();
        //formulaire.setBorder(BorderFactory.createLineBorder(Color.RED));

        add(boxFormulaire, BorderLayout.CENTER);


        //---------------- LISTE CIVILITE ------------------

        String[] listeCivilites = {"Monsieur","Madame","Mademoiselle","Autre"};
        selectCivilite = new JComboBox<>(listeCivilites);

        boxFormulaire.add(HelperForm.generateField(
                "Civilit??",selectCivilite));

        //---------------- CHAMPS TEXT : NOM ---------------

        champsNom = new ChampsSaisie("[\\p{L}\s'-]");
        boxFormulaire.add(
                HelperForm.generateField("Nom", champsNom)
        );

        //---------------- CHAMPS TEXT : PRENOM ---------------

        champsPrenom = new ChampsSaisie("[\\p{L}\s'-]");
        boxFormulaire.add(
                HelperForm.generateField("Pr??nom", champsPrenom)
        );

        //---------------- CHAMPS TEXT : EMAIL ---------------

        champsEmail = new ChampsSaisie("[a-zA-Z0-9@\\.-]");
        boxFormulaire.add(
                HelperForm.generateField("Email", champsEmail)
        );


        //---------------- LISTE PAYS ------------------
        Pays[] listePays = {
                new Pays("France", "FR", "fr.png"),
                new Pays("Royaume-unis", "GBR", "gb.png"),
                new Pays("Allemagne", "DE", "de.png")
        };

        selectPays = new JComboBox<>(listePays);

        selectPays.setRenderer(new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                Pays pays = (Pays)value;
                setText(pays.getNom());

                try {
                    //on charge l'image de drapeau correspond au pays
                    Image image = ImageIO.read(new File("src/main/resources/drapeaux/" + pays.getImage()));

                    //on redimensionne l'image
                    Image resizedImage = image.getScaledInstance(20, 16, Image.SCALE_SMOOTH);

                    setIconTextGap(10);

                    //on change l'icone du JLabel par l'image redimensionn??e
                    setIcon(new ImageIcon(resizedImage));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return this;
            }
        });

        boxFormulaire.add(
                HelperForm.generateField("Pays",selectPays)
        );


        //---------------- CHAMPS TEXT : AGE ---------------

        champsAge = new ChampsSaisie("[0-9]");
        boxFormulaire.add(
                HelperForm.generateField("Age", champsAge,50));

        //---------------- CHAMPS CHECKBOX : MARIE/PACSE ---------------

        champsMarie = new JCheckBox();
        boxFormulaire.add(
                HelperForm.generateField("Marie/pacse", champsMarie));


        //--------- BOUTON VALIDER FORMULAIRE -----------

        JButton boutonValider = new JButton("Enregistrer");

        boutonValider.addActionListener(e -> {

            boolean erreurNom = false;
            boolean erreurPrenom = false;
            boolean erreurAge = false;
            boolean erreurEmail = false;

            String message = "Le formulaire comporte des erreurs : ";

            champsNom.resetMessage();
            champsPrenom.resetMessage();

            //------ validateur nom -------
            if(champsNom.getText().equals("")) {
                erreurNom = true;
                message += "\n - Nom obligatoire,";
                champsNom.erreur("Champs obligatoire");
            }

            //------ validateur prenom -------
            if(champsPrenom.getText().equals("")) {
                erreurPrenom = true;
                message += "\n - Pr??nom obligatoire,";
                champsPrenom.erreur("Champs obligatoire");
            }

            //------ validateur age -------
            if(champsAge.getText().equals("")) {
                erreurAge = true;
                message += "\n - Age obligatoire,";
                champsAge.erreur("Champs obligatoire");
            } else {

                int age = Integer.parseInt(champsAge.getText());

                if(age <= 0 || age > 150) {
                    erreurAge = true;
                    message += "\n - Age avec une valeur impossible,";
                    champsAge.erreur("Valeur impossible");
                }
            }

            //------ validateur email -------
            if(champsEmail.getText().equals("")) {
                erreurEmail = true;
                message += "\n - Email obligatoire,";
                champsEmail.erreur("Champs obligatoire");
            } else {

                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(champsEmail.getText());

                if(!matcher.matches()){
                    erreurEmail = true;
                    message += "\n - Email format invalide,";
                    champsEmail.erreur("Format invalide");
                }

            }

            Utilisateur nouvelUtilisateur = new Utilisateur(
                    (String)selectCivilite.getSelectedItem(),
                    champsNom.getText(),
                    champsPrenom.getText(),
                    champsEmail.getText(),
                    (Pays) selectPays.getSelectedItem(),
                    Integer.parseInt(champsAge.getText()),
                    champsMarie.isSelected()
            );



            //on supprime la derni??re des virgules
            message = message.substring(0,message.length()-1);

            if(erreurNom || erreurPrenom || erreurAge || erreurEmail) {

                JOptionPane.showMessageDialog(
                        this,
                        message,
                        "Erreur de saisie",
                        JOptionPane.WARNING_MESSAGE);
            }


//            ObjectOutputStream oos = null;
//
//            {
//                try {
//                    fichier = new FileOutputStream("personne.old.eesc");
//                    oos = new ObjectOutputStream(fichier);
//                    oos.writeObject(listeUtilisateur);
//                    oos.flush();
//                    oos.close();
//
//                    model.addRow(nouvelUtilisateur.getLigneTableau());
//                    model.fireTableDataChanged();
//
//
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "L'utilisateur " + nouvelUtilisateur.getNom() + " a bien ??t?? ajout??");
//
//                } catch (IOException exception) {
//
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "Impossible d'enregistrer l'utilisateur");
//                }
//            }

            callback.executer(nouvelUtilisateur);
        });


        //---------- BOUTONS DU BAS -------

        boutonValider.setSize(new Dimension(100, 30));

        add(
                HelperForm.generateRow(boutonValider,0,10,10,0, HelperForm.ALIGN_RIGHT),
                BorderLayout.SOUTH);


    }

    public void ouvrirFichier() {

             //--------- GESTION ERREUR -----------
            ObjectInputStream ois = null;

            try {
                final FileInputStream fichier = new FileInputStream("personne.old.eesc");
                ois = new ObjectInputStream(fichier);
                Utilisateur utilisateurFichier = (Utilisateur) ois.readObject();

                //---------HYDRATATION DU FORMULAIRE-----------

                selectCivilite.setSelectedItem(utilisateurFichier.getCivilite());
                selectPays.setSelectedItem(utilisateurFichier.getPays());
                champsNom.getTextField().setText(utilisateurFichier.getNom());
                champsPrenom.getTextField().setText(utilisateurFichier.getPrenom());
                champsEmail.getTextField().setText(utilisateurFichier.getEmail());
                champsAge.getTextField().setText(String.valueOf(utilisateurFichier.getAge()));
                champsMarie.setSelected(utilisateurFichier.isMarie());

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


}
