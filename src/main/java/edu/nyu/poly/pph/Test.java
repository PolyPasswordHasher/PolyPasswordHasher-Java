/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nyu.poly.pph;

import com.tiemens.secretshare.engine.ShamirSchem;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Ali Gholami <gholami@pdc.kth.se>
 */
public class Test {

    private static int shares;
    private static int threashold;
    private Properties props;

    public void initContext() throws IOException {
        props = new Properties();
        InputStream inputStream
                = getClass().getClassLoader().getResourceAsStream("user.properties");
        props.load(inputStream);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        ShamirSchem sc = new ShamirSchem();
        
        //0cac5a40-3690-4167-9993-8b6bf58e
        String thresholdlesskey = SecurityUtil.getRandomString(32);
        
        System.out.println(thresholdlesskey.getBytes().length);
        // divide 3
        int shares = 3;
        
        // revcover
        int threashold = 2;
        
        String[] pieces = sc.splitSecretIntoPieces(thresholdlesskey, shares, threashold);
        System.out.println( shares+ " pieces: " + Arrays.toString(pieces));

        List<String> list = new ArrayList<String>(Arrays.asList(pieces));
        Collections.shuffle(list);
        
        String[] kPieces = list.toArray(new String[0]);
        System.out.println("Any " + threashold + " pieces: " + Arrays.toString(kPieces));
      
    
    }
}