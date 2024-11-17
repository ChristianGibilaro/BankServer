package com.atoudeft.banque;

/**
 * Classe heritant de la classe Operation. Represente une operation de type depot d'argent dans le compte
 * @author aymanelaghrieb
 */
public class OperationDepot extends Operation{
    /* Montant du depot */
    private double montant;

    /**
     * Constructeur d'objet OperationDepot
     * @param type Type d'opération
     * @param montant Montant déposé
     */
    public OperationDepot(TypeOperation type, double montant) {
        super(type);
        this.montant = montant;
    }

    @Override
    public String toString() {
        return (getDate() + "\f" + "DEPOT\f" + this.montant);
    }
}
