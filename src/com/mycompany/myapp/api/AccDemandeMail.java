/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.myapp.api;

import com.codename1.messaging.Message;
import com.codename1.ui.Display;



/**
 *
 * @author TOSHIBA
 */
public class AccDemandeMail {
     public static void sendMail(String email) throws Exception{
        Message m = new Message("votre demande a ete acceptee veuillez me contacter pour finaliser l'affectation");
        String[]address={email};
        Display.getInstance().sendMessage(address, "Reponse demande d'encadrement", m);
    
        }
    
    
}
