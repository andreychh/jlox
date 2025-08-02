package com.andreychh.lox;


public final class Position {
    private final int line;
    private final int column;

    public Position(final int line, final int column) {
        this.line = line;
        this.column = column;
    }

    public String format() {
        return "%d:%d".formatted(this.line, this.column);
    }

    @Override
    public String toString() {
        return this.format();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        return line == other.line && column == other.column;
    }
}
