/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.google.common.collect.ArrayListMultimap;
import entities.Absence;
import java.text.SimpleDateFormat;


import java.util.HashMap;
import java.util.Map;
import service.AbsenceService;

import com.google.common.collect.Multimap;
import com.mycompany.myapp.MyApplication;
import entities.Utilisateur;

/**
 *
 * @author MehdiS
 */
public class StatAbsence extends Form {
    Form current;
  
    
      Multimap<String,String> ListHeureDate = ArrayListMultimap.create();

        
    Map<String,Integer> ListHeureValue = new HashMap<>();
    ComboBox<String> cbHeure;
    int i = 0;
    StatAbsence(Form previous) {
         this.getToolbar().addCommandToLeftBar("back", MyApplication.theme.getImage("back-command.png"), ev->{
 
      new GuiAbsence(this,Utilisateur.current_user.getId()).show();
        });
        cbHeure =new ComboBox("jours de la semaine");
        cbHeure.addItem("lundi");
        cbHeure.addItem("mardi");
        cbHeure.addItem("mercredi");
        cbHeure.addItem("jeudi");
        cbHeure.addItem("vendredi");
        cbHeure.addItem("samedi");
        current = this;
        setLayout(BoxLayout.y());
       
        String dayOfWeek = null;
        for (Absence  r : new AbsenceService().getAllAbsence())
        {
            
          dayOfWeek = new SimpleDateFormat("EEEE").format(r.getDate());
           ListHeureDate.put(dayOfWeek,r.getHeureString(r.getHeure()));
           
        }
        System.out.println(ListHeureDate);
        cbHeure.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                for(Map.Entry<String,String> a: ListHeureDate.entries()){
                    System.out.println(a.getKey());
            if(ListHeureDate.containsKey(cbHeure.getSelectedItem())){
                
                ListHeureValue.put(a.getValue(),i++);
               
            }
        }
            
        System.out.println(ListHeureValue);
       // double[] values = new double[]{12, 14, 11, 10, 19};

  
        // Set up the renderer
    int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Project budget", ListHeureValue), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);

    // Create a form and show it.
   
    add(c);
    }
        });
        add(cbHeure);
    }
    
    
    /**
 * Creates a renderer for the specified colors.
 */
private DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(30);
    //renderer.setLegendTextSize(30);
    renderer.setLabelsColor(ColorUtil.BLACK);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}

/**
 * Builds a category series using the provided values.
 *
 * @param title the series titles
 * @param map the values
 * @return the category series
 */
protected CategorySeries buildCategoryDataset(String title, Map<String,Integer> map) {
    CategorySeries series = new CategorySeries(title);
    series.clear();
    int k = 0;
    System.out.println("map = " + map);
    for(Map.Entry<String,Integer> a: map.entrySet()){
        
        i=0;
        series.add(a.getKey(), a.getValue());
    }

    return series;
}

    
}
