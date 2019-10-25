/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.forwarder.strings.R;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.utils.BackgroundTask;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Edgar
 */
public class IconAnimation extends StackPane{
    private ROMFile file;
    private BackgroundTask task=new BackgroundTask() {
        private ArrayList<ArrayList<String>> colors;
        private ArrayList<int[][]> icons;
        private IconSequence anims;
        @Override
        public Object onAction() {
            colors=file.getAnimationPalettes();
            icons=file.getAnimationIcons();
            anims=file.getIconSequence();
            return null;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onSucceed(Object valor) {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    generateAnimation(colors,icons,anims); //To change body of generated methods, choose Tools | Templates.
                }
            });
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    public IconAnimation(ROMFile file){
        
        //slideshow.setCycleCount(SequentialTransition.INDEFINITE);
        this.file=file;
        if(file.hasAnimatedBanner()){
            this.getChildren().setAll(new ImageView(R.getPlaceholderDSImage()));
            //ArrayList<ArrayList<String>> colors=file.getAnimationPalettes();
            //ArrayList<int[][]> icons=file.getAnimationIcons();
            //IconSequence anims=file.getIconSequence();
task.play();
            //generateAnimation(colors,icons,anims);
            System.out.println("Animated Banner");
        }
        else{
            getChildren().add(file.getROMIcon());
        }
        
        
        //line.play();
        //slideshow.setCycleCount(Timeline.INDEFINITE);
       
    }
    private SequentialTransition generateAnimation(ArrayList<ArrayList<String>> colors, ArrayList<int[][]> icons, IconSequence anims){
        getChildren().setAll(file.getROMIcon());
        SequentialTransition slideshow = new SequentialTransition();
           
        for (SequenceToken anim: anims.getTokens()) {
            if(anim.getSeconds()>0){
                /*System.out.println(anim.getSeconds()+" segundos, "+anim.getIconIndex()+" icon, "+anim.getPaletteIndex()+" palette");
                 ROMIcon icon=new ROMIcon(icons.get(anim.getIconIndex()),colors.get(anim.getPaletteIndex()));
                line.getKeyFrames().add(new KeyFrame(Duration.millis(anim.getSeconds()*100), new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        //System.out.println("Animando: "+anim.getSeconds());
                            getChildren().setAll(icon);
                        //To change body of generated methods, choose Tools | Templates.
                    }
                }));*/
                
               SequentialTransition sequentialTransition = new SequentialTransition();
                
                FadeTransition fadeIn = new FadeTransition(new Duration(0));
                fadeIn.setNode(anim.getROMIcon());
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                
                
                PauseTransition stayOn = new PauseTransition(Duration.millis(anim.getSeconds()*16.6));
                

                //FadeTransition fadeOut = Transition.getFadeTransition(slide, 1.0, 0.0, 2000);
                FadeTransition fadeOut = new FadeTransition(new Duration(0));
                fadeOut.setNode(anim.getROMIcon());
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);

                //System.out.println("Icon index: "+anim.getIconIndex()+"");
                anim.getROMIcon().setOpacity(0.0);
                sequentialTransition.getChildren().addAll(fadeIn,stayOn,fadeOut);      
                
                //sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);
               getChildren().add(anim.getROMIcon());            
                slideshow.getChildren().add(sequentialTransition);
            }
        }
        slideshow.setOnFinished(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                generateAnimation(colors,icons,anims);
                
               //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        slideshow.playFromStart();
        return slideshow;
    }
}
