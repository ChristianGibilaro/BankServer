package com.atoudeft.banque;

import java.io.Serializable;

public abstract class CompteBancaire implements Serializable {
    /* Numero de compte */
    private String numero;
    /* Type de compte bancaire (cheque ou epargnes) */
    private TypeCompte type;
    /* Montant d'argent dans le compte bancaire */
    private double solde;
    /* Historique des operations anterieures du compte*/
    private PileChainee<Operation> historique;

    /**
     * Génère un numéro de compte bancaire aléatoirement avec le format CCC00C, où C est un caractère alphabétique
     * majuscule et 0 est un chiffre entre 0 et 9.
     * @return
     */
    public static String genereNouveauNumero() {
        char[] t = new char[6];
        for (int i=0;i<3;i++) {
            t[i] = (char)((int)(Math.random()*26)+'A');
        }
        for (int i=3;i<5;i++) {
            t[i] = (char)((int)(Math.random()*10)+'0');
        }
        t[5] = (char)((int)(Math.random()*26)+'A');
        return new String(t);
    }

    /**
     * Crée un compte bancaire.
     * @param numero numéro du compte
     * @param type type du compte
     */
    public CompteBancaire(String numero, TypeCompte type) {
        this.numero = numero;
        this.type = type;
        this.solde = 0;
        this.historique = new PileChainee<Operation>();
    }
    public String getNumero() {
        return numero;
    }
    public TypeCompte getType() {
        return type;
    }
    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde; //méthode pour modifier le solde. nécessaire pour la classe CompteCheque
    }

    public void ajouterOperation(Operation operation) {
        historique.empiler(operation);
    }

    /**
     * Retourne un String contenant l'historique des opérations du compte
     * @return L'historique des opérations du compte
     */
    public String getHistoriqueAsString() {
        if (historique.estVide()) {
            return "L'historique est vide";
        }
        return historique.afficherElementsAsString(); // Appelle la méthode de la pile pour générer la chaîne
    }



    public abstract boolean crediter(double montant);
    public abstract boolean debiter(double montant);
    public abstract boolean payerFacture(String numeroFacture, double montant, String description);
    public abstract boolean transferer(double montant, String numeroCompteDestinataire);

    /**
     * Retourne l'historique des opérations du compte
     * @return Une pile chainee contenant des operations
     */
    public PileChainee<Operation> getHistorique() {
        return this.historique;
    }
}