/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.dnaworkspace;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.bioalg.DNAProject.SequenceProject;
import org.netbeans.api.project.Project;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "DNAProject",
id = "org.bioalg.dnaworkspace.AddForwardSequence")
@ActionRegistration(iconBase = "org/bioalg/dnaworkspace/f+.png",
displayName = "#CTL_AddForwardSequence")
@ActionReferences({
    @ActionReference(path = "Toolbars/File", position = 375)
})
@Messages("CTL_AddForwardSequence=Add forward sequence")
public final class AddForwardSequence implements ActionListener {

    private final Project context;

    public AddForwardSequence(Project context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        Lookup.Result<SequenceProject> resutl = org.openide.util.Utilities.actionsGlobalContext().lookupResult(SequenceProject.class);
        Iterator it = resutl.allInstances().iterator();
          FileObject imagefolder=null;         
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof SequenceProject) {
               imagefolder= ((SequenceProject)next).getForwardSequencesFolder(true);
               break;
            }
                
            }
        
        if(imagefolder==null) return;
        JFileChooser jf=new JFileChooser();
        jf.setMultiSelectionEnabled(true);
        if(jf.showOpenDialog(jf)==JFileChooser.APPROVE_OPTION){
            File[] selectedFiles=jf.getSelectedFiles();
            int size=selectedFiles.length;
            for(int i=0;i<size;i++){
            FileObject currentFileObject=FileUtil.toFileObject(selectedFiles[i]);
            try {
                currentFileObject.copy(imagefolder, currentFileObject.getName(), currentFileObject.getExt());
            } catch (IOException ex) {
                //Exceptions.printStackTrace(ex);
                JOptionPane.showMessageDialog(null, "The following error occured" +ex.getMessage());
            }
            }
        }
        
    }
}
