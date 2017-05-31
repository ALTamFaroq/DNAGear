/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.sequenceprocessing;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author faroq
 */
public class SpaProcessor {
    ArrayList<SpaType> spaTypes=new ArrayList<SpaType>();
    ArrayList<Sequence> sequences=new ArrayList<Sequence>();
    ArrayList<SpaRepeate> spaRepeates=new ArrayList<SpaRepeate>();
    
    /**
     * Create a spatype from a sequence by using  given repeats
     * @param sequence 
     */
    public Entry toType(Entry sequence, SpaRepeate repeate,  BufferedWriter bfwritter, SpaType type){
        
        Entry spatype= new Entry(null, null);
        String typevalue=null;
        try {
            
            int repeatsize=repeate.getlength();
            String results=sequence.getValue();
            String newrepeats="";
            // write sequence name and the original sequence
            
            bfwritter.write("Seq:" + sequence.getName());
            bfwritter.newLine();
            bfwritter.write(results);
            String repeats=results;
            //System.out.println(results);
            System.out.println("Finding Repeats");
            int repeatsfound=0;
            for(int i=0;i<repeatsize;i++ ){
                Entry  current_repeat=repeate.getEntry(i);
               
               
                Pattern pattern = Pattern.compile(current_repeat.getValue());
                Matcher matcher = pattern.matcher(results);
                
                String repeatname= StringUtil.getNumberonly(current_repeat.getName());
                if(repeatname.equals("0")) // new repeat get value 0
                    continue;
                
                if(matcher.find()==true){
                    results=matcher.replaceAll("-"+repeatname );
                    repeatsfound++;
                    repeats=pattern.matcher(repeats).replaceAll("-"+repeatname);
                    
                    
                }
                
            }
            System.out.println("Repeats found:"+ repeatsfound);
            
            
            // now find the new repeats
            System.out.println("Finding newrepeats");
            int newrepeatsNO=0;
            newrepeats=results;
            String nrepeats="";
            for(int i=0;i<repeatsize;i++ ){
                Entry  current_repeat=repeate.getEntry(i);
                Pattern pattern = Pattern.compile(current_repeat.getValue());
                Matcher matcher = pattern.matcher(newrepeats);
                String repeatname= StringUtil.getNumberonly(current_repeat.getName());
                if(!repeatname.equals("0")) // new repeat get value 0
                    continue;
                  
                if(matcher.find()==false) continue;
                int start= matcher.toMatchResult().start();
                int end= matcher.toMatchResult().end();
                if(!newrepeats.equals("")) newrepeats+="\n";
                nrepeats+=newrepeats.substring(start, end);
                newrepeats=matcher.replaceAll("-"+repeatname);
                newrepeatsNO++;
            };
            System.out.println("New repeats:"+ newrepeatsNO);
            
            
            // keep the repeats only and remove anything else
            repeats=StringUtil.getAsHexaFormated(repeats);
            
            //if(results.startsWith("-"))results.replaceFirst("-","");
            // get type
            if(repeats!=null){
            if(repeats.startsWith("-")) repeats=repeats.replaceFirst("-", "");
            String types=type.gettypefromRepeats(repeats);
            
           // bfwritter.write("\n");
            bfwritter.newLine();
            bfwritter.write(results);
           // bfwritter.write("\n");
            bfwritter.newLine();
            bfwritter.write("Type:");
            //bfwritter.write("\n");
            bfwritter.newLine();            
            bfwritter.write("["+types+ "]");
            bfwritter.newLine();
            }        
            
            
           if(!nrepeats.equals("")){
              //  bfwritter.write("\n");
                bfwritter.newLine();
                bfwritter.write("New repeats:");
                bfwritter.newLine();
                //bfwritter.write("\n");
                bfwritter.write("["+nrepeats+ "]");
            }
            
                
            //bfwritter.write(typevalue);
            //bfwritter.write("\n");
            bfwritter.newLine();
            //bfwritter.write("\n");
            bfwritter.newLine();
            bfwritter.flush();
//          System.out.println();
            //System.out.println(results);
//          System.out.println();
//          System.out.println();
            
           
           
        } catch (IOException ex) {
            Logger.getLogger(SpaProcessor.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error:"+ex.getMessage());
                   
        } 
       
         //bfwritter.close();
         return spatype;
        
        
    }
    
    
    public String verifySequences(Sequence sequences){
        String errors="";
        int size= sequences.getlength();
        for(int i=0;i<size;i++){
            Entry eseq=sequences.getEntry(i);
            if(eseq.isDNAEntry()){
                String outliers=eseq.getDNAoutliers();
                if(!outliers.equals("")){
                    errors += "\n Sequence:" +  eseq.getName() + "\t has " + " Unknown chars:" + outliers;
                }
            }
        }
        
        return errors;
                
                
    }
    
    
}
