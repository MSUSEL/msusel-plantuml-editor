package edu.montana.gsoc.msusel.plantuml;

import edu.montana.gsoc.msusel.plantuml.action.export.*;
import edu.montana.gsoc.msusel.plantuml.components.ButtonTabComponent;
import edu.montana.gsoc.msusel.plantuml.components.PlantUMLTab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

public class PlantUMLEditor extends JFrame {

    private JTabbedPane tabs;

    public PlantUMLEditor() {
        super("PlantUML Editor");

        createContentArea();
        constructMenuBar();
    }

    private void createContentArea() {
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
    }

    public static void main(String args[]) {

        JFrame frame = new PlantUMLEditor();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void constructMenuBar() {
        JMenuBar jmb = new JMenuBar();

        JMenu mnuFile = new JMenu("File");
        JMenuItem jmiNew = new JMenuItem("New");
        jmiNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTab(null, null, "");
            }
        });
        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiSave = new JMenuItem("Save");
        JMenuItem jmiSaveAs = new JMenuItem("Save As...");
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmiExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenu export = new JMenu("Export");
        export.add(new ExportPNG(this));
        export.add(new ExportSVG(this));
        export.add(new ExportHTML(this));
        export.add(new ExportLaTeX(this));
        export.add(new ExportPDF(this));
        export.add(new ExportStdXMI(this));
        export.add(new ExportStarXMI(this));
        export.add(new ExportArgoXMI(this));

        mnuFile.add(jmiNew);
        mnuFile.add(jmiOpen);
        mnuFile.addSeparator();
        mnuFile.add(jmiSave);
        mnuFile.add(jmiSaveAs);
        mnuFile.add(export);
        mnuFile.addSeparator();
        mnuFile.add(jmiExit);

        JMenu mnuHelp = new JMenu("Help");

        jmb.add(mnuFile);
        jmb.add(mnuHelp);

        this.setJMenuBar(jmb);
    }

    public String getPlantUMLText() {
        String text = "";

        if (tabs.getTabCount() > 0) {
            text = ((PlantUMLTab) tabs.getSelectedComponent()).plantUMLText();
        }

        return text;
    }

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

}
