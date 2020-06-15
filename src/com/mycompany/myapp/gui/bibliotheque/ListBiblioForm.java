/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

import Entite.Bibliotheque.Bibliotheque;
import Entite.Utilisateur.Utilisateur;
import com.codename1.components.MultiButton;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.mycompany.myapp.gui.LoginForm;
import com.mycompany.myapp.gui.homepage.EtudiantForm;
import com.mycompany.myapp.services.ServiceBibliotheque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author william
 */
public class ListBiblioForm extends Form{
	Form current;
	ServiceBibliotheque ser = new ServiceBibliotheque();
	
	public ListBiblioForm(Form previous, Utilisateur user) {
		List<Bibliotheque> biblios = ser.getAllBiblios();
		current = this;
		setTitle("Bibliothèque");
		setLayout(new FlowLayout(CENTER, CENTER));
		getToolbar().addCommandToSideMenu("Home", null, (evt) -> {
			new EtudiantForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Livre", null, (evt) -> {
			new ListBiblioForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Emprunt", null, (evt) -> {
			new ListBiblio_EmpruntForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("Réservation", null, (evt) -> {
			new EtudiantForm(this, user).show();
		});
		getToolbar().addCommandToSideMenu("se déconnecter", null, (evt) -> {
			new LoginForm().show();
		});

		getToolbar().addCommandToRightBar("revenir", null, (evt) -> {
			previous.show();
		});
		
		ComboBox<Map<String, String>> biblio = new ComboBox<>(	);
		for(Bibliotheque b : biblios)
		{
			biblio.addItem(createListEntry(b.getNom(), b.getAdresse()));
		}
		biblio.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
		add(biblio);
		biblio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.out.println(biblio.getSelectedItem().get("Line1").toString());
				int id = 0;
				//Bibliotheque b = biblios.stream().filter(a-> a.getNom().equalsIgnoreCase(biblio.getSelectedItem().get("Line1").toString())).findAny().orElse(null);
				for(Bibliotheque b : biblios){
					if(b.getAdresse().equalsIgnoreCase(biblio.getSelectedItem().get("Line2").toString()))
					{
						id = b.getId();
						break;
					}
				}
				new Catalogue_frontForm(current, user, id).show();
			}
		});
		
	}
	private Map<String, String> createListEntry(String nom, String adresse) {
    Map<String, String> entry = new HashMap<>();
    entry.put("Line1", nom);
    entry.put("Line2", adresse);
    return entry;
}
}
