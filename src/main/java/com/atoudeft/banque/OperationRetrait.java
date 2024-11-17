package com.atoudeft.banque;

public class OperationRetrait extends Operation{
    private int montant;
    public OperationRetrait(TypeOperation type, int montant) {
        super(TypeOperation.RETRAIT);
        this.montant = montant;
    }

    public String toString() {
        return (getDate() + "\f" + "RETRAIT\f" + this.montant);
    }
}
