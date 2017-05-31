/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.dnaworkspace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.bioalg.DNAProject.ProjectUIManager;
import org.bioalg.DNAProject.SequenceProject;
import org.bioalg.sequenceprocessing.Entry;
import org.bioalg.sequenceprocessing.Sequence;
import org.bioalg.sequenceprocessing.StringUtil;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "DNAProject",
id = "org.bioalg.dnaworkspace.CompareFRSequence")
@ActionRegistration(iconBase = "org/bioalg/dnaworkspace/fr_run.png",
displayName = "#CTL_CompareFRSequence")
@ActionReferences({
    @ActionReference(path = "Toolbars/Build", position = 3433)
})
@Messages("CTL_CompareFRSequence=Compare forward/backward sequences")
public final class CompareFRSequence implements ActionListener {

    private final Project context;

    public CompareFRSequence(Project context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        SequenceProject project= ProjectUIManager.getMainProject();
        if(project==null) return;
        FileObject[] Fsfile= project.getForwardSequencesFolder(true).getChildren();
        if(Fsfile==null ||(Fsfile!=null && Fsfile.length==0)){
            JOptionPane.showMessageDialog(null, "No forward sequence file were found in the project!");
            return;
        }
       FileObject[] Rsfile= project.getReverseSequencesFolder(true).getChildren();
        if(Rsfile==null ||(Rsfile!=null && Rsfile.length==0)){
            JOptionPane.showMessageDialog(null, "No reverse sequence file were found in the project!");
            return;
        }
        
        
        // load forward
        int fsize=Fsfile.length;
        Sequence [] FSequences= new Sequence[fsize]; //
        for(int i=0;i<fsize;i++){            
            File sequenceFile= FileUtil.toFile(Fsfile[i]);
            FSequences[i]=new org.bioalg.sequenceprocessing.Sequence(sequenceFile);
            if(!FSequences[i].load()){
                JOptionPane.showMessageDialog(null, "Sequences' file (" + sequenceFile.getAbsolutePath() +") loading faild");
                continue;
            }
        }
        
        int rsize=Rsfile.length;
        Sequence [] RSequences= new Sequence[rsize];
        for(int i=0;i<rsize;i++){            
            File sequenceFile= FileUtil.toFile(Rsfile[i]);
            RSequences[i]=new org.bioalg.sequenceprocessing.Sequence(sequenceFile);
            if(!RSequences[i].load()){
                JOptionPane.showMessageDialog(null, "Sequences' file (" + sequenceFile.getAbsolutePath() +") loading faild");
                continue;
            }
            // reverse it out
            RSequences[i].DNAComplement();
        }
        
        // fsize X rsize comparision
        for(int i=0;i<fsize;i++){
            for(int j=0;j<rsize;j++){
                CompareHelper(FSequences[i], RSequences[j]);
            }
        }
        
    }
    
    public void CompareHelper(Sequence Fsequence, Sequence Rsequence){
        try {
            BufferedWriter bufferedWriter=null;
            
                String filetitle= Fsequence.getFile().getName()+"VS" + Rsequence.getFile().getName()+".FRsqrt";
                String results="";
                for(int i=0;i<Fsequence.getlength();i++)
                    for(int j=0;j<Rsequence.getlength();j++){
                         Entry FEntry= Fsequence.getEntry(i);
                         Entry REntry= Rsequence.getEntry(j);
                         if(StringUtil.getNumberonly(FEntry.getName()).equals(StringUtil.getNumberonly(REntry.getName()))){
                             String fmatch=StringUtil.sliceAndMatch(REntry.getValue(), FEntry.getValue());
                             
                             results+=FEntry.getName() + "\n" + FEntry.getValue() + "\n"
                                     + "Reversed("+REntry.getName() + ")\n" + REntry.getValue() + "\n"
                                     + FEntry.getName() + "\tVS\t" + REntry.getName() + "\n"
                                     + (!fmatch.equals(REntry.getValue())?fmatch:StringUtil.sliceAndMatch(FEntry.getValue(), REntry.getValue()))
                                     + "\n\n";
                                     
                         }
                    }
                    
                
                    FileObject rfFileObject= ProjectUIManager.getMainProject().getFRResultsFolder(true);
                    if(rfFileObject.getFileObject(filetitle)!=null)
                        rfFileObject.getFileObject(filetitle).delete();
                        
                    File resultfile= FileUtil.toFile(rfFileObject.createData(filetitle));
                    bufferedWriter=  new BufferedWriter(new FileWriter(resultfile));
                    bufferedWriter.write(results);
                    bufferedWriter.flush();
                    
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        
    }
}
