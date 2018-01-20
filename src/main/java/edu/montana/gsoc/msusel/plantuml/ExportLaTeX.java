package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportLaTeX extends AbstractExportAction {

    public ExportLaTeX(PlantUMLViewer owner) {
        super(owner, "Export LaTeX", "Exports Latex version of UML", 'L');
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
