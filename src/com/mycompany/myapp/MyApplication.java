package com.mycompany.myapp;


import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;
import java.io.IOException;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;

import gui.GuiAbsence;
import gui.SignInForm;
import gui.AccueilForm;

import entities.Utilisateur;
import service.ServiceUser;
/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication {

   private Form current;
    public static Resources theme;

    public void init(Object context) {
        // use two network threads instead of one
        updateNetworkThreadCount(2);

        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);

        addNetworkErrorListener(err -> {
            // prevent the event from propagating
            err.consume();
            if(err.getError() != null) {
                Log.e(err.getError());
            }
            Log.sendLogAsync();
            Dialog.show("Connection Error", "There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK", null);
        });        
    }
    
    public void start() {
         Database db;
        

        try {
            db = Database.openOrCreate("School.db");
            db.execute("create table if not exists appstates (loggedin NUMBER, loggeduserid NUMBER);");
            Cursor cur = db.executeQuery("select * from appstates");
            int user_id = -1;
            int loggedin = -1;
            while (cur.next()) {
                Row r = cur.getRow();
                loggedin = r.getInteger(0);
                if (loggedin == 1) {
                    user_id = r.getInteger(1);
                }
            }
          
            if (loggedin == 1 && user_id != -1) {
                ServiceUser ser = new ServiceUser();
                Utilisateur.current_user = ser.getUserData(user_id);
                System.out.println("Utilisateur connecté");
                  AccueilForm h = new AccueilForm(theme);
                             h.show();
            } else {
                  System.out.println("Utilisateur  NON connecté");
				SignInForm h = new SignInForm(theme);
				h.show();
           cur.close();
            }
        } catch (IOException ex) {
            
        }
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

   

}
