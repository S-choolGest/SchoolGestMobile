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
public class Emprunteur extends Emprunt{
	private String nom;
	private String prenom;
	private int tel;
	private String email;
	private String img;
	private String titre;
	private String img_livre;

	public Emprunteur() {
		super(0);
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.email = email;
		this.img = img;
	}
	
	public Emprunteur(String nom, String prenom, int tel, String email, String img, String titre, String img_livre, int id, int idEmprunteur, int idLivre, int etat, String dateEmprunt, String dateConfirmation, String dateRendu, String dateDebut, String dateFin) {
		super(id, idEmprunteur, idLivre, etat, dateEmprunt, dateConfirmation, dateRendu, dateDebut, dateFin);
		this.nom = nom;
		this.prenom = prenom;
		this.tel = tel;
		this.email = email;
		this.img = img;
		this.titre = titre;
		this.img_livre = img_livre;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getImg_livre() {
		return img_livre;
	}

	public void setImg_livre(String img_livre) {
		this.img_livre = img_livre;
	}

	@Override
	public String toString() {
		return "Emprunteur{"+super.toString() + "nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + ", email=" + email + ", img=" + img + '}';
	}
	
}
