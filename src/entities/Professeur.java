/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author MehdiS
 */
public class Professeur {
    int id;
    String nom;
   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }



 

   

    public Professeur() {
    }

    public Professeur(int id,String nom) {
        this.id = id;
       this.nom = nom ;
    }

   

    @Override
    public String toString() {
        return "Professeur{" + "id=" + id + ", nom=" + nom + '}';
    }


    
}
