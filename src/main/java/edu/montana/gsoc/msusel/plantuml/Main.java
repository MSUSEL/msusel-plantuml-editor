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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.plantuml.FileFormat;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * Main class for the PlantUMLEditor and Renderer System
 *
 * @author Isaac Griffith
 * @version 1.1.1
 */
@Slf4j
public class Main {

    private static final Map<String, Pair<String, FileFormat>> FORMAT_EXT = Maps.newHashMap();

    static {
        FORMAT_EXT.put("png", new Pair<>("png", FileFormat.PNG));
        FORMAT_EXT.put("svg", new Pair<>("svg", FileFormat.SVG));
        FORMAT_EXT.put("xmi", new Pair<>("xmi", FileFormat.XMI_STANDARD));
        FORMAT_EXT.put("argo", new Pair<>("xmi", FileFormat.XMI_ARGO));
        FORMAT_EXT.put("star", new Pair<>("xmi", FileFormat.XMI_STAR));
        FORMAT_EXT.put("pdf", new Pair<>("pdf", FileFormat.PDF));
        FORMAT_EXT.put("html", new Pair<>("html", FileFormat.HTML5));
        FORMAT_EXT.put("latex", new Pair<>("tex", FileFormat.LATEX));
    }

    /**
     * Command line options object associated with this class.
     */
    private static final Options options;

    static {
        final Option help = Option.builder("h")
                .required(false)
                .longOpt("help")
                .desc("prints this message")
                .hasArg(false)
                .build();
        final Option format = Option.builder("f")
                .required(false)
                .longOpt("format")
                .desc("Select output format")
                .argName("FORMAT")
                .hasArg(true)
                .numberOfArgs(1)
                .build();
        final Option output = Option.builder("o")
                .required(false)
                .longOpt("output")
                .argName("DIRECTORY")
                .desc("Directory where the generated files should be saved.")
                .hasArg(true)
                .numberOfArgs(1)
                .build();
        options = new Options();
        Main.options.addOption(help);
        Main.options.addOption(format);
        Main.options.addOption(output);
    }

    /**
     * Controls the execution of Main given the command line object.
     *
     * @param line The parsed command line arguments.
     * @throws IOException If the file for output cannot be written to
     */
    static void execute(final CommandLine line) throws IOException {
        String output = null;
        String format = "png";

        if (line.getOptions().length == 0) {
            processGUI(Lists.newArrayList());
        }

        if (line.hasOption('h')) {
            printHelp();
        }

        if (line.hasOption('o')) {
            output = line.getOptionValue('o');
        }

        if (line.hasOption('f')) {
            format = line.getOptionValue('f').toLowerCase();
        }

        List<String> input = line.getArgList();

        if (output != null) {
            processCLI(output, input, format);
        } else {
            processGUI(input);
        }
    }

    /**
     * Generates plant UML for the input files
     *
     * @param output The string representing the output directory path
     * @param input  List of strings representing input files
     * @param format The string representing the output file format
     */
    static void processCLI(String output, List<String> input, String format) {
        Path out = Paths.get(output);
        final PlantUMLRenderer renderer;

        if (FORMAT_EXT.containsKey(format)) {
            final String extension = FORMAT_EXT.get(format).getKey();
            renderer = PlantUMLRenderer.builder().format(FORMAT_EXT.get(format).getValue()).build();

            if (Files.exists(out) && Files.isDirectory(out)) {
                input.forEach(file -> {
                    Path inPath = Paths.get(file);
                    String fileName = inPath.getFileName().toString();
                    Path outPath = Paths.get(output, fileName.substring(0, fileName.lastIndexOf(".")) + "." + extension);
                    renderer.render(renderer.extractText(file), outPath);
                });
            } else {
                log.error("Output directory: " + output + ", either is not a directory or does not already exist.");
                log.error("No files were processed.");
            }
        } else {
            log.error("No files were processed, format does not match one of: png, svg, pdf, xmi, star, argo, html, or latex");
        }
    }

    /**
     * Executes the GUI
     *
     * @param input List of files to open
     */
    static void processGUI(List<String> input) {
        PlantUMLEditor puml = new PlantUMLEditor(input);
        puml.setVisible(true);
    }

    /**
     * Prints the help message
     */
    static void printHelp() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(
                "pumledit",
                "\nEdit or generate PlantUML.\n\n",
                Main.options,
                "\n(C) 2018, Montana State University, Gianforte School of Computing",
                true);
    }

    /**
     * Starting point of execution.
     *
     * @param args Raw command line arguments.
     */
    public static void main(final String... args) {
        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine line = parser.parse(Main.options, args);
            execute(line);
        } catch (final ParseException exp) {
            log.error(exp.getMessage());
            System.out.println("");
            printHelp();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
