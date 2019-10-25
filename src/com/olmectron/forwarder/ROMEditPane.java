/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.forwarder.strings.R;
import com.olmectron.material.components.MaterialFlowList;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.TextFile;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Edgar
 */
public class ROMEditPane extends VBox{
    private MaterialFlowList<ROMFile> parentList;
    public ROMEditPane(ROMFile file, MaterialFlowList<ROMFile> parentList){
        super();
        this.parentList=parentList;
        initAll(file);
    }
    private void initAll(ROMFile unit){
        MaterialTextField tidField=new MaterialTextField("TID"){
            @Override
                            public boolean onError(String valor) {
                                if(valor.length()<4){
                                    setErrorText("4 chars, please");
                                    return true;
                                    
                                }
                                return false;
                                //To change body of generated methods, choose Tools | Templates.
                            }
        
        
        };
        tidField.setId("tid-text");
                       tidField.setLimite(4);
                       tidField.setText(unit.getCustomTID());
                       tidField.textField().textProperty().addListener(new ChangeListener<String>(){
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if(newValue.length()==4){
                                unit.setCustomTID(newValue);
                            }
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                      
                       MaterialTextField datField=new MaterialTextField(R.strings.dat_name.get()){

                            @Override
                            public boolean onError(String valor) {
                                if(valor.length()<6){
                                    setErrorText("6 chars, please");
                                    return true;
                                    
                                }
                                return false;
                                //To change body of generated methods, choose Tools | Templates.
                            }
                       
                       };
                       
                       tidField.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent event) {
                                datField.requestFocus();
datField.textField().selectAll();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       datField.setLimite(6);
                       datField.setText(unit.getDatName());
                       datField.allowDiagonal();
                       datField.textField().textProperty().addListener(new ChangeListener<String>(){
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            if(newValue.length()==6){
                                unit.setDatName(newValue);
                            }
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       
                       
                       
                       
                       
                       
                       
                       
                       
                       
                       datField.setPadding(new Insets(8,16,8,16));
                       
                       
                       
                       
                       
                       MaterialTextField gamePathField=new MaterialTextField(R.strings.game_path.get());
                       datField.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent event) {
                                gamePathField.requestFocus();
gamePathField.textField().selectAll();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       gamePathField.setLimite(255);
                       
                       gamePathField.allowDiagonal();
                       gamePathField.allowDot();
                       gamePathField.allowSpace();
                       gamePathField.allowMiddleLine();
                       gamePathField.setText(unit.getGamePath());
                       
                       gamePathField.textField().textProperty().addListener(new ChangeListener<String>(){
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                           if(newValue!=null && !newValue.trim().equals("")){
                                unit.setGamePath(newValue);
                           }
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       gamePathField.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent event) {
                                //menu.hideMenu();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });gamePathField.setPadding(new Insets(8,16,8,16));
                       
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        MaterialTextField gameTitleField=new MaterialTextField(R.strings.game_title.get());
                       gamePathField.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent event) {
                                gamePathField.requestFocus();
gameTitleField.textField().selectAll();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       gameTitleField.setLimite(12);
                       
                       gameTitleField.allowDiagonal();
                       gameTitleField.allowDot();
                       gameTitleField.allowSpace();
                       gameTitleField.allowMiddleLine();
                       gameTitleField.setText(unit.getGameTitle());
                       
                       gameTitleField.textField().textProperty().addListener(new ChangeListener<String>(){
                            @Override
                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                           if(newValue!=null && !newValue.trim().equals("")){
                              
                                unit.setGameTitle(newValue);
                           }
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       gameTitleField.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent event) {
                                //menu.hideMenu();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });gameTitleField.setPadding(new Insets(8,16,8,16));
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                       MaterialIconButton tidButton=new MaterialIconButton(MaterialIconButton.REFRESH_ICON);
                       tidButton.setColorCode(MaterialColor.material.BLUE);
                       tidButton.setOnAction(new EventHandler<ActionEvent>(){
                            @Override
                            public void handle(ActionEvent event) {
                                tidField.setText(R.getRandomTID(parentList));
                                
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                        });
                       HBox tidBox=new HBox(tidField,tidButton);
                       
                       tidBox.setPadding(new Insets(8,16,8,16));
                       tidBox.setAlignment(Pos.CENTER);
                       if(R.getCard().getRequireDAT()){
                           datField.setVisible(true);
                           datField.setManaged(true);
                       }
                       else{
                           datField.setVisible(false);
                           datField.setManaged(false);
                       }
                       
                       
                       
                        R.cardProperty().addListener(new ChangeListener<Card>(){
                   @Override
                   public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
                       if(R.getCard().getRequireDAT()){
                           datField.setVisible(true);
                           datField.setManaged(true);
                       }
                       else{
                           datField.setVisible(false);
                           datField.setManaged(false);
                       }
                       
                       //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                   }
               });
                        HBox tidDat=new HBox(tidBox,datField);
                        getChildren().addAll(tidDat,gamePathField,gameTitleField);
    }
}
