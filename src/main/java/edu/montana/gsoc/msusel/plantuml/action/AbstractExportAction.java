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
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The base class for PlantUML Export Actions
 *
 * TODO Shift towards using the Renderer
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
public abstract class AbstractExportAction extends AbstractAction {

    protected PlantUMLEditor owner;

    /**
     * Constructs a new ExportAction with the given icon, attached to the provided PlantUMLEditor,
     * with the given display title, short description, and mnemonic.
     *
     * @param icon        Icon associated with this action
     * @param owner       Owner of this action
     * @param title       String displayed by this action
     * @param description Description of this action
     * @param mnemonic    Mnemonic for this action
     */
    public AbstractExportAction(Icon icon, PlantUMLEditor owner, String title, String description, int mnemonic) {
        super(title, icon);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(SHORT_DESCRIPTION, description);
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setFileFilter(new FileNameExtensionFilter(getFileDescription(), getFileExtension()));

        int retVal = jfc.showSaveDialog(owner);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();

            exportFile(f);
        }
    }

    /**
     * @return The file description for files exported
     */
    protected abstract String getFileDescription();

    /**
     * @return The file extension for files exported
     */
    protected abstract String getFileExtension();

    /**
     * Exports the PlantUML rendering to the provided file using the Template Method approach.
     * @param f File to export.
     */
    protected void exportFile(File f) {
        String source = owner.getPlantUMLText();

        try (FileOutputStream os = new FileOutputStream(f)) {
            SourceStringReader reader = new SourceStringReader(source);

            // Write the first image to "os"
            reader.outputImage(os, new FileFormatOption(fileFormat()));
            os.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * @return The PlantUML fileformat for the exported rendering.
     */
    protected abstract FileFormat fileFormat();
}
