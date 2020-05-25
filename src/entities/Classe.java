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
public class Classe {
    int id ; 
    String nomClasse;

    public Classe() {
    }

    public Classe(Double id,String nomClasse) {
        this.id = id.intValue();
        this.nomClasse = nomClasse;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Classe{" + "id=" + id + ", nomClasse=" + nomClasse + '}';
    }
    
    
    
    
    
}
