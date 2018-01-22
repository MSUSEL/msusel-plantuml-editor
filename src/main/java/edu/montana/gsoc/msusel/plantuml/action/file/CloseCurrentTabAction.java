package edu.montana.gsoc.msusel.plantuml.action.file;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import edu.montana.gsoc.msusel.plantuml.action.AbstractFileAction;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Action to close the currently selected tab
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
public class CloseCurrentTabAction extends AbstractFileAction {

    /**
     * Construct a new NewAction attached to the given PlantUMLEditor
     *
     * @param owner Owner of this Action
     */
    public CloseCurrentTabAction(PlantUMLEditor owner) {
        super(owner,
                "Close Tab",
                "Close currently selected tab",
                FontIcon.of(Material.CLOSE, 16, Color.BLACK),
                KeyEvent.VK_E,
                KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        owner.closeCurrentTab();
    }
}
