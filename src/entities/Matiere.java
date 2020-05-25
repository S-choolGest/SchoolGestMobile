/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author saghir
 */
public class Matiere {
    private int idMatiere;
    private String nomMatiere;
    

    public Matiere() {
        
    }

    public Matiere(Double idMatiere,String nomMatiere) {
        this.idMatiere = idMatiere.intValue();
        this.nomMatiere = nomMatiere;
       
       
    }

    
    

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public int getId() {
        return idMatiere;
    }

    public void setId(int idMatiere) {
        this.idMatiere = idMatiere;
    }

   

    @Override
    public int hashCode() {
        int hash=0;
        hash = this.idMatiere;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Matiere other = (Matiere) obj;
        if (this.idMatiere != other.idMatiere) {
            return false;
        }
        if (!Objects.equals(this.nomMatiere, other.nomMatiere)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Matieres{" + "\nidMatiere=" + idMatiere + ", \nnomMatiere=" + nomMatiere +'}';
    }
    
    
}
