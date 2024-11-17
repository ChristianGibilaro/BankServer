package com.atoudeft.banque;

import java.io.Serializable;

/**
 * Classe représentant un noeud d'une liste chainee
 * @param <T> Type d'objet contenu dans le noeud
 * @author aymanelaghrieb
 */
public class Noeud<T> implements Serializable {
    /*Contenu du noeud */
    private T valeur;
    /* Noeud suivant */
    private Noeud<T> suivant;

    /**
     * Constructeur d'un noeud
     * @param valeur Contenu du noeud
     */
    public Noeud(T valeur) {
        this.valeur = valeur;
        this.suivant = null;
    }

    /**
     * Retourne le contenu du noeud
     * @return Contenu du noeud
     */
    public T getValeur() {
        return valeur;
    }

    /**
     * Modifie le contenu du noeud
     * @param valeur Nouvelle valeur du noeud
     */
    public void setValeur(T valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne le noeud suivant
     * @return Noeud suivant
     */
    public Noeud<T> getSuivant() {
        return suivant;
    }

    /**
     * Modifie le noeud suivant
     * @param suivant Noeud suivant
     */
    public void setSuivant(Noeud<T> suivant) {
        this.suivant = suivant;
    }
}
