package edu.montana.gsoc.msusel.plantuml.action.toolbar;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import edu.montana.gsoc.msusel.plantuml.action.AbstractFileAction;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Renders the UML for the currently selected tab
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
public class RenderAction extends AbstractFileAction {

    /**
     * Construct a new NewAction attached to the given PlantUMLEditor
     *
     * @param owner Owner of this Action
     */
    public RenderAction(PlantUMLEditor owner) {
        super(owner,
                "Render",
                "Renders UML in selected tab",
                FontIcon.of(Material.PLAY_CIRCLE_OUTLINE, 20, Color.BLACK),
                KeyEvent.VK_R,
                KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        owner.renderCurrentTab();
    }
}
