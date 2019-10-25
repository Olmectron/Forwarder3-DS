/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder.strings;

import com.olmectron.forwarder.Card;
import com.olmectron.forwarder.Hex;
import com.olmectron.forwarder.ROMFile;
import com.olmectron.material.components.MaterialFlowList;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.utils.LanguageReader;
import com.olmectron.material.utils.XMLString;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Edgar
 */
public class R {
    private static Image dsImage;
    public static Image getPlaceholderDSImage(){
        if(dsImage==null){
            dsImage=new Image("/com/olmectron/forwarder/images/ds_placeholder.png");
        
        }
        return dsImage;
    }
    public static String getRandomTID(MaterialFlowList<ROMFile> list){
        Random random=new Random();
                                String tid=R.getTidList().get(random.nextInt(R.getTidList().size()));
                                while(R.wasTidUsed(tid,list)){
                                    tid=R.getTidList().get(random.nextInt(R.getTidList().size()));
                                    
                                }
                                
                                
                                return tid;
    }
    public static void readConfigDir(){
        File dir=new File(".forwarders");
        if(!dir.exists()){
            
        
        try { 
            Path path = Paths.get(dir.toURI());
//< input target path
Files.createDirectory(path);
Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
        } catch (IOException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
        else{
            //System.err.println("Forwarders directory already exists");
            
            
        }
        
    }
    public static ArrayList<String> tidList;
    
    public static ArrayList<String> getTidList(){
        if(tidList==null){
            tidList=new ArrayList<String>();
            InputStream stream=Hex.getTidStream();
            String tokenized=Hex.convertStreamToString(stream);
            StringTokenizer c=new StringTokenizer(tokenized,",");
            while(c.hasMoreTokens()){
                tidList.add(c.nextToken());
            }
        }
        return tidList;
    }
    private static BooleanProperty keepDS;
public static BooleanProperty keepDSProperty(){
   if(keepDS==null){
       keepDS=new SimpleBooleanProperty(R.class,"keepDS");
   }
   return keepDS;
}
public static void setKeepDS(boolean val){
   keepDSProperty().set(val);
}
public static boolean getKeepDS(){
   return keepDSProperty().get();
}

    private static FieldsFile settingsFile;
    public static FieldsFile getSettingsFile(){
        if(settingsFile==null){
            try {
                settingsFile=new FieldsFile("settings");
            } catch (FileNotFoundException ex) {
                System.err.println("Error loading settings file");
                //Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return settingsFile;
    }
    
    
private static ObjectProperty<Card> card;
public static ObjectProperty<Card> cardProperty(){
   if(card==null){
       card=new SimpleObjectProperty<Card>(R.class,"card");
   }
   return card;
}
public static void setCard(Card val){
   cardProperty().set(val);
}
public static Card getCard(){
   return cardProperty().get();
}

  /*  private static IntegerProperty cardType;
public static IntegerProperty cardTypeProperty(){
   if(cardType==null){
       cardType=new SimpleIntegerProperty(R.class,"cardType");
   }
   return cardType;
}
public static void setCardType(int val){
   cardTypeProperty().set(val);
}
public static int getCardType(){
   return cardTypeProperty().get();
   
}*/


private static BooleanProperty ROMSon3DS;
public static BooleanProperty ROMSon3DSProperty(){
   if(ROMSon3DS==null){
       ROMSon3DS=new SimpleBooleanProperty(R.class,"ROMSon3DS");
   }
   return ROMSon3DS;
}
public static void setROMSon3DS(boolean val){
   ROMSon3DSProperty().set(val);
}
public static boolean getROMSon3DS(){
   return ROMSon3DSProperty().get();
}

private static BooleanProperty ROMSonFlashCard;
public static BooleanProperty ROMSonFlashCardProperty(){
   if(ROMSonFlashCard==null){
       ROMSonFlashCard=new SimpleBooleanProperty(R.class,"ROMSonFlashCard");
   }
   return ROMSonFlashCard;
}
public static void setROMSonFlashCard(boolean val){
   ROMSonFlashCardProperty().set(val);
}
public static boolean getROMSonFlashCard(){
   return ROMSonFlashCardProperty().get();
}




   private static BooleanProperty filesFromCard;
public static BooleanProperty filesFromCardProperty(){
   if(filesFromCard==null){
       filesFromCard=new SimpleBooleanProperty(R.class,"filesFromCard");
   }
   return filesFromCard;
}
public static void setFilesFromCard(boolean val){
   filesFromCardProperty().set(val);
}
public static boolean getFilesFromCard(){
   return filesFromCardProperty().get();
}
private static BooleanProperty autoCorrectTID;
public static BooleanProperty autoCorrectTIDProperty(){
   if(autoCorrectTID==null){
       autoCorrectTID=new SimpleBooleanProperty(R.class,"autoCorrectTID");
   }
   return autoCorrectTID;
}
public static void setAutoCorrectTID(boolean val){
   autoCorrectTIDProperty().set(val);
}
public static Boolean getAutoCorrectTID(){
   return autoCorrectTIDProperty().get();
}


private static TextFile usedTidsFile;
        public static TextFile getUsedTidsFile(){
            if(usedTidsFile==null){
                usedTidsFile=new TextFile("used_tids");
            }
            return usedTidsFile;
        }
        public static boolean wasTidUsed(String tid, MaterialFlowList<ROMFile> list){
            for(int i=0;i<list.getItemCount();i++){
                if(list.getItem(i).getCustomTID().equalsIgnoreCase(tid)){
                    return true;
                }
            }
            
            StringTokenizer t=new StringTokenizer(getUsedTidsFile().getText(),System.getProperty("line.separator"));
            while(t.hasMoreTokens()){
                String token=t.nextToken().trim();
                if(tid.equals(token)){
                    return true;
                }
            }
            return false;
        }

    private static BooleanProperty autoUpdateTemplates;
public static BooleanProperty autoUpdateTemplatesProperty(){
   if(autoUpdateTemplates==null){
       autoUpdateTemplates=new SimpleBooleanProperty(R.class,"autoUpdateTemplates");
   }
   return autoUpdateTemplates;
}
public static void setAutoUpdateTemplates(boolean val){
   autoUpdateTemplatesProperty().set(val);
}
public static Boolean getAutoUpdateTemplates(){
   return autoUpdateTemplatesProperty().get();
}
    public static class strings{
        
        public static StringProperty title=LanguageReader.getProperty("title");
        public static StringProperty open_files=LanguageReader.getProperty("open_files");
        public static StringProperty nds_files=LanguageReader.getProperty("nds_files");
        public static StringProperty change_ds=LanguageReader.getProperty("change_ds");
        public static StringProperty remove=LanguageReader.getProperty("remove");
        public static StringProperty dat_name=LanguageReader.getProperty("dat_name");
        public static StringProperty game_path=LanguageReader.getProperty("game_path");
        public static StringProperty success_process=LanguageReader.getProperty("success_process");
        public static StringProperty ds_normal=LanguageReader.getProperty("ds_normal");
        public static StringProperty dsi_enhanced=LanguageReader.getProperty("dsi_enhanced");
        
        public static StringProperty version=LanguageReader.getProperty("version");
        public static StringProperty r4_card=LanguageReader.getProperty("r4_card");
        public static StringProperty dstt_card=LanguageReader.getProperty("dstt_card");
        public static StringProperty keep_ds=LanguageReader.getProperty("keep_ds");
       public static StringProperty keep_ds_detail=LanguageReader.getProperty("keep_ds_detail");
        public static StringProperty connection_failed=LanguageReader.getProperty("connection_failed");
        public static StringProperty update=LanguageReader.getProperty("update");
        
        public static StringProperty update_available=LanguageReader.getProperty("update_available");
        public static StringProperty retrieve_update=LanguageReader.getProperty("retrieve_update");
        public static StringProperty already_updated=LanguageReader.getProperty("already_updated");
        public static StringProperty check_update=LanguageReader.getProperty("check_update");
         public static StringProperty game_title=LanguageReader.getProperty("game_title");
        public static StringProperty shuffle_tid=LanguageReader.getProperty("shuffle_tid");
       public static StringProperty r4idsn_card=LanguageReader.getProperty("r4idsn_card");
        public static StringProperty acekard_rpg=LanguageReader.getProperty("acekard_rpg");
        public static StringProperty dstwo=LanguageReader.getProperty("dstwo");
        public static StringProperty blue=LanguageReader.getProperty("blue");
        
       
       
         
       
        
        
        
    }
}
