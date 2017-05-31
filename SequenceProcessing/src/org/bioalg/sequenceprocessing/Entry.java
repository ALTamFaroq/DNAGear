/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.sequenceprocessing;

/**
 *
 * @author faroq
 */
public class Entry  {
    
    String name;
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Entry(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" + "name=" + name + ", value=" + value + '}';
    }

    public int compareTo(String t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean equals(Object o) {
       return(value.equals(((Entry)o).value));
    }
    
    
        public boolean isDNAEntry(){
        
        return !StringUtil.getoutliers(value, "[ACGT-]").equals("");
    }
    
    public String getDNAoutliers(){
        return StringUtil.getoutliers(value, "[^ACGT-]");
    }
        
        
    public void DNAComplement(){
        this.value=StringUtil.DNAComplement(value);
    }
   

   
    
    
    
}
