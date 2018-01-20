package edu.montana.gsoc.msusel.plantuml.action.export;

import edu.montana.gsoc.msusel.plantuml.PlantUMLEditor;
import edu.montana.gsoc.msusel.plantuml.action.AbstractExportAction;

public abstract class ExportXMI extends AbstractExportAction {

    public ExportXMI(PlantUMLEditor owner, String title, String desc, char mnemonic) {
        super(owner, title, desc, mnemonic);
    }

    @Override
    protected String getFileDescription() {
        return "XMI Files";
    }

    @Override
    protected String getFileExtension() {
        return "xmi";
    }
}
