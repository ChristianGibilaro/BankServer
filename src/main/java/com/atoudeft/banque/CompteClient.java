package com.atoudeft.banque;

import com.sun.corba.se.spi.ior.ObjectKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompteClient implements Serializable {
    private String numero;
    private String nip;
    private List<CompteBancaire> comptes;

    /**
     * Crée un compte-client avec un numéro et un nip.
     *
     * @param numero le numéro du compte-client
     * @param nip le nip
     */
    public CompteClient(String numero, String nip) {
        this.numero = numero;
        this.nip = nip;
        comptes = new ArrayList<>();
    }

    public String getNumero() {//ajouter pour la classe Banque exercice 2.2
        return numero;
    }
    public String getNip() {
        return nip;
    }
    public List<CompteBancaire> getComptes() { // pour la liste des compte
        return comptes;
    }

    @Override
    public boolean equals  (Object obj)  {
        return  ((CompteClient)obj).numero.equals(numero);
    }

    /**
     * Ajoute un compte bancaire au compte-client.
     *
     * @param compte le compte bancaire
     * @return true si l'ajout est réussi
     */
    public boolean ajouter(CompteBancaire compte) {
        return this.comptes.add(compte);
    }
}