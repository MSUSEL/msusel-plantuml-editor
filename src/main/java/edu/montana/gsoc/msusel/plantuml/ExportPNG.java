package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportPNG extends AbstractExportAction {

    public ExportPNG(PlantUMLViewer owner) {
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
