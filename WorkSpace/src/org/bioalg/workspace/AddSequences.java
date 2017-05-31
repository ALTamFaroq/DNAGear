/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bioalg.workspace;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.netbeans.api.project.Project;

import org.openide.awt.ActionRegistration;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "DNAProject",
id = "org.bioalg.workspace.AddSequences")
@ActionRegistration(iconBase = "org/bioalg/workspace/S+.png",
displayName = "#CTL_AddSequences")
@ActionReferences({
    @ActionReference(path = "Toolbars/File", position = 50)
})
@Messages("CTL_AddSequences=Add sequence files to the project")
public final class AddSequences implements ActionListener {

    private final Project context;

    public AddSequences(Project context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
    }
}
