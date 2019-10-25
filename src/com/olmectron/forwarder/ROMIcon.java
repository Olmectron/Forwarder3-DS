/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.material.MaterialDesign;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Edgar
 */
public class ROMIcon extends ImageView {
    
    public ROMIcon(ROMFile file){
        super();
        paintAll(file.getIconData(),file.getPaletteColors());
    }
    public ROMIcon(int[][] values,ArrayList<String> palette){
        super();
        
        paintAll(values, palette);
    }
    public void flipVertically(){
        this.setRotationAxis(Rotate.Y_AXIS);
        this.setRotate(180);
    }
    public void flipHorizontally(){
        this.setRotationAxis(Rotate.X_AXIS);
        this.setRotate(180);
    }
    private void paintAll(int[][] values, ArrayList<String> palette){
        WritableImage image=new WritableImage(32,32){};//=new WritableImage();
       
                for(int i=0;i<32;i++){
                   for(int j=0;j<32;j++){
                       
                       //image.getPixelWriter().setArgb(i, j, base.getPixelReader().getArgb(i, j));
                       //if(hair.getPixelReader().getArgb(i, j)){
                       
                             
                       //int hairARGB=hair.getPixelReader().getArgb(i,j);
                         if(values[i][j]==0){
                             
                             
                             image.getPixelWriter().setColor(i, j,Color.TRANSPARENT);
                         }
                         else{
                             image.getPixelWriter().setColor(i, j,Color.web(palette.get(values[i][j])));
                         }
                          //System.out.println(Integer.toBinaryString(i));
                          
                         
                         }  
                          
                              //image.getPixelWriter().setArgb(i,j,hairARGBimage.getPixelReader().getArgb(i, j));
                          
                       //}
                   }
                
                this.setImage(image);
                
                this.setFitHeight(64);
                this.setFitWidth(64);
                
    }
}
