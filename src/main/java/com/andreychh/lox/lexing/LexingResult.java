package com.andreychh.lox.lexing;

import com.andreychh.lox.Tokens;

/**
 * Represents the accumulated result at a specific step of the computation.
 */
public record LexingResult(Tokens tokens) {
}
