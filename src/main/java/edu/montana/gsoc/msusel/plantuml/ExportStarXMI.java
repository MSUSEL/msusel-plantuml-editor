package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportStarXMI extends ExportXMI {

    public ExportStarXMI(PlantUMLViewer owner) {
        super(owner, "Export StarUML XMI", "Exports StarUML XMI", 'S');
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.XMI_STAR;
    }
}
