package com.atoudeft.banque;

/**
 * Classe heritant de la classe Operation. Represente une operation de type paiement de facture
 * @author aymanelaghrieb
 */
public class OperationFacture extends Operation{
    /* Montant paye dans la facture */
    double montant;
    /* Numero de la facture */
    String numFacture;
    /* Description de la facture */
    String description;

    /**
     * Constructeur d'OperationFacture
     * @param type Type de l'operation
     * @param montant Montant paye dans la facture
     * @param numFacture Numero de la facture
     * @param description Description de la facture
     */
    public OperationFacture(TypeOperation type, double montant, String numFacture, String description) {
        super(type);
        this.montant = montant;
        this.numFacture = numFacture;
        this.description = description;
    }

    @Override
    public String toString() {
        return (getDate() + "\f" + "FACTURE\f" + this.montant + "\f" + numFacture + "\f" + this.description);
    }
}
