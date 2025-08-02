package com.andreychh.lox;

import java.util.function.Predicate;


public final class Source {
    private final String text;
    private final int offset;

    public Source(final String text, final int offset) {
        this.text = text;
        this.offset = offset;
    }

    public Source(final String text) {
        this(text, 0);
    }

    public char peek(final int offset) {
        return this.text.charAt(this.offset + offset);
    }

    public boolean canPeek(final int offset) {
        int position = this.offset + offset;
        return position >= 0 && position < this.text.length();
    }

    public Source skip(final int offset) {
        return new Source(this.text, this.offset + offset);
    }

    public boolean canSkip(final int offset) {
        int position = this.offset + offset;
        return position >= 0 && position <= this.text.length();
    }

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

    public Source skipWhile(final Predicate<Character> predicate) {
        int offset = this.offset;
        while (offset < this.text.length() && predicate.test(this.text.charAt(offset))) {
            offset++;
        }
        return new Source(this.text, offset);
    }

    public String sliceUntil(final Source end) {
        if (this.text != end.text) {
            throw new IllegalArgumentException("Cannot slice between different source texts.");
        }
        return this.text.substring(this.offset, end.offset);
    }
}
