/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entite.Bibliotheque;

import java.util.Objects;

/**
 *
 * @author william
 */
public class Emprunt {
    private int id;
    private int idEmprunteur;
    private int idLivre;
    private int etat;
    private String dateEmprunt;
    private String dateConfirmation;
    private String dateRendu;
	private String dateDebut;
	private String dateFin;

    public Emprunt(int idEmprunteur, int idLivre, int etat, String dateEmprunt, String dateConfirmation, String dateRendu, String dateDebut, String dateFin) {
        this.idEmprunteur = idEmprunteur;
        this.idLivre = idLivre;
        this.etat = etat;
        this.dateEmprunt = dateEmprunt;
        this.dateConfirmation = dateConfirmation;
        this.dateRendu = dateRendu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Emprunt(int id, int idEmprunteur, int idLivre, int etat, String dateEmprunt, String dateConfirmation, String dateRendu, String dateDebut, String dateFin) {
        this.id = id;
        this.idEmprunteur = idEmprunteur;
        this.idLivre = idLivre;
        this.etat = etat;
        this.dateEmprunt = dateEmprunt;
        this.dateConfirmation = dateConfirmation;
        this.dateRendu = dateRendu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Emprunt(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEmprunteur() {
        return idEmprunteur;
    }

    public void setIdEmprunteur(int idEmprunteur) {
        this.idEmprunteur = idEmprunteur;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
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
		return "Emprunt{" + "id=" + id + ", idEmprunteur=" + idEmprunteur + ", idLivre=" + idLivre + ", etat=" + etat + ", dateEmprunt=" + dateEmprunt + ", dateConfirmation=" + dateConfirmation + ", dateRendu=" + dateRendu + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
	}

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Emprunt other = (Emprunt) obj;
        if (this.idEmprunteur != other.idEmprunteur) {
            return false;
        }
        if (this.idLivre != other.idLivre) {
            return false;
        }
        if (this.etat != other.etat) {
            return false;
        }
        if (!Objects.equals(this.dateEmprunt, other.dateEmprunt)) {
            return false;
        }
        if (!Objects.equals(this.dateConfirmation, other.dateConfirmation)) {
            return false;
        }
        if (!Objects.equals(this.dateRendu, other.dateRendu)) {
            return false;
        }
        return true;
    }
    
}
