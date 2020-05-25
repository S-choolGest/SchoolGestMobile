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
import entities.Classe;
import entities.Matiere;
import entities.Professeur;
import entities.affectation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MehdiS
 */
public class AffectationService {
    public ConnectionRequest req;
    
         public static AffectationService  instance=null;
         public boolean resultOK;
         public affectation ti=new affectation();
         public ArrayList<Classe> tasksC;
         public ArrayList<Professeur> tasksP;
         public ArrayList<affectation> tasks;
         public ArrayList<Matiere> tasksM;
         
         public AffectationService() {
        req = new ConnectionRequest();    
    }
           public static AffectationService getInstance() {
        if (instance == null) {
            instance = new AffectationService();
        }
        return instance;
    }  

   
   
           public ArrayList<Classe> parseClasse(String jsonText)throws Exception{
        try {
            tasksC=new ArrayList<>();
            
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list){
              
                Classe c = new Classe();
                float id = Float.parseFloat(obj.get("idClasse").toString());
                String nom = obj.get("nomClasse").toString();
                c.setId((int)id);
                c.setNomClasse(nom);
                
                tasksC.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tasksC;
        }
    public ArrayList<Classe> getAllClasse(){
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/Classes";
           System.out.println(url);
            req.setUrl(url);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                   
                    tasksC = parseClasse(new String(req.getResponseData()));
                   
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return tasksC;
        }
    public ArrayList<Matiere> parseMatieres(String jsonText)throws Exception{
        try {
            tasksM=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list){
                Matiere M = new Matiere();
               float id = Float.parseFloat(obj.get("idMatiere").toString());
               String nom = obj.get("nomMatiere").toString();
                M.setId((int)id);
                M.setNomMatiere(nom);
                tasksM.add(M);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tasksM;
        }
    public ArrayList<Matiere> getAllMatiere(){
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/Matieres";
           System.out.println(url);
            req.setUrl(url);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                   
                    tasksM = parseMatieres(new String(req.getResponseData()));
                   
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return tasksM;
        }
     public ArrayList<Professeur> parseProfs(String jsonText)throws Exception{
        try {
            tasksP=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list){
                Professeur E = new Professeur();
               
               //String nom = obj.get("nomMatiere").toString();
                  
                E.setId((int)Float.parseFloat(obj.get("id").toString()));
                E.setNom(obj.get("nom").toString());
               
                tasksP.add(E);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tasksP;
        }
    public ArrayList<Professeur> getAllProf(){
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/Profs";
           System.out.println(url);
            req.setUrl(url);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                   
                    tasksP = parseProfs(new String(req.getResponseData()));
                   
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return tasksP;
        }
         public boolean addAffectation(affectation t) {
        String url ="http://localhost/SchoolGest/web/app_dev.php/school/ajouterAff?prof="+t.getProf().getId()+"&classe="+t.getClasse().getId()+"&matiere="+t.getMatiere().getId();
             System.out.println(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
                
           }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
         public ArrayList<affectation> parseAffectation(String jsonText)throws Exception{
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.println("size :"+list.size());
            for(Map<String,Object> obj : list){
                affectation t = new affectation();
                float id = Float.parseFloat(obj.get("id").toString());
               
                Map<String, Object> prof = (Map<String, Object>) obj.get("idProf");
                Map<String, Object> classe = (Map<String, Object>) obj.get("idClasse");
                
               
                Professeur p = new   Professeur((int)id,prof.get("nom").toString());
                t.setProf(p);
                
                Classe c = new Classe((Double)classe.get("idClasse"),classe.get("nomClasse").toString());
               
                t.setClasse(c);
               t.setId((int)id);
                
                tasks.add(t);
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return tasks;
    }
     public affectation getAffectation(int id){
        String url ="http://localhost//SchoolGest/web/app_dev.php/school/AffApi/"+id;
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent evt) {
               
               // ti = parseAbsence1(new String(req.getResponseData()));
                 //System.out.println("chnia mochkol "+tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ti;
    }    
     
      public ArrayList<affectation> suppAbsence(int id){
        String url ="http://localhost/SchoolGest/web/app_dev.php/school/supprimerAff?id="+id;
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                
                try {
                    tasks = parseAffectation(new String(req.getResponseData()));
                } catch (Exception ex) {
                
                }
                    //System.out.println("chnia mochkol "+tasks);
                    req.removeResponseListener(this);
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
      public boolean modifierAffectation(affectation t) {
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/modifierAff?prof="+t.getProf().getId()+"&classe="+t.getClasse().getId()+"&id="+t.getId();
            System.out.println(url);
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
       public ArrayList<affectation> getAllAffectation(){
        String url ="http://localhost/SchoolGest/web/app_dev.php/school/afficherAff";
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                    tasks = parseAffectation(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
            System.out.println(tasks);
        return tasks;
    }
     
      
}
