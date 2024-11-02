package com.atoudeft.banque;

public class CompteCheque extends CompteBancaire{
    /**
     * Crée un compte bancaire de type CompteCheque.
     *
     * @param numero numéro du compte
     * @param type   type du compte (par exemple, compte courant)
     */
    public CompteCheque(String numero, TypeCompte type) {
        super(numero, type);
    }
    @Override
    public boolean crediter(double montant){
        if(montant > 0){ //montant positif?
            double nouveauSolde = getSolde() + montant;
            setSolde(nouveauSolde);  //je ne saia pas s'il faut créer cette méthode dans CompteBancaire pcq c'est pas demandé
            return true; //crédit réussi
        }
        return false; //montant invalide
    }

    @Override
    public boolean debiter(double montant){
        if(montant > 0 && getSolde() >= montant){
            double nouveauSolde = getSolde() - montant;
            setSolde(nouveauSolde);
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description){
       return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire){
        return false;
    }
}
