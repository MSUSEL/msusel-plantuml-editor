package edu.montana.gsoc.msusel.plantuml;

import de.sciss.syntaxpane.DefaultSyntaxKit;
import de.sciss.syntaxpane.syntaxkits.JavaSyntaxKit;
import lombok.Getter;
import lombok.Setter;
import net.sourceforge.plantuml.SourceStringReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class PlantUMLTab extends JPanel implements DocumentListener {

    @Getter
    @Setter
    private String title;
    private String path;
    private JEditorPane jepPlantUML;
    private JLabel lblUMLView;
    @Getter
    @Setter
    private boolean dirty;
    private JTabbedPane pane;

    public PlantUMLTab(JTabbedPane pane) {
        this(pane, null, "newfile.puml");
    }

    public PlantUMLTab(JTabbedPane pane, String path, String title) {
        this(pane, path, title, "");
    }

    public PlantUMLTab(JTabbedPane pane, String path, String title, String text) {
        this.pane = pane;
        this.path = path;
        this.title = title;
        dirty = false;
        setupComponents(text);
    }

    private void setupComponents(String text) {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        jepPlantUML = new JEditorPane();
        jepPlantUML.setText(text);
        jepPlantUML.getDocument().addDocumentListener(this);
        DefaultSyntaxKit kit = new JavaSyntaxKit();

        jepPlantUML.setEditorKit(kit);
        jepPlantUML.setFont(new Font("Courier", Font.PLAIN, 12));


        JScrollPane jsp = new JScrollPane();
        jsp.setViewportView(jepPlantUML);

        JScrollPane jspImage = new JScrollPane();
        lblUMLView = new JLabel();
        lblUMLView.setHorizontalAlignment(SwingConstants.CENTER);
        lblUMLView.setVerticalAlignment(SwingConstants.CENTER);
        jspImage.setViewportView(lblUMLView);

        JPanel pnlText = new JPanel();
        pnlText.setLayout(new BorderLayout());

        pnlText.add(jsp, BorderLayout.CENTER);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUML();
            }
        });

        pnlText.add(btnUpdate, BorderLayout.SOUTH);

        this.setLayout(new BorderLayout());

        split.setLeftComponent(jspImage);
        split.setRightComponent(pnlText);

        this.add(split, BorderLayout.CENTER);
        split.setDividerLocation(0.5);
        split.setDividerSize(5);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                split.setDividerLocation(0.5);
            }
        });
    }

    private void updateUML() {
        try {
            ByteArrayOutputStream png = new ByteArrayOutputStream();
            String source = jepPlantUML.getText();

            SourceStringReader reader = new SourceStringReader(source);
            reader.outputImage(png);
            png.flush();
            byte[] image = png.toByteArray();
            png.close();

            ByteArrayInputStream in = new ByteArrayInputStream(image);
            BufferedImage bufimg = ImageIO.read(in);
            in.close();

            ImageIcon icon = new ImageIcon(bufimg);
            lblUMLView.setIcon(icon);
        } catch (IOException e) {

        }
    }

    public String plantUMLText() {
        return jepPlantUML.getText();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        makeDirty();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        makeDirty();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        makeDirty();
    }

    private void makeDirty() {
        dirty = true;
        pane.getTabComponentAt(pane.getSelectedIndex()).setName("* " + title);
    }

    private void makeClean() {
        dirty = false;
        pane.getTabComponentAt(pane.getSelectedIndex()).setName(title);
    }

    public void save() {
        if (path == null) {
            saveAs();
        } else {
            Path p = Paths.get(path);
            Path bak = Paths.get(p.toAbsolutePath().toString(), ".bak");

            if (Files.exists(p)) {
                try {
                    Files.copy(p, bak, StandardCopyOption.REPLACE_EXISTING);
                    Files.deleteIfExists(p);
                } catch (IOException e) {

                }
            }

            try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(p))) {
                pw.println(this.jepPlantUML.getText());
            } catch (IOException e) {

            }

            if (!Files.exists(p)) {
                try {
                    Files.copy(bak, p, StandardCopyOption.REPLACE_EXISTING);
                    Files.deleteIfExists(bak);
                } catch (IOException e) {

                }
            }
        }

        makeClean();
    }

    public void saveAs() {

    }
}
