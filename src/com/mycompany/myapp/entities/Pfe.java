/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.entities;

/**
 *
 * @author TOSHIBA
 */
public class Pfe {
    private int id;
    private int idEtduiant;
    private String titre;
    private String sujet;
    
    public Pfe(String titre,String sujet,int idEtudiant)
    {
        this.titre=titre;
        this.sujet=sujet;
        this.idEtduiant=idEtudiant;

    }   
    public Pfe(int id,int idEtudiant,String titre,String sujet)
    {
        this.titre=titre;
        this.sujet=sujet;
    } 
    public Pfe()
    {
    } 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEtduiant() {
        return idEtduiant;
    }

    public void setIdEtduiant(int idEtduiant) {
        this.idEtduiant = idEtduiant;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    @Override
    public String toString() {
        return titre;
    }
    
    
}
