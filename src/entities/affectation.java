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
public class affectation {
    int id;
    Classe classe;
  Professeur prof;
  Matiere matiere;
    
    public int getId() {
        return id;
    }

    public affectation() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Professeur getProf() {
        return prof;
    }

    public void setProf(Professeur prof) {
        this.prof = prof;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

   

    public affectation(int id, Classe classe, Professeur prof) {
        this.id = id;
        this.classe = classe;
        this.prof = prof;
    }

   public affectation( Classe classe, Professeur prof,Matiere Matiere) {
       this.matiere = Matiere;
        this.classe = classe;
        this.prof = prof;
    }


    @Override
    public String toString() {
        return "affectation{" + "id=" + id + ", classe=" + classe + ", prof=" + prof + '}';
    }
    
    
    
}
