package edu.montana.gsoc.msusel.plantuml.action;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OpenAction extends AbstractAction {

    private PlantUMLEditor owner;

    public OpenAction(PlantUMLEditor owner) {
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        int result = jfc.showOpenDialog(owner);

        if (result == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            String title = f.getName();
            String path = f.getAbsolutePath();

            String text = "";
            try {
                StringBuilder builder = new StringBuilder();
                List<String> lines = Files.readAllLines(Paths.get(path));
                lines.forEach(builder::append);
                text = builder.toString();
            } catch (IOException x) {

            }

            owner.openTab(path, title, text);
        }
    }
}
