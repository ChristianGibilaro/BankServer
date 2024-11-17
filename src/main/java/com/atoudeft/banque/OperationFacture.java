package com.atoudeft.banque;

public class OperationFacture extends Operation{
    int montant;
    String numFacture;
    String description;

    public OperationFacture(TypeOperation type, String numFacture, String description) {
        super(TypeOperation.FACTURE);
        this.numFacture = numFacture;
        this.description = description;
    }

    public String toString() {
        return (getDate() + "\f" + "FACTURE\f" + this.montant + "\f" + numFacture + "\f" + this.description);
    }
}
