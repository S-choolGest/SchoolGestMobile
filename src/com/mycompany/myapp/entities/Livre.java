/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entite.Bibliotheque;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author william
 */
public class Livre {
    private int id;
    private int id_bibliotheque;
    private String titre;
    private String editeur;
    private String auteur;
    private String categorie;
    private String dateSortie;
    private int taille;
    private int quantite;
	private String img;
	private String dateajout;

    public Livre(int id, int id_bibliotheque, String titre, String editeur, String auteur, String categorie, String dateSortie, int taille, int quantite, String img, String dateajout) {
        this.id = id;
        this.id_bibliotheque = id_bibliotheque;
        this.titre = titre;
        this.editeur = editeur;
        this.auteur = auteur;
        this.categorie = categorie;
        this.dateSortie = dateSortie;
        this.taille = taille;
        this.quantite = quantite;
		this.img = img;
		this.dateajout = dateajout;
    }

    public Livre(int id_bibliotheque, String titre, String editeur, String auteur, String categorie, String dateSortie, int taille, int quantite, String img, String dateajout) {
        this.id_bibliotheque = id_bibliotheque;
        this.titre = titre;
        this.editeur = editeur;
        this.auteur = auteur;
        this.categorie = categorie;
        this.dateSortie = dateSortie;
        this.taille = taille;
        this.quantite = quantite;
		this.img = img;
		this.dateajout = dateajout;
    }

	public Livre(int id, String titre, String editeur, String auteur, String categorie, String dateSortie, int taille, int quantite, String img) {
		this.id = id;
		this.titre = titre;
		this.editeur = editeur;
		this.auteur = auteur;
		this.categorie = categorie;
		this.dateSortie = dateSortie;
		this.taille = taille;
		this.quantite = quantite;
		this.img = img;
	}

	public Livre() {
		this.id = 0;
        this.id_bibliotheque = 0;
        this.titre = "";
        this.editeur = "";
        this.auteur = "";
        this.categorie = "";
        this.dateSortie = "";
        this.taille = 0;
        this.quantite = 0;
		this.img = "";
		this.dateajout = "";
	}
	
    public Livre(int id) {
        this.id = id;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_bibliotheque() {
        return id_bibliotheque;
    }

    public void setId_bibliotheque(int id_bibliotheque) {
        this.id_bibliotheque = id_bibliotheque;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(String dateSortie) {
        this.dateSortie = dateSortie;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDateajout() {
		return dateajout;
	}

	public void setDateajout(String dateajout) {
		this.dateajout = dateajout;
	}

	@Override
	public String toString() {
		return "Livre{" + "id=" + id + ", id_bibliotheque=" + id_bibliotheque + ", titre=" + titre + ", editeur=" + editeur + ", auteur=" + auteur + ", categorie=" + categorie + ", dateSortie=" + dateSortie + ", taille=" + taille + ", quantite=" + quantite + ", img=" + img + ", dateajout=" + dateajout + '}';
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Livre other = (Livre) obj;
        if (this.id_bibliotheque != other.id_bibliotheque) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.editeur, other.editeur)) {
            return false;
        }
        if (!Objects.equals(this.auteur, other.auteur)) {
            return false;
        }
        if (!Objects.equals(this.dateSortie, other.dateSortie)) {
            return false;
        }
        if (this.taille != other.taille) {
            return false;
        }
        return true;
    }

    
}
