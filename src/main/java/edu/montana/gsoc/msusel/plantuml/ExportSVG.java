package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportSVG extends AbstractExportAction {

    public ExportSVG(PlantUMLViewer owner) {
        super(owner, "Exprot SVG", "Exports SVG images of UML", 'V');
    }

    @Override
    protected String getFileDescription() {
        return "SVG Files";
    }

    @Override
    protected String getFileExtension() {
        return "svg";
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.SVG;
    }
}
