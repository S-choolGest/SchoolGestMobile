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
import entities.Absence;
import entities.Classe;
import entities.Etudiant;
import entities.Matiere;
import entities.Utilisateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author MehdiS
 */
public class AbsenceService {
    public ConnectionRequest req;
         public static AbsenceService  instance=null;
         public boolean resultOK;
         public ArrayList<Absence> tasks;
         public ArrayList<Matiere> tasksM;
         public ArrayList<Etudiant> tasksE;
         public ArrayList<Classe> tasksC;
         public Absence ti=new Absence();
          public AbsenceService() {
        req = new ConnectionRequest();    
    }
           public static AbsenceService getInstance() {
        if (instance == null) {
            instance = new AbsenceService();
        }
        return instance;
    }  
     public ArrayList<Absence> parseAbsence(String jsonText)throws Exception{
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.println("size :"+list.size());
            for(Map<String,Object> obj : list){
                Absence t = new Absence();
                float id = Float.parseFloat(obj.get("id").toString());
                Map<String, Object>  matiere = (Map<String, Object>)  obj.get("matiere");
                Map<String, Object> idEtudiant = (Map<String, Object>) obj.get("idEtudiant");
                Map<String, Object> classe = (Map<String, Object>) obj.get("classe");
                Map<String, Object> date = (Map<String, Object>) obj.get("date");
                String heure = obj.get("heureFormat").toString();
                Map<String, Object> InfoEtudiant = (Map<String, Object>) idEtudiant.get("id");
                System.out.println(idEtudiant);
                t.setId((int)id);
                Matiere m = new Matiere((Double) matiere.get("idMatiere"),matiere.get("nomMatiere").toString());
                t.setMatiere(m);
                Etudiant e = new Etudiant((Double)InfoEtudiant.get("id"),InfoEtudiant.get("nom").toString(),InfoEtudiant.get("prenom").toString());
                t.setIdEtudiant(e);
                t.setEtat(obj.get("etat").toString());
                float dateF = Float.parseFloat(date.get("timestamp").toString());
                t.setDate((long)dateF);
                t.setHeure(heure);
                Classe c = new Classe((Double)classe.get("idClasse"),classe.get("nomClasse").toString());
               
                t.setClasse(c);
               
                
                tasks.add(t);
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return tasks;
    }
     public ArrayList<Absence> parseAbsenceEtudiant(String jsonText)throws Exception{
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.println("size :"+list.size());
            for(Map<String,Object> obj : list){
                Absence t = new Absence();
                float id = Float.parseFloat(obj.get("id").toString());
                Map<String, Object>  matiere = (Map<String, Object>)  obj.get("matiere");
                Map<String, Object> idEtudiant = (Map<String, Object>) obj.get("idEtudiant");
                Map<String, Object> classe = (Map<String, Object>) obj.get("classe");
                Map<String, Object> date = (Map<String, Object>) obj.get("date");
                String heure = obj.get("heureFormat").toString();
                Map<String, Object> InfoEtudiant = (Map<String, Object>) idEtudiant.get("id");
                
                t.setId((int)id);
                Matiere m = new Matiere((Double) matiere.get("idMatiere"),matiere.get("nomMatiere").toString());
                t.setMatiere(m);
                Etudiant e = new Etudiant((Double)InfoEtudiant.get("id"),InfoEtudiant.get("nom").toString(),InfoEtudiant.get("prenom").toString());
                t.setIdEtudiant(e);
                t.setEtat(obj.get("etat").toString());
                float dateF = Float.parseFloat(date.get("timestamp").toString());
                t.setDate((long)dateF);
                t.setHeure(heure);
                Classe c = new Classe((Double)classe.get("idClasse"),classe.get("nomClasse").toString());
               
                t.setClasse(c);
               
                
                tasks.add(t);
            }
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return tasks;
    }
    public Absence getAbsence(int id){
        String url ="http://localhost//SchoolGest/web/app_dev.php/school/AbsenceApi/"+id;
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
        
        
         public boolean addAbsence(Absence t) {
        String url ="http://localhost/SchoolGest/web/app_dev.php/school/ajouterAbs?matiere="+t.getMatiere().getId()+
                "&heure="+t.getHeure()+"&classe="+t.getClasse().getId()+"&etat="+t.getetat()+"&etudiant="+t.getIdEtudiant().getId(); //création de l'URL
             
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
         
         
         
     public ArrayList<Absence> suppAbsence(int id){
        String url ="http://localhost/SchoolGest/web/app_dev.php/school/supprimerAbs?id="+id;
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                
                try {
                    tasks = parseAbsence(new String(req.getResponseData()));
                } catch (Exception ex) {
                
                }
                    //System.out.println("chnia mochkol "+tasks);
                    req.removeResponseListener(this);
              
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
   
  public boolean modifierAbsence(Absence t) {
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/modifierAbs?matiere="+t.getMatiere().getId()+"&date="+t.getDateString(t.getDate())+
                "&heure="+t.getHeure()+"&classe="+t.getClasse().getId()+"&etat="+t.getetat()+"&etudiant="+t.getIdEtudiant().getId()+"&id="+t.getId();
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
     
  
  
        public ArrayList<Absence> getAllAbsence(){
        String url ="http://localhost/SchoolGest/web/app_dev.php/school/afficherProfAbs/"+Utilisateur.current_user.getId();
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                    tasks = parseAbsence(new String(req.getResponseData()));
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
         public ArrayList<Etudiant> parseEtudiant(String jsonText)throws Exception{
        try {
            tasksE=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            System.out.print("size :"+list.size());
            for(Map<String,Object> obj : list){
                Etudiant E = new Etudiant();
                Map<String, Object> id = (Map<String, Object>) obj.get("id");
               //String nom = obj.get("nomMatiere").toString();
                  System.out.println("id = " + id.get("id") + "nom = "+ id.get("nom"));
                E.setId((int)Float.parseFloat(id.get("id").toString()));
                E.setNom(id.get("nom").toString());
               
                tasksE.add(E);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return tasksE;
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
             public ArrayList<Matiere> getAllMatieres(){
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/Matieres";
       //System.out.println(url);
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
        public ArrayList<Etudiant> getAllEtudiant(String Classe){
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/Etudiants/"+Classe;
           //System.out.println(url);
            req.setUrl(url);
            req.setPost(false);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                    tasksE = parseEtudiant(new String(req.getResponseData()));
                   
                    req.removeResponseListener(this);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
            return tasksE;
        }      
        public ArrayList<Classe> getAllClasse(int id ){
            String url ="http://localhost/SchoolGest/web/app_dev.php/school/Classes/"+id;
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

    public ArrayList<Absence> getAllAbsenceEtudiant(int id) {
         String url ="http://localhost/SchoolGest/web/app_dev.php/school/EtudAbsaff/"+id;
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
                try {
                    tasks = parseAbsenceEtudiant(new String(req.getResponseData()));
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
