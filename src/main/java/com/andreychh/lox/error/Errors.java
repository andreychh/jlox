package com.andreychh.lox.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an immutable collection of {@link Error} objects.
 */
public final class Errors {
    private final List<Error> errors;

    /**
     * Constructs an {@code Errors} object with the provided list of errors.
     *
     * @param errors list of error objects to initialize the collection with
     */
    private Errors(final List<Error> errors) {
        this.errors = errors;
    }

    /**
     * Constructs an empty {@code Errors} object.
     */
    public Errors() {
        this(new ArrayList<>());
    }

    /**
     * Returns a new {@code Errors} instance with the specified error added.
     *
     * @param error error to add
     * @return new errors instance with the error added
     */
    public Errors withError(final Error error) {
        List<Error> errors = new ArrayList<>(this.errors);
        errors.add(error);
        return new Errors(errors);
    }

    /**
     * Returns a new {@code Errors} instance with all errors from another {@code Errors} object added.
     *
     * @param other errors object whose errors will be added
     * @return new errors instance with all errors from the other object added
     */
    public Errors withErrors(final Errors other) {
        List<Error> errors = new ArrayList<>(this.errors);
        errors.addAll(other.errors);
        return new Errors(errors);
    }

    /**
     * Returns internal list of errors.
     *
     * @return list of error objects
     */
    public List<Error> asList() {
        return this.errors;
    }
}
