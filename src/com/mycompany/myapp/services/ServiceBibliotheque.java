/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import Entite.Bibliotheque.Bibliotheque;
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
public class ServiceBibliotheque {
	public boolean resultOK;
	public ArrayList<Bibliotheque> biblios;
	public static ServiceBibliotheque instance;
	public ConnectionRequest req;
	
	public ServiceBibliotheque(){
		req = new ConnectionRequest();
	}
	
	public static ServiceBibliotheque getInstance(){
		if (instance == null) {
			instance = new ServiceBibliotheque();
		}
		return instance;
	}
	
	public ArrayList<Bibliotheque> parseBiblios(String jsonText) {
		try {
			biblios = new ArrayList<>();
			JSONParser j = new JSONParser();
			Map<String, Object> BibliosListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

			List<Map<String, Object>> list = (List<Map<String, Object>>) BibliosListJson.get("biblios");
			for(Map<String, Object> obj : list) {
				float id = Float.parseFloat(obj.get("id").toString());
				//float biblio = Float.parseFloat(obj.get("bibliotheque").toString());
				float capacite = Float.parseFloat(obj.get("capacite").toString());
				//float id_bibliothecaire = Float.parseFloat(obj.get("idBibliothecaire").toString());
				Bibliotheque b = new Bibliotheque((int)id, obj.get("nom").toString(), (int)capacite, obj.get("adresse").toString(), 0/*(int)id_bibliothecaire*/);
				biblios.add(b);
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}

		return biblios;
	}
	
	public ArrayList<Bibliotheque> getAllBiblios(){
		String url = Statics.BASE_URL+"/bibliotheque/mobile/front/biblios";
		req.setUrl(url);
		req.setPost(false);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				biblios = parseBiblios(new String (req.getResponseData()));
				req.removeResponseListener(this);
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return biblios;
	}
}
