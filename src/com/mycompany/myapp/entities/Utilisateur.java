/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entite.Utilisateur;

import javafx.scene.image.ImageView;

/**
 *
 * @author william
 */
public class Utilisateur {
	private int id;
	private String username;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int cin;
    private int numTel;
    private String dateNaissance;
    private String adresse;
    private String role;
	private String profil;

	public Utilisateur(String username, String password) {
		this.username = username;
		this.password = password;
	}	

	public Utilisateur() {
		this.id = 0;
		this.username = "";
		this.nom = "";
		this.prenom = "";
		this.email = "";
		this.password = "";
		this.cin = 0;
		this.numTel = 0;
		this.dateNaissance = "";
		this.adresse = "";
		this.role = "";
		this.profil = "";
	}

	public Utilisateur(int id, String username, String nom, String prenom, String email, String password, int cin, int numTel, String dateNaissance, String adresse, String role, String profil) {
		this.id = id;
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.cin = cin;
		this.numTel = numTel;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.role = role;
		this.profil = profil;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCin() {
		return cin;
	}

	public void setCin(int cin) {
		this.cin = cin;
	}

	public int getNumTel() {
		return numTel;
	}

	public void setNumTel(int numTel) {
		this.numTel = numTel;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	@Override
	public String toString() {
		return "Utilisateur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password=" + password + ", cin=" + cin + ", numTel=" + numTel + ", dateNaissance=" + dateNaissance + ", adresse=" + adresse + ", type=" + role + ", profil=" + profil + '}';
	}
	
}
