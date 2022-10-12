package edu.jdrouin.eesc.exempleFormulaire;

public abstract class Personne {

    String nom;

    public abstract void fullName();
    public String getCivilite(){
        return "Monsieur";
    }

}
