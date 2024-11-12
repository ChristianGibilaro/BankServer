package com.atoudeft.banque.serveur;

import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.TypeCompte;

public class CompteEpargne extends CompteBancaire {
    private double tauxInteret;

    private final double LIMITE_MILLE = 1000;
    private final double PRELEVEMENT = 2;
    private final double NB_MOIS = 12;
    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param type   type du compte
     */
    public CompteEpargne(String numero, TypeCompte type, double tauxInteret) {
        super(numero, type);
        this.tauxInteret = tauxInteret;
    }

    @Override
    public boolean crediter(double montant) {
        double solde = getSolde();
        if (montant > 0) {
            solde+= montant;
            setSolde(solde);
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        double soldeInitial = getSolde();
        if(montant > 0) {
            if(montant < soldeInitial) {
                setSolde(soldeInitial-montant);
                if(soldeInitial < LIMITE_MILLE) {
                    setSolde(getSolde() - PRELEVEMENT);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }

    /**
     * Calcule les interets et les ajoute au solde
     */
    public void ajouterInterets() {
        double interets = getSolde() * tauxInteret / NB_MOIS;
        setSolde(getSolde() - interets);
    }
}
