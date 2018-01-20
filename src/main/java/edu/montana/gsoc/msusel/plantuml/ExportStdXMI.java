package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportStdXMI extends ExportXMI {

    public ExportStdXMI(PlantUMLViewer owner) {
        super(owner, "Export Standard XMI", "Exports Standard XMI", 'N');
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.XMI_STANDARD;
    }
}
