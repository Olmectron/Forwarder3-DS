/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import static com.olmectron.forwarder.Hex.NTR;
import static com.olmectron.forwarder.Hex.TWL;
import com.olmectron.forwarder.strings.R;
import com.olmectron.material.MaterialDesign;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.Times;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.utils.MDataView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.FileChooser;
import org.utilities.Dates;

/**
 *
 * @author Edgar
 */
public class ROMFile {
    private File file;
    public File getFile(){
        return file;
    }
    private ArrayList<String> colores=null;
    public ArrayList<ArrayList<String>> getAnimationPalettes(){
        ArrayList<ArrayList<String>> n=new ArrayList<ArrayList<String>>();
        int start=0;
        for(int i=start;i<0x100;i+=0x20){
            n.add(getPaletteFrom(start));
        }
        return n;
        
    }
    public ArrayList<int[][]> getAnimationIcons(){
        ArrayList<int[][]> n=new  ArrayList<int[][]>();
        int start=0;
        for(int i=start;i<0x1000;i+=0x200){
            n.add(getIconDataFrom(i));
        }
        return n;
    }
    private ArrayList<String> getPaletteFrom(int start){
        
        
        ArrayList<String> colors=new ArrayList<String>();
        byte[] bites=null;
            if(getImportedBanner()==null){
                bites=getBytes(getAnimationPalettesLocation()+start,getAnimationPalettesLocation()+start+0x20);
            
            }
            else{
                bites=Arrays.copyOfRange(getImportedBanner(), 0x2240+start,0x2240+start+0x20);
            }
            
        for(int i=0;i<32;i+=2){
            byte[] reversed=Hex.reverse(Arrays.copyOfRange(bites, i, i+2));
            
            //String normalColor=Hex.getHexString(Arrays.copyOfRange(bites,i,i+2));
            String normalColor=Hex.getHexString(reversed);
            String bits=Integer.toBinaryString(Integer.parseInt(normalColor,16));
            while(bits.length()<16){
                bits='0'+bits;
            }
            int r=Integer.parseInt(bits.substring(11,16),2);
            int g=Integer.parseInt(bits.substring(6,11),2);
            int b=Integer.parseInt(bits.substring(1,6),2);
            
            
            //5 bit RGB = 12,3,21

int R = (r * 255) / 31;

int G = (g * 255) / 31;
int B = (b * 255) / 31;
String color=Hex.getHexPair(R)+Hex.getHexPair(G)+Hex.getHexPair(B);

            //byte[] reversed=Hex.reverse(Arrays.copyOfRange(bites, i, i+2));
            
            //byte[] RGB=MDataView.getBytes((int)MaterialColor.rgb16_to_rgb32(MDataView.getShort16(reversed)));
            
            
            //String color=Hex.getHexString(RGB).substring(0,6);
            colors.add("#"+color);
            //System.out.println("Color: #"+color);
        }
        
        return colors;
    
    }
    public ArrayList<String> getPaletteColors(){
        if(colores==null){
        colores=new ArrayList<String>();
        byte[] bites=getPalleteBytes();
        for(int i=0;i<32;i+=2){
            byte[] reversed=Hex.reverse(Arrays.copyOfRange(bites, i, i+2));
            
            //String normalColor=Hex.getHexString(Arrays.copyOfRange(bites,i,i+2));
            String normalColor=Hex.getHexString(reversed);
            String bits=Integer.toBinaryString(Integer.parseInt(normalColor,16));
            while(bits.length()<16){
                bits='0'+bits;
            }
            int r=Integer.parseInt(bits.substring(11,16),2);
            int g=Integer.parseInt(bits.substring(6,11),2);
            int b=Integer.parseInt(bits.substring(1,6),2);
            
            
            //5 bit RGB = 12,3,21

int R = (r * 255) / 31;

int G = (g * 255) / 31;
int B = (b * 255) / 31;
String color=Hex.getHexPair(R)+Hex.getHexPair(G)+Hex.getHexPair(B);

            //byte[] reversed=Hex.reverse(Arrays.copyOfRange(bites, i, i+2));
            
            //byte[] RGB=MDataView.getBytes((int)MaterialColor.rgb16_to_rgb32(MDataView.getShort16(reversed)));
            
            
            //String color=Hex.getHexString(RGB).substring(0,6);
            colores.add("#"+color);
            //System.out.println("Color: #"+color);
        }
        }
        return colores;
    }
    public ArrayList<String> readImportedPaletteColors(){
        
        colores=new ArrayList<String>();
        byte[] bites=Arrays.copyOfRange(getImportedBanner(), 0x220, 0x240);
        for(int i=0;i<32;i+=2){
            byte[] reversed=Hex.reverse(Arrays.copyOfRange(bites, i, i+2));
            
            //String normalColor=Hex.getHexString(Arrays.copyOfRange(bites,i,i+2));
            String normalColor=Hex.getHexString(reversed);
            String bits=Integer.toBinaryString(Integer.parseInt(normalColor,16));
            while(bits.length()<16){
                bits='0'+bits;
            }
            int r=Integer.parseInt(bits.substring(11,16),2);
            int g=Integer.parseInt(bits.substring(6,11),2);
            int b=Integer.parseInt(bits.substring(1,6),2);
            
            
            //5 bit RGB = 12,3,21

int R = (r * 255) / 31;

int G = (g * 255) / 31;
int B = (b * 255) / 31;
String color=Hex.getHexPair(R)+Hex.getHexPair(G)+Hex.getHexPair(B);

            //byte[] reversed=Hex.reverse(Arrays.copyOfRange(bites, i, i+2));
            
            //byte[] RGB=MDataView.getBytes((int)MaterialColor.rgb16_to_rgb32(MDataView.getShort16(reversed)));
            
            
            //String color=Hex.getHexString(RGB).substring(0,6);
            colores.add("#"+color);
            //System.out.println("Color: #"+color);
        
        }
        return colores;
    }
    public boolean hasAnimatedBanner(){
        byte[] data=getBytes(getBannerLocation(),getBannerLocation()+0x01);
        
        
        return (data[0]==0x3 && data[1]==0x1);
    }
    public int[][] getIconDataFrom(int start){
        int[][] vals=new int[32][32];
            byte[] bytes=null;
            if(getImportedBanner()==null){
                bytes=getBytes(getIconAnimDataLocation()+start,getIconAnimDataLocation()+start+0x200);
            
            }
            else{
                bytes=Arrays.copyOfRange(getImportedBanner(), 0x1240+start, 0x1240+start+0x200);
            }
            
            
            int counter=0;
            int vCounter=0;
            int[] v=new int[1024];
            for(int j=0;j<16;j++){
                
                for(int i=0;i<32;i++){
                    
                    int val=bytes[counter];
                    if(val<0){
                        val=val & 0xff;
                    }
                    String bits=Integer.toBinaryString(val);
                    while(bits.length()<8){
                        bits="0"+bits;
                    }
                    int val1=Integer.parseInt(bits.substring(0,4),2);
                    int val2=Integer.parseInt(bits.substring(4,8),2);
                    v[vCounter]=val2;
                    v[vCounter+1]=val1;
                    vCounter+=2;
                    counter++;
                }
            }
            int contador=0;
            for(int tile=0;tile<16;tile++){
                for(int i=0;i<64;i++){
                    
                    int posI=((i)%8)+((tile%4)*8);
                    int posJ=getPosJ(tile,i);
                    
                    vals[posI][posJ]=v[contador];
                    //if(posI==30){
                    //System.out.println("valores["+posI+","+posJ+"]="+v[contador]);
                    //}
                    contador++;
                    //System.out.println(counter+"");
                    
                    
                }
                
            }
            
        return vals;
    }
    
    private int[][] valores=null;
        public int[][] readImportedIconData(){
        
        valores=new int[32][32];
            byte[] bytes=Arrays.copyOfRange(getImportedBanner(), 0x20, 0x220);
            
            int counter=0;
            int vCounter=0;
            int[] v=new int[1024];
            for(int j=0;j<16;j++){
                
                for(int i=0;i<32;i++){
                    
                    int val=bytes[counter];
                    if(val<0){
                        val=val & 0xff;
                    }
                    String bits=Integer.toBinaryString(val);
                    while(bits.length()<8){
                        bits="0"+bits;
                    }
                    int val1=Integer.parseInt(bits.substring(0,4),2);
                    int val2=Integer.parseInt(bits.substring(4,8),2);
                    v[vCounter]=val2;
                    v[vCounter+1]=val1;
                    vCounter+=2;
                    counter++;
                }
            }
            int contador=0;
            for(int tile=0;tile<16;tile++){
                for(int i=0;i<64;i++){
                    
                    int posI=((i)%8)+((tile%4)*8);
                    int posJ=getPosJ(tile,i);
                    
                    valores[posI][posJ]=v[contador];
                    if(posI==30){
                    //System.out.println("valores["+posI+","+posJ+"]="+v[contador]);
                    }
                    contador++;
                    //System.out.println(counter+"");
                    
                    
                }
                
            }
            
        
        
        
        return valores;
    }

    public int[][] getIconData(){
        if(valores==null){
        valores=new int[32][32];
            byte[] bytes=getIconBytes();
            int counter=0;
            int vCounter=0;
            int[] v=new int[1024];
            for(int j=0;j<16;j++){
                
                for(int i=0;i<32;i++){
                    
                    int val=bytes[counter];
                    if(val<0){
                        val=val & 0xff;
                    }
                    String bits=Integer.toBinaryString(val);
                    while(bits.length()<8){
                        bits="0"+bits;
                    }
                    int val1=Integer.parseInt(bits.substring(0,4),2);
                    int val2=Integer.parseInt(bits.substring(4,8),2);
                    v[vCounter]=val2;
                    v[vCounter+1]=val1;
                    vCounter+=2;
                    counter++;
                }
            }
            int contador=0;
            for(int tile=0;tile<16;tile++){
                for(int i=0;i<64;i++){
                    
                    int posI=((i)%8)+((tile%4)*8);
                    int posJ=getPosJ(tile,i);
                    
                    valores[posI][posJ]=v[contador];
                    if(posI==30){
                    //System.out.println("valores["+posI+","+posJ+"]="+v[contador]);
                    }
                    contador++;
                    //System.out.println(counter+"");
                    
                    
                }
                
            }
            
        
        
        }
        return valores;
    }
    private int getPosJ(int tile, int i){
        int posJ=((int)i/8);
        if(tile<4){
            posJ+=0;
        }
        else if(tile<8){
            posJ+=8;
        }
        else if(tile<12){
            posJ+=16;
        }
        else if(tile<16){
            posJ+=24;
        }
        return posJ;
    }
    public IconSequence getIconSequence(){
        return new IconSequence(Hex.getHexString(getBytes(getBannerLocation()+0x2340,getBannerLocation()+0x2340+0x7F)),this);
        
    }
    private ROMIcon romIcon=null;
    public ROMIcon getROMIcon(){
        
        if(romIcon==null){
            romIcon=new ROMIcon(this.getIconData(),this.getPaletteColors());
        }
        return romIcon;
    }public ROMIcon readROMIcon(){
        romIcon=new ROMIcon(this.getIconData(),this.getPaletteColors());
        return romIcon;
    }
    public void readPalette(){
        if(romIcon==null){
            romIcon=new ROMIcon(this.getIconData(),this.getPaletteColors());
        }
        
        
        //ArrayList<String> colores=getPaletteColors();
        
        
        //System.out.println("Animation: "+;
        //System.out.println("Pallete: "+Hex.getHexString(getPalleteBytes()));
    }
    public byte[] getTID(){
        return getBytes(0x0C,0x0F);
    }
    public byte[] getJAPName(){
        return getLengthBytes(getJAPNameLocation(),0x100);
        
    }
    public byte[] getENGName(){
        return getLengthBytes(getENGNameLocation(),0x100);
        
    }
    public String getEnglishName(){
        return Hex.getWordFromPairBytes(getENGName());
    }
    
    public String getFileName(){
        return file.getName();
    }
    public String getDisplayName(){
        return this.getEnglishName();
        //return file.getName();
    }
    public int getCardMode(){
        byte b=getBytes(0x12,0x12)[0];
        if(b==0){
            return Hex.NTR;
        }
        else if(b==2){
            return Hex.TWL;
        }
        return 0;
    }
    public byte[] getIconBytes(){
         
        
        return getBytes(getIconDataLocation(),getIconDataLocation()+0x200);
    }
    public byte[] getPalleteBytes(){
        
       
        
        return getBytes(getPalleteLocation(),getPalleteLocation()+0x20);
        
    }
    public int getIconAnimDataLocation(){
        return getBannerLocation()+0x1240;
    }
    public int getIconDataLocation(){
        return getBannerLocation()+0x20;
    }
    public int getAnimationPalettesLocation(){
        return getBannerLocation()+0x2240;
    }
    public int getPalleteLocation(){
        return getBannerLocation()+0x220;
    }
    public int getJAPNameLocation(){
        return getBannerLocation()+0x240;
    }
    public int getENGNameLocation(){
        return getBannerLocation()+0x340;
    }
    
    public int getBannerLocation(){
        return Hex.getHexNumber(Hex.reverse(getBytes(0x68,0x68+0x03)));
    }
    public String getStringTID(){
        return Hex.getWordFromBytes(getTID());
    }
    private StringProperty gameTitle;
public StringProperty gameTitleProperty(){
   if(gameTitle==null){
       gameTitle=new SimpleStringProperty(this,"gameTitle");
   }
   return gameTitle;
}
public void setGameTitle(String val){
   gameTitleProperty().set(val);
}
public String getGameTitle(){
   return gameTitleProperty().get();
}

    public byte[] getBannerIconBytes(){
        int location=getBannerLocation();
        
        switch(getMode()){
            case NTR:
                return getLengthBytes(location,0x840);
            case TWL:
                return getLengthBytes(location,0x23C0);
        }
        return null;
        
    }
    public byte[] copyROMHeaderToTemplate( int templateMode){
        byte[] romHeader=getBytes(0x0, 0x11);
        switch(templateMode){
            case NTR:
                byte[] ntrBytes=Arrays.copyOf(Hex.readTWLFile(), Hex.readTWLFile().length);
                
                for(int i=0;i<romHeader.length;i++){
                    ntrBytes[i]=romHeader[i];
                    
                }
                return ntrBytes;
                //break;
                
            case TWL:
                byte[] twlBytes=Arrays.copyOf(Hex.readTWLFile(), Hex.readTWLFile().length);
                
                for(int i=0;i<romHeader.length;i++){
                    twlBytes[i]=romHeader[i];
                    
                }
                return twlBytes;
                
                
            default:
                return null;
        }
    }
    
    private byte[] writeDATName(){
        byte DAT[]=Hex.getBytesFromWord(getDatName());
        int counter=0;
        int start=0xD1A0;
        while(counter<6){
            templateBytes[start]=DAT[counter];
            counter++;
            start++;
        }
        return templateBytes;
    }
    public void processIniFile(String folder){
        
        String lines[] = getDisplayName().split("\\r?\\n");
        ArrayList<String> toFile=new ArrayList<String>();
        toFile.add("[FLASHCARD-ROM]");
        toFile.add("NDS_PATH = "+this.getGamePath());
        int i=1;
        if(lines!=null)
        for(String linea:lines){
            toFile.add("BNR_TEXT"+i+" = "+linea.trim());
            i++;
        }
        String t="";
        for(String l:toFile){
            t=t+l.trim()+System.getProperty("line.separator");
            
        }
        String fileName=this.getFileName().substring(0,this.getFileName().length()-4);
        if(R.getROMSonFlashCard()){
            
        TextFile textFile=new TextFile(folder+"/"+fileName+".ini");
        Util.saveToFile(folder+"/"+fileName+".ini.png", this.getROMIcon().getImage());
        
        textFile.setText(t);
        }
        else if(R.getROMSon3DS()){
            Util.saveToFile(folder+"/"+fileName+".nds.png", this.getROMIcon().getImage());
        
        }
    }
    public void process(String dateFolder){
        templateBytes=copyROMHeaderToTemplate(getMode());
        templateBytes=writeGameTitle();
        if(getCustomTID()==null){
            templateBytes=writeReverseTID();
        
        }
        else{
            templateBytes=writeCustomTID();
        }
        
        templateBytes=writeBanner(R.getCard());
        
        
        
        if(R.getCard().getRequireDAT()){
            templateBytes=writeDATName();
            
            generateDAT(dateFolder);
        }
        else{
            templateBytes=writeGamePath(R.getCard().getGamePathOffset(),R.getCard().getGamePathLength());
        }
        
        /*if(R.getCardType()==CardType.DSTT_R4i_SHDC){
            templateBytes=writeDATName();
            generateDAT(dateFolder);
        
        }
        else if(R.getCardType()==CardType.R4_ORIGINAL){
            templateBytes=writeR4GamePath();
        }
        else if(R.getCardType()==CardType.R4IDSN){
            templateBytes=writeR4IDSNGamePath();
        }
        else if(R.getCardType()==CardType.ACEKARD_RPG){
            templateBytes=writeGamePath(0x61588,240);
        }*/
        generateCIA(dateFolder);
        
    }
    private StringProperty gamePath;
public StringProperty gamePathProperty(){
   if(gamePath==null){
       gamePath=new SimpleStringProperty(this,"gamePath");
   }
   return gamePath;
}
public void setGamePath(String val){
   gamePathProperty().set(val);
}
public String getGamePath(){
   return gamePathProperty().get();
}
private byte[] writeGamePath(int offset,int length){
         
                 
                 
                 //byte[] datBytes=Arrays.copyOf(Hex.readABPathSetterFile(),Hex.readABPathSetterFile().length);
             byte[] gamePath=Hex.getBytesFromWord(getGamePath());
             int counter=0;    
             int i=offset;
             while(counter<length){
                 
                 
                 //if(i>=0x22DE7 && i<=0x22EE8){
                 try{
                     templateBytes[i]=gamePath[counter];
                     //System.out.println("No Zeroes!");
                 }
                 catch(ArrayIndexOutOfBoundsException ex){
                     templateBytes[i]=0;
                     //System.err.println("Zeroes!");
                 }
                 counter++;
                 i++;
                 //}
                 
            
             }   
             return templateBytes;
    }
private byte[] writeR4GamePath(){
         
                 
                 
                 //byte[] datBytes=Arrays.copyOf(Hex.readABPathSetterFile(),Hex.readABPathSetterFile().length);
             byte[] gamePath=Hex.getBytesFromWord(getGamePath());
             int counter=0;    
             int i=0x5B99D;
             while(counter<240){
                 
                 
                 //if(i>=0x22DE7 && i<=0x22EE8){
                 try{
                     templateBytes[i]=gamePath[counter];
                     //System.out.println("No Zeroes!");
                 }
                 catch(ArrayIndexOutOfBoundsException ex){
                     templateBytes[i]=0;
                     //System.err.println("Zeroes!");
                 }
                 counter++;
                 i++;
                 //}
                 
            
             }   
             return templateBytes;
    }
    private byte[] writeR4IDSNGamePath(){
         
                 
                 
                 //byte[] datBytes=Arrays.copyOf(Hex.readABPathSetterFile(),Hex.readABPathSetterFile().length);
             byte[] gamePath=Hex.getBytesFromWord(getGamePath());
             int counter=0;    
             int i=0x5BEA5;
             while(counter<240){
                 
                 
                 //if(i>=0x22DE7 && i<=0x22EE8){
                 try{
                     templateBytes[i]=gamePath[counter];
                     //System.out.println("No Zeroes!");
                 }
                 catch(ArrayIndexOutOfBoundsException ex){
                     templateBytes[i]=0;
                     //System.err.println("Zeroes!");
                 }
                 counter++;
                 i++;
                 //}
                 
            
             }   
             return templateBytes;
    }
    private void generateDAT(String folder){
        FileOutputStream fos=null;
        try {
            
            
            String datName=getDatName().toUpperCase();
            if(datName.contains("/")){
                datName=datName.substring(2, 6);
            }   File tempFile = new File(folder+"/"+datName.replace("/", "")+".DAT");
            
            fos = new FileOutputStream(tempFile);
             byte[] datBytes=Hex.readABPathSetterFile();
             byte[] gamePath=Hex.getBytesFromWord(getGamePath());
             int counter=0;    
             
             for(int i=R.getCard().getGamePathOffset();i<R.getCard().getGamePathOffset()+R.getCard().getGamePathLength();i++){
                 
                 
                 //if(i>=0x22DE7 && i<=0x22EE8){
                 try{
                     datBytes[i]=gamePath[counter];
                     //System.out.println("No Zeroes!");
                 }
                 catch(ArrayIndexOutOfBoundsException ex){
                     datBytes[i]=0;
                     //System.err.println("Zeroes!");
                 }
                 counter++;
                 //}
                 
            
             }   
            fos.write(datBytes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    private void calculateHeaderCRC16(byte[] bytes){
        byte[] data=Arrays.copyOfRange(bytes, 0x0, 0x15E);
        int crc=Hex.getCRC16(data);
        byte[] crcBytes=Hex.reverse(Hex.getHexBytes(crc));
        bytes[0x15E]=crcBytes[0];
        bytes[0x15F]=crcBytes[1];
        
    }
    private void generateCIA(String folder){
        try {
            String datName=getDatName().toUpperCase();
            if(datName.contains("/")){
                datName=datName.substring(2, 6);
            }  
            String fileName=folder+"/forwarder-"+getFileName().substring(0, getFileName().length()-4)+".nds";
            String ciaName=folder+"/forwarder-"+getFileName().substring(0, getFileName().length()-4)+".cia";
            calculateHeaderCRC16(templateBytes);
            
            File tempFile = new File(fileName);
            
            
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(templateBytes);
            fos.close();
            System.out.println(tempFile.getAbsolutePath());
            
            //String ndsTool = ("ndstool -f \""+f+"\"");
            //String makeCia=("make_cia --srl=\""+f+"\"");
            //System.out.println("NDS TOOL: "+ndsTool);
            //System.out.println("MAKE CIA: "+makeCia);
            //String batch="build_cia.bat";
           
            
            //System.out.println(executeCommand(ndsTool));
            //String output=(executeCommand(batch,folder+"/"+tempFile.getName()));
            String output=(executeCommand("./make_cia","--srl="+folder+"/"+tempFile.getName()));
            
            System.out.println(output);
            if(!R.getKeepDS() && output.contains("generated Successfully") && new File(ciaName).exists()){
                tempFile.delete();
                //tempFile.delete();
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private String executeCommand(String command, String param) {

		StringBuffer output = new StringBuffer();
ProcessBuilder pb;
		Process p;
		try {
                    pb= new ProcessBuilder(command, param);
			p = pb.start(); //Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
    private byte[] getBannerExportBytes(){
        byte banner[]=getBannerIconBytes();
        byte result[]=new byte[0x23C0];
//int cardType=R.getCardType();
        int counter=0;
        
        
        //while(counter<banner.length){
        if(banner.length==0x840)
        {
            int startInit=0;
             while(counter<0x23C0){
                try{
                result[startInit]=banner[counter];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    result[startInit]=0;
                }
                counter++;
                startInit++;
            }
             startInit=0x840;
            byte[] japaneseName=Arrays.copyOfRange(banner, 0x240, 0x240+0x100);
           for(int i=0;i<japaneseName.length;i++){
               try{
                result[startInit]=japaneseName[i];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    result[startInit]=0;
                }
               startInit++;
           }
           for(int i=0;i<japaneseName.length;i++){
               try{
                result[startInit]=japaneseName[i];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    result[startInit]=0;
                }
               startInit++;
           }
           
            byte[] startByte=new byte[]{0x03,0x01};
            result[0]=startByte[0];
            result[1]=startByte[1];
            int tileStart=0x1240;
            int bannerCounter=0;
            while(bannerCounter<8){
                for (int i=0x20;i<0x220;i++){
                    result[tileStart]=banner[i];
                    tileStart++;
                }
                bannerCounter++;
            }
            int palleteStart=0x2240;
            int paletteCounter=0;
            while(paletteCounter<8){
            for (int i=0x220;i<0x240;i++){
                result[palleteStart]=banner[i];
                palleteStart++;
            }
            paletteCounter++;
            }
            byte[] endByte=new byte[]{0x01,0x00,0x00,0x01};
            result[0x2340]=endByte[0];
            result[0x2341]=endByte[1];
            result[0x2342]=endByte[2];
            result[0x2343]=endByte[3];
            
            
            byte[] firstCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(result, 0x20, 0x83F+0x01)));
            //byte[] ra=Arrays.copyOfRange(templateBytes,0x20,0x83F+0x01);
            /*System.out.println(ra[ra.length-1]+" rarara");
            System.out.println(Arrays.copyOfRange(templateBytes, 0x20, 0x93F+0x01).length+" bytes copy example");
            byte[] arregloPrueba=Arrays.copyOfRange(templateBytes, 0x20, 0x93F+0x01);
            for(int i=0;i<arregloPrueba.length;i++){
                int ar=arregloPrueba[i];
                if(ar<0){
                    ar=ar & 0xff;
                }
                System.out.println ("prueba "+Hex.getHexPair(ar));
            }*/
            byte[] secondCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(result, 0x20, 0x93F+0x01)));
            byte[] thirdCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(result, 0x20, 0xA3F+0x01)));
            byte[] fourthCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(result, 0x1240, 0x23BF+0x01)));
            System.out.println(Hex.getCRC16(Arrays.copyOfRange(result, 0x1240, 0x23BF))+" CRC");
            //System.arraycopy(firstCRC, 0, result, start+0x02, 2);
            System.arraycopy(secondCRC, 0, result, 0x04, 2);
            System.arraycopy(thirdCRC, 0, result, 0x06, 2);
            System.arraycopy(fourthCRC, 0, result, 0x08, 2);
            
        }
        else{
            int start=0;
            while(counter<0x23C0){
                try{
                result[start]=banner[counter];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    result[start]=0;
                }
                counter++;
                start++;
            }
        }
        return result;
    }
    public void importBannerFile(){
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Import Banner");
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Banner file","*.bin"));
        File f=chooser.showOpenDialog(MaterialDesign.primary);
        if(f!=null){
            try {
                setImportedBanner(Files.readAllBytes(f.toPath()));
                new MaterialToast("Banner imported successfully!").unhide();
            } catch (IOException ex) {
                Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void exportBannerFile(){
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Save Banner");
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        String fileName=this.getFileName();
        if(fileName.substring(fileName.length()-4,fileName.length()).equalsIgnoreCase(".nds")){
            fileName=fileName.substring(0,fileName.length()-4);
        }
        chooser.setInitialFileName("banner-"+fileName);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Banner file","*.bin"));
        File saved=chooser.showSaveDialog(MaterialDesign.primary);
        if(saved!=null){
              FileOutputStream fooStream = null;
            try {
                //if(saved.exists()){
                fooStream = new FileOutputStream(saved, false); // true to append
                // false to overwrite.
                //byte[] myBytes = data;
                fooStream.write(getBannerExportBytes());
                fooStream.close();
                new MaterialToast("Banner exported successfully!").unhide();
                
                
                /*else{
                try {
                saved.createNewFile();
                //System.out.println(Hex.getHexString(getBannerExportBytes()));
                Files.write(saved.toPath(), getBannerExportBytes(), StandardOpenOption.WRITE);
                new MaterialToast("File exported successfully!").unhide();
                } catch (IOException ex) {
                Logger.getLogger(ROMIcon.class.getName()).log(Level.SEVERE, null, ex);
                }
                }*/
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fooStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ROMFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private ObjectProperty<byte[]> importedBanner=null;
    public ObjectProperty<byte[]> importedBannerProperty(){
        if(importedBanner==null){
            importedBanner=new SimpleObjectProperty<byte[]>(this,"importedBanner");
            importedBanner.set(null);
            importedBanner.addListener(new ChangeListener<byte[]>(){
                @Override
                public void changed(ObservableValue<? extends byte[]> observable, byte[] oldValue, byte[] newValue) {
                    if(newValue!=null){
                        int start=0;
        /*if(cardType==CardType.DSTT_R4i_SHDC){
            start=0x11000;
        }
        else if(cardType==CardType.R4_ORIGINAL){
            start= 0x5F800;
        }
        else if(cardType==CardType.R4IDSN){
            start= 0x5FC00;
        }
        else if(cardType==CardType.ACEKARD_RPG){
            start=0x65400;
        }
        */
        //start=R.getCard().getBannerOffset();
        //byte banner[]=getBannerIconBytes();
        int counter=0;
        byte templateBytes[]=new byte[0x23C0];
        byte banner[]=newValue;
        //while(counter<banner.length){
        if(banner.length<0x23C0)
        {
            int startInit=start;
             while(counter<0x23C0){
                try{
                templateBytes[startInit]=banner[counter];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[startInit]=0;
                }
                counter++;
                startInit++;
            }
             startInit=start+0x840;
            byte[] japaneseName=Arrays.copyOfRange(banner, 0x240, 0x240+0x100);
           for(int i=0;i<japaneseName.length;i++){
               try{
                templateBytes[startInit]=japaneseName[i];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[startInit]=0;
                }
               startInit++;
           }
           for(int i=0;i<japaneseName.length;i++){
               try{
                templateBytes[startInit]=japaneseName[i];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[startInit]=0;
                }
               startInit++;
           }
           
            byte[] startByte=new byte[]{0x03,0x01};
            templateBytes[start]=startByte[0];
            templateBytes[start+1]=startByte[1];
            int tileStart=start+0x1240;
            int bannerCounter=0;
            while(bannerCounter<8){
                for (int i=0x20;i<0x220;i++){
                    templateBytes[tileStart]=banner[i];
                    tileStart++;
                }
                bannerCounter++;
            }
            int palleteStart=start+0x2240;
            int paletteCounter=0;
            while(paletteCounter<8){
            for (int i=0x220;i<0x240;i++){
                templateBytes[palleteStart]=banner[i];
                palleteStart++;
            }
            paletteCounter++;
            }
            byte[] endByte=new byte[]{0x01,0x00,0x00,0x01};
            templateBytes[start+0x2340]=endByte[0];
            templateBytes[start+0x2341]=endByte[1];
            templateBytes[start+0x2342]=endByte[2];
            templateBytes[start+0x2343]=endByte[3];
            
            
            byte[] firstCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x20, start+0x83F+0x01)));
            //byte[] ra=Arrays.copyOfRange(templateBytes,0x20,0x83F+0x01);
            /*System.out.println(ra[ra.length-1]+" rarara");
            System.out.println(Arrays.copyOfRange(templateBytes, 0x20, 0x93F+0x01).length+" bytes copy example");
            byte[] arregloPrueba=Arrays.copyOfRange(templateBytes, 0x20, 0x93F+0x01);
            for(int i=0;i<arregloPrueba.length;i++){
                int ar=arregloPrueba[i];
                if(ar<0){
                    ar=ar & 0xff;
                }
                System.out.println ("prueba "+Hex.getHexPair(ar));
            }*/
            byte[] secondCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x20, start+0x93F+0x01)));
            byte[] thirdCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x20, start+0xA3F+0x01)));
            byte[] fourthCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x1240, start+0x23BF+0x01)));
            System.out.println(Hex.getCRC16(Arrays.copyOfRange(templateBytes, 0x1240, 0x23BF))+" CRC");
            System.arraycopy(firstCRC, 0, templateBytes, start+0x02, 2);
            System.arraycopy(secondCRC, 0, templateBytes, start+0x04, 2);
            System.arraycopy(thirdCRC, 0, templateBytes, start+0x06, 2);
            System.arraycopy(fourthCRC, 0, templateBytes, start+0x08, 2);
            setImportedBanner(templateBytes);
        }
        }
                    //To change body of generated methods, choose Tools | Templates.
                }
            });
            
        }
        return importedBanner;
    }
    public byte[] getImportedBanner(){
        return importedBannerProperty().get();
    }
    public void setImportedBanner(byte[] imported){
        importedBannerProperty().set(imported);
    }
    private byte[] writeBanner(Card card){
        int start=0;
        /*if(cardType==CardType.DSTT_R4i_SHDC){
            start=0x11000;
        }
        else if(cardType==CardType.R4_ORIGINAL){
            start= 0x5F800;
        }
        else if(cardType==CardType.R4IDSN){
            start= 0x5FC00;
        }
        else if(cardType==CardType.ACEKARD_RPG){
            start=0x65400;
        }
        */
        start=card.getBannerOffset();
        if(getImportedBanner()==null){
        byte banner[]=getBannerIconBytes();
        int counter=0;
        
        
        //while(counter<banner.length){
        if(banner.length==0x840)
        {
            int startInit=start;
             while(counter<0x23C0){
                try{
                templateBytes[startInit]=banner[counter];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[startInit]=0;
                }
                counter++;
                startInit++;
            }
             startInit=start+0x840;
            byte[] japaneseName=Arrays.copyOfRange(banner, 0x240, 0x240+0x100);
           for(int i=0;i<japaneseName.length;i++){
               try{
                templateBytes[startInit]=japaneseName[i];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[startInit]=0;
                }
               startInit++;
           }
           for(int i=0;i<japaneseName.length;i++){
               try{
                templateBytes[startInit]=japaneseName[i];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[startInit]=0;
                }
               startInit++;
           }
           
            byte[] startByte=new byte[]{0x03,0x01};
            templateBytes[start]=startByte[0];
            templateBytes[start+1]=startByte[1];
            int tileStart=start+0x1240;
            int bannerCounter=0;
            while(bannerCounter<8){
                for (int i=0x20;i<0x220;i++){
                    templateBytes[tileStart]=banner[i];
                    tileStart++;
                }
                bannerCounter++;
            }
            int palleteStart=start+0x2240;
            int paletteCounter=0;
            while(paletteCounter<8){
            for (int i=0x220;i<0x240;i++){
                templateBytes[palleteStart]=banner[i];
                palleteStart++;
            }
            paletteCounter++;
            }
            byte[] endByte=new byte[]{0x01,0x00,0x00,0x01};
            templateBytes[start+0x2340]=endByte[0];
            templateBytes[start+0x2341]=endByte[1];
            templateBytes[start+0x2342]=endByte[2];
            templateBytes[start+0x2343]=endByte[3];
            
            
            byte[] firstCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x20, start+0x83F+0x01)));
            //byte[] ra=Arrays.copyOfRange(templateBytes,0x20,0x83F+0x01);
            /*System.out.println(ra[ra.length-1]+" rarara");
            System.out.println(Arrays.copyOfRange(templateBytes, 0x20, 0x93F+0x01).length+" bytes copy example");
            byte[] arregloPrueba=Arrays.copyOfRange(templateBytes, 0x20, 0x93F+0x01);
            for(int i=0;i<arregloPrueba.length;i++){
                int ar=arregloPrueba[i];
                if(ar<0){
                    ar=ar & 0xff;
                }
                System.out.println ("prueba "+Hex.getHexPair(ar));
            }*/
            byte[] secondCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x20, start+0x93F+0x01)));
            byte[] thirdCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x20, start+0xA3F+0x01)));
            byte[] fourthCRC=DataView.getCRCFlippedPairBytes(Hex.getCRC16(Arrays.copyOfRange(templateBytes, start+0x1240, start+0x23BF+0x01)));
            System.out.println(Hex.getCRC16(Arrays.copyOfRange(templateBytes, 0x1240, 0x23BF))+" CRC");
            //System.arraycopy(firstCRC, 0, templateBytes, start+0x02, 2);
            System.arraycopy(secondCRC, 0, templateBytes, start+0x04, 2);
            System.arraycopy(thirdCRC, 0, templateBytes, start+0x06, 2);
            System.arraycopy(fourthCRC, 0, templateBytes, start+0x08, 2);
            
        }
        else{
            while(counter<0x23C0){
                try{
                templateBytes[start]=banner[counter];
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[start]=0;
                }
                counter++;
                start++;
            }
        }
        
        
        }
        else{
            int counter=0;
            
             while(counter<0x23C0){
                try{
                templateBytes[start]=getImportedBanner()[counter];
                System.out.println("Byte "+counter+": "+getImportedBanner()[counter]);
                }
                catch(ArrayIndexOutOfBoundsException ex){
                    templateBytes[start]=0;
                }
                counter++;
                start++;
            }
        }
        return templateBytes;
    }
    private byte[] writeGameTitle(){
        
        byte gameTitle[]=new byte[12];
        for(int i=0;i<gameTitle.length;i++){
            gameTitle[i]=0;
        }
        
        byte title[]=Hex.getBytesFromWord(getGameTitle());
        for(int i=0;i<title.length;i++){
            gameTitle[i]=title[i];
        }
        int c=0;
        for(int i=0x0;i<0xC;i+=0x1){
            templateBytes[i]=gameTitle[c];
            c++;
        }
        return templateBytes;
    }
    private byte[] writeCustomTID(){
        
        
        byte normalTID[]=Hex.getBytesFromWord(getCustomTID());
        int c=0;
        for(int i=0xC;i<(0xC+0x4);i+=0x1){
            templateBytes[i]=normalTID[c];
            c++;
        }
        
        
        byte reverseTID[]=Hex.reverse(normalTID);
        int counter=0;
        for(int i=0x230;i<(0x230+0x4);i+=0x1){
            templateBytes[i]=reverseTID[counter];
            counter++;
        }
        return templateBytes;
    }
    private byte[] writeReverseTID(){
        byte reverseTID[]=Hex.reverse(getTID());
        int counter=0;
        for(int i=0x230;i<(0x230+0x4);i+=0x1){
            templateBytes[i]=reverseTID[counter];
            counter++;
        }
        return templateBytes;
    }
    public byte[] getLengthBytes(int start, int length){
        RandomAccessFile raf = null;
        byte[] header=new byte[length];
            try {
                raf = new RandomAccessFile(getFile(), "r");
                raf.seek(start);
                for(int i=0;i<header.length;i++){
                    header[i]=raf.readByte();
                    //if(part<0){
                    //    part= part & 0xff;
                    //}
                    //String hexPart=Integer.toHexString(part);
                    //if(hexPart.length()==1){
                    //    hexPart="0"+hexPart;
                    //}
                    
                }
                return header;
              
            }
            catch(IOException ex){
                return null;
            }
    }
    public String parseGameTitle(){
        return Hex.getWordFromBytes(getBytes(0x00,0x0B));
        
    }
    public byte[] getBytes(int start, int end){
        RandomAccessFile raf = null;
        byte[] header=new byte[end+0x1-start];
            try {
                raf = new RandomAccessFile(getFile(), "r");
                raf.seek(start);
                for(int i=0;i<header.length;i++){
                    header[i]=raf.readByte();
                    //if(part<0){
                    //    part= part & 0xff;
                    //}
                    //String hexPart=Integer.toHexString(part);
                    //if(hexPart.length()==1){
                    //    hexPart="0"+hexPart;
                    //}
                    
                }
                return header;
              
            }
            catch(IOException ex){
                return null;
            }
    }
    private byte[] templateBytes;
    public ROMFile(File file, int mode){
        this.file=file;
        parseDatName(file);
        setCustomTID(getStringTID());
       setMode(getCardMode());
       setGameTitle(parseGameTitle());
       System.out.println(getGameTitle().length()+" length");
       readPalette();
    }
    
    private void parseDatName(File file){
        String nombre=file.getName().toUpperCase().replace(".NDS", "");
        String six="";
        for(int i=0;i<nombre.length();i++){
        char c=nombre.charAt(i);
        if((c >= 'A' && c <= 'Z') || (c > '0' && c <= '9')){
//if(Character.isLetter(nombre.charAt(i)) || Character.isLetter(nombre.charAt(i))){
                
                six+=c;
            }
        }
        while(six.length()<6){
            six+="0";
        }
        
        setDatName(six.substring(0,6));
        if(R.getFilesFromCard()){
            if(file.getAbsolutePath().substring(1,2).equals(":")){
                setGamePath(file.getAbsolutePath().substring(3).replace("\\", "/"));
            }
            else{
                setGamePath(file.getAbsolutePath().substring(1).replace("\\", "/"));
            }
            
        }
        else{
            setGamePath("Games/"+file.getName());
        
        }
        
    }
    public String getModeDescription(){
        switch(getMode()){
            case Hex.NTR:
                return R.strings.ds_normal.get();
            case Hex.TWL:
                return R.strings.dsi_enhanced.get();
                
        }
        return null;
    }
    private IntegerProperty mode;
    public IntegerProperty modeProperty(){
       if(mode==null){
           mode=new SimpleIntegerProperty(this,"mode");
       }
       return mode;
    }
    public void setMode(int val){
       modeProperty().set(val);
    }
    public int getMode(){
       return modeProperty().get();
    }
private StringProperty customTID;
public StringProperty customTIDProperty(){
   if(customTID==null){
       customTID=new SimpleStringProperty(this,"customTID");
       customTID.set(null);
   }
   return customTID;
}
public boolean hasValidTID(){
    return (R.getTidList().contains(getCustomTID()));
}
public void setCustomTID(String val){
   customTIDProperty().set(val);
}
public String getCustomTID(){
   return customTIDProperty().get();
}
private StringProperty datName;
public StringProperty datNameProperty(){
   if(datName==null){
       datName=new SimpleStringProperty(this,"datName");
   }
   return datName;
}
public void setDatName(String val){
   
   datNameProperty().set(val);
}
public String getDatName(){
   return datNameProperty().get();
}

}
