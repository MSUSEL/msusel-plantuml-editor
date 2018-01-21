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
package edu.montana.gsoc.msusel.plantuml.action;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;

import javax.swing.*;

/**
 * A base class for FileActions
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
public abstract class AbstractFileAction extends AbstractAction {

    protected PlantUMLEditor owner;

    /**
     * Constructs a new FileAction attached to the provided PlantUMLEditor, with the given display name,
     * the given short description, the given icon, the given mnemonic, and the given Shortcut Key.
     *
     * @param owner       The owner of this action
     * @param name        The displayed name of the action
     * @param description The short description
     * @param icon        The Icon displayed for this action
     * @param mnemonic    The mnemonic for this action
     * @param accelerator The shortcut key sequence for this action
     */
    public AbstractFileAction(PlantUMLEditor owner, String name, String description, Icon icon, int mnemonic, KeyStroke accelerator) {
        super(name, icon);
        this.putValue(AbstractAction.MNEMONIC_KEY, mnemonic);
        this.putValue(AbstractAction.ACCELERATOR_KEY, accelerator);
        this.putValue(AbstractAction.ACCELERATOR_KEY, accelerator);
        this.putValue(AbstractAction.SHORT_DESCRIPTION, description);
        this.owner = owner;
    }
}
