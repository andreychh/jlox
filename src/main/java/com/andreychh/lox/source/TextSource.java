package com.andreychh.lox.source;

import com.andreychh.lox.Position;

/**
 * TextSource is an implementation of {@link Source} that reads from a {@link String}.
 * <p>
 * {@snippet :
 * Source input = new TextSource("example");
 * boolean hasTwo = input.hasNext(2); // true
 * String secondChar = input.peek(1); // "x"
 * Fragment firstThree = input.take(3); // value="exa", remaining="mple"
 * Source afterTwo = input.skip(2); // remaining="ample"
 *}
 */
public final class TextSource implements Source {
    private final String text;
    private final int offset;

    /**
     * Constructs a TextSource with the given text and offset.
     *
     * @param text   The source text
     * @param offset The starting offset
     */
    private TextSource(final String text, final int offset) {
        this.text = text;
        this.offset = offset;
    }

    /**
     * Constructs a TextSource starting at offset 0.
     *
     * @param text The source text
     */
    public TextSource(final String text) {
        this(text, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext(final int count) {
        return this.offset + count <= this.text.length();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String peek(final int offset) {
        return this.text.substring(this.offset + offset, this.offset + offset + 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fragment take(final int count) {
        return new Fragment(
                this.text.substring(this.offset, this.offset + count),
                this.skip(count)
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Source skip(final int count) {
        return new TextSource(this.text, this.offset + count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position position() {
        int line = 1;
        int column = 1;
        for (int i = 0; i < this.offset; i++) {
            if (this.text.charAt(i) == '\n') {
                line++;
                column = 1;
            } else {
                column++;
            }
        }
        return new Position(line, column);
    }
}
