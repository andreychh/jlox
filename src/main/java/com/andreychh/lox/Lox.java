package com.andreychh.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.andreychh.lox.lexing.LexingFSM;
import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.source.Source;
import com.andreychh.lox.source.TextSource;

/**
 * The main Lox interpreter application class.
 * <p>
 * This class serves as the primary interface for the Lox language interpreter. It processes command-line arguments to
 * determine the execution mode.
 */
public final class Lox {
    private final String[] args;

    /**
     * Constructs a new Lox application instance.
     *
     * @param args The command-line arguments.
     */
    public Lox(final String[] args) {
        this.args = args.clone();
    }

    /**
     * Executes the interpreter based on the provided arguments.
     * <p>
     * If no arguments are provided, it starts the REPL. Otherwise, it attempts to run the script specified by the first
     * argument.
     */
    public void exec() {
        if (this.args.length == 0) {
            this.runREPL();
        } else {
            this.runFile(this.args[0]);
        }
    }

    /**
     * Starts an interactive Read-Eval-Print Loop (REPL).
     * <p>
     * It reads lines of code from the standard input, executes them, and prints the results until the program is
     * terminated.
     */
    private void runREPL() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
            while (true) {
                System.out.print(">>> ");
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                this.run(new TextSource(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from input", e);
        }
    }

    /**
     * Executes a Lox script from a file.
     *
     * @param path The path to the script file
     */
    private void runFile(final String path) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(path));
            this.run(new TextSource(new String(bytes, Charset.defaultCharset())));
        } catch (IOException e) {
            throw new RuntimeException("Could not read file '%s'".formatted(path), e);
        }
    }

    /**
     * Runs the lexical analysis on a given source and prints the result.
     *
     * @param source The source code to process
     */
    private void run(final Source source) {
        LexingResult result = new LexingFSM(source).tokenize();
        System.out.println("Errors:");
        result.errors().forEach(e -> System.out.println(e.format()));
        System.out.println("Tokens:");
        result.tokens().forEach(t -> System.out.println(t.toString()));
    }
}
