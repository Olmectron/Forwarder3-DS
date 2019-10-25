/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.forwarder.strings.R;
import com.olmectron.material.MaterialDesign;
import com.olmectron.material.components.MaterialCard;
import com.olmectron.material.components.MaterialDisplayText;
import com.olmectron.material.components.MaterialDropdownMenu;
import com.olmectron.material.components.MaterialDropdownMenuItem;
import com.olmectron.material.components.MaterialFloatingButton;
import com.olmectron.material.components.MaterialFlowList;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialLabel;
import com.olmectron.material.components.MaterialStandardListItem;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.MaterialTransparentPane;
import com.olmectron.material.components.RaisedButton;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.TextFile;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.WindowEvent;
import org.utilities.Dates;

/**
 *
 * @author Edgar
 */
public class FilePane extends MaterialTransparentPane{
    private Pane gRoot;
    public FilePane(){
        
        this.getContentCard().setCardPadding(new Insets(0));
        gRoot=getFileList().getPerfectSizeFlowListPane();
        
            
        setRootComponent(gRoot);
        MaterialFloatingButton b=new MaterialFloatingButton(MaterialIconButton.SAVE_ICON);
        b.setOnClick(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(Forwarder3DS.getBuildMode()==Forwarder3DS.FORWARDERS){
                    forwarderAction();
                }
                else if(Forwarder3DS.getBuildMode()==Forwarder3DS.TWLOADER){
                    twLoaderAction();
                }
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        this.getContentCard().setFloatingButton(b);
    }
    private void twLoaderAction(){
           String folder=getFolderName();
                int badCounter=0;
                for(int i=0;i<getFileList().size();i++){
                   ROMFile item=getFileList().getItem(i);
                       item.processIniFile(folder);
                 
                   
                    
                }
              
                if(badCounter==0){
                    new MaterialToast(R.strings.success_process.get()).unhide();
                
                }
                else{
                    if(badCounter==1){
                    new MaterialToast(R.strings.success_process.get()+"\n"+badCounter+" entry was ignored because of bad TID").unhide();
                    
                    }
                    else{
                    new MaterialToast(R.strings.success_process.get()+"\n"+badCounter+" entries were ignored because of bad TID").unhide();
                    
                    }
                    
                }
                onCompleteFolderOpen(folder);
             
    }
    private void forwarderAction(){
        
                String folder=getFolderName();
                int badCounter=0;
                for(int i=0;i<getFileList().size();i++){
                   ROMFile item=getFileList().getItem(i);
                   //if(item.hasValidTID()){
                   item.process(folder);
                   if(item.getCustomTID()!=null && !item.getCustomTID().trim().equals("null") && !item.getCustomTID().trim().equals("")){
                        if(R.getUsedTidsFile().getText()!=null && !R.getUsedTidsFile().getText().trim().equals(""))
                            R.getUsedTidsFile().setText(R.getUsedTidsFile().getText().trim()+System.getProperty("line.separator")+item.getCustomTID());
                        else{
                            R.getUsedTidsFile().setText(item.getCustomTID());
                        }
                   }
                   //}
                   //else{
                   //    badCounter++;
                   //}
                   
                    
                }
              
                if(badCounter==0){
                    new MaterialToast(R.strings.success_process.get()).unhide();
                
                }
                else{
                    if(badCounter==1){
                    new MaterialToast(R.strings.success_process.get()+"\n"+badCounter+" entry was ignored because of bad TID").unhide();
                    
                    }
                    else{
                    new MaterialToast(R.strings.success_process.get()+"\n"+badCounter+" entries were ignored because of bad TID").unhide();
                    
                    }
                    
                }
                onCompleteFolderOpen(folder);
                
    }
    public String getFolderName(){
        File f=new File("generated");
        if(!f.exists())
        f.mkdir();
        String dateFolder="generated/"+Dates.getToday().getMySqlCompatibleDate()+"_"+Dates.getNow().replace(":","");
        File date=new File(dateFolder);
        if(!date.exists())
        date.mkdir();
        return dateFolder;
    }
    public void onCompleteFolderOpen(String folder){};
    public void addROMFile(ROMFile rFile){
        getFileList().addItem(rFile);
    }
    private MaterialFlowList<ROMFile> fileList;
    public MaterialFlowList<ROMFile> getFileList(){
        if(fileList==null){
            fileList=new MaterialFlowList<ROMFile>(new StackPane()) {
            

            @Override
            public void onLongPressSelection(int selected) {
              
                //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public boolean asCard(){
                return true;
            }
            

            @Override
            public void onItemContainerClick(MaterialStandardListItem<ROMFile> itemBox, MouseEvent event) {
                   if(event.getButton().equals(MouseButton.PRIMARY)){
                    triggerAnimation(itemBox);
                }
                //super.onItemContainerClick(itemBox, event); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void onItemClick(ROMFile unit, MouseEvent event) {
            
                if(event.getButton().equals(MouseButton.SECONDARY)){
                        MaterialDropdownMenu menu=new MaterialDropdownMenu(event.getScreenX(),event.getScreenY());
                       
                      // menu.addNodeAsItem(new ROMEditPane(unit));
                       
                           
                       //menu.addNodeAsItem(datField);
                       
                      
                       
                       //menu.addNodeAsItem(gamePathField);
                       /*menu.addItem(new MaterialDropdownMenuItem(R.strings.change_ds.get()){
                           @Override
                           public void onItemClick(){
                               if(unit.getMode()==Hex.NTR){
                                   unit.setMode(Hex.TWL);
                                   
                               }
                               
                               else if(unit.getMode()==Hex.TWL){
                                   unit.setMode(Hex.NTR);
                               }
                           }
                       });*/
                       menu.addItem(new MaterialDropdownMenuItem(R.strings.shuffle_tid.get()){
                
                        @Override
                        public void onItemClick(){
                          
                                unit.setCustomTID(R.getRandomTID(getFileList()));
                            
                       }
                
                });
                       menu.addItem(new MaterialDropdownMenuItem("Export banner"){
                           @Override
                           public void onItemClick(){
                               menu.setOnHidden(new EventHandler<WindowEvent>(){
                                   @Override
                                   public void handle(WindowEvent event) {
                                       unit.exportBannerFile(); //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                           }
                       });
                       menu.addItem(new MaterialDropdownMenuItem("Import banner"){
                           @Override
                           public void onItemClick(){
                               menu.setOnHidden(new EventHandler<WindowEvent>(){
                                   @Override
                                   public void handle(WindowEvent event) {
                                       unit.importBannerFile(); //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                           }
                       });
                       menu.addItem(new MaterialDropdownMenuItem(R.strings.remove.get()){
                           @Override
                           public void onItemClick(){
                               getFileList().removeItem(unit);
                           }
                       });
                       
                       
                       
                       menu.unhide();
                }
                /*if(event.getButton().equals(MouseButton.SECONDARY)){
                    MaterialDropdownMenu menu=new MaterialDropdownMenu(event.getScreenX(),event.getScreenY());
                    menu.addItem(new MaterialDropdownMenuItem("Save portrait"){
                        @Override
                        public void onItemClick(){
                            R.saveImageToFile(unit.getName(),((ImageView)unit.getPortrait()).getImage());
                        }
                    
                    });
                    menu.unhide();
                }*/
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void cardConverter(MaterialCard card, ROMFile file, MaterialStandardListItem<ROMFile> itemContainer){
               card.setCardWidth(500);
               card.setCardPadding(new Insets(16));
               MaterialDisplayText nameText=new MaterialDisplayText(file.getDisplayName());
               //nameText.setWrapText(true);
               MaterialDisplayText TIDText=new MaterialDisplayText(file.getCustomTID());
              
               MaterialDisplayText datText=new MaterialDisplayText(file.getDatName());
               MaterialDisplayText gamePathText=new  MaterialDisplayText(file.getGamePath());
               
               MaterialDisplayText gameTitleText=new  MaterialDisplayText(file.getGameTitle());
               file.gameTitleProperty().addListener(new ChangeListener<String>(){
                   @Override
                   public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if(newValue!=null){
                           gameTitleText.setText(newValue);
                       }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               file.gamePathProperty().addListener(new ChangeListener<String>(){
                   @Override
                   public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if(newValue!=null){
                           gamePathText.setText(newValue);
                       }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               file.customTIDProperty().addListener(new ChangeListener<String>(){
                   @Override
                   public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if(newValue!=null){
                           TIDText.setText(newValue);
                       }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               file.datNameProperty().addListener(new ChangeListener<String>(){
                   @Override
                   public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if(newValue!=null){
                           datText.setText(newValue);
                       }
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               MaterialDisplayText modeText=new MaterialDisplayText(file.getModeDescription());
               
               file.modeProperty().addListener(new ChangeListener<Number>(){
                   @Override
                   public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                       if(newValue!=null){
                           modeText.setText(file.getModeDescription());
                       }
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               
               /*
               /*
               Timeline timeline=new Timeline();
                        KeyValue kv1=new KeyValue(nameText
                        .translateXProperty(),nameText.getTranslateX()-200);
                       
                        KeyFrame kf=new KeyFrame(Duration.millis(300),kv1);
                       //timeline.setCycleCount(Timeline.INDEFINITE);
                        timeline.setOnFinished(new EventHandler<ActionEvent>(){
                   @Override
                   public void handle(ActionEvent event) {
                      nameText.setTranslateX(nameText.getTranslateX()-200);
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
                        itemContainer.setOnMouseExited(new EventHandler<MouseEvent>(){
                   @Override
                   public void handle(MouseEvent event) {
                      
                       nameText.setTranslateX(0);
                      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
                        itemContainer.setOnMouseEntered(new EventHandler<MouseEvent>(){
                   @Override
                   public void handle(MouseEvent event) {
                      
                       timeline.play();
                      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
                        timeline.getKeyFrames().add(kf);
                        */
                        
               nameText.setColorCode(MaterialColor.material.BLACK_87);
               nameText.setFontSize(18);
               
               TIDText.setColorCode(MaterialColor.material.BLUE);
               TIDText.setFontSize(15);
               
               
               MaterialDisplayText TIDLabel=new  MaterialDisplayText("TID:  ");
               TIDLabel.setFontSize(13);
               TIDLabel.setColorCode(MaterialColor.material.BLACK_54);
               
               
               if(!R.getTidList().contains(TIDText.getText())){
                   if(R.getAutoCorrectTID()){
                        TIDText.setColorCode(MaterialColor.material.BLUE);
                   TIDLabel.setColorCode(MaterialColor.material.BLACK_54);
                    file.setCustomTID(R.getRandomTID(getFileList()));
                   }
                   else{
                   // TIDText.setColorCode(MaterialColor.material.RED);
                    //TIDLabel.setColorCode(MaterialColor.material.RED);
                   }
               }
               else{
                   TIDText.setColorCode(MaterialColor.material.BLUE);
                   TIDLabel.setColorCode(MaterialColor.material.BLACK_54);
                    
               }
               TIDText.textProperty().addListener(new ChangeListener<String>(){
                   @Override
                   public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if(!R.getTidList().contains(newValue)){
                   //TIDText.setColorCode(MaterialColor.material.RED);
                   //TIDLabel.setColorCode(MaterialColor.material.RED);
                       
               }
               else{
                   TIDText.setColorCode(MaterialColor.material.BLUE);
                   TIDLabel.setColorCode(MaterialColor.material.BLACK_54);
                    
               }
                      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               
               
               MaterialDisplayText datLabel=new  MaterialDisplayText("DAT:  ");
               datLabel.setFontSize(13);
               datLabel.setColorCode(MaterialColor.material.BLACK_54);
               MaterialDisplayText gamePathLabel=new  MaterialDisplayText("Path:  ");
               gamePathLabel.setFontSize(13);
               gamePathLabel.setColorCode(MaterialColor.material.BLACK_54);
               MaterialDisplayText gameTitleLabel=new  MaterialDisplayText("Title:  ");
               gameTitleLabel.setFontSize(13);
               gameTitleLabel.setColorCode(MaterialColor.material.BLACK_54);
               datText.setColorCode(MaterialColor.material.BLUE);
               datText.setFontSize(15);
               gamePathText.setColorCode(MaterialColor.material.INDIGO);
               gamePathText.setFontSize(14);
               gameTitleText.setColorCode(MaterialColor.material.INDIGO);
               gameTitleText.setFontSize(14);
               modeText.setMinWidth(100);
               HBox TIDBox=new HBox(TIDLabel,TIDText);
               HBox gameTitleBox=new HBox(gameTitleLabel,gameTitleText);
               TIDBox.setAlignment(Pos.CENTER);
              
               HBox datBox=new HBox(datLabel,datText);
               datBox.setAlignment(Pos.CENTER);
               HBox gamePathBox=new HBox(gamePathLabel,gamePathText);
               gamePathBox.setAlignment(Pos.CENTER_LEFT);
               gameTitleBox.setAlignment(Pos.CENTER_LEFT);
               
               MaterialDisplayText modeLabel=new  MaterialDisplayText("Card Type:  ");
               modeLabel.setFontSize(13);
               modeLabel.setColorCode(MaterialColor.material.BLACK_54);
               HBox modeBox=new HBox(modeLabel,modeText);
               modeBox.setPadding(new Insets(8));
               modeBox.setAlignment(Pos.CENTER);
               TIDBox.setPadding(new Insets(8));
               datBox.setPadding(new Insets(8));
               datBox.setPadding(new Insets(8));
               
               gamePathBox.setPadding(new Insets(8));
               gameTitleBox.setPadding(new Insets(8));
               
               HBox span=new HBox();
               HBox.setHgrow(span, Priority.ALWAYS);
              
               modeText.setColorCode(MaterialColor.material.GREEN);
               modeText.setFontSize(15);
               MaterialIconButton backButton=new MaterialIconButton(MaterialIconButton.CLOSE_ICON);
               backButton.setOnAction(new EventHandler<ActionEvent>(){
                   @Override
                   public void handle(ActionEvent event) {
                       
                        triggerAnimation(itemContainer);
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               
               backButton.setColorCode(MaterialColor.material.BLUE);
               backButton.setId("back-button");
               backButton.setVisible(false);
               HBox nameBox=new HBox(new IconAnimation(file)/*new ROMIcon(file)*/, nameText,span,backButton);
               file.importedBannerProperty().addListener(new ChangeListener<byte[]>(){
                   @Override
                   public void changed(ObservableValue<? extends byte[]> observable, byte[] oldValue, byte[] newValue) {
                    if(newValue!=null){
                     nameBox.getChildren().remove(0);
                     file.readImportedIconData();
                     file.readImportedPaletteColors();
                     
                     file.readROMIcon();
                     nameBox.getChildren().add(0,new IconAnimation(file));
                    }//To change body of generated methods, choose Tools | Templates.
                   }
               });
               nameText.setPadding(new Insets(0,0,0,16));
               nameBox.setAlignment(Pos.CENTER);
               card.addComponent(nameBox);
               VBox dataBox=new VBox();
               dataBox.getChildren().addAll(new HBox(TIDBox,datBox,modeBox));//,new HBox(gameTitleBox,gamePathBox));
                 card.addComponent(dataBox);
               
               /*FlowPane paneColors=new FlowPane();
               
               ArrayList<ArrayList<String>> paletas=file.getAnimationPalettes();
               ArrayList<int[][]> iconos=file.getAnimationIcons();
               
               for(int i=0;i<8;i++){
                   paneColors.getChildren().add(new ROMIcon(iconos.get(i),paletas.get(i)));
               }
               /*ArrayList<String> colores=file.getPaletteColors();
               
               for(String c: colores){
                VBox colorBox=new VBox();
                colorBox.setMinWidth(100);
                MaterialDisplayText t=new MaterialDisplayText(c);
                t.setFontSize(12);
                t.setColorCode(MaterialColor.material.AMBER);
                
                colorBox.getChildren().add(t);
                
                colorBox.setMinHeight(20);
                colorBox.setStyle(colorBox.getStyle()+"-fx-background-color: "+c+" !important;");
                
                paneColors.getChildren().add(colorBox);
               }
               card.addComponent(paneColors);*/
               if(R.getCard()!=null){
              if(R.getCard().getRequireDAT()){
                           datBox.setVisible(true);
                           datBox.setManaged(true);
                       }
                       else{
                           datBox.setVisible(false);
                           datBox.setManaged(false);
                       }
                   
               }
               else{
                
                           datBox.setVisible(false);
                           datBox.setManaged(false);
            }
                       
               R.cardProperty().addListener(new ChangeListener<Card>(){
                   @Override
                   public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card tarjeta) {
                       if(tarjeta.getRequireDAT()){
                            datBox.setVisible(true);
                           datBox.setManaged(true);
                       }
                       else{
                            datBox.setVisible(false);
                           datBox.setManaged(false);
                       }
                       /*if(newValue.intValue()==CardType.R4_ORIGINAL){
                           datBox.setVisible(false);
                           datBox.setManaged(false);
                       }
                       else if(newValue.intValue()==CardType.DSTT_R4i_SHDC){
                           datBox.setVisible(true);
                           datBox.setManaged(true);
                       }*/
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
               
            }
            @Override
            public Node itemConverter(ROMFile unit, MaterialStandardListItem<ROMFile> itemContainer) {
                
                
                return null;
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void onItemSwap(int index1, int index2, ROMFile unit1, ROMFile unit2){
                /*int indexB=R.getUnitList().indexOf(unit2);
                int indexA=R.getUnitList().indexOf(unit1);
                EmblemUnit item2=R.getUnitList().set(indexB,null);
                EmblemUnit item1=R.getUnitList().set(indexA,null);
                
            R.getUnitList().set(indexA,item2);
            R.getUnitList().set(indexB,item1);*/
            }

        };
        }
        return fileList;
    }

    @Override
    public void onShown() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onKeyPressed(KeyEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    private void onItemExpanded(MaterialStandardListItem<ROMFile> item){
        MaterialIconButton button=((MaterialIconButton)item.lookup("#back-button"));
        button.setVisible(true);
        
    }
    private void onItemDiminished(MaterialStandardListItem<ROMFile> item){
        MaterialIconButton button=((MaterialIconButton)item.lookup("#back-button"));
        button.setVisible(false);
    }
    
    
    
    
    
    private double originalCardHeight=0, originalCardWidth=0;
    private double originalHeight=0, originalWidth=0;
    public void triggerAnimation(MaterialStandardListItem<ROMFile> pressedItem){
        
                
                MaterialCard theCard=((MaterialCard)pressedItem.lookup("#the_card"));
                if(originalCardHeight==0){
                    originalCardHeight=theCard.getCardHeight(); 
                }
                if(originalCardWidth==0){
                    originalCardWidth=theCard.getCardWidth();
                    
                }
                if(originalHeight==0){
                    originalHeight=pressedItem.getHeight();
                }
                if(originalWidth==0){
                    originalWidth=pressedItem.getWidth();
                }
                
                if(isExpanded){
                    condenseItem(pressedItem);
                    
                }
                else{
                    expandItem(pressedItem);
                }
    }
    private MaterialStandardListItem<ROMFile> selectedItem;
     public MaterialStandardListItem<ROMFile> getSelectedItem(){
        return selectedItem;
    }
     public void setSelectedItem(MaterialStandardListItem<ROMFile> item){
         this.selectedItem=item;
     }
    private void setRealPositions(MaterialStandardListItem<ROMFile> pressedItem){
        pressedItem.setTranslateX(0);
        pressedItem.setTranslateY(0);
        for(int i=0;i<fileList.size();i++){
                    if(!pressedItem.equals(fileList.getItemBox(i))){
                        fileList.getItemBox(i).setManaged(false);
                    }}
    }
    private void restoreItems(MaterialStandardListItem<ROMFile> pressedItem){
        onAnimationEnded(pressedItem);
        for(int i=0;i<fileList.size();i++){
                        
                        fileList.getItemBox(i).setManaged(true);
                        fileList.getItemBox(i).setVisible(true);
                    }
        
          TimerTask debounceTask=new TimerTask() {

                        @Override
                        public void run() {
         Platform.runLater(new Runnable() {
            public void run() {
               
               showAllItems(pressedItem);
               //System.out.println("I'm playing ");
            }
        });
                        }};
                            
                            
                            
                        
                new Timer().schedule(
                debounceTask, 300);
        
        
        
        
        
        
        
    
    }
    private void moveUpPressedItem(MaterialStandardListItem<ROMFile> pressedItem){
        pressedItem.setFlowLayoutOriginalXPosition(pressedItem.getLayoutX());
        pressedItem.setFlowLayoutOriginalYPosition(pressedItem.getLayoutY());
        
        Timeline timeline=new Timeline();
            
            KeyValue kv8=new KeyValue(pressedItem.translateXProperty(),-pressedItem.getLayoutX()+((Pane)pressedItem.getParent()).getWidth()/2-pressedItem.getWidth()/2);
            KeyValue kv9=new KeyValue(pressedItem.translateYProperty(),-pressedItem.getLayoutY());
                
            //System.out.println(-pressedItem.getTranslateY()+" y translate");
            KeyFrame kf=new KeyFrame(Duration.millis(250),kv8,kv9);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                setRealPositions(pressedItem);
                changeSizeAnimation(pressedItem);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            timeline.play();
    }
    private DoubleProperty expandedWidth;
    private DoubleProperty expandedWidthProperty(){
        if(expandedWidth==null){
            expandedWidth=new SimpleDoubleProperty(this,"expandedWidth");
             gRoot.widthProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    expandedWidth.set(newValue.doubleValue()*0.88);
//To change body of generated methods, choose Tools | Templates.
                }
            });
            expandedWidth.set(gRoot.widthProperty().get()*0.88);
        }
        return expandedWidth;
    }
    
    private DoubleProperty expandedHeight;
    private DoubleProperty expandedHeightProperty(){
        if(expandedHeight==null){
            
            expandedHeight=new SimpleDoubleProperty(this,"expandedHeight");
            
            gRoot.heightProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    expandedHeight.set(newValue.doubleValue()*0.88);
//To change body of generated methods, choose Tools | Templates.
                }
            });
            expandedHeight.set(gRoot.heightProperty().get()*0.88);
            
            
        }
        return expandedHeight;
    }
    private double getExpandedHeight(){
        return expandedHeightProperty().get();
    }
    private double getExpandedWidth(){
        return expandedWidthProperty().get();
    }
    private boolean isExpanded=false;
    private void expandItem(MaterialStandardListItem<ROMFile> pressedItem){
        isExpanded=true;
        setSelectedItem(pressedItem);
        fadeNonPressedItems(pressedItem);
                
                moveUpPressedItem(pressedItem);
                onItemExpanded(pressedItem);
              
    }
    private void condenseItem(MaterialStandardListItem<ROMFile> pressedItem){
        isExpanded=false;
        setSelectedItem(null);
        //this.moveToOriginalPlace(pressedItem);
        //restoreItemsPositions(pressedItem);
        reduceSizeAnimation(pressedItem);
        onItemDiminished(pressedItem);
    }
    private void reduceSizeAnimation(MaterialStandardListItem<ROMFile> pressedItem){
        MaterialCard theCard=((MaterialCard)pressedItem.lookup("#the_card"));
        
        Timeline timeline=new Timeline();
            KeyValue kv=new KeyValue(pressedItem.prefHeightProperty(),originalHeight);
            KeyValue kv2=new KeyValue(pressedItem.prefWidthProperty(),originalWidth);
             KeyValue kv3=new KeyValue(pressedItem.maxHeightProperty(),originalHeight);
            KeyValue kv4=new KeyValue(pressedItem.maxWidthProperty(),originalWidth);
            KeyValue kv5=new KeyValue(theCard.cardHeightProperty(),originalCardHeight);
            KeyValue kv6=new KeyValue(theCard.cardWidthProperty(),originalCardWidth);
                 KeyValue fadeValue=new KeyValue(theCard.opacityProperty(),0);
            KeyValue fadeValue2=new KeyValue(pressedItem.opacityProperty(),0);
            
            
            KeyFrame kf=new KeyFrame(Duration.millis(300),fadeValue,fadeValue2,kv,kv2,kv3,kv4,kv5,kv6);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                pressedItem.setVisible(false);
                pressedItem.setManaged(false);
                restoreItems(pressedItem);
                //restoreItemsPositions(pressedItem);
                //moveToOriginalPlace(pressedItem);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            timeline.play();
    }
    private void changeSizeAnimation(MaterialStandardListItem<ROMFile> pressedItem){
        MaterialCard theCard=((MaterialCard)pressedItem.lookup("#the_card"));
        
        theCard.setCardWidth(originalCardWidth);
        theCard.setCardHeight(originalCardHeight);
        pressedItem.prefHeightProperty().set(originalHeight);
        pressedItem.maxHeightProperty().set(originalHeight);
        
        pressedItem.prefWidthProperty().set(originalWidth);
        pressedItem.maxWidthProperty().set(originalWidth);
        
        Timeline timeline=new Timeline();
            KeyValue kv=new KeyValue(pressedItem.prefHeightProperty(),getExpandedHeight());
            KeyValue kv2=new KeyValue(pressedItem.prefWidthProperty(),getExpandedWidth());
             KeyValue kv3=new KeyValue(pressedItem.maxHeightProperty(),getExpandedHeight());
            KeyValue kv4=new KeyValue(pressedItem.maxWidthProperty(),getExpandedWidth());
            KeyValue kv5=new KeyValue(theCard.cardHeightProperty(),getExpandedHeight());
            KeyValue kv6=new KeyValue(theCard.cardWidthProperty(),getExpandedWidth());
                
            
            KeyFrame kf=new KeyFrame(Duration.millis(300),kv,kv2,kv3,kv4,kv5,kv6);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(isExpanded){
                                 pressedItem.removeRipple();
                                 onAnimationEnded(pressedItem);
                                 //System.out.println("amsdkasmdk");
                            }//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            timeline.play();
    }
    private void moveToOriginalPlace(MaterialStandardListItem<ROMFile> pressedItem){
         MaterialCard theCard=((MaterialCard)pressedItem.lookup("#the_card"));
       
         Timeline timeline=new Timeline();
         /*   
         KeyValue kv=new KeyValue(pressedItem.prefHeightProperty(),originalHeight);
            KeyValue kv2=new KeyValue(pressedItem.prefWidthProperty(),originalWidth);
             KeyValue kv3=new KeyValue(pressedItem.maxHeightProperty(),originalHeight);
            KeyValue kv4=new KeyValue(pressedItem.maxWidthProperty(),originalWidth);
            KeyValue kv5=new KeyValue(theCard.cardHeightProperty(),originalCardHeight);
            KeyValue kv6=new KeyValue(theCard.cardWidthProperty(),originalCardWidth);
                */
            KeyValue kv8=new KeyValue(pressedItem.translateXProperty(),pressedItem.getFlowLayoutOriginalXPosition()-pressedItem.getLayoutX());
            KeyValue kv9=new KeyValue(pressedItem.translateYProperty(),pressedItem.getFlowLayoutOriginalYPosition()-pressedItem.getLayoutY());
                
            //System.out.println(-pressedItem.getTranslateY()+" y translate");
            KeyFrame kf=new KeyFrame(Duration.millis(300),kv8,kv9);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                //setRealPositions(pressedItem);
                //changeSizeAnimation(pressedItem);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            timeline.play();
    }
    private void fadeNonPressedItems(MaterialStandardListItem<ROMFile> pressedItem){
        for(int i=0;i<fileList.size();i++){
                    if(!pressedItem.equals(fileList.getItemBox(i))){
                         Timeline timeline=new Timeline();
                          MaterialStandardListItem<ROMFile> newItem=fileList.getItemBox(i);
                MaterialCard newCard=((MaterialCard)newItem.lookup("#the_card"));
               
            KeyValue fadeValue=new KeyValue(newCard.opacityProperty(),0);
            KeyValue fadeValue2=new KeyValue(newItem.opacityProperty(),0);
            
            KeyFrame kf=new KeyFrame(Duration.millis(250),fadeValue,fadeValue2);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            newItem.setVisible(false);
                            //newItem.setManaged(false);
                        }
                    });
            timeline.play();
                    }
                }
    }
    private void showAllItems(MaterialStandardListItem<ROMFile> pressedItem){
        for(int i=0;i<fileList.size();i++){
                    
                         Timeline timeline=new Timeline();
                          MaterialStandardListItem<ROMFile> newItem=fileList.getItemBox(i);
                MaterialCard newCard=((MaterialCard)newItem.lookup("#the_card"));
               
            KeyValue fadeValue=new KeyValue(newCard.opacityProperty(),1);
            KeyValue fadeValue2=new KeyValue(newItem.opacityProperty(),1);
            
            KeyFrame kf=new KeyFrame(Duration.millis(250),fadeValue,fadeValue2);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            if(!isExpanded){
                                 pressedItem.restoreRipple();
                                 
                            }
                            //newItem.setVisible(false);
                            //newItem.setManaged(false);
                        }
                    });
            timeline.play();
                    }
                
    }
    private boolean deleted=false;
    public void onAnimationEnded(MaterialStandardListItem<ROMFile> pressedItem){
        MaterialCard card=((MaterialCard)pressedItem.lookup("#the_card"));
        if(isExpanded){
            
            ROMEditPane info=new ROMEditPane(pressedItem.getItem(),getFileList()){
                
            };
            info.setId("file_info");
            card.addComponent(info);
        }
        else{
            
            ROMEditPane info=(ROMEditPane)card.lookup("#file_info");
            if(info!=null){
                card.removeComponent(info);
                
            }
        }
    }
    
    
    
    
    
    
    
    
    
}
