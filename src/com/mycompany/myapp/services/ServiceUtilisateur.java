/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

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
public class ServiceUtilisateur {

	public boolean resultOK;
	public Utilisateur user = new Utilisateur();

	public Utilisateur getUser(String jsonText) throws IOException {
		JSONParser j = new JSONParser();
		Map<String, Object> u = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
		Utilisateur user = new Utilisateur();
		float id = Float.parseFloat(u.get("id").toString());
		user.setId((int)id);
		user.setUsername(u.get("username").toString());
		user.setNom(u.get("nom").toString());
		user.setPrenom(u.get("prenom").toString());
		user.setEmail(u.get("email").toString());
		float cin = Float.parseFloat(u.get("cin").toString());
		user.setCin((int)cin);
		float numTel = Float.parseFloat(u.get("numTel").toString());
		user.setNumTel((int)numTel);
		user.setDateNaissance(u.get("dateNaissance").toString());
		user.setAdresse(u.get("adresse").toString());
		user.setRole(u.get("role").toString());
		user.setProfil(u.get("profil").toString());
		return user;
	}

	public Utilisateur connecter(Utilisateur u) {
		String url = Statics.BASE_URL + "/mobile/login/" + u.getUsername() + "/" + u.getPassword();
		ConnectionRequest req = new ConnectionRequest(url);
		req.addResponseListener(new ActionListener<NetworkEvent>() {
			@Override
			public void actionPerformed(NetworkEvent evt) {
				resultOK = req.getResponseCode() == 200;
				if (resultOK) {
					try {
						user = getUser(new String(req.getResponseData()));
					} catch (IOException ex) {
						System.out.println(ex);
					}
				}
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(req);
		return user;
	}
	
	public Utilisateur parseUser(Map<String, Object> u){
		Utilisateur user = new Utilisateur();
		float id = Float.parseFloat(u.get("id").toString());
		user.setId((int)id);
		user.setUsername(u.get("username").toString());
		user.setNom(u.get("nom").toString());
		user.setPrenom(u.get("prenom").toString());
		user.setEmail(u.get("email").toString());
		float cin = Float.parseFloat(u.get("cin").toString());
		user.setCin((int)cin);
		float numTel = Float.parseFloat(u.get("numTel").toString());
		user.setNumTel((int)numTel);
		user.setDateNaissance(u.get("datenaissance").toString());
		user.setAdresse(u.get("adresse").toString());
		user.setRole(u.get("roles").toString());
		user.setProfil(u.get("profile").toString());
		return user;
	}
}
