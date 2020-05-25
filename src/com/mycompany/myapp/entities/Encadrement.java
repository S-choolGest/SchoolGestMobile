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
public class Encadrement {
    private int id ;
    private int idProf ;
    private int idPfe ;
    private String etat ;
public Encadrement (int id ,int idProf ,int idPfe , String etat){
    this.id=id;
    this.idProf=idProf;
    this.idPfe=idPfe;
    this.etat=etat;
}
    public Encadrement (int idProf ,int idPfe , String etat){
    this.idProf=idProf;
    this.idPfe=idPfe;
    this.etat=etat;
 
}
        public Encadrement (int idProf ,int idPfe ){
    this.idProf=idProf;
    this.idPfe=idPfe;
}
public Encadrement(){
}

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public int getIdPfe() {
        return idPfe;
    }

    public void setIdPfe(int idPfe) {
        this.idPfe = idPfe;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
