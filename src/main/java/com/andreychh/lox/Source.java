package com.andreychh.lox;

/**
 * todo: make StringSource immutable
 */
public final class Source {
    private final String text;
    private int position;

    public Source(final String text) {
        this.text = text;
    }

    public char peek(int offset) {
        return this.text.charAt(position + offset);
    }

    public boolean canPeek(int offset) {
        int position = this.position + offset;
        return position >= 0 && position < this.text.length();
    }

    public void skip(int offset) {
        this.position += offset;
    }

    public boolean canSkip(int offset) {
        int position = this.position + offset;
        return position >= 0 && position <= this.text.length();
    }

    public Position position() {
        int line = 1;
        int column = 1;
        for (int i = 0; i < this.position; i++) {
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
