package com.andreychh.lox.source;

/**
 * Fragment represents a piece of text taken from a {@link Source}, along with the remaining {@link Source}.
 * <p>
 * {@snippet :
 * Source input = new TextSource("abc");
 * Fragment fragment = input.take(2);
 * String value = fragment.value(); // "ab"
 * Source rest = fragment.remaining();
 *}
 *
 * @param value     The extracted string fragment.
 * @param remaining The Source after extraction.
 */
public record Fragment(String value, Source remaining) {
}
