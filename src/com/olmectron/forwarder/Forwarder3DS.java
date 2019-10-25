/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import com.olmectron.forwarder.strings.R;
import com.olmectron.material.MaterialDesign;
import com.olmectron.material.components.FlatButton;
import com.olmectron.material.components.MaterialCard;
import com.olmectron.material.components.MaterialCheckBox;
import com.olmectron.material.components.MaterialConfirmDialog;
import com.olmectron.material.components.MaterialDisplayText;
import com.olmectron.material.components.MaterialDropdownMenu;
import com.olmectron.material.components.MaterialDropdownMenuItem;
import com.olmectron.material.components.MaterialFlowList;
import com.olmectron.material.components.MaterialIconButton;
import com.olmectron.material.components.MaterialLabel;
import com.olmectron.material.components.MaterialRadioButton;
import com.olmectron.material.components.MaterialSelector;
import com.olmectron.material.components.MaterialStandardListItem;
import com.olmectron.material.components.MaterialTabs;
import com.olmectron.material.components.MaterialTextField;
import com.olmectron.material.components.MaterialToast;
import com.olmectron.material.components.MaterialTooltip;
import com.olmectron.material.constants.MaterialColor;
import com.olmectron.material.files.FieldsFile;
import com.olmectron.material.files.TextFile;
import com.olmectron.material.layouts.MaterialEditableLayout;
import com.olmectron.material.utils.LanguageRegion;

import java.awt.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.utilities.OsCheck;

/**
 *
 * @author Edgar
 */
public class Forwarder3DS extends Application {
    private StackPane root;
    public static int FORWARDERS=99;
    public static int TWLOADER=199;
    public static int BUILD_MODE=0;
    public static void setBuildMode(int bMode){
        BUILD_MODE=bMode;
    }
    public static int getBuildMode(){
        return BUILD_MODE;
    }
    private EventHandler<ActionEvent> openHandler=new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event) {
            openFiles();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    private void openFiles(){
        FileChooser chooser=new FileChooser();
        chooser.setTitle(R.strings.open_files.get());
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(R.strings.nds_files.get()+" (*.nds)", "*.nds");
            chooser.getExtensionFilters().add(extFilter);
        List<File> opened=chooser.showOpenMultipleDialog(MaterialDesign.primary);
        if(opened!=null){
            for (File file : opened) {
                getFilePane().addROMFile(new ROMFile(file,Hex.NTR));
            }
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        /*String text="4348524F4E4F5F542020200041424B4A4B580000020000000000000000000004004000005004000250040002B8F50500003606000000380200003802481D0000405C0600090000004C5C06000000000000000000000000000000000000000000FF7F7F00FF1F3F200054060048791E0500000000000000000000000000000000405C0600004000000000000000000000000000000000000000000000000000005352414D5F56313130000000504153533031960000000000000000000000000024FFAE51699AA2213D84820A84E409AD11248B98C0817F21A352BE199309CE2010464A4AF82731EC58C7E83382E3CEBF85F4DF94CE4B09C194568AC01372A7FC9F844D73A3CA9A615897A327FC039876231DC7610304AE56BF38840040A70EFDFF52FE036F9530F197FBC08560D68025A963BE03014E38E2F9A234FFBB3E0344780090CB88113A9465C07C6387F03CAFD625E48B380AAC7221D4F80756CF";
        byte[] data=new byte[text.length()/2];
        int counter=0;
        for(int i=0;i<text.length();i+=2){
            
            int numero=Integer.decode("0x"+text.substring(i,i+2));
            data[counter]=(byte)numero;
            counter++;
        }
        int crc=Hex.getCRC16(data);
        byte[] crcBytes=Hex.getHexBytes(crc);
        System.out.println(Integer.toHexString(crcBytes[1])+Integer.toHexString(crcBytes[0]));*/
        R.readConfigDir();
        //System.out.println(System.getProperty("os.name"));
    
    
       /*int[] bytes=new int[]{
           0xF9,0xFF,0x11,0x11,0x99,0x1F,0x1F,0x11,0x99,0xF9,0xF1,0x11,0x99,0x19,0xFF,0xFF,0x99,0x63,0x66,0x66,0x59,0x66,0x66,0x66,0x39,0x66,0x66,0x66,0x65,0x66,0x66,0x66,0x21,0x22,0x22,0x12,0x11,0x22,0x22,0x22,0x11,0x21,0x22,0x22,0x11,0x11,0x22,0x22,0x1F,0x11,0x22,0x22,0xF6,0x11,0x21,0x22,0x66,0x1F,0x21,0x22,0x66,0xF6,0x11,0x22,0x43,0x44,0x44,0x34,0x12,0x44,0x44,0x44,0x22,0x41,0x44,0x44,0x22,0x12,0x44,0x44,0x22,0x22,0x41,0x44,0x22,0x22,0x12,0x44,0x22,0x22,0x22,0x43,0x22,0x22,0x22,0x31,0x23,0x22,0x99,0x99,0x34,0x22,0x92,0x99,0x44,0x23,0x52,0x99,0x44,0x44,0x22,0x99,0x44,0x45,0x24,0x95,0x54,0xEE,0x24,0x92,0x44,0xE5,0x35,0x52,0x44,0x54,0x45,0x22,0x63,0x66,0x66,0x66,0x66,0x66,0x66,0x66,0x66,0x66,0x76,0x77,0x66,0x76,0x77,0x77,0x66,0x77,0x77,0x77,0x67,0x66,0x77,0x77,0xEE,0x65,0x66,0x76,0xE5,0xBE,0x22,0x61,0x66,0x66,0x1F,0x22,0x66,0x66,0x1F,0x21,0x77,0x77,0xF6,0x21,0x77,0x77,0xF7,0x21,0x77,0x77,0x67,0x1F,0x77,0x77,0x77,0x1F,0x77,0x77,0x67,0xF6,0x76,0x77,0x76,0x36,0x22,0x22,0x22,0x12,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x22,0x21,0x22,0x22,0x22,0x11,0x22,0x22,0x22,0x1F,0x21,0x22,0x22,0x44,0x44,0x44,0x23,0x43,0x44,0x44,0x13,0x41,0x44,0x44,0x14,0x32,0x44,0x44,0x74,0x12,0x44,0x44,0x74,0x12,0x76,0x37,0x33,0x62,0x98,0x99,0x99,0x86,0x88,0x98,0x99,0x55,0x2B,0x22,0x22,0x55,0x11,0x21,0x22,0x45,0x11,0x11,0xE1,0x15,0x11,0x11,0x51,0x15,0x11,0x11,0x21,0x14,0x11,0x11,0x22,0x1F,0x11,0x11,0x12,0x1F,0x11,0x11,0x42,0x61,0x66,0x67,0xFC,0x22,0x61,0x67,0xFD,0x25,0x22,0xC6,0xCD,0x25,0x12,0xC6,0xCD,0x31,0x53,0xBE,0xDD,0xBC,0x5E,0xEE,0xDD,0xE5,0xEE,0xEB,0xDE,0xEE,0xEE,0xEE,0xBE,0xFC,0x11,0x22,0x12,0x6E,0x1F,0x21,0x12,0xDE,0xFD,0x11,0x12,0x6E,0x77,0x1F,0x11,0xEC,0x76,0xF7,0x11,0xCB,0x6C,0x66,0x1F,0xBB,0xEB,0xDE,0xF4,0xBD,0xBB,0xBB,0xCD,0x87,0x88,0x88,0xE8,0x71,0x88,0x88,0x88,0x71,0x88,0x88,0x88,0x11,0x87,0x88,0x78,0x11,0x87,0x88,0x56,0x11,0x71,0x68,0x91,0x11,0x11,0x17,0x95,0x1F,0x11,0x12,0x99,0x1F,0x11,0x11,0x42,0xFC,0x1F,0x11,0x42,0xAA,0xFC,0x1F,0x41,0xCA,0xAA,0xFA,0xFF,0xAC,0xAA,0xCC,0x6C,0xAA,0xCC,0xCC,0xFF,0xCA,0xCC,0xFC,0xCC,0xCC,0xCC,0xFC,0x1F,0xEE,0xEE,0xBE,0xEE,0xE5,0xEE,0x5E,0xEE,0x55,0xEE,0x45,0xE5,0x54,0x45,0x54,0x55,0x66,0x33,0x55,0xC5,0x6F,0x66,0xFF,0xC3,0xCC,0xFC,0x66,0xF6,0x11,0xCC,0xAC,0xAA,0xBD,0xBB,0xBB,0xBB,0xDE,0xBB,0xBB,0xBB,0xCE,0xBB,0xBB,0xBB,0xC5,0xBB,0xBB,0xBB,0xBC,0xCB,0xDD,0xCD,0xBB,0xBB,0xBB,0xCC,0xDC,0xBB,0xCC,0x9D,0xFC,0xCF,0x9D,0x99,0xFC,0x21,0x91,0x99,0xFD,0x1F,0x99,0x99,0xCB,0xFD,0x99,0x99,0xCD,0x54,0x99,0x99,0xDF,0x99,0x99,0x99,0x9D,0x99,0x99,0x99,0x99,0x99,0x99,0x99,0x99,0x99,0x99,0x99,0xAA,0x2E,0x57,0x18,0x9F,0x28,0xF2,0x38,0x35,0x49,0x5C,0x6A,0x8A,0x2C,0x0E,0x41,0x0D,0x52,0xB5,0x77,0x9F,0x32,0xFF,0x5A,0x56,0x21,0x7C,0x46,0xFF,0x7F,0x4D,0x10,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4D,0x00,0x45,0x00,0x47,0x00,0x41,0x00,0x20,0x00,0x4D,0x00,0x41,0x00,0x4E,0x00,0x20,0x00,0x5A,0x00,0x45,0x00,0x52,0x00,0x4F,0x00,0x0A,0x00,0x43,0x00,0x4F,0x00,0x4C,0x00,0x4C,0x00,0x45,0x00,0x43,0x00,0x54,0x00,0x49,0x00,0x4F,0x00,0x4E,0x00,0x0A,0x00,0x43,0x00,0x41,0x00,0x50,0x00,0x43,0x00,0x4F,0x00,0x4D,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
       System.out.println(bytes.length+" bytes length example");
       byte[] copia=new byte[bytes.length];
       
       for(int i=0;i<bytes.length;i++){
           copia[i]=(byte) bytes[i];
       }
        byte[] data;
       
        data = DataView.getCRCFlippedPairBytes(Hex.getCRC16(copia));
        for(int i=0;i<data.length;i++){
            int num=data[i];
            if(num<0){
                num=num & 0xff;
            }
            System.out.println("Data: "+Hex.getHexPair(num));
        }*/
       MaterialDesign.setLanguage("/com/olmectron/forwarder/strings",
                    LanguageRegion.IDENTIFY);
            MaterialDesign.setContentStage(primaryStage);
            MaterialDesign.setAnimationType(MaterialDesign.ANIMATION_FADE);
            MaterialDesign.setExitOnClose(true);
            MaterialDesign.setTooltipsOn(true);
               root=new StackPane();
       
             R.setCard(Card.getCardFromId(R.getSettingsFile().getValue("card_type", null)));
             
           primaryStage.getIcons().add(new Image("/com/olmectron/forwarder/images/icon.png"));
           
           
           if(OsCheck.getOperatingSystemType().equals(OsCheck.OSType.Windows)){
                File windowsFile=new File("make_cia.exe");
                    if(!windowsFile.exists()){
                        FileOutputStream fos=null;
                    try {
                        //InputStream makeStream=R.class.getResourceAsStream();
                       
                        fos = new FileOutputStream(windowsFile);
                        fos.write(Hex.getInternalFile("/com/olmectron/forwarder/files/windows/make_cia.exe"));
                        fos.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
    }
           else if(OsCheck.getOperatingSystemType().equals(OsCheck.OSType.Linux)){
        File linuxFile=new File("make_cia");
                    if(!linuxFile.exists()){
                        FileOutputStream fos=null;
                    try {
                        //InputStream makeStream=R.class.getResourceAsStream();
                       
                        fos = new FileOutputStream(linuxFile);
                        fos.write(Hex.getInternalFile("/com/olmectron/forwarder/files/linux/make_cia"));
                        fos.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
    }
           else if(OsCheck.getOperatingSystemType().equals(OsCheck.OSType.MacOS)){
         File macosFile=new File("make_cia");
                    if(!macosFile.exists()){
                        FileOutputStream fos=null;
                    try {
                        //InputStream makeStream=R.class.getResourceAsStream();
                       
                        fos = new FileOutputStream(macosFile);
                        fos.write(Hex.getInternalFile("/com/olmectron/forwarder/files/macos/make_cia"));
                        fos.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
    }
            /*File build=new File("build_cia.bat");
            if(!build.exists()){
                try {
                    build.createNewFile();
                    TextFile f=new TextFile("build_cia.bat");
                    f.setText(
                            "make_cia --srl=%1");
                } catch (IOException ex) {
                    Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }*/
            R.getTidList();
            initAll();
            
            Scene scene=MaterialDesign.getMaterialSizableScene(primaryStage,root,970,550);
            setDragAndDropFunction(scene);
            primaryStage.setScene(scene);
            primaryStage.setTitle(R.strings.title.get()+" "+R.strings.version.get());
            primaryStage.setOnShown(new EventHandler<WindowEvent>(){
                @Override
                public void handle(WindowEvent event) {
                    if(R.getSettingsFile().getValue("show_update_notes","true").equalsIgnoreCase("true")){
                        new MaterialConfirmDialog("New on "+R.strings.version.get(),
                                //"* Fixed some issues when loading NDS files without a target card in Forwarders tab.\n"
                               "- Removed the TWLoader tab.\n"+
                        "- Improved templates updating process. Now it shouldn't lock the app completely, since it now runs on several download threads.\n"+
                                "- Re-added the native Window since some Linux distros had problem managing the window without its OS borders."
                                //"* Added 'auto-random TID' option for auto fixing the TID when loading a game with a bad one.\n"
                                //        + "* Improved animations loading speed."
                                        ,"OK"){
                                            @Override
                                            public void onPositiveButton(){
                                                dismiss();
                                            }
                            @Override
                            public void onDialogShown() {
                                R.getSettingsFile().setValue("show_update_notes", "false");
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onDialogHidden() {
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onDialogKeyReleased(KeyEvent event) {
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }

                            @Override
                            public void onDialogKeyPressed(KeyEvent event) {
                                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            }
                                        
                                        
                                        }.unhide();
                        
                    }
                    getMaterialLayout().setRootView(getFilePane());
                    Updater.checkForUpdates(false,true);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            primaryStage.show();
       
        
    }
    private MaterialSelector<Card> cardSelector;
    private void setDragAndDropFunction(Scene scene){
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.LINK);
                } else {
                    event.consume();
                }
            }
        });
        
        // Dropping over surface
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    //if(db.getFiles().size()>1){
                    //new MaterialToast(R.string.drop_only_one.get(),MaterialToast.LENGTH_SHORT).unhide();
                    //}
                    //else{
                    for (File file : db.getFiles()) {
                        if(file.getName().endsWith(".nds")){
                            ROMFile archivo=new ROMFile(file,Hex.NTR);
                            
                            getFilePane().addROMFile(archivo);
                        }
                        else{
                            System.err.println("Invalid file type");
                        }
                    }  
                    
                   
                    //}
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }
    private MaterialEditableLayout mLayout;
    public MaterialEditableLayout getMaterialLayout(){
       
        if(mLayout==null){
            mLayout=new MaterialEditableLayout(true){
                @Override
                public void onMenuButtonPressed(Button button) {
                    
                    MaterialDropdownMenu menu=new MaterialDropdownMenu(button);
                    menu.addItem(new MaterialDropdownMenuItem(R.strings.check_update.get()){
                        @Override
                        public void onItemClick(){
                            Updater.checkForUpdates(true,false);
                        }
                    
                    });
                    menu.addItem(new MaterialDropdownMenuItem("Update templates"){
                        @Override
                        public void onItemClick(){
                            Updater.checkForForwardersUpdate(true);
                        }
                    
                    });
                    menu.addItem(new MaterialDropdownMenuItem("Import template"){
                      
                        @Override
                        public void onItemClick() {
                            menu.setOnHidden(new EventHandler<WindowEvent>(){
                                @Override
                                public void handle(WindowEvent event) {
                                    
                            Card card=showTemplateChooser();
                            if(card!=null){
                                
                                
                                card.setRequireDAT(false);
                               
                               MaterialDropdownMenu menu=new MaterialDropdownMenu(button);
                               menu.setWidth(600);
                               MaterialDisplayText fileName=new MaterialDisplayText(card.getFilePath());
                               fileName.setPadding(new Insets(8,16,8,16));
                               fileName.setColorCode(MaterialColor.material.GREEN);
                               fileName.setFontSize(15);
                               menu.addNodeAsItem(fileName);
                               
                               fileName.setWrapText(true);
                               MaterialTextField gpLocationField=new MaterialTextField("0x"+Integer.toHexString(card.getGamePathOffset()).toUpperCase(),"GamePath Location offset"){
                                   
                                   @Override
                                   public boolean onError(String valor) {
                                       if(valor.startsWith("0x")){
                                       try{
                                           Integer.decode(valor);
                                       }
                                       catch(NumberFormatException ex){
                                           
                                           return true;
                                       }
                                       
                                       }
                                       else return true;
                                       
                                       return false;
//To change body of generated methods, choose Tools | Templates.
                                   }
                               };
                               gpLocationField.setErrorText("Bad hex number");
                               MaterialTextField bannerLocationField=new MaterialTextField("0x"+Integer.toHexString(card.getBannerOffset()).toUpperCase(),"Banner Location offset"){
                                   
                                   @Override
                                   public boolean onError(String valor) {
                                       if(valor.startsWith("0x")){
                                       try{
                                           Integer.decode(valor);
                                       }
                                       catch(NumberFormatException ex){
                                           
                                           return true;
                                       }
                                       
                                       }
                                       else{
                                           return true;
                                       }
                                       
                                       return false;
//To change body of generated methods, choose Tools | Templates.
                                   }
                               };
                               
                               bannerLocationField.setErrorText("Bad hex number");
                                MaterialTextField nameField=new MaterialTextField("","Card name");
                              
                               MaterialTextField idField=new MaterialTextField("","Card ID");
                               idField.setLimite(100);
                               idField.allowMiddleLine();
                               idField.allowBottomLine();
                               nameField.allowBottomLine();
                               
                               idField.allowDot();
                               idField.setPadding(new Insets(8,8,16,16));
                               idField.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        nameField.requestFocus();
nameField.textField().selectAll();//To change body of generated methods, choose Tools | Templates.
                                    }
                                });
                               nameField.setLimite(100);
                               nameField.allowComma();
                               nameField.allowDiagonal();
                               nameField.allowDot();
                               nameField.allowMiddleLine();
                               nameField.allowSpace();
                               nameField.setPadding(new Insets(8,8,16,16));
                               nameField.setOnAction(new EventHandler<ActionEvent>(){
                                    @Override
                                    public void handle(ActionEvent event) {
                                        gpLocationField.requestFocus();
                                       gpLocationField.textField().selectAll(); //To change body of generated methods, choose Tools | Templates.
                                    }
                                });
                               MaterialTextField gpLengthField=new MaterialTextField(card.getGamePathLength()+"","GamePath Length"){
                                   
                                   @Override
                                   public boolean onError(String valor) {
                                       
                                       try{
                                           Integer.parseInt(valor);
                                       }
                                       catch(NumberFormatException ex){
                                           
                                           return true;
                                       }
                                       return false;
                                       
//To change body of generated methods, choose Tools | Templates.
                                   }
                               };
                               gpLengthField.setErrorText("Write a decimal number");
                               gpLengthField.lockLetters();
                               
                               gpLocationField.setPadding(new Insets(8,8,16,16));
                               
                               gpLengthField.setPadding(new Insets(8,8,16,16));
                               
                               
                               bannerLocationField.setPadding(new Insets(8,8,16,16));
                               
                               gpLocationField.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       gpLengthField.requestFocus();
                                       gpLengthField.textField().selectAll();
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                                gpLengthField.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       bannerLocationField.requestFocus();
                                       bannerLocationField.textField().selectAll();
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                                
                               FlatButton button=new FlatButton("Import template");
                                 bannerLocationField.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       
                                       button.fireEvent(event);
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                                 menu.addNodeAsItem(idField);
                                 menu.addNodeAsItem(nameField);
                               menu.addNodeAsItem(gpLocationField);
                               menu.addNodeAsItem(gpLengthField);
                               menu.addNodeAsItem(bannerLocationField);
                               
                               button.setColorCode(MaterialColor.material.BLUE);
                               BooleanProperty c=new SimpleBooleanProperty();
                               c.set(false);
                               menu.setOnHidden(new EventHandler<WindowEvent>(){
                                   @Override
                                   public void handle(WindowEvent event) {
                                      if(!c.get()){
                                          //sel.getSelectionModel().select(0);
                                          new MaterialToast("You didn't complete the template importing process").unhide();
                                      }
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                               button.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       if(!gpLengthField.hasError() && !bannerLocationField.hasError() && !gpLocationField.hasError()
                                               && !idField.getText().trim().isEmpty() &&
                                               !nameField.getText().trim().isEmpty()
                                               ){
                                           
                                           try {
                                               if(!new File(".forwarders/"+idField.getText().trim()+".nds").exists()){
                                               Files.copy(new File(card.getFilePath().substring(5)).toPath(),
                                                       new File(".forwarders/"+idField.getText().trim()+".nds").toPath(),
                                                       StandardCopyOption.REPLACE_EXISTING);
                                               
                                               card.setGamePathLength(Integer.parseInt(gpLengthField.getText()));
                                               card.setGamePathOffset(Integer.decode(gpLocationField.getText()));
                                               card.setBannerOffset(Integer.decode(bannerLocationField.getText()));
                                               card.setName(nameField.getText());
                                               card.setValue(idField.getText());
                                               ConfigFile.writeNewConfigFile(card);
                                               
                                               //R.setCard(card);
                                               //R.getSettingsFile().setValue("card_type", idField.getText());
                                               
                                               c.set(true);
                                               menu.hideMenu();
                                               
                                               new MaterialToast("New template imported successfully").unhide();
                                               }
                                               else{
                                                   idField.setErrorText("This ID is already used");
                                                   idField.showError();
                                                   idField.requestFocus();
                                                   idField.textField().selectAll();
                                               }
                                           } catch (IOException ex) {
                                               Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                                           }
                                       }
                                       else{
                                           if(gpLocationField.hasError()){
                                               gpLocationField.requestFocus();
                                               gpLocationField.textField().selectAll();
                                           }
                                           else if(gpLengthField.hasError()){
                                               gpLengthField.requestFocus();
                                               gpLengthField.textField().selectAll();
                                           }
                                           else if(bannerLocationField.hasError()){
                                               bannerLocationField.requestFocus();
                                               bannerLocationField.textField().selectAll();
                                           }
                                           else if(idField.getText().trim().isEmpty()){
                                               idField.requestFocus();
                                           idField.setErrorText("Write an ID");
                                           idField.showError();
                                       }
                                        else if(nameField.getText().trim().isEmpty()){
                                           nameField.setErrorText("Write a name for the card");
                                           nameField.requestFocus();
                                           nameField.showError();
                                        }
                                       }
//To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                               
                               menu.addNodeAsItem(button);
                               menu.unhide();
                               
                                
                            }
                             //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                            //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                    menu.unhide();
                    
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
            VBox cardTypeBox=new VBox();
            /*MaterialRadioButton r4Radio=new MaterialRadioButton(R.strings.r4_card.get());
            try{
                r4Radio.setSelected(R.getCard().getValue()==CardType.R4_ORIGINAL);
            
            }
            catch(NullPointerException ex){
               r4Radio.setSelected(true);
                R.setCard(Card.ORIGINAL_R4);
               
            }
            
            r4Radio.setPadding(new Insets(8,0,8,0));*/
            
            MaterialLabel cardSelectorLabel=new MaterialLabel("Target");
            //MaterialTooltip tt=new MaterialTooltip(cardSelectorLabel);
            
            cardSelector=new MaterialSelector<Card>(cardSelectorLabel){

                @Override
                public void onSelectionChange(Card valor) {
                     if(valor!=null && !valor.getValue().equals(CardType.FILE)){
                            R.getSettingsFile().setValue("card_type", valor.getValue()+"");
                            R.setCard(valor);
                     }
                     else if(valor!=null && valor.getValue().equals(CardType.FILE)){
                         loadExternalTemplate(cardSelector);
                     }
                     
                    //To change body of generated methods, choose Tools | Templates.
                }
                

                @Override
                public ArrayList<Card> getValues() {
                    ArrayList<Card> vacia=new ArrayList<Card>();
                    for(int i=0;i<Card.getExistingCards().size();i++){
                        vacia.add(Card.getExistingCards().get(i));
                
                  
                    }
                    
                    return vacia; //To change body of generated methods, choose Tools | Templates.
                }
                @Override
                public String getDataString(Card valor) {
                    return valor.getName(); //To change body of generated methods, choose Tools | Templates.
                }
            
            };
            Card.getExistingCards().addListener(new ListChangeListener<Card>(){
                        @Override
                        public void onChanged(ListChangeListener.Change<? extends Card> c) {
                            if(c.next()){
                                if(c.wasAdded()){
                                    for(Card ac:c.getAddedSubList()){
                                        
                                        cardSelector.addItem(ac);
                                        if(ac.getSelectWhenAdded())
                                        cardSelector.getSelectionModel().select(ac);
                                    }
                                }
                                if(c.wasRemoved()){
                                    boolean selectZeroOnEnd=false;
                                    for(Card ac: c.getRemoved()){
                                        
                                        for(int i=0;i<cardSelector.getItems().size();i++){
                                            if(cardSelector.getItems().get(i).getValue().equals(ac.getValue())){
                                                if(cardSelector.getSelectionModel().getSelectedItem().getValue()
                                                        .equals(cardSelector.getItems().get(i).getValue()))
                                                    selectZeroOnEnd=true;
                                                
                                                cardSelector.getItems().remove(i);
                                                break;
                                            }
                                        }
                                    }
                                    if(selectZeroOnEnd)
                                    cardSelector.getSelectionModel().select(0);
                                }
                            }
                            //To change body of generated methods, choose Tools | Templates.
                        }
                    });
            
            cardSelector.setPrefWidth(400);
            try{
                cardSelector.getSelectionModel().select(0);
            for(int i=0;i<cardSelector.getItems().size();i++){
                if(!cardSelector.getItems().get(i).getValue().equals(CardType.FILE) && cardSelector.getItems().get(i).getValue().equals(R.getCard().getValue())){
                    cardSelector.getSelectionModel().select(i);
                }
                
            }
            
            }
            catch(NullPointerException ex){
                cardSelector.getSelectionModel().select(0);
            }
            /*MaterialRadioButton dsttRadio=new MaterialRadioButton(R.strings.dstt_card.get());
            dsttRadio.setSelected(R.getCard().getValue()==CardType.DSTT_R4i_SHDC);
            dsttRadio.setPadding(new Insets(8,0,8,0));
            
            
            MaterialRadioButton dsnRadio=new MaterialRadioButton(R.strings.r4idsn_card.get());
            dsnRadio.setSelected(R.getCard().getValue()==CardType.R4IDSN);
            dsnRadio.setPadding(new Insets(8,0,8,0));
            
            dsnRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                            R.getSettingsFile().setValue("card_type", Card.R4i_GOLD.getValue()+"");
                            R.setCard(Card.R4i_GOLD);
                       
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
            MaterialRadioButton acekardRadio=new MaterialRadioButton(R.strings.acekard_rpg.get());
            acekardRadio.setSelected(R.getCard().getValue()==CardType.ACEKARD_RPG);
            acekardRadio.setPadding(new Insets(8,0,8,0));
            */
            /*MaterialRadioButton anyRadio=new MaterialRadioButton("External template");
            anyRadio.setSelected(R.getCard().getValue()==CardType.FILE);
            anyRadio.setPadding(new Insets(8,0,8,0));*/
            
       /*anyRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });*/
            
            /*acekardRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                      
                            R.getSettingsFile().setValue("card_type", Card.ACEKARD_RPG.getValue()+"");
                            R.setCard(Card.ACEKARD_RPG);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
            r4Radio.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue){
                           
                            R.getSettingsFile().setValue("card_type", Card.ORIGINAL_R4.getValue()+"");
                            R.setCard(Card.ORIGINAL_R4);
                       
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            dsttRadio.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     if(newValue){
                            
                            R.getSettingsFile().setValue("card_type", Card.DSTT.getValue()+"");
                            R.setCard(Card.DSTT);
                    }
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            */
            
            //ToggleGroup group=new ToggleGroup();
            cardTypeBox.setAlignment(Pos.CENTER_LEFT);
            
            //cardSelector.setStyle(cardSelector.getStyle()+"-fx-font-size: 12px !important;");
            
            /*r4Radio.setStyle(r4Radio.getStyle()+"-fx-font-size: 12px !important;");
            dsttRadio.setStyle(dsttRadio.getStyle()+"-fx-font-size: 12px !important;");
            dsnRadio.setStyle(dsnRadio.getStyle()+"-fx-font-size: 12px !important;");
            acekardRadio.setStyle(dsnRadio.getStyle()+"-fx-font-size: 12px !important;");
            anyRadio.setStyle(dsnRadio.getStyle()+"-fx-font-size: 12px !important;");
            
            r4Radio.setToggleGroup(group);
            dsttRadio.setToggleGroup(group);
            dsnRadio.setToggleGroup(group);
            acekardRadio.setToggleGroup(group);*/
            
            //anyRadio.setToggleGroup(group);
//            cardTypeBox.setPadding(new Insets());
            MaterialCheckBox keepDS=new  MaterialCheckBox(R.strings.keep_ds.get());
            MaterialCheckBox filesFromCard=new MaterialCheckBox("Automatically set ROM path");
            MaterialCheckBox autoCorrectTID=new MaterialCheckBox("Auto-random TID");
            MaterialCheckBox autoUpdateTemplates=new MaterialCheckBox("Update templates at startup");
            
            new MaterialTooltip("Having this selected will set automatically a random TID to every game with bad TID when loaded. While this is not needed anymore in Luma 8+ versions, I let the option here in case anyone is using an older version or want to change TIDs by reasons.",autoCorrectTID);
            autoUpdateTemplates.setSelected(R.getSettingsFile().getValue("auto_update_template","true").equals("true"));
             R.setAutoUpdateTemplates(autoUpdateTemplates.isSelected());
            
             autoCorrectTID.setSelected(R.getSettingsFile().getValue("auto_correct_tid","false").equals("true"));
             R.setAutoCorrectTID(autoCorrectTID.isSelected());
            new MaterialTooltip("Check this for looking for updates on new templates (from different flashcards models) for creating forwarders, when the app starts",
                    autoUpdateTemplates);
            
            autoUpdateTemplates.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     
                            R.getSettingsFile().setValue("auto_update_template", newValue.booleanValue()+"");
                           R.setAutoUpdateTemplates(newValue);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            autoCorrectTID.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     
                            R.getSettingsFile().setValue("auto_correct_tid", newValue.booleanValue()+"");
                           R.setAutoCorrectTID(newValue);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            new MaterialTooltip("Select this option if your games are loaded from your SD card, so that the path is set to where the files are already set, when you load the games",filesFromCard);
            filesFromCard.setSelected(R.getSettingsFile().getValue("files_from_card","false").equals("true"));
             R.setFilesFromCard(filesFromCard.isSelected());
            filesFromCard.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     
                            R.getSettingsFile().setValue("files_from_card", newValue.booleanValue()+"");
                           R.setFilesFromCard(newValue);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
            
            VBox autoTidBox=new VBox(autoCorrectTID);
            autoTidBox.setPadding(new Insets(8,0,8,0));
            
            
            VBox autoTemplateBox=new VBox(autoUpdateTemplates);
            autoTemplateBox.setPadding(new Insets(8,0,8,0));
            
            VBox cardBox=new VBox(filesFromCard);
            cardBox.setPadding(new Insets(8,0,8,0));
            
            VBox dsbox=new VBox(keepDS);
           dsbox.setPadding(new Insets(8,0,8,0));
           VBox c=new VBox(cardSelectorLabel,cardSelector);
           
           c.setPadding(new Insets(0,0,12,0));
            //cardTypeBox.getChildren().addAll(r4Radio,dsttRadio,dsnRadio,acekardRadio,anyRadio,dsbox,cardBox,autoTidBox);
            BorderPane mainDrawerPane=new BorderPane();
            
            MaterialTabs tabs=new MaterialTabs();
           // mainDrawerPane.setTop(tabs);
            tabs.addTab("Forwarders");
            
            tabs.addTab("TWLoader");
            VBox twLoader=new VBox();
            twLoader.setPadding(new Insets(16,16,0,16));

            MaterialRadioButton radioSD=new MaterialRadioButton("ROMs on 3DS' SD card");
            radioSD.setSelected(R.getSettingsFile().getValue("roms_on_3ds","true").equals("true"));
             R.setROMSon3DS(radioSD.isSelected());
           
            radioSD.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     
                            R.getSettingsFile().setValue("roms_on_3ds", newValue.booleanValue()+"");
                           R.setROMSon3DS(newValue);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
            
            VBox radioSDBox=new VBox(radioSD);
            radioSDBox.setPadding(new Insets(8,0,8,0));

            MaterialRadioButton radioFlashCard=new MaterialRadioButton("ROMs on flashcard's micro SD card");
             radioFlashCard.setSelected(R.getSettingsFile().getValue("roms_on_flashcard","false").equals("true"));
             R.setROMSonFlashCard(radioFlashCard.isSelected());
           
            radioFlashCard.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     
                            R.getSettingsFile().setValue("roms_on_flashcard", newValue.booleanValue()+"");
                           R.setROMSonFlashCard(newValue);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            
            
            VBox radioFlashCardBox=new VBox(radioFlashCard);
            radioFlashCardBox.setPadding(new Insets(8,0,8,0));

            ToggleGroup group=new ToggleGroup();
            radioFlashCard.setToggleGroup(group);
            radioSD.setToggleGroup(group);

//          
            twLoader.getChildren().addAll(radioSDBox,radioFlashCardBox);
           
            cardTypeBox.getChildren().addAll(c,dsbox,autoTidBox,autoTemplateBox);
            VBox bottomBox=new VBox(cardBox);
            mainDrawerPane.setBottom(bottomBox);
            bottomBox.setPadding(new Insets(0,16,16,16));
            cardTypeBox.setPadding(new Insets(16,16,0,16));
            tabs.getTabAt(0).setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    setBuildMode(FORWARDERS);
                    mainDrawerPane.setCenter(cardTypeBox);
//To change body of generated methods, choose Tools | Templates.
                }
            });
            tabs.getTabAt(1).setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    setBuildMode(TWLOADER);
                    mainDrawerPane.setCenter(twLoader);
                    //To change body of generated methods, choose Tools | Templates.
                }
            });
            tabs.getTabAt(0).select();
            mLayout.addNodeAsDrawerItem(mainDrawerPane);
            keepDS.setSelected(R.getSettingsFile().getValue("keep_nds_files","true").equals("true"));
             R.setKeepDS(keepDS.isSelected());
            keepDS.selectedProperty().addListener(new ChangeListener<Boolean>(){
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                     
                            R.getSettingsFile().setValue("keep_nds_files", newValue.booleanValue()+"");
                           R.setKeepDS(newValue);
                    
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            new MaterialTooltip(R.strings.keep_ds_detail.get(),keepDS);
            //mLayout.addNodeAsDrawerItem(keepDS);
            mLayout.setDrawerWidth(330);
            
            mLayout.setMiniDrawerSize(0);
            mLayout.setTitle(R.strings.title.get());
            mLayout.addToolbarActionButton(MaterialIconButton.FOLDER_OPEN_ICON,openHandler, R.strings.open_files.get());
            try{
            File carchivo=new File("backgrounds/"+R.getSettingsFile().getValue("background_file", null));    
            if(carchivo.exists()){
                
            
            BackgroundImage myBI= new BackgroundImage(new Image(carchivo.toURI().toString()),
            
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
//then you set to your node

root.setBackground(new Background(myBI));
            }
            }
            catch(Exception ex){
                System.err.println(ex.getMessage());
            }
            
            
            mLayout.getToolbar().getStatusBar().setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                
                if(event.getButton().equals(MouseButton.SECONDARY)){
                MaterialDropdownMenu m=new MaterialDropdownMenu(event.getScreenX(),event.getScreenY());
                m.addItem(new MaterialDropdownMenuItem("Main background"){

                    @Override
                    public void onItemClick() {
                        m.setOnHidden(new EventHandler<WindowEvent>(){
                            @Override
                            public void handle(WindowEvent event) {
                                FileChooser f=new FileChooser();
                        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg"));
                        File archivo=f.showOpenDialog(MaterialDesign.primary);
                        if(archivo!=null){
                            File dir=new File("backgrounds");
                            if(!dir.exists()){
                                dir.mkdir();
                            }
                            File to=new File("backgrounds/"+archivo.getName());
                            
                            
                                    try {
                                        Files.copy(archivo.toPath(), to.toPath(),StandardCopyOption.REPLACE_EXISTING);
                                        
                            R.getSettingsFile().setValue("background_file", to.getName());
                                    } catch (IOException ex) {
                                        Logger.getLogger(Forwarder3DS.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            System.out.println(archivo.getAbsolutePath());
                            BackgroundImage myBI= new BackgroundImage(new Image(to.toURI().toString()),
        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
//then you set to your node
root.setBackground(new Background(myBI));
                        }//To change body of generated methods, choose Tools | Templates.
                            }
                        });
                        m.hideMenu();
                        
                         //To change body of generated methods, choose Tools | Templates.
                    }
                });
               
                m.unhide();
                }
                //To change body of generated methods, choose Tools | Templates.
            }
        });
            
            
            
       
        
        mLayout.setWindowTitle(R.strings.title.get()+" "+R.strings.version.get());
        //mLayout.setTitle("MessAnimDSIco");
            
            
        }
        return mLayout;
    }
    private void initAll(){
        root.getChildren().add(getMaterialLayout());
        
    }
    private Card showTemplateChooser(){
        FileChooser chooser=new FileChooser();
        chooser.setTitle("Open Template file");
        chooser.getExtensionFilters().add(new ExtensionFilter("Template files","*.nds"));
        File selectedTemplate=chooser.showOpenDialog(MaterialDesign.primary);
        if(selectedTemplate!=null){
            Card external=new Card();
            external.setFilePath("file:"+selectedTemplate.getAbsolutePath());
            return external;
        }
        return null;
        
    }
    private FilePane filePane;
    private FilePane getFilePane(){
        if(filePane==null){
            filePane=new FilePane(){
                
                @Override
                public void onCompleteFolderOpen(String folder){
                    getHostServices().showDocument("file:"+folder);
                }
            
            };
        }
        return filePane;
    }
    private void loadExternalTemplate(MaterialSelector sel){
        
                           Card card=showTemplateChooser();
                           if(card!=null){
                               card.setRequireDAT(false);
                               
                               MaterialDropdownMenu menu=new MaterialDropdownMenu(sel);
                               menu.setWidth(600);
                               MaterialDisplayText fileName=new MaterialDisplayText(card.getFilePath());
                               fileName.setPadding(new Insets(8,16,8,16));
                               fileName.setColorCode(MaterialColor.material.GREEN);
                               fileName.setFontSize(15);
                               menu.addNodeAsItem(fileName);
                               
                               fileName.setWrapText(true);
                               MaterialTextField gpLocationField=new MaterialTextField("0x"+Integer.toHexString(card.getGamePathOffset()).toUpperCase(),"GamePath Location offset"){
                                   
                                   @Override
                                   public boolean onError(String valor) {
                                       if(valor.startsWith("0x")){
                                       try{
                                           Integer.decode(valor);
                                       }
                                       catch(NumberFormatException ex){
                                           
                                           return true;
                                       }
                                       
                                       }
                                       else return true;
                                       
                                       return false;
//To change body of generated methods, choose Tools | Templates.
                                   }
                               };
                               gpLocationField.setErrorText("Bad hex number");
                               MaterialTextField bannerLocationField=new MaterialTextField("0x"+Integer.toHexString(card.getBannerOffset()).toUpperCase(),"Banner Location offset"){
                                   
                                   @Override
                                   public boolean onError(String valor) {
                                       if(valor.startsWith("0x")){
                                       try{
                                           Integer.decode(valor);
                                       }
                                       catch(NumberFormatException ex){
                                           
                                           return true;
                                       }
                                       
                                       }
                                       else{
                                           return true;
                                       }
                                       
                                       return false;
//To change body of generated methods, choose Tools | Templates.
                                   }
                               };
                               
                               bannerLocationField.setErrorText("Bad hex number");
                               MaterialTextField gpLengthField=new MaterialTextField(card.getGamePathLength()+"","GamePath Length"){
                                   
                                   @Override
                                   public boolean onError(String valor) {
                                       
                                       try{
                                           Integer.parseInt(valor);
                                       }
                                       catch(NumberFormatException ex){
                                           
                                           return true;
                                       }
                                       return false;
                                       
//To change body of generated methods, choose Tools | Templates.
                                   }
                               };
                               gpLengthField.setErrorText("Write a decimal number");
                               gpLengthField.lockLetters();
                               
                               gpLocationField.setPadding(new Insets(8,8,16,16));
                               
                               gpLengthField.setPadding(new Insets(8,8,16,16));
                               
                               
                               bannerLocationField.setPadding(new Insets(8,8,16,16));
                               
                               gpLocationField.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       gpLengthField.requestFocus();
                                       gpLengthField.textField().selectAll();
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                                gpLengthField.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       bannerLocationField.requestFocus();
                                       bannerLocationField.textField().selectAll();
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                                
                               FlatButton button=new FlatButton("Import template");
                                 bannerLocationField.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       
                                       button.fireEvent(event);
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                               menu.addNodeAsItem(gpLocationField);
                               menu.addNodeAsItem(gpLengthField);
                               menu.addNodeAsItem(bannerLocationField);
                               
                               button.setColorCode(MaterialColor.material.BLUE);
                               BooleanProperty c=new SimpleBooleanProperty();
                               c.set(false);
                               menu.setOnHidden(new EventHandler<WindowEvent>(){
                                   @Override
                                   public void handle(WindowEvent event) {
                                      if(!c.get()){
                                          sel.getSelectionModel().select(0);
                                          new MaterialToast("You didn't complete the external template setup").unhide();
                                      }
                                        //To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                               button.setOnAction(new EventHandler<ActionEvent>(){
                                   @Override
                                   public void handle(ActionEvent event) {
                                       if(!gpLengthField.hasError() && !bannerLocationField.hasError() && !gpLocationField.hasError()){
                                        R.getSettingsFile().setValue("card_type", CardType.FILE+""); 
                                        card.setGamePathLength(Integer.parseInt(gpLengthField.getText()));
                                        card.setGamePathOffset(Integer.decode(gpLocationField.getText()));
                                        card.setBannerOffset(Integer.decode(bannerLocationField.getText()));
                                        card.setValue(CardType.FILE);
                                        R.setCard(card);
                                        
                                        c.set(true);
                                        menu.hideMenu();
                                        
                                        new MaterialToast("Custom template set").unhide();
                                       }
                                       else{
                                           if(gpLocationField.hasError()){
                                               gpLocationField.requestFocus();
                                               gpLocationField.textField().selectAll();
                                           }
                                           else if(gpLengthField.hasError()){
                                               gpLengthField.requestFocus();
                                               gpLengthField.textField().selectAll();
                                           }
                                           else if(bannerLocationField.hasError()){
                                               bannerLocationField.requestFocus();
                                               bannerLocationField.textField().selectAll();
                                           }
                                       }
//To change body of generated methods, choose Tools | Templates.
                                   }
                               });
                               menu.addNodeAsItem(button);
                               menu.unhide();
                               
                               
                           }
                           else{
                               sel.getSelectionModel().select(0);
                               //r4Radio.setSelected(true);
                           }
                    
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
