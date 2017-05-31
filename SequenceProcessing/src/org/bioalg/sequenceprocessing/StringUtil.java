/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.sequenceprocessing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author faroq
 */
public class StringUtil {
    public static String getNumberonly(String string){
        String number="";
        Pattern p= Pattern.compile("[0-9]+");
        Matcher m=p.matcher(string);
        
        if(m.find()==true)
           number= m.group();
        
        return number;
            
           
    }
    
    public static String getoutliers(String string, String regex){
        String outlies="";
        Pattern p= Pattern.compile(regex);
        Matcher m=p.matcher(string);
        if(m.find()==true)
            outlies=m.group();
        return outlies;
    }
    
    public static String getAsHexaFormated(String string){
         Pattern p= Pattern.compile("[-\\d\\d]+");
         Matcher m=p.matcher(string);
         if(m.find()==true)
         return m.group();
        
         return null;
    }
    
    
  
    public static String DNAComplement(String dnastring){
        
        
        // A<->T
        // C<->G
        //TACG
        //!@$^
        //!@$C
        //!@GC
        //!TGC
        //ATGC done
        
        dnastring=dnastring.replace('T', '!');
        dnastring=dnastring.replace('A', '@');
        dnastring=dnastring.replace('C', '$');
        dnastring=dnastring.replace('G', '^');
        
        dnastring=dnastring.replace('!', 'A');
        dnastring=dnastring.replace('@', 'T');
        dnastring=dnastring.replace('$', 'G');
        dnastring=dnastring.replace('^', 'C');
        
        //now reverse it 
        StringBuilder stringBuffer= new StringBuilder(dnastring);
        
        dnastring=stringBuffer.reverse().toString();
        
        return dnastring;
        
    }
    
    public static String sliceAndMatch(String fstring, String rstring){
        String resultString="";
        for(int i=0;i<fstring.length();i++){
            String subString=fstring.substring(i, fstring.length());
            
            if(rstring.startsWith(subString)){
                resultString+=subString+rstring.substring(subString.length());
                break;
              }
            resultString+=fstring.charAt(i);
        }
        return resultString;
    }
    
    
    
    
    
    
    
}
