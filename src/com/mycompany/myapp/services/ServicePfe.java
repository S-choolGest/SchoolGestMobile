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
import com.mycompany.myapp.entities.Pfe;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author TOSHIBA
 */
public class ServicePfe {
    public ArrayList<Pfe> pfes;
    public static ServicePfe instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    private ServicePfe() {
         req = new ConnectionRequest();
    }
    public static ServicePfe getInstance() {
        if (instance == null) {
            instance = new ServicePfe();
        }
        return instance;
    }
    public boolean addPfe(Pfe p) {
        String url = Statics.BASE_URL +"pfe/addpfe/new?titre="+p.getTitre()+"&sujet="+p.getSujet()+"&idEtudiant="+p.getIdEtduiant();
        /*String url = Statics.BASE_URL + "pfe/addpfe/new?titre="+p.getTitre()+"&sujet="+ p.getSujet()+"idEtudiant="+p.getIdEtduiant();*/
        req.setUrl(url);
       /* req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });*/
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public ArrayList<Pfe> parsePfes(String jsonText){
        try {
            pfes=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> pfesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)pfesListJson.get("root");
            for(Map<String,Object> obj : list){
                Pfe p = new Pfe();
                float id = Float.parseFloat(obj.get("id").toString());
                p.setId((int)id);
                p.setIdEtduiant(((int)Float.parseFloat(obj.get("idEtudiant").toString())));
                p.setTitre(obj.get("titre").toString());
                p.setSujet(obj.get("sujet").toString());
                pfes.add(p);
            }
            
            
        } catch (IOException ex) {
            
        }
        return pfes;
    }
    public ArrayList<Pfe> getMyPfes(int id){
        int idEtudiant = id; /*etudiant co*/
        String url = Statics.BASE_URL +"pfe/"+idEtudiant+"/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                pfes = parsePfes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return pfes;
    }
    public ArrayList<Pfe> getOnePfe(int id){
        String url = Statics.BASE_URL +"pfe/find/"+id+"/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                pfes = parsePfes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return pfes;
    }
    public void SupprimerPfe(Pfe p)
    {
        
       /* String url ="http://localhost/pitest/web/app_dev.php/mobile/pfe/delete/43";*/
        String url = Statics.BASE_URL +"pfe/delete/"+p.getId();
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    public void ModifierPfe(Pfe p,String titre,String sujet)
    {
        /*http://localhost/pitest/web/app_dev.php/mobile/pfe/up/50?titre=finish&sujet=pppppppppppppppppppppppppppppp*/
        String url = Statics.BASE_URL +"pfe/up/"+p.getId()+"?titre="+titre+"&sujet="+sujet;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);       
    }
}
