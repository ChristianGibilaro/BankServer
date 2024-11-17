package com.atoudeft.banque;

public class OperationFacture extends Operation{
    double montant;
    String numFacture;
    String description;

    public OperationFacture(TypeOperation type, double montant, String numFacture, String description) {
        super(type);
        this.montant = montant;
        this.numFacture = numFacture;
        this.description = description;
    }

    public String toString() {
        return (getDate() + "\f" + "FACTURE\f" + this.montant + "\f" + numFacture + "\f" + this.description);
    }
}
