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
package edu.montana.gsoc.msusel.plantuml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Class to provide methods which handle files for rendering source PlantUML text to different file types.
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
@AllArgsConstructor
@Builder
public class PlantUMLRenderer {

    @Setter
    private FileFormat format;

    /**
     * Exports the provided source PlantUML rendering to the provided file using current file format
     *
     * @param source The source PlantUML specification text
     * @param path   File to export.
     */
    public void render(String source, Path path) {
        try (OutputStream os = Files.newOutputStream(path, StandardOpenOption.READ)) {
            SourceStringReader reader = new SourceStringReader(source);
            reader.outputImage(os, new FileFormatOption(format));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Extracts a string representing the entire content of the file with the provided path.
     *
     * @param file String representing the path of the file to extract the content of
     * @return String representation of the file contents.
     */
    public String extractText(String file) {
        StringBuilder builder = new StringBuilder();

        try {
            Path path = Paths.get(file);
            Files.readAllLines(path).forEach(line -> builder.append(line + "\n"));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return builder.toString();
    }
}
