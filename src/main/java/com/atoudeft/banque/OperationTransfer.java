package com.atoudeft.banque;

public class OperationTransfer extends Operation{
    int montant;
    String destinataire;
    public OperationTransfer(TypeOperation type, int montant, String destinataire) {
        super(type);
        this.montant = montant;
        this.destinataire = destinataire;
    }

    public String toString() {
        return (getDate() + "\f" + "DEPOT\f" + this.montant + "\f" + this.destinataire);
    }
}
