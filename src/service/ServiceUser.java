/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import entities.Utilisateur;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author actar
 */
public class ServiceUser {

    public Utilisateur U;

    public Utilisateur getUserEntity(String json) {
        Utilisateur u = null;
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> obj = j.parseJSON(new CharArrayReader(json.toCharArray()));

            if (obj.size() == 0) {
                return null;
            }
            u = new Utilisateur();
            float id = Float.parseFloat(obj.get("id").toString());
            u.setId((int) id);


            u.setUsername(obj.get("username").toString());
            u.setRoles(obj.get("roles").toString());
//            u.setEmail(obj.get("email").toString());
//            u.setPhoto(obj.get("photo").toString());
//            u.setPrenom(obj.get("prenom").toString());
//            u.setNumtel(obj.get("numtel").toString());
//            u.setNom(obj.get("nom").toString());

          
          u.setRoles(obj.get("roles").toString());
           u.setPrenom(obj.get("prenom").toString());
          
           u.setNom(obj.get("nom").toString());

            //u.setProfilepicture("" + obj.get("profilePicture")); Au cas ou
        } catch (IOException ex) {
        }
        return u;
    }

    public Utilisateur getUserData(int user_id) {
        ConnectionRequest con = new ConnectionRequest();
     
        con.setUrl("http://localhost/SchoolGest/web/app_dev.php/school/getuserid/" + user_id);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceUser ser = new ServiceUser();
                U = ser.getUserEntity(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return U;
    }

    public Utilisateur CheckLoginData(String username, String password) {
        ConnectionRequest con = new ConnectionRequest();

      
           con.setUrl("http://localhost/SchoolGest/web/app_dev.php/school/checklogindata/username/" + username + "/password/" + password);

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceUser ser = new ServiceUser();
                System.out.println(new String(con.getResponseData()));
                U = ser.getUserEntity(new String(con.getResponseData()));

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);

        return U;
    }

    

   

}
