/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Edgar
 */
public class SequenceToken {
    private String bits;
    private ROMIcon file;
    public SequenceToken(String hex, ROMFile f){
        bits=Integer.toBinaryString(Integer.parseInt(hex,16));
        while(bits.length()<16){
            bits="0"+bits;
        }
        setFlipVertically(bits.substring(0,1).equals("1"));
        setFlipHorizontally(bits.substring(1,2).equals("1"));
        setPaletteIndex(Integer.parseInt(bits.substring(2,5),2));
        setIconIndex(Integer.parseInt(bits.substring(5,8),2));
        setSeconds(Integer.parseInt(bits.substring(8,16),2));
        
        file=new ROMIcon(f.getAnimationIcons().get(getIconIndex()),f.getAnimationPalettes().get(getPaletteIndex()));
        if(getFlipVertically()){
            file.flipHorizontally();
            
        }
        if(getFlipHorizontally()){
            file.flipVertically();
        }
        //System.out.println("Bit: "+bits);
    }
    public ROMIcon getROMIcon(){
        return file;
    }
    private BooleanProperty flipVertically;
public BooleanProperty flipVerticallyProperty(){
   if(flipVertically==null){
       flipVertically=new SimpleBooleanProperty(this,"flipVertically");
   }
   return flipVertically;
}
public void setFlipVertically(boolean val){
   flipVerticallyProperty().set(val);
}
public boolean getFlipVertically(){
   return flipVerticallyProperty().get();
}

private BooleanProperty flipHorizontally;
public BooleanProperty flipHorizontallyProperty(){
   if(flipHorizontally==null){
       flipHorizontally=new SimpleBooleanProperty(this,"flipHorizontally");
   }
   return flipHorizontally;
}
public void setFlipHorizontally(boolean val){
   flipHorizontallyProperty().set(val);
}
public boolean getFlipHorizontally(){
   return flipHorizontallyProperty().get();
}
private IntegerProperty paletteIndex;
public IntegerProperty paletteIndexProperty(){
   if(paletteIndex==null){
       paletteIndex=new SimpleIntegerProperty(this,"paletteIndex");
   }
   return paletteIndex;
}
public void setPaletteIndex(int val){
   paletteIndexProperty().set(val);
}
public int getPaletteIndex(){
   return paletteIndexProperty().get();
}

private IntegerProperty iconIndex;
public IntegerProperty iconIndexProperty(){
   if(iconIndex==null){
       iconIndex=new SimpleIntegerProperty(this,"iconIndex");
   }
   return iconIndex;
}
public void setIconIndex(int val){
   iconIndexProperty().set(val);
}
public int getIconIndex(){
   return iconIndexProperty().get();
}

private IntegerProperty seconds;
public IntegerProperty secondsProperty(){
   if(seconds==null){
       seconds=new SimpleIntegerProperty(this,"seconds");
   }
   return seconds;
}
public void setSeconds(int val){
   secondsProperty().set(val);
}
public int getSeconds(){
   return secondsProperty().get();
}

}
