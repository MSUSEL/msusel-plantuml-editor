package edu.montana.gsoc.msusel.plantuml.action;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractExportAction extends AbstractAction {

    protected PlantUMLEditor owner;

    public AbstractExportAction(PlantUMLEditor owner, String title, String description, char mnemonic) {
        super(title);
        //putValue(MNEMONIC_KEY, mnemonic);
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

    protected abstract String getFileDescription();

    protected abstract String getFileExtension();

    protected void exportFile(File f) {
        String source = owner.getPlantUMLText();

        try (FileOutputStream os = new FileOutputStream(f)) {
            SourceStringReader reader = new SourceStringReader(source);

            // Write the first image to "os"
            reader.outputImage(os, new FileFormatOption(fileFormat()));
            os.close();
        } catch (IOException e) {

        }
    }

    protected abstract FileFormat fileFormat();
}
