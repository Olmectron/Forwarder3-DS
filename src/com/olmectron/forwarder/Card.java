/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.forwarder.strings.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Edgar
 */
public class Card {
    /*public static final Card ACEKARD_RPG=
            new Card(R.strings.acekard_rpg.get(),"ACEKARD_RPG",0x61588,240,0x65400,"/com/olmectron/forwarder/files/twl-acekard.nds");
    public static final Card DSTT=
            new Card(R.strings.dstt_card.get(),"DSTT",0x22E6F,258,0x11000,"/com/olmectron/forwarder/files/twl-dstt.nds",true);
    public static final Card ORIGINAL_R4=
            new Card(R.strings.r4_card.get(),"ORIGINAL_R4",0x5B99D,240,0x5F800,"/com/olmectron/forwarder/files/twl-r4.nds");
    public static final Card DSTWO=
            new Card(R.strings.dstwo.get(),"DSTWO",0x26AFF,258,0x32000,"/com/olmectron/forwarder/files/twl-dstwo.nds");
    //public static final Card BLUE=
    
    //        new Card(R.strings.blue.get(),30,0xB65A7,251,0xC9800,"/com/olmectron/forwarder/files/twl-blue.nds");
    public static final Card ACEKARD_2i=
            new Card("Acekard 2(i)","ACEKARD_2I",0xED63B,251,0x100A00,"/com/olmectron/forwarder/files/twl-ace2i.nds");
    public static final Card R4i_GOLD=
            new Card(R.strings.r4idsn_card.get(),"R4I_GOLD",0x5BEA5,240,0x5FC00,"/com/olmectron/forwarder/files/twl-r4idsn.nds");
    public static final Card ANY=
            new Card("External template","ANY",0,0,0,null);*/
    public static ArrayList<File> getForwarderFWDFiles(){
        ArrayList<File> f=getForwardersContents();
        if(f!=null)
        for(int i=f.size()-1;i>=0;i--){
            if(!f.get(i).getName().endsWith(".fwd")){
               
                f.remove(i);
                
            }
        }
        else 
            f=new ArrayList<File>();
        return f;
    }
    public static ArrayList<File> getForwardersContents(){
        return getFolderContents(new File(".forwarders"));
    }
    public static ArrayList<File> getFolderContents(File folder){
        Path dir = folder.toPath();
        ArrayList<File> f=new ArrayList<File>();
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path file: stream) {
        
        f.add(file.toFile());
        //System.err.println(file.toFile().getName());
    }
} catch (IOException | DirectoryIteratorException x) {
    // IOException can never be thrown by the iteration.
    // In this snippet, it can only be thrown by newDirectoryStream.
    System.err.println(x);
}
if(f.size()==0){
    return null;
}
return f;
    }
    private static void readLocalCards(ObservableList<Card> list){
        Path dir = new File(".forwarders").toPath();
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
    for (Path file: stream) {
        if(file.toFile().getName().endsWith(".fwd")){
            list.add(new ConfigFile(file).getCard());
        }
        //System.err.println(file.toFile().getName());
    }
} catch (IOException | DirectoryIteratorException x) {
    // IOException can never be thrown by the iteration.
    // In this snippet, it can only be thrown by newDirectoryStream.
    System.err.println(x);
}
    }
    private IntegerProperty version;
public IntegerProperty versionProperty(){
   if(version==null){
       version=new SimpleIntegerProperty(this,"version");
   }
   return version;
}
public void setVersion(int val){
   versionProperty().set(val);
}
public int getVersion(){
   return versionProperty().get();
}
public static void addCardWithId(String id){
    Card c=new ConfigFile(new File(".forwarders/"+id+".fwd")).getCard();
    c.setSelectWhenAdded(false);
  getExistingCards().add(c);
}

private BooleanProperty selectWhenAdded;
public BooleanProperty selectWhenAddedProperty(){
   if(selectWhenAdded==null){
       selectWhenAdded=new SimpleBooleanProperty(this,"selectWhenAdded");
       selectWhenAdded.set(true);
   }
   return selectWhenAdded;
}
public void setSelectWhenAdded(boolean val){
   selectWhenAddedProperty().set(val);
}
public boolean getSelectWhenAdded(){
   return selectWhenAddedProperty().get();
}

public static void removeCardWithId(String value){
    for(int i=0;i<getExistingCards().size();i++){
        if(getExistingCards().get(i).getValue().equalsIgnoreCase(value)){
            getExistingCards().remove(i);
            break;
        }
    }
}
    private static ObservableList<Card> cards;//new Card[]{ACEKARD_RPG,DSTT,ORIGINAL_R4,R4i_GOLD,DSTWO,ACEKARD_2i,ANY};
    public static ObservableList<Card> getExistingCards(){
        if(cards==null){
            cards=FXCollections.observableArrayList();
            readLocalCards(cards);
        }
        return cards;
    }
    public static Card getCardFromId(String id){
        for(int i=0;i<getExistingCards().size();i++){
            if(getExistingCards().get(i).getValue().equals(id)){
                return getExistingCards().get(i);
            }
        }
        return null;
        
    }
   
    public Card(){
        
    }
    
    public Card(String name,String id,int gpOffset, int gpLength,int bOffset, String filePath){
        this(name,id,gpOffset,gpLength,bOffset,filePath,false);
    }
    public Card(String name, String id, int gpOffset, int gpLength, int bOffset, String filePath, boolean requireDAT){
        setName(name);
        setValue(id);
        setGamePathOffset(gpOffset);
        setGamePathLength(gpLength);
        setBannerOffset(bOffset);
        setRequireDAT(requireDAT);
        setFilePath(filePath);
    }
    
private StringProperty filePath;
public StringProperty filePathProperty(){
   if(filePath==null){
       filePath=new SimpleStringProperty(this,"filePath");
       filePath.addListener(new ChangeListener<String>(){
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
               if(newValue!=null && newValue.startsWith("file:")){
                   File f=new File(newValue.substring(5));
                   //Code by @DanTheMan827 at the GBATemp forums
                           if(f.exists() &&
                                   f.getName().substring(f.getName().length()-4).equalsIgnoreCase(".nds")){
                               
                           
                            int bannerLocation= Hex.getHexNumber(getBytes(f,0x68, 0x6B, true));
            int bannerSize = Hex.getHexNumber(getBytes(f, 0x208, 0x20B, true));
            String ndsData = "";
            
            try {
                FileInputStream fis = new FileInputStream(f);
                byte[] data = new byte[(int) f.length()];
                fis.read(data);
                fis.close();
                ndsData = new String(data);
                data = null;
            } catch (IOException ex) {
                return;
            }
            
            
            int gamePathStartOffset = ndsData.indexOf("<<<Start NDS Path");
            int gamePathEndOffset = ndsData.indexOf("End NDS Path>>>");
            
            if(gamePathEndOffset > -1){
                gamePathEndOffset += 15;
            }
            
            if(gamePathStartOffset > -1){
                System.out.printf(" GamePath Start: 0x%X\n", gamePathStartOffset);
                setGamePathOffset(gamePathStartOffset);
            } else {
                System.out.printf(" GamePath Start: NOT FOUND\n");
            }
            
            if(gamePathEndOffset > -1){
                System.out.printf("GamePath Length: %d\n", (gamePathEndOffset - gamePathStartOffset));
                setGamePathLength(gamePathEndOffset-gamePathStartOffset);
            } else {
                System.out.printf("GamePath Length: 0\n");
            }
            
            System.out.println("Banner Location: 0x"+Integer.toHexString(bannerLocation).toUpperCase());
                setBannerOffset(bannerLocation);
            System.out.println("    Banner Size: 0x"+Integer.toHexString(bannerSize).toUpperCase());
                           
               }
               }
               else if(newValue!=null && newValue.startsWith("config:")){
                   
                   
               
               }

           }
       });
   }
   return filePath;
}
public void setFilePath(String val){
   
   filePathProperty().set(val);
}
public String getFilePath(){
   return filePathProperty().get();
}

private BooleanProperty requireDAT;
public BooleanProperty requireDATProperty(){
   if(requireDAT==null){
       requireDAT=new SimpleBooleanProperty(this,"requireDAT");
   }
   return requireDAT;
}
public void setRequireDAT(boolean val){
   requireDATProperty().set(val);
}
public boolean getRequireDAT(){
   return requireDATProperty().get();
}

    private StringProperty name;
public StringProperty nameProperty(){
   if(name==null){
       name=new SimpleStringProperty(this,"name");
   }
   return name;
}
public void setName(String val){
   nameProperty().set(val);
}
public String getName(){
   return nameProperty().get();
}

private StringProperty value;
public StringProperty valueProperty(){
   if(value==null){
       value=new SimpleStringProperty(this,"value");
   }
   return value;
}
public void setValue(String val){
   valueProperty().set(val);
}
public String getValue(){
   return valueProperty().get();
}

private IntegerProperty gamePathOffset;
public IntegerProperty gamePathOffsetProperty(){
   if(gamePathOffset==null){
       gamePathOffset=new SimpleIntegerProperty(this,"gamePathOffset");
   }
   return gamePathOffset;
}
public void setGamePathOffset(int val){
   gamePathOffsetProperty().set(val);
}
public int getGamePathOffset(){
   return gamePathOffsetProperty().get();
}

private IntegerProperty gamePathLength;
public IntegerProperty gamePathLengthProperty(){
   if(gamePathLength==null){
       gamePathLength=new SimpleIntegerProperty(this,"gamePathLength");
   }
   return gamePathLength;
}
public void setGamePathLength(int val){
   gamePathLengthProperty().set(val);
}
public int getGamePathLength(){
   return gamePathLengthProperty().get();
}

private IntegerProperty bannerOffset;
public IntegerProperty bannerOffsetProperty(){
   if(bannerOffset==null){
       bannerOffset=new SimpleIntegerProperty(this,"bannerOffset");
   }
   return bannerOffset;
}
public void setBannerOffset(int val){
   bannerOffsetProperty().set(val);
}
public int getBannerOffset(){
   return bannerOffsetProperty().get();
}
//Method by @DanTheMan827 at the GBATemp forums.
private byte[] getBytes(File file, int start, int end, boolean reverse) {
        RandomAccessFile raf = null;
        byte[] header = new byte[end + 1 - start];
        try {
            raf = new RandomAccessFile(file, "r");
            raf.seek(start);
            for (int i = 0; i < header.length; ++i) {
                if(reverse == true){
                    header[(header.length-1) - i] = raf.readByte();
                } else {
                    header[i] = raf.readByte();
                }
            }
            return header;
        }
        catch (IOException ex) {
            return null;
        }
    }
    

}
