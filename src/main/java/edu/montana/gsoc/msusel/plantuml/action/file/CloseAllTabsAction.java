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
 * Action to close all currently open tabs
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
public class CloseAllTabsAction extends AbstractFileAction {

    /**
     * Construct a new NewAction attached to the given PlantUMLEditor
     *
     * @param owner Owner of this Action
     */
    public CloseAllTabsAction(PlantUMLEditor owner) {
        super(owner,
                "Close All",
                "Close All open tabs",
                FontIcon.of(Material.CLOSE, 16, Color.BLACK),
                KeyEvent.VK_C,
                KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        owner.closeAllTabs();
    }
}
