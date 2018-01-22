/**
 * MIT License
 *
 * Copyright (c) 2018 Montana State University, Gianforte School of Computing,
 * Software Engineering Laboratory
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
 * Action to close all unmodified tabs
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
public class CloseAllUnmodifiedTabsAction extends AbstractFileAction {

    /**
     * Construct a new NewAction attached to the given PlantUMLEditor
     *
     * @param owner Owner of this Action
     */
    public CloseAllUnmodifiedTabsAction(PlantUMLEditor owner) {
        super(owner,
                "Close All Unmodified Tabs",
                "Close all the unmodified tabs",
                FontIcon.of(Material.CLOSE, 16, Color.BLACK),
                KeyEvent.VK_L,
                KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        owner.closeAllUnmodifiedTabs();
    }
}
