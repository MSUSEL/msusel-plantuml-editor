package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public class ExportArgoXMI extends ExportXMI {

    public ExportArgoXMI(PlantUMLViewer owner) {
        super(owner, "Export ArgoUML XMI", "Exports ArgoUML XMI", 'A');
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.XMI_ARGO;
    }
}
