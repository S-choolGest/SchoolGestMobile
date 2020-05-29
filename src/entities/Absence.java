/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;



import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.Date;


/**
 *
 * @author MehdiS
 */
public class Absence {
    private int id;
    private Matiere matiere;
    private Date date;
    private Date heure;
    private Classe Classe;
    private Etudiant IdEtudiant;
    private String etat;

    public Absence() {
    }
    

    public Absence( Matiere matiere,Etudiant IdEtudiant,String heure,Classe classe)  {
        this.matiere = matiere;
         
          
         
        try {
            this.heure = new SimpleDateFormat("hh:mm:ss").parse(heure);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        this.etat = "non Justifié";
        this.IdEtudiant = IdEtudiant;
        this.Classe = classe;
    }
    public Absence( Matiere matiere,Etudiant IdEtudiant,String heure,Classe classe,String etat)  {
        this.matiere = matiere;
         
          
         
        try {
            this.heure = new SimpleDateFormat("hh:mm:ss").parse(heure);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        this.etat = etat;
        this.IdEtudiant = IdEtudiant;
        this.Classe = classe;
    }

    public Etudiant getIdEtudiant() {
        return IdEtudiant;
    }
    
    
    
    public String getIdS() {
        return Integer.toString(id);
    }
    
    public int getId() {
        return id;
    }

    public Matiere getMatiere() {
        return matiere;
    }

   
    
    public String getetat(){
       return etat;
    }

    public void setId(int id) {
        this.id = id;
    }
   
    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

  

    public void setIdEtudiant(Etudiant IdEtudiant) {
        this.IdEtudiant = IdEtudiant;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDate() {
        return date;
    }
    
     public String getDateString(Date date) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String dateText = df2.format(date);
        return dateText;
    }

    public void setDate(long date) {
      
       this.date = new Date(date * 1000);
       
    }

    public Date getHeure() {
        return heure;
    }
    
    public String getHeureString(Date heure) {
       System.out.println("HeureEntitéAbs= "+heure);
       
       /*Date date = new Date( heure.getTime() *1000);
       SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");*/
        String dateText = heure.toString().substring(11,19);//df2.format(date);
        return dateText;
    }

    public void setHeure(String heure) {
        try {
            this.heure = new SimpleDateFormat("hh:mm:ss").parse(heure);
             
        } catch (ParseException ex) {
            System.out.println(ex);
        }
    }

   

    @Override
    public String toString() {

       String result = "Absence{" + "id=" + id + ", matiere=" + matiere.toString() + ", date=" + date+ ", heure=" + heure  ;
       
       return result +",Etat="+ etat+  " , Etudiant="+IdEtudiant.toString()+"  , Classe="+Classe.toString() +"}";
    }
    
    public String afficherAbsEtu(){
        String Etat ;
       String result = "Absence{" + "matiere=" + matiere + ", date=" + date+ ", heure=" + heure ;
      
       
       return result +",Etat="+ etat +"} ";
    }

    public Classe getClasse() {
        return Classe;
    }

    public void setClasse(Classe Classe) {
        this.Classe = Classe;
    }

    
    
    
}

 