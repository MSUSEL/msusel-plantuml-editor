package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import net.sourceforge.plantuml.FileFormat;

public class ExportStarXMI extends ExportXMI {

    public ExportStarXMI(PlantUMLEditor owner) {
        super(owner, "Export StarUML XMI", "Exports StarUML XMI", 'S');
    }

    @Override
    protected FileFormat fileFormat() {
        return FileFormat.XMI_STAR;
    }
}
