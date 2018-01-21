/**
 * MIT License
 *
 * Copyright (c) 2018 Montana State University, Gianforte School of Computing,
 * Software Engineering Laboratory
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package edu.montana.gsoc.msusel.plantuml.components;

import de.sciss.syntaxpane.DefaultSyntaxKit;
import de.sciss.syntaxpane.syntaxkits.JavaSyntaxKit;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.SourceStringReader;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.swing.FontIcon;

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

/**
 * A Tab which allows for both the display of rendered PlantUML as well as the editing of the underlying display
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
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

    /**
     * Constructs a new PlantUMLTab as a part of the provided JTabbedPane
     *
     * @param pane The parent pane
     */
    public PlantUMLTab(JTabbedPane pane) {
        this(pane, null, "newfile.puml");
    }

    /**
     * Constructs a new PlantUMLTab as a part of the provided JTabbedPane
     *
     * @param pane  The parent pane
     * @param path  The file path of the specification to be editted
     * @param title The title of this tab (the name of the file)
     */
    public PlantUMLTab(JTabbedPane pane, String path, String title) {
        this(pane, path, title, "");
    }

    /**
     * Constructs a new PlantUMLTab as a part of the provided JTabbedPane
     *
     * @param pane  The parent pane
     * @param path  The file path of the specification to be editted
     * @param title The title of this tab (the name of the file)
     * @param text  The initial text of the specification
     */
    public PlantUMLTab(JTabbedPane pane, String path, String title, String text) {
        this.pane = pane;
        this.path = path;
        this.title = title;
        dirty = false;
        initComponents(text);
    }

    /**
     * Initializes the UI Components of this container
     *
     * @param text The initial text to display.
     */
    private void initComponents(String text) {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        jepPlantUML = new JEditorPane();
        jepPlantUML.getDocument().addDocumentListener(this);
        DefaultSyntaxKit kit = new JavaSyntaxKit();

        jepPlantUML.setEditorKit(kit);
        jepPlantUML.setFont(new Font("Courier", Font.PLAIN, 12));
        jepPlantUML.setText(text);

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

        JButton btnRender = new JButton("Render");
        btnRender.setIcon(FontIcon.of(Material.PLAY_CIRCLE_OUTLINE, 20, Color.BLACK));
        btnRender.setIconTextGap(5);
        btnRender.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnRender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUML();
            }
        });

        pnlText.add(btnRender, BorderLayout.SOUTH);

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
            log.error(e.getMessage());
        }
    }

    /**
     * Returns the text of the PlantUML specification being editted within this tab
     *
     * @return Text of the specification being editted
     */
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

    /**
     * Sets the dirty flag and updates title of this Tab
     */
    private void makeDirty() {
        dirty = true;
        pane.getTabComponentAt(pane.getSelectedIndex()).setName("* " + title);
    }

    /**
     * Unsets the dirty flag and updates title of this Tab
     */
    private void makeClean() {
        dirty = false;
        pane.getTabComponentAt(pane.getSelectedIndex()).setName(title);
    }

    /**
     * Saves the document currently being editted in this tab
     */
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
                    log.error(e.getMessage());
                }
            }

            try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(p))) {
                pw.println(this.jepPlantUML.getText());
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            if (!Files.exists(p)) {
                try {
                    Files.copy(bak, p, StandardCopyOption.REPLACE_EXISTING);
                    Files.deleteIfExists(bak);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }

        makeClean();
    }

    public void saveAs() {

    }
}
