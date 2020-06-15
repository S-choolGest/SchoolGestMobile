/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import Entite.Bibliotheque.Livre;
import Entite.Bibliotheque.LivreEmprunte;
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
public class ServiceLivre {

	public boolean resultOK;
	public ArrayList<Livre> livres;
	public static ServiceLivre instance;
	public ConnectionRequest req;
	ServiceEmprunt ser = new ServiceEmprunt();

	public ServiceLivre() {
		req = new ConnectionRequest();
	}

	public static ServiceLivre getInstance() {
		if (instance == null) {
			instance = new ServiceLivre();
		}
		return instance;
	}

	public ArrayList<Livre> parseLivres(String jsonText) {
		try {
			livres = new ArrayList<>();
			JSONParser j = new JSONParser();
			Map<String, Object> LivresListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

			List<Map<String, Object>> list = (List<Map<String, Object>>) LivresListJson.get("livres");
			for (Map<String, Object> obj : list) {
				float id = Float.parseFloat(obj.get("id").toString());
				//float biblio = Float.parseFloat(obj.get("bibliotheque").toString());
				float taille = Float.parseFloat(obj.get("taille").toString());
				float quantite = Float.parseFloat(obj.get("quantite").toString());
				//String datesortie = obj.get("datesortie").toString();
				Map<String, Object> dateS = (Map<String, Object>) obj.get("datesortie");
				String datesortie = dateS.get("date").toString();
				Map<String, Object> dateA = (Map<String, Object>) obj.get("dateajout");
				String dateajout = dateA.get("date").toString();
				Livre l = new Livre((int) id, 0, obj.get("titre").toString(), obj.get("editeur").toString(), obj.get("auteur").toString(), obj.get("categorie").toString(), ser.parseDate(datesortie), (int) taille, (int) quantite, obj.get("img").toString(), ser.parseDate(dateajout));
				livres.add(l);
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}

		return livres;
	}

	public Livre parseLivre(Map<String, Object> obj) {
		float id = Float.parseFloat(obj.get("id").toString());
		//float biblio = Float.parseFloat(obj.get("bibliotheque").toString());
		float taille = Float.parseFloat(obj.get("taille").toString());
		float quantite = Float.parseFloat(obj.get("quantite").toString());
		//String datesortie = obj.get("datesortie").toString();
		Map<String, Object> dateS = (Map<String, Object>) obj.get("datesortie");
		String datesortie = dateS.get("date").toString();
		Map<String, Object> dateA = (Map<String, Object>) obj.get("dateajout");
		String dateajout = dateA.get("date").toString();
		Livre l = new Livre((int) id, 0, obj.get("titre").toString(), obj.get("editeur").toString(), obj.get("auteur").toString(), obj.get("categorie").toString(), ser.parseDate(datesortie), (int) taille, (int) quantite, obj.get("img").toString(), ser.parseDate(dateajout));
		return l;
	}

	public ArrayList<Livre> getAllLivres(int id) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/livres/" + id;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livres = parseLivres(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livres;
	}

	public ArrayList<Livre> getAllLivresByBiblio(int id) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/livres/" + id;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livres = parseLivres(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livres;
	}

	public boolean editLivre(Livre l) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/livre/edit/" + l.getId() + "/" + l.getTitre() + "/"
				+ l.getAuteur() + "/" + l.getEditeur() + "/" + l.getCategorie() + "/" + l.getTaille() + "/" + l.getQuantite();
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

	public boolean deleteLivre(int id) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/livre/delete/" + id;
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

	public boolean addLivre(Livre l) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/livre/add/" + l.getTitre() + "/" + l.getAuteur()
				+ "/" + l.getEditeur() + "/" + l.getCategorie() + "/" + l.getDateSortie() + "/" + l.getImg() + "/"
				+ l.getDateajout();
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

	public ArrayList<Livre> getAllLivres() {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/livres";
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livres = parseLivres(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livres;
	}

	public Livre getLivre(int idlivre) {
		List<Livre> list_livre = getAllLivres();
		for (Livre l : list_livre) {
			if (l.getId() == idlivre) {
				return l;
			}
		}
		//Livre l = list_livre.stream().filter(a -> a.getId() == idlivre).findAny().orElse(null);
		//return l;
		return new Livre();
	}

	public ArrayList<Livre> rechercher_livreFront(String text, int idbiblio) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/front/livres/rechercher/" + text + "/" + idbiblio;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livres = parseLivres(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livres;
	}

	public ArrayList<Livre> rechercher_livreBack(String text, int iduser) {
		String url = Statics.BASE_URL + "/bibliotheque/mobile/back/livres/rechercher/" + text + "/" + iduser;
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				livres = parseLivres(new String(req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return livres;
	}
}
