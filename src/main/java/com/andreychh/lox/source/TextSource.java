package com.andreychh.lox.source;

import com.andreychh.lox.Position;

/**
 * An immutable source of characters backed by a {@link String}. It provides character-by-character consumption and
 * tracks the current line and column position efficiently.
 * <p>
 * {@snippet :
 * Source source = new TextSource("var a = 1;");
 * while (source.hasNext()) {
 *     char c = source.peek();
 *     // Process the character 'c'
 *     source = source.next();
 * }
 *}
 *
 * @see Source
 */
public class TextSource implements Source {
    /**
     * The full source text.
     */
    private final String text;
    /**
     * The current offset in the text.
     */
    private final int offset;
    /**
     * The cached current position (line and column).
     */
    private final Position position;

    /**
     * Primary constructor for internal state transitions.
     *
     * @param text     The full source text.
     * @param offset   The current offset in the text.
     * @param position The current position (line, column).
     */
    private TextSource(final String text, final int offset, final Position position) {
        this.text = text;
        this.offset = offset;
        this.position = position;
    }

    /**
     * Creates a new source from a string, starting at the beginning.
     *
     * @param text The full source text.
     */
    public TextSource(final String text) {
        this(text, 0, new Position(1, 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char peek() {
        if (!this.hasNext()) {
            throw new IllegalStateException(
                    ("Cannot peek character: end of source reached. Attempted to read at"
                            + "offset %d, but source length is %d. ").formatted(this.offset, this.text.length())
            );
        }
        return this.text.charAt(this.offset);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return this.offset < this.text.length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Source next() {
        if (!this.hasNext()) {
            throw new IllegalStateException(
                    "Cannot advance source: end of source reached. Attempted to advance past offset %d."
                            .formatted(this.offset - 1)
            );
        }
        Position position = switch (this.peek()) {
            case '\n' -> this.position.addLine();
            default -> this.position.addColumn();
        };
        return new TextSource(this.text, this.offset + 1, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position position() {
        return this.position;
    }
}
