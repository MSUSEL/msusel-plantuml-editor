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

import lombok.extern.slf4j.Slf4j;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.swing.FontIcon;
import org.markdownj.MarkdownProcessor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Displays the About Information
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
public class AboutDialog extends JDialog {

    /**
     * Constructs a new AboutDialog attached to the provided JFrame
     *
     * @param parent JFrame parent of this dialog
     */
    public AboutDialog(JFrame parent) {
        super(parent, "About PlantUML Editor");
        initComponents();
    }

    /**
     * Initializes the UI components of this dialog
     */
    private void initComponents() {
        Container c = this.getContentPane();

        c.setLayout(new BorderLayout());

        JLabel logo = new JLabel();
        try {
            logo.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("logo.png"))));
        } catch (IOException e) {

        }
        logo.setVerticalAlignment(SwingConstants.CENTER);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        c.add(logo, BorderLayout.NORTH);

        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        jep.setOpaque(false);
        jep.setEditorKit(new HTMLEditorKit());
        jep.setDocument(MarkdownRenderer.render());

        c.add(jep, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setIcon(FontIcon.of(Material.CLOSE, 16, Color.RED));
        closeButton.setIconTextGap(5);
        closeButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        closeButton.setMnemonic('A');
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutDialog.this.setVisible(false);
            }
        });
        closeButton.setDefaultCapable(true);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        btnPanel.add(closeButton);

        c.add(btnPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.getRootPane().setDefaultButton(closeButton);

        this.pack();
        this.setSize(400, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    /**
     * Private internal class used to render the markdown in the "about.md" file into HTML for display in the pane.
     *
     * @author Isaac Griffith
     * @version 1.1.1
     */
    private static class MarkdownRenderer {

        /**
         * Renders markdown into HTML and returns an HTMLDocument used as the model for the main about text display.
         *
         * @return HTMLDocument representing the rendered Markdown
         */
        public static HTMLDocument render() {
            String markdown = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(AboutDialog.class.getResourceAsStream("about.md")))) {
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = br.readLine()) != null)
                    builder.append(line + "\n");

                markdown = builder.toString();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            MarkdownProcessor processor = new MarkdownProcessor();
            String html = processor.markdown(markdown);

            Reader stringReader = new StringReader(html);
            HTMLEditorKit htmlKit = new HTMLEditorKit();
            HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
            try {
                htmlKit.read(stringReader, htmlDoc, 0);
            } catch (BadLocationException | IOException e) {
                log.error(e.getMessage());
            }

            return htmlDoc;
        }
    }
}
