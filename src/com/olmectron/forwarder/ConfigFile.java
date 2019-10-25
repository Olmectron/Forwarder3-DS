/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.material.files.FieldsFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar
 */
public class ConfigFile {
    private Card card;
    private void setCard(Card c){
        card=c;
    }
    public Card getCard(){
        return card;
    }
    public ConfigFile(Path path){
        this(path.toFile());
    }
    public static void writeNewConfigFile(Card card){
        try {
            FieldsFile f=new FieldsFile(".forwarders/"+card.getValue()+".fwd");
            f.setValue("name",card.getName());
            f.setValue("gamepath_location","0x"+Integer.toHexString(card.getGamePathOffset()).toUpperCase());
            f.setValue("gamepath_length",card.getGamePathLength()+"");
            f.setValue("banner_location","0x"+Integer.toHexString(card.getBannerOffset()).toUpperCase());
            f.setValue("generate_dat","false");
            f.setValue("version","1");
            Card.getExistingCards().add(new ConfigFile(new File(".forwarders/"+card.getValue()+".fwd")).getCard());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ConfigFile(File file){
        try {
            FieldsFile f=new FieldsFile(file.getAbsolutePath());
            String cardName=f.getValue("name",null);
            String cardId=file.getName().substring(0,file.getName().length()-4);
            String gamePathOffset=f.getValue("gamepath_location",null);
            String gamePathLength=f.getValue("gamepath_length",null);
            String bannerOffset=f.getValue("banner_location",null);
            String requireDAT=f.getValue("generate_dat","false");
            String version=f.getValue("version","1");
            //String fileName=f.getValue("file_name",null);
            if(cardName==null ||
                    cardId==null ||
                    gamePathOffset==null ||
                    gamePathLength==null ||
                    bannerOffset==null ||
                    requireDAT==null
                    ){
                System.err.println("Bad config file: "+file.getAbsolutePath());
            }
            else{
            try{
                Card c=new Card(cardName,cardId,Integer.decode(gamePathOffset),Integer.parseInt(gamePathLength)
                        ,Integer.decode(bannerOffset),"config:.forwarders/"+cardId+".nds",requireDAT.equalsIgnoreCase("true"));
                c.setVersion(Integer.parseInt(version));
                setCard(c);
            }
            catch(Exception ex){
                
                System.err.println(ex.getMessage());
            }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
