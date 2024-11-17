package com.atoudeft.banque;

import java.io.Serializable;

public class PileChainee<T> implements Serializable {
    private Noeud<T> sommet;

    public PileChainee() {
        this.sommet = null;
    }

    // Ajouter un élément à la pile
    public void empiler(T valeur) {
        Noeud<T> nouveauNoeud = new Noeud<>(valeur);
        nouveauNoeud.setSuivant(sommet);
        sommet = nouveauNoeud;
    }

    // Retirer un élément de la pile
    public T depiler() throws Exception {
        if (estVide()) {
            throw new Exception("La pile est vide.");
        }
        T valeur = sommet.getValeur();
        sommet = sommet.getSuivant();
        return valeur;
    }

    // Vérifier si la pile est vide
    public boolean estVide() {
        return sommet == null;
    }

    // Obtenir la valeur au sommet sans la retirer
    public T peek() throws Exception {
        if (estVide()) {
            throw new Exception("La pile est vide.");
        }
        return sommet.getValeur();
    }
    public String afficherElementsAsString() {
        if (estVide()) {
            return "L'historique est vide";
        }

        StringBuilder sb = new StringBuilder();
        Noeud<T> courant = sommet; // Commence par le sommet de la pile
        while (courant != null) {
            sb.append(courant.getValeur().toString()).append("\n"); // Ajoute chaque élément avec un saut de ligne
            courant = courant.getSuivant(); // Passe au nœud suivant
        }
        return sb.toString(); // Retourne la chaîne construite
    }


}
