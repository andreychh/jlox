package com.andreychh.lox.lexing.state;

import com.andreychh.lox.lexing.LexingResult;
import com.andreychh.lox.lexing.LexingStep;

public final class TerminatedState implements LexingState {
    private final LexingResult result;

    public TerminatedState(final LexingResult result) {
        this.result = result;
    }

    @Override
    public LexingStep next() {
        return new LexingStep(this, true);
    }

    @Override
    public LexingResult collectResult() {
        return this.result;
    }
}
