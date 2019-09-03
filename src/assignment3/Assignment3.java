/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;



import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.io.IOException;
//import javafx.scene.input.DataFormat.URL;

//import java.util.Collection;
//import java.util.Locale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import static javafx.scene.layout.Background.EMPTY;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.CornerRadii;

import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javax.swing.ImageIcon;

/**
 *
 * @descText.appendText("Changed to "+selectedCountry+" from favourites list ");author mille
 */
public class Assignment3 extends Application {
    
    Country[] countries = Country.getCountries();
    ObservableList<Country> visibleCountriesList = FXCollections.observableArrayList();
    ObservableList<Country> toolbarCountriesList = FXCollections.observableArrayList();
    Country selectedCountry;
    GridPane g = new GridPane();
    HBox textAndFlag = new HBox();
    //VBox centreP = new VBox();
    GridPane centreP = new GridPane();
    GridPane topStuff = new GridPane();
   // HBox bottomStuff = new HBox();
    GridPane bottomStuff = new GridPane();
    GridPane testGrid = new GridPane();
    HBox adrem = new HBox();
    Button addToToolbar = new Button("Add favourite");   
 
    Label CountryTitle = new Label("Label Test"); 
    Image image; 
    ImageView imageHolder = new ImageView();
    Button saveB = new Button("Save");
    Button quitB = new Button("Quit");
    TextArea descText = new TextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
    GridPane saveQuit = new GridPane();
    TextArea eventLog = new TextArea();
    final ListView lv = new ListView();
    
     String[] ContList = {"Asia","South America","Europe","Africa","Austrialia","North America"};
    @Override
    public void start(Stage primaryStage) throws IOException {
        //Set values and important GUI things for all pieces of the GUI.
        
        
        //HBox.setHgrow(adrem, Priority.ALWAYS);
        //HBox.setHgrow(quitB, Priority.ALWAYS);
        //HBox.setHgrow(saveB, Priority.ALWAYS);
        //ColumnConstraints test1 = ColumnConstraintsBuilder.create().percentWidth.(20).build();
        for (Country c : countries)
        {
            visibleCountriesList.add(c);
        }
        
        //Build top toolbar
        g.setHgap(10);
        for (int i = 0; i < 5; i++)
        {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(20);
            g.getColumnConstraints().add(column); 
        }
        
        //Top toolbar creation + eventHandler.
        for (int i = 0; i < 5; i++)
        {
            final Button b = new Button();
            b.setMaxWidth(Double.MAX_VALUE);
            GridPane.setColumnIndex(b, i);
            g.getChildren().add(b);
            b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (!"None".equals(b.getText()))
                    {
                        
                        selectedCountry = Country.getCountry(b.getText());
                        eventLog.appendText("Selected "+selectedCountry.Name+" From favourites list \n");
                        selectedCountryChanged();
                    } else {
                        //don't wanna do anything
                    }
                   
                }
            });
        }
        refreshToolbarButtons();
        
        BorderPane root = new BorderPane();
        
       
        //Build country list
        
        lv.setPrefHeight(400);
        lv.setPrefWidth(100);
        lv.setItems(visibleCountriesList);
        
        lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lv.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                selectedCountry = (Country)lv.getSelectionModel().getSelectedItem();
                selectedCountryChanged();
                //eventLog.appendText("Changed Flag, Country Text and Image to:"+selectedCountry.Name+"\n");
            }
        });
        
        //Build main window
       
        addToToolbar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                addToToolbar();
            }
        });
        Button remFromToolbar = new Button("Remove favourite");
        remFromToolbar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                removeFromToolbar();
            }
        });
        adrem.getChildren().add(addToToolbar);
        adrem.getChildren().add(remFromToolbar);
        
        //Set up for checkbox Filters
         GridPane filters  = new GridPane();
         for (int i = 0; i < 6; i++)
        {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(20);
            filters.getRowConstraints().add(row); 
        }
        saveB.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
             FileChooser fileChooser = new FileChooser();
  
              //Set extension filter
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
              fileChooser.getExtensionFilters().add(extFilter);
              
              //Show save file dialog
              File filepath = fileChooser.showSaveDialog(primaryStage);
              
              //Modify this to  print out the file path.
              eventLog.appendText("Saved file to: "+filepath.toString()+"\n");
              //if(file != null){
                  //SaveFile( file);
              //}
          
            } 
      }); 
     
     
         //Creation and Observer for checkbox Filters.
        for (int i = 0; i < 6; i++)
        {
            CheckBox ch = new CheckBox(ContList[i]);
            ch.setMaxWidth(Double.MAX_VALUE);
            ch.setMaxHeight(Double.MAX_VALUE);
            GridPane.setRowIndex(ch, i);
            ch.setSelected(true);
            //filters.;
            ch.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(ch.isSelected() == false){
                String Title = ch.getText();
                
                String Contval;
                //visibleCountriesList.clear();
                //System.out.println(Title);
                for (Country c : countries){
                    switch (c.Continent.ordinal()) {
                        case 0:
                            Contval = ContList[0];
                            break;
                        case 1:
                            Contval = ContList[1];
                            break;
                        case 2:
                            Contval = ContList[2];
                            break;
                        case 3:
                            Contval = ContList[3];
                            break;
                        case 4:
                            Contval = ContList[4];
                            break;
                        case 5:
                            Contval = ContList[5];
                            break;
                        default:
                            Contval = "Doesn't Exist";
                            break;
                    }
                  //System.out.println(Contval);
                    if (Title.equals(Contval)){
                        visibleCountriesList.remove(c);
                    }
                  
                }
                    eventLog.appendText("Filtered Continent:"+ch.getText()+"\n");
            } else {     
                String Title = ch.getText();
                String Contval;
                for (Country c : countries){
                  switch (c.Continent.ordinal()) {
                        case 0:
                            Contval = ContList[0];
                            break;
                        case 1:
                            Contval = ContList[1];
                            break;
                        case 2:
                            Contval = ContList[2];
                            break;
                        case 3:
                            Contval = ContList[3];
                            break;
                        case 4:
                            Contval = ContList[4];
                            break;
                        case 5:
                            Contval = ContList[5];
                            break;
                        default:
                            Contval = "Doesn't Exist";
                            break;
                    }
                  //System.out.println(Contval);
                    if (Title.equals(Contval)){
                        visibleCountriesList.add(c);
                    }    
                    if (visibleCountriesList.isEmpty()){
                        visibleCountriesList.clear();
                    }
                
               }
                eventLog.appendText("Unfiltered Continent:"+ch.getText()+"\n");
            }
                
        }
    });
    filters.getChildren().add(ch);
}
Scene scene = new Scene(root, 700, 500);
primaryStage.setTitle("Assignment3");
primaryStage.setScene(scene);
image = new Image((getClass().getResourceAsStream("/Flags/vi.png")));
CountryTitle.setText(countries[0].Name);
CountryTitle.setFont(new Font("Arial",20));
//CountryTitle.setWrapText(true);
selectedCountry = Country.getCountry(CountryTitle.getText());
//lv.getSelectionModel().select(0);
//lv.getFocusModel().focus(0);
        
imageHolder.setImage(image);
//imageHolder.setFitWidth(200);
imageHolder.setPreserveRatio(true);
          
   centreP.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,CornerRadii.EMPTY,Insets.EMPTY)));
   adrem.setAlignment(Pos.CENTER);
    lv.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    filters.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    centreP.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    bottomStuff.setPrefHeight(300);
    eventLog.setPrefHeight(500);
    saveQuit.setPrefHeight(500);
        
   testGrid.add(topStuff,0,1); 
        topStuff.add(filters,0,0);
    testGrid.add(g, 0, 0);
        ColumnConstraints cTopSt1 = new ColumnConstraints(50,5000,Double.MAX_VALUE);
        cTopSt1.setPercentWidth(15);
        cTopSt1.setHgrow(Priority.ALWAYS);
        topStuff.getColumnConstraints().add(cTopSt1);
        RowConstraints gtst =new RowConstraints (30,30,Double.MAX_VALUE);
        gtst.setVgrow(Priority.ALWAYS);
        gtst.setPercentHeight(100);
        g.getRowConstraints().add(gtst);
            topStuff.add(centreP,1,0);
                //centreP.getChildren().addAll(textAndFlag,descText,adrem);
                ColumnConstraints cTopSt2 = new ColumnConstraints(50,5000,Double.MAX_VALUE);
                cTopSt2.setPercentWidth(55);
                cTopSt2.setHgrow(Priority.ALWAYS);
                topStuff.getColumnConstraints().add(cTopSt2);
                centreP.add(textAndFlag,0,0);
                    textAndFlag.getChildren().addAll(CountryTitle,imageHolder);
                    textAndFlag.setAlignment(Pos.CENTER);
                centreP.add(descText,0,1);
                centreP.add(adrem,0,2);
                ColumnConstraints tstCol = new ColumnConstraints(50,1000,Double.MAX_VALUE);
                tstCol.setHgrow(Priority.ALWAYS);
                tstCol.setPercentWidth(100);
                RowConstraints tstRow = new RowConstraints(50,1000,Double.MAX_VALUE);
                tstRow.setVgrow(Priority.ALWAYS);
                //tstRow.getPercentHeight();
                centreP.getRowConstraints().addAll(tstRow,tstRow,tstRow);
                centreP.getColumnConstraints().addAll(tstCol);
                //topStuff.getRowConstraints().add(tstRow);
            topStuff.add(lv, 2, 0);
                ColumnConstraints cTopSt3 = new ColumnConstraints(50,5000,Double.MAX_VALUE);
                cTopSt3.setPercentWidth(30);
                cTopSt3.setHgrow(Priority.ALWAYS);
                topStuff.getColumnConstraints().add(cTopSt3);
            testGrid.add(bottomStuff,0,2);
                bottomStuff.add(eventLog,0,0);
                    ColumnConstraints cBotSt1 = new ColumnConstraints(50,50,Double.MAX_VALUE);
                    cBotSt1.setPercentWidth(90);
                    cBotSt1.setHgrow(Priority.ALWAYS);
                    bottomStuff.getColumnConstraints().add(cBotSt1);
                //RowConstraints rBotSt1 = new RowConstraints(50,50,Double.MAX_VALUE);
                //rBotSt1.setVgrow(Priority.NEVER);
                //rBotSt1.setPercentHeight(100);
                bottomStuff.add(saveQuit,1,0);
                    ColumnConstraints cBotSt2 = new ColumnConstraints(50,50,Double.MAX_VALUE);
                    cBotSt2.setPercentWidth(10);
                    cBotSt2.setHgrow(Priority.ALWAYS);
                    bottomStuff.getColumnConstraints().add(cBotSt2);
                        saveQuit.add(saveB,0,0);
                        saveQuit.add(quitB,0,1);
           
               RowConstraints cRowSt3 = new RowConstraints(50,1000,Double.MAX_VALUE);
               cRowSt3.setPercentHeight(100);
               cRowSt3.setVgrow(Priority.ALWAYS);
               bottomStuff.getRowConstraints().add(cRowSt3);
        //testGrid.setGridLinesVisible(true);
        //RowConstraints tstR1 = new RowConstraints(50,50,Double.MAX_VALUE);
        //tstR1.setVgrow(Priority.ALWAYS);
        //tstR1.setPercentHeight(100);
        //testGrid.getRowConstraints().add(tstR1);
        //RowConstraints tstR2 = new RowConstraints(50,50,Double.MAX_VALUE);
        //tstR2.setVgrow(Priority.ALWAYS);
        //tstR2.setPercentHeight(100);
        //testGrid.getRowConstraints().add(tstR2);
        //Setting each piece of the overall TestGrid (why did I name it that?
        //Well it's it was originally a test
        //RowConstraints tGRow1 = new RowConstraints(50,50,Double.MAX_VALUE);
        //tGRow1.setVgrow(Priority.ALWAYS);
        //tGRow1.setPercentHeight(5);
        //testGrid.getRowConstraints().add(0,tGRow1);
        //RowConstraints tGRow2 = new RowConstraints(50,50,Double.MAX_VALUE);
        //tGRow2.setVgrow(Priority.ALWAYS);
        //tGRow2.setPercentHeight(55);
        //testGrid.getRowConstraints().add(1, tGRow2);
        //RowConstraints tGRow3 = new RowConstraints(50,50,Double.MAX_VALUE);
        //tGRow3.setVgrow(Priority.ALWAYS);
        //tGRow3.setPercentHeight(40);
        //testGrid.getRowConstraints().add(2,tGRow3);
        //g.setPadding(new Insets(0,10,10,0));
        //root.setTop(g);
        //g.setGridLinesVisible(true);
        //root.setCenter(topStuff);
        //topStuff.setGridLinesVisible(true);
        //root.setBottom(bottomStuff);
        //bottomStuff.setGridLinesVisible(true);
        adrem.setSpacing(20);
        root.setCenter(testGrid);
       
        primaryStage.show();
        
        quitB.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
             System.exit(0); 
            }
            
        });
           
    }
    
    /**
     *
     */
    public void selectedCountryChanged()
    {
    String countryString = selectedCountry.toString();
     CountryTitle.setText(countryString);
    String description = selectedCountry.Description;
      descText.setText(description);
      String countryCode = selectedCountry.Code;
      String lowerCase=countryCode.toLowerCase();
      //File f = new File("/src//"+lowerCase+".png”");
      //System.out.println(f);
      image = new Image(getClass().getResourceAsStream("/Flags/"+lowerCase+".png"));  
      //System.out.println(f.toURI().toString());
      //image = new Image("/Flags/"+lowerCase+".png");
      imageHolder.setImage(image);
      int index = 0;
      for (Country c: countries){
      if (c.Name.equals(selectedCountry.Name)){
          index = visibleCountriesList.indexOf(c);
        }
      }
      
      lv.scrollTo(index);
    //lv.getSelectionModel().select(0);
    //lv.getFocusModel().focus(0);
      //
      eventLog.appendText("Changed Flag, Country Text and Image to:"+selectedCountry.Name+"\n");
      //if (get)
      //lv.scrollTo(visibleCountriesList.indexOf(selectedCountry));
      //Sqystem.out.println(
      
      
        
                //try{
                    
                //}
        //TODO
        //change the flag image to the correct image
        //change the country name textbox
        //change country description box
        //scroll the list to the selected country

    }
    
     
    public void addToToolbar()
    {
        if (selectedCountry != null){
        toolbarCountriesList.add(0, selectedCountry);
        while (toolbarCountriesList.size() > 5)
            toolbarCountriesList.remove(toolbarCountriesList.size() - 1);
            eventLog.appendText("Added "+selectedCountry+" to favourites \n");
            refreshToolbarButtons();
        } else {
            refreshToolbarButtons();
        }
    }
    
    public void removeFromToolbar()
    {
        if (toolbarCountriesList.remove(selectedCountry))
           eventLog.appendText("Removed "+selectedCountry+" from favourites \n");
            refreshToolbarButtons();
        
    }
    
    public void refreshToolbarButtons()
    {
        for (int i = 0; i < toolbarCountriesList.size(); i++)
        {
            ((Button)g.getChildren().get(i)).setText(toolbarCountriesList.get(i).toString());
        }
        
        for (int i = toolbarCountriesList.size(); i < 5; i++)
        {
            ((Button)g.getChildren().get(i)).setText("None");
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
//when you wanna make something expand with a window, you wanna set it to max value/infinity
    //-Example on BUTTON for loop thing. It works nicely.

        //BorderPane centreP = new BorderPane();
        //centreP.setPrefSize(400,400);
        //AnchorPane centreP = new AnchorPane();
        //centreP.setLeft(CountryTitle);
        //centreP.setRight(imageHolder);
        //centreP.setBottom(descText);
        //centreP.getChildren().add(descText);
        //Add to overall layout

//Notes to self:
//1: Move all your pane pieces to before the program so they're instance variables you dope. Come on you should've probably known better by now.
//2: You're gonna have to change the file to something. Look up how to change the file name to relate to another package or something that might help i dunno.
//3: The checkboxes have to start check, then filter out when they're unchecked.
//4: Try using gridPanes to store you subsections in: makes it easier and better for resizing. This is mainly for the bottom eventLog portion,
//4Continued: but try it for your centre stuff as well. Learn ways to better program now so when you work on GUI stuff later you're better off.
//TODO:
//Fix layout bugs/resizing issues (probably biggest)
//Implement flag/name switching
//Implement checkmark filters
//Implement 'save'/quit functionality
//After you implement ALL of this,implementing the UI events should be pretty easy.
//This seems like a lot, but it's really not that much 