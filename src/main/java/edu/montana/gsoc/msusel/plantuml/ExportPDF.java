package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportPDF extends AbstractExportAction {

    public ExportPDF(PlantUMLViewer owner) {
        super(owner, "Export PDF", "Exports PDF version of UML", 'D');
    }

    @Override
    protected String getFileDescription() {
        return "PDF Files";
    }

    @Override
    protected String getFileExtension() {
        return "pdf";
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.PDF;
    }
}
