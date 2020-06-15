/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.bibliotheque;

/**
 * GUI builder created Form
 *
 * @author william
 */
public class GuiComponent extends com.codename1.ui.Form {

	public GuiComponent() {
		this(com.codename1.ui.util.Resources.getGlobalResources());
	}
	
	public GuiComponent(com.codename1.ui.util.Resources resourceObjectInstance) {
		initGuiBuilderComponents(resourceObjectInstance);
	}

//////-- DON'T EDIT BELOW THIS LINE!!!
    protected com.codename1.ui.spinner.Picker gui_Picker = new com.codename1.ui.spinner.Picker();
    protected com.codename1.ui.Container gui_Date_Spinner = new com.codename1.ui.Container();
    protected com.codename1.components.ShareButton gui_Share_Button = new com.codename1.components.ShareButton();
    protected com.codename1.components.ImageViewer gui_Image_Viewer = new com.codename1.components.ImageViewer();


// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.LayeredLayout());
        setInlineStylesTheme(resourceObjectInstance);
        setScrollableY(true);
                setInlineStylesTheme(resourceObjectInstance);
        setTitle("GuiComponent");
        setName("GuiComponent");
        gui_Picker.setPreferredSizeStr("58.201057mm inherit");
        gui_Picker.setText("...");
                gui_Picker.setInlineStylesTheme(resourceObjectInstance);
        gui_Picker.setName("Picker");
        gui_Picker.setType(4);
                gui_Date_Spinner.setInlineStylesTheme(resourceObjectInstance);
        gui_Date_Spinner.setName("Date_Spinner");
                gui_Share_Button.setInlineStylesTheme(resourceObjectInstance);
        gui_Share_Button.setName("Share_Button");
        com.codename1.ui.FontImage.setMaterialIcon(gui_Share_Button,"\ue80d".charAt(0));
                gui_Image_Viewer.setInlineStylesTheme(resourceObjectInstance);
        gui_Image_Viewer.setName("Image_Viewer");
        addComponent(gui_Picker);
        addComponent(gui_Date_Spinner);
        addComponent(gui_Share_Button);
        addComponent(gui_Image_Viewer);
        ((com.codename1.ui.layouts.LayeredLayout)gui_Picker.getParent().getLayout()).setInsets(gui_Picker, "21.223022% 15.871471% auto auto").setReferenceComponents(gui_Picker, "-1 -1 -1 -1").setReferencePositions(gui_Picker, "0.0 0.0 0.0 0.0");
        ((com.codename1.ui.layouts.LayeredLayout)gui_Date_Spinner.getParent().getLayout()).setInsets(gui_Date_Spinner, "33.333332% 13.826679% auto auto").setReferenceComponents(gui_Date_Spinner, "-1 -1 -1 -1").setReferencePositions(gui_Date_Spinner, "0.0 0.0 0.0 0.0");
        ((com.codename1.ui.layouts.LayeredLayout)gui_Share_Button.getParent().getLayout()).setInsets(gui_Share_Button, "auto 25.413826% 29.856115% auto").setReferenceComponents(gui_Share_Button, "-1 -1 -1 -1").setReferencePositions(gui_Share_Button, "0.0 0.0 0.0 0.0");
        ((com.codename1.ui.layouts.LayeredLayout)gui_Image_Viewer.getParent().getLayout()).setInsets(gui_Image_Viewer, "auto auto 21.342926% 33.008762%").setReferenceComponents(gui_Image_Viewer, "-1 -1 -1 -1").setReferencePositions(gui_Image_Viewer, "0.0 0.0 0.0 0.0");
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
}
