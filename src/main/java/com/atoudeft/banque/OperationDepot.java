package com.atoudeft.banque;

public class OperationDepot extends Operation{
    private int montant;
    public OperationDepot(TypeOperation type, int montant) {
        super(TypeOperation.DEPOT);
        this.montant = montant;
    }

    public String toString() {
        return (getDate() + "\f" + "DEPOT\f" + this.montant);
    }
}
