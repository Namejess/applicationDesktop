package edu.jdrouin.eesc;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Fenetre extends JFrame {

    protected boolean themeSombre = false;
    protected static int largeurEcran = 800;
    protected static int hauteurEcran = 600;

    public Fenetre() {


        setSize(largeurEcran, hauteurEcran);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panneau = new JPanel(new BorderLayout());
        setContentPane(panneau);
        setTitle("Application Desktop");


        JButton bouton = new JButton("Fermer l'application");

        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(largeurEcran, this.hauteurEcran));
        setBounds(0, 0, this.largeurEcran, this.hauteurEcran);
        setLocationRelativeTo(null);



//        ActionListener evt = new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("clic");
//            }
//        };

        bouton.addActionListener(e -> {
            Object[] choix = {"Oui", "Non"};
            int reponse = JOptionPane.showOptionDialog(this, "Voulez vous fermer l'application ?", "Confirmer", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
            if (reponse == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        //-----------COMBOBOX-------------
        String[] listeCivilite = {"M.", "Me.", "Mlle.", "Non précisé"};
        JComboBox<Object> selectCivilite = new JComboBox<>(listeCivilite);
        selectCivilite.addActionListener((ActionEvent e) -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            System.out.println(comboBox.getSelectedItem());
        });


        JButton boutonChangeTheme = new JButton("Change theme");
        boutonChangeTheme.addActionListener(e -> {
            try {
                if(themeSombre) {
                    themeSombre = false;
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                } else {
                    themeSombre = true;
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                }
                SwingUtilities.updateComponentTreeUI(this);
            } catch (UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            }
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



        JButton boutonFormulaire = new JButton("Envoyer");
        boutonFormulaire.addActionListener(e -> {
            System.out.println(selectCivilite.getSelectedItem() + ((Utilisateur) selectUtilisateur.getSelectedItem()).getNom());
        });

        //-------------AJOUT BOUTON-----------

//        panneau.add(boutonFormulaire);
//        panneau.add(selectUtilisateur);
//        panneau.add(selectCivilite);
//        panneau.add(boutonChangeTheme);
//        panneau.add(bouton);

        Box boxPrincipal = Box.createVerticalBox();
        panneau.add(boxPrincipal, BorderLayout.CENTER);

        Box boxMenu = Box.createHorizontalBox();
        boxMenu.add(bouton);
        boxMenu.add(boutonChangeTheme);
        boxPrincipal.add(boxMenu);

        boxPrincipal.add(Box.createRigidArea(new Dimension(1,50)));

        boxPrincipal.add(Champs.generate("Civilite", selectCivilite, selectUtilisateur));


        panneau.add(boutonFormulaire, BorderLayout.SOUTH);




        setVisible(true);

    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        FlatDarculaLaf.setup();

        new Fenetre();

    }
}