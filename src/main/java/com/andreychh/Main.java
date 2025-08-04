package com.andreychh;

import com.andreychh.lox.Lox;

/**
 * The entry point for the jlox interpreter.
 * <p>
 * This class is responsible for bootstrapping the application. It follows the
 * principle of having a minimal main method, delegating all logic to the Lox
 * application class.
 */
public final class Main {
    /**
     * The entry point of the application.
     *
     * @param args Command line arguments, which can be a path to a script file.
     */
    public static void main(final String[] args) {
        new Lox(args).exec();
    }
}
