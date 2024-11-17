package com.atoudeft.banque;

import java.io.Serializable;

public class Noeud<T> implements Serializable {
    private T valeur;
    private Noeud<T> suivant;

    public Noeud(T valeur) {
        this.valeur = valeur;
        this.suivant = null;
    }

    public T getValeur() {
        return valeur;
    }

    public void setValeur(T valeur) {
        this.valeur = valeur;
    }

    public Noeud<T> getSuivant() {
        return suivant;
    }

    public void setSuivant(Noeud<T> suivant) {
        this.suivant = suivant;
    }
}
