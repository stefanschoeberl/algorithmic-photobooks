# Algorithmic Photo Books: Kotlin DSLs demystified

## Requirements and usage

JDK 21 or above is required to run the examples.

Note: Run all subsequent scripts within the `photobook-dsl` folder.

## Part 1: Photo Book DSL

Generate Photo Book defined in `dev/ssch/photobook/dsl/PhotoBookGenerator.kt` (`dsl` module) to `book.pdf`:

```bash
$ ./gradlew dsl:run
```

## Part 2: Kotlin Scripting

Generate Photo Book defined in `album.book.kts` to `album.pdf`

```bash
$ ./gradlew scripting:run --args=album.book.kts
```

Or by using the all-in-one jar:

```bash
$ ./gradlew scripting:allInOneJar
$ java -jar scripting/build/libs/photobook-generator-1.0.jar album.book.kts
```