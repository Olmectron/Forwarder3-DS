/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olmectron.forwarder;

import java.util.ArrayList;

/**
 *
 * @author Edgar
 */
public class IconSequence {
    private ArrayList<SequenceToken> tokens;
    private ROMFile f;
    public IconSequence(String tok, ROMFile f){
        this.f=f;
        processString(tok);
    }
    private void processString(String tok){
        tokens=new ArrayList<SequenceToken>();
        for(int i=0;i<tok.length();i+=4){
            
            String t=tok.substring(i+2,i+4)+tok.substring(i,i+2);
           //System.out.println("Not working: "+t);
            //System.out.println("Working: "+tok.substring(i,i+4));
            tokens.add(new SequenceToken(t,f));
        }
    }
    public ArrayList<SequenceToken> getTokens(){
        return tokens;
    }
    /*15    Flip Vertically   (0=No, 1=Yes)
  14    Flip Horizontally (0=No, 1=Yes)
  13-11 Palette Index     (0..7)
  10-8  Bitmap Index      (0..7)
  7-0   Frame Duration    (01h..FFh) (in 60Hz units)*/

}
