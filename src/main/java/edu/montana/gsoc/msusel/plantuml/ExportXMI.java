package edu.montana.gsoc.msusel.plantuml;

import net.sourceforge.plantuml.FileFormat;

public abstract class ExportXMI extends AbstractExportAction {

    public ExportXMI(PlantUMLViewer owner, String title, String desc, char mnemonic) {
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
