/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.dnaworkspace;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JOptionPane;
import org.bioalg.DNAProject.ProjectUIManager;
import org.bioalg.DNAProject.SequenceProject;
import org.netbeans.api.project.Project;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "Build",
id = "org.bioalg.dnaworkspace.DownloadRTActions")
@ActionRegistration(iconBase = "org/bioalg/dnaworkspace/download.png",
displayName = "#CTL_DownloadRTActions")
@ActionReferences({
    @ActionReference(path = "Toolbars/File", position = 350)
})
@Messages("CTL_DownloadRTActions=Download repeats and types base files form the Internet")
public final class DownloadRTActions implements ActionListener {

    private final Project context;
    URL types;
    URL repeats;

    public DownloadRTActions(Project context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        try {
            // TODO use context
            // load urls
            types= new URL("http://spa.ridom.de/dynamic/spatypes.txt");
            repeats= new URL("http://spa.ridom.de/dynamic/sparepeats.fasta");
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
            JOptionPane.showMessageDialog(null, "An error occured while forming the download request!");
            return;
        }
        
        // intiate a download request by the filedownloader
        // get the main project
        SequenceProject sequenceProject= ProjectUIManager.getMainProject();
        if(sequenceProject==null) return;
        // get types and repeats folder
        FileObject rtFileObject=sequenceProject.getTRFolder(true);
        if(rtFileObject==null) return;
            
        FileDownloader.downloadAndSave(types, rtFileObject);
        FileDownloader.downloadAndSave(repeats, rtFileObject);
    }
}
