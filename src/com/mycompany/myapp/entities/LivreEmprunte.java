/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entite.Bibliotheque;

/**
 *
 * @author william
 */
public class LivreEmprunte extends Livre{
	private int id_emprunt;
	private int id_emprunteur;
	private int etat;
	private String dateEmprunt;
	private String dateConfirmation;
	private String dateRendu;
	private String dateDebut;
	private String dateFin;

	public LivreEmprunte(int id_emprunt, int id_emprunteur, int etat, String dateEmprunt, String dateConfirmation, String dateRendu, int id, int id_bibliotheque, String titre, String editeur, String auteur, String categorie, String dateSortie, int taille, int quantite, String img, String dateajout, String datedebut, String datefin) {
		super(id, id_bibliotheque, titre, editeur, auteur, categorie, dateSortie, taille, quantite, img, dateajout);
		this.id_emprunt = id_emprunt;
		this.id_emprunteur = id_emprunteur;
		this.etat = etat;
		this.dateEmprunt = dateEmprunt;
		this.dateConfirmation = dateConfirmation;
		this.dateRendu = dateRendu;
		this.dateDebut = datedebut;
		this.dateFin = datefin;
	}
	
	public int getId_emprunt() {
		return id_emprunt;
	}

	public void setId_emprunt(int id_emprunt) {
		this.id_emprunt = id_emprunt;
	}

	public int getId_emprunteur() {
		return id_emprunteur;
	}

	public void setId_emprunteur(int id_emprunteur) {
		this.id_emprunteur = id_emprunteur;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public String getDateEmprunt() {
		return dateEmprunt;
	}

	public void setDateEmprunt(String dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	public String getDateConfirmation() {
		return dateConfirmation;
	}

	public void setDateConfirmation(String dateConfirmation) {
		this.dateConfirmation = dateConfirmation;
	}

	public String getDateRendu() {
		return dateRendu;
	}

	public void setDateRendu(String dateRendu) {
		this.dateRendu = dateRendu;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	@Override
	public String toString() {
		return "LivreEmprunte{" + "id_emprunt=" + id_emprunt + ", id_emprunteur=" + id_emprunteur + ", etat=" + etat + ", dateEmprunt=" + dateEmprunt + ", dateConfirmation=" + dateConfirmation + ", dateRendu=" + dateRendu + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin+ ", " + super.toString() + '}';
	}
	
}
