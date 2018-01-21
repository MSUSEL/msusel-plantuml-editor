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
package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import edu.montana.gsoc.msusel.plantuml.action.AbstractExportAction;
import net.sourceforge.plantuml.FileFormat;
import org.kordamp.ikonli.devicons.Devicons;
import org.kordamp.ikonli.swing.FontIcon;

import java.awt.*;

/**
 * Export Action to export PlantUML as LaTeX (with Pre-Amble)
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
public class ExportLaTeX extends AbstractExportAction {

    /**
     * Constructs a new ExportLatex Action for the given PlantUMLEditor
     * @param owner Owner of this action
     */
    public ExportLaTeX(PlantUMLEditor owner) {
        super(FontIcon.of(Devicons.GNU, 16, Color.BLACK), owner, "Export LaTeX", "Exports Latex version of UML", 'L');
    }

    @Override
    protected String getFileDescription() {
        return "LaTeX Files";
    }

    @Override
    protected String getFileExtension() {
        return "tex";
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.LATEX;
    }
}
