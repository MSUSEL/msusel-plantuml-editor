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
package edu.montana.gsoc.msusel.plantuml;

import com.google.common.collect.Lists;
import edu.montana.gsoc.msusel.plantuml.action.export.*;
import edu.montana.gsoc.msusel.plantuml.action.file.*;
import edu.montana.gsoc.msusel.plantuml.action.help.AboutAction;
import edu.montana.gsoc.msusel.plantuml.components.AboutDialog;
import edu.montana.gsoc.msusel.plantuml.components.ButtonTabComponent;
import edu.montana.gsoc.msusel.plantuml.components.PlantUMLTab;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A Quick and Dirty PlantUML Editor
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
public class PlantUMLEditor extends JFrame {

    /**
     * The tab pane for each open document
     */
    private JTabbedPane tabs;
    /**
     * The About Dialog
     */
    private AboutDialog aboutDialog;

    /**
     * Constructs a new PlantUML Editor
     */
    public PlantUMLEditor() {
        this(Lists.newArrayList());
    }

    /**
     * Constructs a new PlantUML Editor
     */
    public PlantUMLEditor(List<String> files) {
        super("PlantUML Editor");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        initMenuBar();
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);

        loadFiles(files);
    }

    /**
     * Creates the content area
     */
    private void initComponents() {
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(tabs = new JTabbedPane(), BorderLayout.CENTER);
        tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        tabs.addContainerListener(new ContainerListener() {
            @Override
            public void componentAdded(ContainerEvent e) {

            }

            @Override
            public void componentRemoved(ContainerEvent e) {
                Component c = e.getChild();
                Component p = e.getComponent();

                if (c instanceof PlantUMLTab) {
                    PlantUMLTab tab = (PlantUMLTab) c;
                    if (tab.isDirty()) {
                        int result = JOptionPane.showConfirmDialog(PlantUMLEditor.this, "The file " + tab.getTitle() + " has changed. Do you want to save it?", "Save File?", JOptionPane.YES_NO_CANCEL_OPTION);
                        switch (result) {
                            case JOptionPane.YES_OPTION:
                                //saveTab(tab);
                                break;
                            case JOptionPane.CANCEL_OPTION:

                        }
                    }
                }
            }
        });

        aboutDialog = new AboutDialog(this);
    }

    /**
     * Constructs the menu bar
     */
    private void initMenuBar() {
        JMenuBar jmb = new JMenuBar();

        JMenu mnuFile = new JMenu("File");
        mnuFile.setMnemonic('F');
        JMenu export = new JMenu("Export");
        export.setMnemonic('T');
        JMenu mnuHelp = new JMenu("Help");
        mnuHelp.setMnemonic('H');

        mnuFile.add(new NewAction(this));
        mnuFile.add(new OpenAction(this));
        mnuFile.addSeparator();
        mnuFile.add(new SaveAction(this));
        mnuFile.add(new SaveAsAction(this));
        mnuFile.add(export);
        mnuFile.addSeparator();
        mnuFile.add(new ExitAction(this));

        export.add(new ExportPNG(this));
        export.add(new ExportSVG(this));
        export.add(new ExportHTML(this));
        export.add(new ExportLaTeX(this));
        export.add(new ExportPDF(this));
        export.add(new ExportStdXMI(this));
        export.add(new ExportStarXMI(this));
        export.add(new ExportArgoXMI(this));

        mnuHelp.add(new AboutAction(this));

        jmb.add(mnuFile);
        jmb.add(mnuHelp);

        this.setJMenuBar(jmb);
    }

    /**
     * @return the UML Text from the currently selected tab
     */
    public String getPlantUMLText() {
        // TODO Should work with a document
        String text = "";

        if (tabs.getTabCount() > 0) {
            text = ((PlantUMLTab) tabs.getSelectedComponent()).plantUMLText();
        }

        return text;
    }

    /**
     * Opens a new tab for the given path, with the given title, and containing the given text
     * // TODO  this should be constrained to a PlantUMLDocument
     * @param path the path of the file
     * @param title the title of the tab
     * @param text the PlantUML text
     */
    public void openTab(String path, String title, String text) {
        PlantUMLTab tab = null;
        if (path == null && title == null) {
            tab = new PlantUMLTab(tabs);
        } else {
            tab = new PlantUMLTab(tabs, path, title, text);
        }
        tabs.add(tab.getTitle(), tab);
        tabs.setTabComponentAt(tabs.getTabCount() - 1, new ButtonTabComponent(tabs));
    }

    /**
     * show the about dialog
     */
    public void showAbout() {
        aboutDialog.setVisible(true);
    }

    /**
     * loads the files as tabs in the editor.
     * @param paths List of file names to open
     */
    public void loadFiles(List<String> paths) {
        paths.forEach(file -> {
            Path path = Paths.get(file);
            if (Files.exists(path) && Files.isRegularFile(path)) {
                StringBuilder builder = new StringBuilder();
                try {
                    Files.readAllLines(path).forEach(line -> builder.append(line + "\n"));
                } catch (IOException e) {
                    log.error("Could not read file: " + path.toAbsolutePath().toString());
                }
                openTab(path.toAbsolutePath().toString(), path.getFileName().toString(), builder.toString());
            } else {
                log.warn("File " + file + " does not exist or is not a regular file.");
            }
        });
    }
}
