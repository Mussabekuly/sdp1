package builder;

public class MatchReportDirector {

    /**
     * Standard regular-season game report: score and teams only,
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
