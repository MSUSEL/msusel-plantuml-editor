package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import edu.montana.gsoc.msusel.plantuml.action.AbstractExportAction;
import net.sourceforge.plantuml.FileFormat;

public class ExportHTML extends AbstractExportAction {

    public ExportHTML(PlantUMLEditor owner) {
        super(owner, "Export HTML", "Exports UML as HTML", 'H');
    }

    @Override
    protected String getFileDescription() {
        return "HTML Files";
    }

    @Override
    protected String getFileExtension() {
        return "html";
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.HTML;
    }
}
