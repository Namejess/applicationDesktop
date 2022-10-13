package edu.jdrouin.eesc.exempleFormulaire;

import edu.jdrouin.eesc.exempleFormulaire.model.Pays;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    protected String nom;
    protected String prenom;
    protected String email;
    protected String civilite;
    protected Pays pays;
    protected int age;
    protected boolean marie;

    public Utilisateur(String nom, String prenom, String email, Pays pays, String civilite, int age, boolean marie) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.civilite = civilite;
        this.pays = pays;
        this.age = age;
        this.marie = marie;
    }

    public Utilisateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public Utilisateur(String selectedItem, String text, String text1, String text2, Pays selectedItem1, int parseInt, boolean selected) {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarie() {
        return marie;
    }

    public void setMarie(boolean marie) {
        this.marie = marie;
    }
}
