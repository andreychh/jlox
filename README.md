# Java implementation of Lox language

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=andreychh_jlox&metric=alert_status&token=d60e5c15147d07bfccf66760ad3cd8aa01005891)](https://sonarcloud.io/summary/new_code?id=andreychh_jlox)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=andreychh_jlox&metric=bugs&token=d60e5c15147d07bfccf66760ad3cd8aa01005891)](https://sonarcloud.io/summary/new_code?id=andreychh_jlox)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=andreychh_jlox&metric=code_smells&token=d60e5c15147d07bfccf66760ad3cd8aa01005891)](https://sonarcloud.io/summary/new_code?id=andreychh_jlox)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=andreychh_jlox&metric=coverage&token=d60e5c15147d07bfccf66760ad3cd8aa01005891)](https://sonarcloud.io/summary/new_code?id=andreychh_jlox)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=andreychh_jlox&metric=duplicated_lines_density&token=d60e5c15147d07bfccf66760ad3cd8aa01005891)](https://sonarcloud.io/summary/new_code?id=andreychh_jlox)

Lox is a dynamically typed programming language from Robert
Nystrom's [Crafting Interpreters](https://craftinginterpreters.com/) featuring:

- Variables and block scoping
- Control flow constructs (if/else, loops)
- First-class functions and closures
- Classes with inheritance

A detailed language specification can be found in
the [Lox Language](https://craftinginterpreters.com/the-lox-language.html) chapter.

## Quick Start

Install [Java 21+](https://www.oracle.com/java/technologies/downloads/)
and [GNU Make](https://www.gnu.org/software/make/).

Then build the project:

```bash
make
```

Run jlox with a path to your Lox script file:

```bash
./jlox script.lox
```

Run jlox without arguments to enter interactive mode:

```bash
./jlox
```

In this mode, you can enter Lox expressions and immediately see the result of their execution:

```text
>>> print "Hello, world!";
Hello, world!
>>> var x = 42;
>>> print x;
42
```

## Current Status

- ✅ **Lexer**: FSM-based implementation
- ⏳ **Parser**: Planned
- ⏳ **Interpreter**: Planned

## Contributing

Contributions are welcome! Before submitting changes, run `make test` and `make validate` to ensure everything works
correctly. New features should include tests and follow the design principles outlined above.

## Acknowledgments

Based on [Crafting Interpreters](https://craftinginterpreters.com/) by Robert Nystrom.
