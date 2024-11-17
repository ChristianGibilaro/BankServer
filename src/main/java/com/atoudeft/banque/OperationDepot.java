package com.atoudeft.banque;

public class OperationDepot extends Operation{
    private double montant;
    public OperationDepot(TypeOperation type, double montant) {
        super(type);
        this.montant = montant;
    }

    public String toString() {
        return (getDate() + "\f" + "DEPOT\f" + this.montant);
    }
}
