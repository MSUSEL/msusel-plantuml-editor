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
import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Action to Open an existing PlantUML Specification
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
public class OpenAction extends AbstractFileAction {

    /**
     * Constructs a new OpenAction for the provided PlantUMLEditor
     *
     * @param owner Owner of this OpenAction
     */
    public OpenAction(PlantUMLEditor owner) {
        super(owner,
                "Open",
                "Open an existing PlantUML Specification",
                FontIcon.of(Material.OPEN_IN_NEW, 16, Color.BLACK),
                KeyEvent.VK_O,
                KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileFilter(new FileNameExtensionFilter("Plant UML Specification", "puml", "plt"));
        int result = jfc.showOpenDialog(owner);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String title = f.getName();
            String path = f.getAbsolutePath();

            String text = "";
            try {
                StringBuilder builder = new StringBuilder();
                List<String> lines = Files.readAllLines(Paths.get(path));
                lines.forEach(line -> builder.append(line + "\n"));
                text = builder.toString();
            } catch (IOException x) {
                log.error(x.getMessage());
            }

            owner.openTab(path, title, text);
        }
    }
}
