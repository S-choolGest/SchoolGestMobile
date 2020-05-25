/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Encadrement;
import entities.Utilisateur;
import static com.mycompany.myapp.services.ServiceEncadrement.instance;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TOSHIBA
 */
public class ServiceUtilisateur {
    public ArrayList<Utilisateur> utilisateur;
    public static ServiceUtilisateur instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    private ServiceUtilisateur() {
         req = new ConnectionRequest();
    }
    public static ServiceUtilisateur getInstance() {
        if (instance == null) {
            instance = new ServiceUtilisateur();
        }
        return instance;
    }
     public ArrayList<Utilisateur> parseUtilisateurs(String jsonText){
        try {
            utilisateur=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> utilisateursListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)utilisateursListJson.get("root");
            for(Map<String,Object> obj : list){
                Utilisateur p = new Utilisateur();
                float id = Float.parseFloat(obj.get("id").toString());
                p.setId((int)id);
                p.setUsername(obj.get("username").toString());
             //   p.setRole(obj.get("role").toString());

                utilisateur.add(p);
            }
        } catch (IOException ex) {
        }
        return utilisateur;
    }
    public ArrayList<Utilisateur> getUser(String username){
        /*http://localhost/pitest/web/app_dev.php/mobile/user/etud*/
        String url = Statics.BASE_URL +"user/"+username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                utilisateur = parseUtilisateurs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return utilisateur;
    }
}
