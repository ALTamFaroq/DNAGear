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

@ActionID(category = "Build",
id = "org.bioalg.workspace.ProcessSequencesAction")
@ActionRegistration(iconBase = "org/bioalg/workspace/run.png",
displayName = "#CTL_ProcessSequencesAction")
@ActionReferences({
    @ActionReference(path = "Menu/BuildProject", position = -90),
    @ActionReference(path = "Toolbars/Build", position = -120)
})
@Messages("CTL_ProcessSequencesAction=Process sequenceses in the selected project")
public final class ProcessSequencesAction implements ActionListener {

    private final Project context;

    public ProcessSequencesAction(Project context) {
        this.context = context;
    }

    public void actionPerformed(ActionEvent ev) {
        // TODO use context
    }
}
