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
import org.bioalg.sequenceprocessing.Sequence;
import org.bioalg.sequenceprocessing.SpaProcessor;
import org.bioalg.sequenceprocessing.SpaRepeate;
import org.bioalg.sequenceprocessing.SpaType;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Build",
id = "org.bioalg.dnaworkspace.ProcessSequencesAction")
@ActionRegistration(iconBase = "org/bioalg/dnaworkspace/run.png",
displayName = "#CTL_ProcessSequencesAction")
@ActionReferences({
    @ActionReference(path = "Menu/BuildProject", position = 1000),
    @ActionReference(path = "Toolbars/Build", position = 3333)
})
@Messages("CTL_ProcessSequencesAction=Process sequences")
public final class ProcessSequencesAction implements ActionListener {

    private final Project context;

    public ProcessSequencesAction(Project context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        
        SequenceProject project= ProjectUIManager.getMainProject();
        if(project==null) return;
        FileObject rfile=  project.getTRFolder(true).getFileObject("spatypes.txt");
        FileObject tfile=  project.getTRFolder(true).getFileObject("sparepeats.fasta");
        if(rfile==null || tfile== null){
            JOptionPane.showMessageDialog(null, "Repeats or types base files are missing!");
            return;
        }
        
        
        SpaRepeate repeate= new SpaRepeate( FileUtil.toFile(tfile));
        SpaType type= new SpaType(FileUtil.toFile(rfile));
        
        // now process each sequence in the sequence folder
        FileObject[] sfile= project.getSequencesFolder(true).getChildren();
        if(sfile==null ||(sfile!=null && sfile.length==0)){
            JOptionPane.showMessageDialog(null, "No sequence file were found in the project!");
            return;
        }
        
        
        // now load the repeat and types 
        if(!repeate.load()){
            JOptionPane.showMessageDialog(null, "Repeats' file loading faild");
            return;
        }
        
        if(!type.load()){
            JOptionPane.showMessageDialog(null, "Types' file loading faild");
            return;
        }
        
        int size=sfile.length;
        for(int i=0;i<size;i++){
            
            File sequenceFile= FileUtil.toFile(sfile[i]);
            Sequence sequence=new Sequence(sequenceFile);
            if(!sequence.load()){
                JOptionPane.showMessageDialog(null, "Sequences' file (" + sequenceFile.getAbsolutePath() +") loading faild");
                continue;
            }
            
            sequenceProcessingHelper(repeate, type, sequence, project);
            
        }
        
     
            
        
    }
    
    public void sequenceProcessingHelper(SpaRepeate repeate, SpaType type, Sequence sequence, SequenceProject project){
        try {
            SpaProcessor processor= new SpaProcessor();
             String errorString= processor.verifySequences(sequence);
             if(!errorString.equals(""))             
                 if(JOptionPane.showConfirmDialog(null, "File:"+ sequence.getFile().getName() +"\nThese errors were found\n"+ errorString + "\n Want to compelete?","Sequence Processsing",JOptionPane.YES_NO_OPTION)== JOptionPane.NO_OPTION) return;
             //create new resultfile and store it 
             FileObject resultfolder= project.getResultsFolder(true);
              
             FileObject resultfile= resultfolder.getFileObject(FileUtil.toFileObject(sequence.getFile()).getName()+".seqrst");
             // if exist delete it 
             if(resultfile!=null)
                  resultfile.delete();
              
                 resultfile= resultfolder.createData(FileUtil.toFileObject(sequence.getFile()).getName()+".seqrst");
             if(resultfile!=null){
                 File seqrstFile= FileUtil.toFile(resultfile);
                 BufferedWriter bufferedWriter=  new BufferedWriter(new FileWriter(seqrstFile));
             for(int i=0;i<sequence.getlength();i++)
                    processor.toType(sequence.getEntry(i), repeate,bufferedWriter , type);
             }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
       
         
         
         
        
    }
    
    
          
}
