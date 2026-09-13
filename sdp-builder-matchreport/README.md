# Builder Pattern — Basketball Match Report

**Course:** Software Design Patterns — Assignment #1 (Builder)
**Author:** Kuanysh, BDA-2502, Astana IT University
**Language:** Java 21

## 1. What the product is

`MatchReport` is a media-ready report about a basketball game: two teams,
final score, venue, MVP and a list of highlight clips. This kind of object
genuinely benefits from step-by-step construction because:

- Not every field is known at the same time (score is known right after
  the game, MVP/highlights only once footage is edited later).
- Some reports are minimal (quick regular-season score update), others
  are rich (full finals report with MVP + highlights) — same Product,
  different construction sequences.
- The object should be **immutable** once published, so it can't be
  built with a telescoping constructor or mutable setters.

## 2. Pattern components

| Component | Class | Responsibility |
|---|---|---|
| Product | `MatchReport` | Immutable result object |
| Builder | `MatchReportBuilder` | Fluent, validated step-by-step construction |
| Director | `MatchReportDirector` | Known recipes: `buildRegularSeasonReport`, `buildFinalsReport` |
| Client | `Main` | Demonstrates manual building, Director use, and validation failure |

## 3. Clean Code principles applied

### 1. Meaningful, intention-revealing names
Builder methods read like a sentence: `withHomeTeam(...).withAwayTeam(...).withScore(...)`.

```java
// Before (unclear, positional)
new MatchReport("Astana Hawks", "Almaty Falcons", 88, 82, null, null, List.of());

// After (self-documenting)
new MatchReportBuilder()
    .withHomeTeam("Astana Hawks")
    .withAwayTeam("Almaty Falcons")
    .withScore(88, 82)
    .build();
```

### 2. Small methods, each doing one thing
Every `with...` method sets exactly one field; validation lives in its own
`validate()` method instead of being inlined inside `build()`.

```java
public MatchReportBuilder withScore(int homeScore, int awayScore) {
    this.homeScore = homeScore;
    this.awayScore = awayScore;
    return this;
}

public MatchReport build() {
    validate();               // one job: check invariants
    return new MatchReport(...); // one job: assemble the product
}
```

### 3. No magic numbers/strings
Default values are named constants, not bare literals scattered in the code.

```java
// Before
private String venue = "TBD Arena"; // literal repeated wherever a default is needed

// After
private static final String DEFAULT_VENUE = "TBD Arena";
private String venue = DEFAULT_VENUE;
```

### 4. Validated construction
`build()` throws a clear, specific exception instead of silently producing
a broken report.

```java
private void validate() {
    if (homeTeam == null || homeTeam.isBlank()) {
        throw new IllegalStateException("Home team must be set before building a MatchReport");
    }
    if (homeTeam.equals(awayTeam)) {
        throw new IllegalStateException("Home team and away team must be different");
    }
    ...
}
```

### 5. Immutability of the Product
`MatchReport` has only `final` fields, no public setters, and its
constructor is package-private — the only way to obtain an instance is
through `MatchReportBuilder#build()`.

```java
public final class MatchReport {
    private final String homeTeam;
    ...
    MatchReport(...) { ... } // package-private, not called directly
}
```

## 4. How to run

```bash
javac -d out src/builder/*.java
java -cp out builder.Main
```

## 5. Project structure

```
sdp-builder-matchreport/
├── README.md
└── src/
    └── builder/
        ├── MatchReport.java          (Product)
        ├── MatchReportBuilder.java   (Builder)
        ├── MatchReportDirector.java  (Director)
        └── Main.java                 (Client)
```
