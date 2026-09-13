package builder;

/**
 * Director.
 *
 * Encapsulates known, reusable build sequences so client code doesn't
 * need to repeat the same chain of builder calls for common scenarios.
 *
 * Clean Code #2 (continued) — Small, focused class:
 * MatchReportDirector has exactly one responsibility — orchestrating
 * pre-defined report "recipes" — and knows nothing about how
 * MatchReport is assembled internally.
 */
public class MatchReportDirector {

    /**
     * Standard regular-season game report: score and teams only,
     * no MVP or highlights yet (published right after the final buzzer).
     */
    public MatchReport buildRegularSeasonReport(MatchReportBuilder builder,
                                                 String homeTeam, String awayTeam,
                                                 int homeScore, int awayScore) {
        return builder
                .withHomeTeam(homeTeam)
                .withAwayTeam(awayTeam)
                .withScore(homeScore, awayScore)
                .build();
    }

    /**
     * Full playoff/final report: includes venue, MVP and highlight
     * reel, published later for media once footage is edited.
     */
    public MatchReport buildFinalsReport(MatchReportBuilder builder,
                                          String homeTeam, String awayTeam,
                                          int homeScore, int awayScore,
                                          String venue, String mvp,
                                          String... highlights) {
        builder.withHomeTeam(homeTeam)
               .withAwayTeam(awayTeam)
               .withScore(homeScore, awayScore)
               .withVenue(venue)
               .withMvp(mvp);
        for (String highlight : highlights) {
            builder.addHighlight(highlight);
        }
        return builder.build();
    }
}
