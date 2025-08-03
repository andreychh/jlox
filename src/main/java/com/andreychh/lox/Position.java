package com.andreychh.lox;

import java.util.Objects;

/**
 * Represents a position in the source code.
 * <p>
 * This class encapsulates line and column information to precisely locate
 * tokens and errors in the source text.
 */
public final class Position {
    private final int line;
    private final int column;

    /**
     * Creates a new position with the specified line and column.
     *
     * @param line   The 1-based line number in the source code
     * @param column The 1-based column number in the source code
     */
    public Position(final int line, final int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * Returns a formatted string representation of this position.
     *
     * @return A string in the format "line:column"
     */
    public String format() {
        return "%d:%d".formatted(this.line, this.column);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.format();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        return this.line == other.line && this.column == other.column;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.line, this.column);
    }
}
