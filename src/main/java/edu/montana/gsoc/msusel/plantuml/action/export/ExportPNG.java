package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import edu.montana.gsoc.msusel.plantuml.action.AbstractExportAction;
import net.sourceforge.plantuml.FileFormat;

public class ExportPNG extends AbstractExportAction {

    public ExportPNG(PlantUMLEditor owner) {
        super(owner, "Export PNG", "Exports PNG image of UML", 'P');
    }

    @Override
    protected String getFileDescription() {
        return "PNG Files";
    }

    @Override
    protected String getFileExtension() {
        return "png";
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.PNG;
    }
}
