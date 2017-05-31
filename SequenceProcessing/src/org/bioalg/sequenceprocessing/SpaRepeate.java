/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.sequenceprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author faroq
 */
public class SpaRepeate {

    ArrayList<Entry> entries = new ArrayList<Entry>();
    public File file;

    public SpaRepeate() {
    }

    public void addreules() {
        Entry r1 = new Entry(">r0", "AAAGAAGA[ACGT-]{4}AA[ACGT-]{1}AA[ACGT-]{1}[ACGT-]{1,4}CC[ACGT-]{4}");
        Entry r2 = new Entry(">r0", "GAGGAAGA[ACGT-]{4}AA[ACGT-]{1}AA[ACGT-]{1}[ACGT-]{1,4}CC[ACGT-]{4}");
        entries.add(r1);
        entries.add(r2);

    }

    public SpaRepeate(File file) {
        this.file = file;
    }

    public boolean load() {

        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return false;
        }
        // add the rules to the repeats
        addreules();


        BufferedReader bFreader = null;
        try {
            bFreader = new BufferedReader(new FileReader(file));

            String nameline = null;
            String stringline = null;
            int linescounter = 0;
            // repeat until all lines are read
            // lines should be even so we starts form 1 to n

            // the file has the following structure
            // repeat number
            // sequence
            // empty line

            while ((nameline = bFreader.readLine()) != null) {
                //now read the string line
                if (nameline.equals("")) {
                    continue; //empty line
                }
                linescounter++;
                stringline = bFreader.readLine();
                if (stringline == null) {
                    JOptionPane.showMessageDialog(null, "Opps! there is a string missing at line:" + linescounter + 1);
                    return false;
                } else {
                    Entry ent = new Entry(nameline, stringline);
                    entries.add(ent);
                }


            }

        } catch (IOException ex) {
            Logger.getLogger(SpaType.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void print() {
        int size = entries.size();
        for (int i = 0; i < size; i++) {
            System.out.println(entries.get(i).toString());
        }
        System.out.println("Number of entries: " + size);

    }

    public Entry getEntry(int index) {

        return entries.get(index);
    }

    public int getlength() {
        return entries.size();
    }
}
