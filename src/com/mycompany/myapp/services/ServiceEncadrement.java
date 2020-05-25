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
import com.mycompany.myapp.api.AccDemandeMail;
import com.mycompany.myapp.api.AddMail;
import com.mycompany.myapp.api.RefDemandeMail;
import com.mycompany.myapp.entities.Encadrement;
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
public class ServiceEncadrement {
    public ArrayList<Encadrement> encadrements;
    public static ServiceEncadrement instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    private ServiceEncadrement() {
         req = new ConnectionRequest();
    }
    public static ServiceEncadrement getInstance() {
        if (instance == null) {
            instance = new ServiceEncadrement();
        }
        return instance;
    }
    public boolean addEncadrement(int idpfe ,String email) {
        /*http://localhost/pitest/web/app_dev.php/mobile/encad/addencad/new?email=p3@p3.com&idpfe=28*/
        String url = Statics.BASE_URL+"encad/addencad/new?email="+email+"&idpfe="+idpfe+"/";
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        resultOK=true;
        
        try {
            AddMail.sendMail("wazkasmi@gmail.com");
        } catch (Exception ex) {
        }
        
        return resultOK;
    }
    public ArrayList<Encadrement> parseEncadrements(String jsonText){
        try {
            encadrements=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> encadrementsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)encadrementsListJson.get("root");
            for(Map<String,Object> obj : list){
                Encadrement p = new Encadrement();
                float id = Float.parseFloat(obj.get("id").toString());
                p.setId((int)id);
                p.setIdPfe(((int)Float.parseFloat(obj.get("idPfe").toString())));
                p.setIdProf(((int)Float.parseFloat(obj.get("idProf").toString())));
                p.setEtat(obj.get("etat").toString());
                encadrements.add(p);
            }
        } catch (IOException ex) {
        }
        return encadrements;
    }
    public ArrayList<Encadrement> getMyEncadrements(int id){
        int idEtudiant = id; /*etudiant co*/
        /*http://localhost/pitest/web/app_dev.php/mobile/encad/6/*/
        String url = Statics.BASE_URL +"encad/"+idEtudiant+"/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                encadrements = parseEncadrements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return encadrements;
    }
    public ArrayList<Encadrement> getMyEncadrementsProf(int id){
        int idProf = id; /*etudiant co*/
        /*http://localhost/pitest/web/app_dev.php/mobile/encadprof/5*/
        String url = Statics.BASE_URL +"encadprof/"+idProf;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                encadrements = parseEncadrements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return encadrements;
    }
     public ArrayList<Encadrement> getOneEncadrement(int id){
         /*http://localhost/pitest/web/app_dev.php/mobile/encad/find/41/*/
        String url = Statics.BASE_URL +"encad/find/"+id+"/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                encadrements = parseEncadrements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return encadrements;
    }
    public void AccepterEncadrement(int id ){
        /*http://localhost/pitest/web/app_dev.php/mobile/encad/accepter/41*/
    String url = Statics.BASE_URL +"encad/accepter/"+id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        try {
            AccDemandeMail.sendMail("wazkasmi@gmail.com");
        } catch (Exception ex) {
        }
    
    }
    public void RefuserEncadrement(int id ){
        /*http://localhost/pitest/web/app_dev.php/mobile/encad/refuser/41*/
    String url = Statics.BASE_URL +"encad/refuser/"+id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        try {
            RefDemandeMail.sendMail("wazkasmi@gmail.com");
        } catch (Exception ex) {
        }
    
    }
}
