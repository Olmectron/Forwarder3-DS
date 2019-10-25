/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.forwarder.strings.R;
import com.olmectron.material.components.FlatButton;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.utils.BackgroundTask;
import com.olmectron.material.utils.HTTPReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Edgar
 */
public class Updater {
    public static void checkForForwardersUpdate(boolean showIfUpdated){
        if(showIfUpdated){
        new MaterialToast("Looking for templates updates...",MaterialToast.LENGTH_LONG).unhide();
        }
        if(Card.getExistingCards().size()==0){
            new MaterialToast("Downloading templates, please wait...",MaterialToast.LENGTH_UNDEFINED).unhide();
        }
        BackgroundTask updateTask=new BackgroundTask(){
               ArrayList<String> idList=null;
               @Override
               public Object onAction() {
                
                   HTTPReader readURL=new HTTPReader("http://olmectron.github.io/forwarders/list.txt");
                   readURL.setShowNetworkFailedToast(showIfUpdated);
                   System.out.println("Checking list.txt");
                   readURL.setNetworkFailedMessage(R.strings.connection_failed.get()); 
                   idList=readURL.readLines();
                  
                    return null;
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void onSucceed(Object valor) {
                   if(idList!=null){
                       ArrayList<File> files=Card.getForwarderFWDFiles();
                       
                       for(int i=0;i<files.size();i++){
                           System.out.println("name: "+files.get(i).getName());
                       }
                       for(int i=files.size()-1;i>=0;i--){
                           //System.err.println(idList.get(i)+" letter i");
                           for(int g=0;g<idList.size();g++){
                               
                               //System.err.println(files.get(g).getName().substring(0,files.get(g).getName().length()-4)+" letter g");
                               
                               if(files.get(i).getName().substring(0,files.get(i).getName().length()-4).equals(idList.get(g))){
                                   files.remove(i);
                                   //System.err.println("Removed: "+files.get(i).getName());
                                   //files.remove(i);
                                   break;
                               }
                           }
                       }
                       
                       for(int i=0;i<files.size();i++){
                           //File fwd=files.get(i);
                           //String name=files.get(i).toString();
                           //String id=files.get(i).getName().substring(0,files.get(i).getName().length()-4);
                           //Card.removeCardWithId(id);
                           //File nds=new File(name.substring(0,name.length()-4)+".nds");
                           //if(fwd.exists()){
                           //    fwd.delete();
                           //}
                           //if(nds.exists()){
                           //    nds.delete();
                           //}
                           
                           //System.out.println(files.get(i).getName() +" great");
                       }
                       int counter=0;
                       for(int i=0;i<idList.size();i++){
                           if(updateFilesIfNeeded(idList.get(i))){
                               counter++;
                           }
                       }
                       for(int i=0;i<idList.size();i++){
                           Card cd=Card.getCardFromId(idList.get(i));
                           if(cd==null){
                               downloadUpdatedVersion(idList.get(i));
                           }
                       }
                       System.err.println("Updating "+counter+" files");
                       if(counter>0){
                    if(counter==1){
                        
                    new MaterialToast("Updating "+counter+" forwarder").unhide();
                    }
                    else{
                    new MaterialToast("Updating "+counter+" forwarders").unhide();
                    }
                }
                       FlatButton updateButton=new FlatButton(R.strings.update.get());
                       
                       /*MaterialToast updateToast=new MaterialToast((R.strings.update_available.get() + latestVersion.trim()),updateButton,MaterialToast.LENGTH_UNDEFINED){
                           @Override
                           public void onButtonClicked(){
                               
                                   new MaterialToast(R.strings.retrieve_update.get(),MaterialToast.LENGTH_UNDEFINED).unhide();
                                   new BackgroundTask(){
                                       @Override
                                       public Object onAction() {
                                           startUpdate();
                                           return null;
                                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                       }

                                       @Override
                                       public void onSucceed(Object valor) {
                                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                       }
                                   }.play();
                                           
                               
                               
                               //System.out.println("Actualizando...");
                           }
                       
                       };
                       updateToast.unhide();
                   */
                   }
                   /*else if(latestVersion!=null && latestVersion.toLowerCase().trim().equals(R.strings.version.get())){
                       if(showIfUpdated){
                       
                           new MaterialToast(R.strings.already_updated.get()).unhide();
                           
                       
                       }
                   }*/
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }
           };
           updateTask.play();
    }
    private static boolean updateFilesIfNeeded(String id){
        BooleanProperty updating=new SimpleBooleanProperty();
        updating.set(false);
        BackgroundTask checkTask=new BackgroundTask() {
            private ArrayList<String> paramList;
            @Override
            public Object onAction() {
                
                   HTTPReader readURL=new HTTPReader("http://olmectron.github.io/forwarders/"+id+".fwd");
                   readURL.setShowNetworkFailedToast(false);
                   //System.out.println("Checking list.txt");
                   readURL.setNetworkFailedMessage(R.strings.connection_failed.get()); 
                   paramList=readURL.readLines();
                return null;//To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onSucceed(Object valor) {
                
                for(int i=0;i<paramList.size();i++){
                    String line=paramList.get(i);
                    if(line.startsWith("version=")){
                        int version=Integer.parseInt(line.replace("version=",""));
                        Card c=Card.getCardFromId(id);
                        if(c!=null && c.getVersion()<version){
                            
                            downloadUpdatedVersion(id);
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                   //new MaterialToast("Updating "+c.getName()+" template").unhide();//To change body of generated methods, choose Tools | Templates.
                                }
                            });
                            
                        }
                    }
                }
                updating.set(true);
                //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        checkTask.play();
        return updating.get();
    }
    
    private static void downloadUpdatedVersion(String id){
        try {
            BooleanProperty fwd=new SimpleBooleanProperty();
            fwd.set(false);
            BooleanProperty nds=new SimpleBooleanProperty();
            nds.set(false);
            
            fwd.addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue && nds.get()){
                        Card.removeCardWithId(id);
                        Card.addCardWithId(id);
                        
                        new MaterialToast("Done updating templates!").noSound().unhide();
                    }
//To change body of generated methods, choose Tools | Templates.
                }
            });
            nds.addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue && fwd.get()){
                        Card.removeCardWithId(id);
                        Card.addCardWithId(id);
                        new MaterialToast("Done updating templates!").noSound().unhide();
                    }
                    //To change body of generated methods, choose Tools | Templates.
                }
            });
            HTTPReader f=new HTTPReader("http://olmectron.github.io/forwarders/"+id+".fwd"){
                
                @Override
                public void onFileDownloaded() {
                    fwd.set(true);
//To change body of generated methods, choose Tools | Templates.
                }
                
            };
            HTTPReader n=new HTTPReader("http://olmectron.github.io/forwarders/"+id+".nds"){
                
                @Override
                public void onFileDownloaded() {
                    nds.set(true);
//To change body of generated methods, choose Tools | Templates.
                }
                
            };
            
            f.downloadFile(".forwarders");
            n.downloadFile(".forwarders");
            
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void checkForUpdates(boolean showIfUpdated, boolean checkTemplates){
       
      BackgroundTask updateTask=new BackgroundTask(){
               String latestVersion=null;
               @Override
               public Object onAction() {
                
                   HTTPReader readURL=new HTTPReader("http://olmectron.github.io/forwarder_update.html");
                   readURL.setShowNetworkFailedToast(showIfUpdated);
                   readURL.setNetworkFailedMessage(R.strings.connection_failed.get()); 
                   latestVersion=readURL.readFile();
                  
                    return null;
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }

               @Override
               public void onSucceed(Object valor) {
                   if(latestVersion!=null && latestVersion.trim().toLowerCase().startsWith("v") && !latestVersion.toLowerCase().trim().equals(R.strings.version.get())){
                       FlatButton updateButton=new FlatButton(R.strings.update.get());
                       
                       MaterialToast updateToast=new MaterialToast((R.strings.update_available.get() + latestVersion.trim()),updateButton,MaterialToast.LENGTH_UNDEFINED){
                           @Override
                           public void onButtonClicked(){
                               
                                   new MaterialToast(R.strings.retrieve_update.get(),MaterialToast.LENGTH_UNDEFINED).unhide();
                                   new BackgroundTask(){
                                       @Override
                                       public Object onAction() {
                                           startUpdate();
                                           return null;
                                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                       }

                                       @Override
                                       public void onSucceed(Object valor) {
                                           //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                                       }
                                   }.play();
                                           
                               
                               
                               //System.out.println("Actualizando...");
                           }
                       
                       };
                       updateToast.unhide();
                   
                   }
                   else if(latestVersion!=null && latestVersion.toLowerCase().trim().equals(R.strings.version.get())){
                       if(showIfUpdated){
                       
                           new MaterialToast(R.strings.already_updated.get()).unhide();
                           
                       
                       }
                       if(checkTemplates && R.getAutoUpdateTemplates()){
                            checkForForwardersUpdate(showIfUpdated);
                       }
                       
                   }
                   //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               }
           };
           updateTask.play();
            
      
    }
    public static void startUpdate(){
        R.getSettingsFile().setValue("show_update_notes", "true");
        try {
            File jarFile=new File("updater/AppUpdater.jar");
            if(jarFile.exists()){
                File exe=new File("../Forwarder3DS.exe");
                        if(exe.exists()){
                            
                            TextFile file=new TextFile("Forwarder3DS.cfg");
                            file.setText(file.getText().replace("Forwarder3DS.jar", "updater/AppUpdater.jar").
                                    replace("com/olmectron/forwarder/Forwarder3DS","appupdater/AppUpdater"));
                          
                            
                        try {
                            Process process = Runtime.getRuntime().exec("../Forwarder3DS.exe \"Forwarder3DS\" \"Forwarder3DS\" \"Forwarder3DS\" \"com/olmectron/forwarder/Forwarder3DS\"");
                if(process.isAlive()){
                    System.exit(0);
                }
                        } catch (IOException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                        }
                        else{
                String jarName=null;
                  try {
            File c=new File(Updater.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            jarName=(c.getName());
            jarName=jarName.substring(0,jarName.length()-4);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
                  String command="java -jar updater/AppUpdater.jar \""+"Forwarder3DS"+"\" \""+jarName+"\" \""+"Forwarder3DS"+"\" \""+"com/olmectron/forwarder/Forwarder3DS"+"\"";
                  
                  String commandTest="java -jar updater/AppUpdater.jar \""+"SocketPunch"+"\" \""+"SocketPunch"+"\" \""+"SocketPunch"+"\" \""+"socketpunch/SocketPunch"+"\"";
                  System.out.println(command);
                Process process = Runtime.getRuntime().exec(command);
          if(process.isAlive()){
                    System.exit(0);
                }
          
                        }
          
          
          
            }
            else{
                File dir=new File("updater");
                if(!dir.exists()){
                    dir.mkdir();
                }
                HTTPReader downloader=new HTTPReader("http://olmectron.github.io/AppUpdater.jar"){
                    @Override
                    public void onFileDownloaded(){
                        File exe=new File("../Forwarder3DS.exe");
                        if(exe.exists()){
                            
                            TextFile file=new TextFile("Forwarder3DS.cfg");
                            file.setText(file.getText().replace("Forwarder3DS.jar", "updater/AppUpdater.jar").
                                    replace("com/olmectron/forwarder/Forwarder3DS","appupdater/AppUpdater"));
                          
                            
                        try {
                            Process process = Runtime.getRuntime().exec("../Forwarder3DS.exe \"Forwarder3DS\" \"Forwarder3DS\" \"Forwarder3DS\" \"com/olmectron/forwarder/Forwarder3DS\"");
                if(process.isAlive()){
                    System.exit(0);
                }
                        } catch (IOException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                            
                        }
                        else{
                        try {
                            String jarName=null;
             try {
            File c=new File(Updater.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            jarName=(c.getName());
            jarName=jarName.substring(0,jarName.length()-4);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
             String command="java -jar updater/AppUpdater.jar \""+"Forwarder3DS"+"\" \""+jarName+"\" \""+"Forwarder3DS"+"\" \""+"com/olmectron/forwarder/Forwarder3DS"+"\"";
                  
                            Process process = Runtime.getRuntime().exec(command);
                if(process.isAlive()){
                    System.exit(0);
                }
                        } catch (IOException ex) {
                            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        }
                    }
                };
                downloader.downloadFile("updater");
                
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
