package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import net.sourceforge.plantuml.FileFormat;

public class ExportStdXMI extends ExportXMI {

    public ExportStdXMI(PlantUMLEditor owner) {
        super(owner, "Export Standard XMI", "Exports Standard XMI", 'N');
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.XMI_STANDARD;
    }
}
