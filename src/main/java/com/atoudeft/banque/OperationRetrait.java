package com.atoudeft.banque;

public class OperationRetrait extends Operation{
    private double montant;
    public OperationRetrait(TypeOperation type, double montant) {
        super(type);
        this.montant = montant;
    }

    public String toString() {
        return (getDate() + "\f" + "RETRAIT\f" + this.montant);
    }
}
