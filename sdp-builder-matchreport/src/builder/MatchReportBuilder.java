package builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder.
 *
 * Builds a {@link MatchReport} step by step through a fluent API
 * (each method returns {@code this}), then validates and assembles
 * the final immutable Product in {@link #build()}.
 *
 * Clean Code #1 — Meaningful, intention-revealing names:
 * methods are named withHomeTeam / withAwayTeam / withScore / ...
 * so a call chain reads like a sentence, e.g.
 * new MatchReportBuilder().withHomeTeam("Astana Hawks").withScore(101, 97).build();
 *
 * Clean Code #2 — Small methods, each doing one thing:
 * every "with..." method sets exactly one piece of state; validation
 * logic lives in its own private method instead of being inlined
 * inside build().
 */
public class MatchReportBuilder {

    // Clean Code #3 — No magic numbers/strings:
    // default values are named constants instead of bare literals
    // scattered through the code.
    private static final String DEFAULT_VENUE = "TBD Arena";
    private static final int MIN_SCORE = 0;

    private String homeTeam;
    private String awayTeam;
    private int homeScore = MIN_SCORE;
    private int awayScore = MIN_SCORE;
    private String venue = DEFAULT_VENUE;
    private String mvp;
    private final List<String> highlights = new ArrayList<>();

    public MatchReportBuilder withHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public MatchReportBuilder withAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }

    public MatchReportBuilder withScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        return this;
    }

    public MatchReportBuilder withVenue(String venue) {
        this.venue = venue;
        return this;
    }

    public MatchReportBuilder withMvp(String mvp) {
        this.mvp = mvp;
        return this;
    }

    public MatchReportBuilder addHighlight(String highlight) {
        this.highlights.add(highlight);
        return this;
    }

    /**
     * Clean Code #4 — Validated construction:
     * build() throws a clear, specific exception on invalid state
     * instead of silently returning a broken/incomplete Product.
     */
    public MatchReport build() {
        validate();
        return new MatchReport(homeTeam, awayTeam, homeScore, awayScore, venue, mvp, highlights);
    }

    private void validate() {
        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalStateException("Home team must be set before building a MatchReport");
        }
        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalStateException("Away team must be set before building a MatchReport");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalStateException("Home team and away team must be different");
        }
        if (homeScore < MIN_SCORE || awayScore < MIN_SCORE) {
            throw new IllegalStateException("Scores cannot be negative");
        }
    }
}
