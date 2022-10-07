package edu.jdrouin.eesc;

public abstract class Personne {

    String nom;

    public abstract void fullName();
    public String getCivilite(){
        return "Monsieur";
    }

}
