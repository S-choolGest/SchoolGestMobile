/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import Entite.Bibliotheque.Emprunt;
import Entite.Bibliotheque.Emprunteur;
import Entite.Bibliotheque.Livre;
import Entite.Bibliotheque.LivreEmprunte;
import Entite.Utilisateur.Utilisateur;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author william
 */
public class ServiceEmprunt {

	public boolean resultOK;
	public ArrayList<Emprunt> emprunts;
	public ArrayList<LivreEmprunte> livreempruntes;
	public ArrayList<Emprunteur> emprunteurs;
	public static ServiceEmprunt instance;
	public ConnectionRequest req;

	public ServiceEmprunt() {
		req = new ConnectionRequest();
	}

	public static ServiceEmprunt getInstance() {
		if (instance == null) {
			instance = new ServiceEmprunt();
		}
		return instance;
	}

	public boolean emprunter(int idemprunteur, int idlivre, String datedebut, String datefin) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/emprunter/" + idemprunteur + "/" + idlivre + "/" + datedebut + "/" + datefin;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200;
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public String parseDate(String jsonText) {
		String result = "";
		if (jsonText.length() >= 9) {
			for (int i = 0; i < 10; i++) {
				result += jsonText.charAt(i);
			}
			return result;
		}
		return "RAS";
	}

	public ArrayList<LivreEmprunte> parseEmprunts(String jsonText) {
		try {
			livreempruntes = new ArrayList<>();
			JSONParser j = new JSONParser();
			Map<String, Object> LivresListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

			List<Map<String, Object>> list = (List<Map<String, Object>>) LivresListJson.get("emprunts");
			for (Map<String, Object> obj : list) {
				String dateconfirmation = "", daterendu = "", datedebut = "", datefin = "";
				float id = Float.parseFloat(obj.get("id").toString());
				float etat = Float.parseFloat(obj.get("etat").toString());
				Map<String, Object> dateE = (Map<String, Object>) obj.get("dateemprunt");
				String dateemprunt = dateE.get("date").toString();
				Map<String, Object> dateC = (Map<String, Object>) obj.get("dateconfirmation");
				if (dateC != null) {
					dateconfirmation = dateC.get("date").toString();
				}
				Map<String, Object> dateR = (Map<String, Object>) obj.get("daterendu");
				if (dateR != null) {
					daterendu = dateR.get("date").toString();
				}
				Map<String, Object> dateD = (Map<String, Object>) obj.get("datedebut");
				if (dateD != null) {
					datedebut = dateD.get("date").toString();
				}
				Map<String, Object> dateF = (Map<String, Object>) obj.get("datefin");
				if (dateF != null) {
					datefin = dateF.get("date").toString();
				}
				Map<String, Object> user = (Map<String, Object>) obj.get("idemprunteur");
				float iduser = Float.parseFloat(user.get("id").toString());
				Map<String, Object> idlivre = (Map<String, Object>) obj.get("idlivre");
				Livre l = new ServiceLivre().parseLivre(idlivre);
				LivreEmprunte livre = new LivreEmprunte((int) id, (int) iduser, (int) etat, parseDate(dateemprunt), parseDate(dateconfirmation), parseDate(daterendu), l.getId(), l.getId_bibliotheque(), l.getTitre(), l.getEditeur(), l.getAuteur(), l.getCategorie(), l.getDateSortie(), l.getTaille(), l.getQuantite(), l.getImg(), l.getDateajout(), parseDate(datedebut), parseDate(datefin));
				livreempruntes.add(livre);
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}

		return livreempruntes;
	}

	public ArrayList<LivreEmprunte> getAllEmprunts(int iduser, int idbiblio) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/emprunts/" + iduser + "/" + idbiblio;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livreempruntes = parseEmprunts(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livreempruntes;
	}

	public boolean annuler(int id) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/emprunt/annuler/" + id;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200;
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public ArrayList<Emprunteur> parseEmprunteurs(String jsonText) {
		try {
			emprunteurs = new ArrayList<>();
			JSONParser j = new JSONParser();
			Map<String, Object> LivresListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

			List<Map<String, Object>> list = (List<Map<String, Object>>) LivresListJson.get("emprunts");
			for (Map<String, Object> obj : list) {
				String dateconfirmation = "", daterendu = "", datedebut = "", datefin = "";
				float id = Float.parseFloat(obj.get("id").toString());
				float etat = Float.parseFloat(obj.get("etat").toString());
				Map<String, Object> dateE = (Map<String, Object>) obj.get("dateemprunt");
				String dateemprunt = dateE.get("date").toString();
				Map<String, Object> dateC = (Map<String, Object>) obj.get("dateconfirmation");
				if (dateC != null) {
					dateconfirmation = dateC.get("date").toString();
				}
				Map<String, Object> dateR = (Map<String, Object>) obj.get("daterendu");
				if (dateR != null) {
					daterendu = dateR.get("date").toString();
				}
				Map<String, Object> dateD = (Map<String, Object>) obj.get("datedebut");
				if (dateD != null) {
					datedebut = dateD.get("date").toString();
				}
				Map<String, Object> dateF = (Map<String, Object>) obj.get("datefin");
				if (dateF != null) {
					datefin = dateF.get("date").toString();
				}
				Map<String, Object> user = (Map<String, Object>) obj.get("idemprunteur");
				Utilisateur u = new ServiceUtilisateur().parseUser(user);

				Map<String, Object> idlivre = (Map<String, Object>) obj.get("idlivre");
				Livre l = new ServiceLivre().parseLivre(idlivre);

				Emprunteur e = new Emprunteur(u.getNom(), u.getPrenom(), u.getNumTel(), u.getEmail(),
						 u.getProfil(), l.getTitre(), l.getImg(), (int) id, u.getId(), l.getId(), (int) etat, parseDate(dateemprunt), parseDate(dateconfirmation),
						parseDate(daterendu), parseDate(datedebut), parseDate(datefin));
				emprunteurs.add(e);
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}

		return emprunteurs;
	}

	public ArrayList<Emprunteur> getAllEmprunteurs(int idbibliothecaire) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/emprunts/" + idbibliothecaire;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				emprunteurs = parseEmprunteurs(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return emprunteurs;
	}

	public boolean valider_emprunt(int id, int choix) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/emprunt/editer/" + id + "/" + choix;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200;
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return resultOK;
	}

	public ArrayList<LivreEmprunte> rechercher_livre_emprunte(String text, int iduser, int idbiblio) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/emprunts/rechercher/" + text + "/" + iduser + "/" + idbiblio;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livreempruntes = parseEmprunts(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livreempruntes;
	}

	public ArrayList<Emprunteur> rechercher_emprunteur(String text, int iduser) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/emprunts/rechercher/" + text + "/" + iduser;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				emprunteurs = parseEmprunteurs(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return emprunteurs;
	}
}
