package com.atoudeft.banque;

import java.io.Serializable;

/**
 * Classe representant une liste chainee simulant une pile
 * @param <T> Type d'objet dans les noeuds de la pile chainee
 */
public class PileChainee<T> implements Serializable {
    /* Sommet de la pile sous forme de noeud */
    private Noeud<T> sommet;

    /**
     * Constructeur de la pile chainee
     */
    public PileChainee() {
        this.sommet = null;
    }

    /**
     * Ajoute un element au sommet de la pile
     * @param valeur Objet a ajouter au sommet de la pile
     */
    public void empiler(T valeur) {
        Noeud<T> nouveauNoeud = new Noeud<>(valeur);
        nouveauNoeud.setSuivant(sommet);
        sommet = nouveauNoeud;
    }

    /**
     * Enleve l'objet au sommet de la pile et la retourne
     * @return L'objet au sommet de la pile
     * @throws Exception si la pile est vide
     */
    public T depiler() throws Exception {
        if (estVide()) {
            throw new Exception("La pile est vide.");
        }
        T valeur = sommet.getValeur();
        sommet = sommet.getSuivant();
        return valeur;
    }

    /**
     * Verifie si la pile est vide
     * @return Une booleene disant si la pile est vide ou non
     */
    public boolean estVide() {
        return sommet == null;
    }

    /**
     * Retourne l'objet au sommet de la pile sans la depiler
     * @return L'objet au sommet de la pile
     * @throws Exception si la pile est vide
     */
    public T peek() throws Exception {
        if (estVide()) {
            throw new Exception("La pile est vide.");
        }
        return sommet.getValeur();
    }

    /**
     * Retourne en representation textuelle tous les elements de la pile separes par un saut de ligne
     * @return Une chaine de caracteres contenant les elements de la pile
     */
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
