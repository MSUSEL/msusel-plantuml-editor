package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import net.sourceforge.plantuml.FileFormat;

public class ExportArgoXMI extends ExportXMI {

    public ExportArgoXMI(PlantUMLEditor owner) {
        super(owner, "Export ArgoUML XMI", "Exports ArgoUML XMI", 'A');
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.XMI_ARGO;
    }
}
