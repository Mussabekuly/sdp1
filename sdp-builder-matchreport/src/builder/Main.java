package builder;

/**
 * Client.
 *
 * Demonstrates the Builder pattern from three angles:
 *  1. Direct, fully manual use of MatchReportBuilder (fluent chain).
 *  2. Director reused for a "regular season" report recipe.
 *  3. Director reused for a "finals" report recipe.
 *  4. Validation failure — build() rejects an invalid report.
 */
public class Main {

    public static void main(String[] args) {

        // 1. Manual fluent build — full control, no Director.
        MatchReport manual = new MatchReportBuilder()
                .withHomeTeam("Astana Hawks")
                .withAwayTeam("Almaty Falcons")
                .withScore(88, 82)
                .withVenue("Barys Arena")
                .withMvp("A. Serik")
                .addHighlight("Buzzer-beater 3-pointer at Q4")
                .addHighlight("15-0 run in the 3rd quarter")
                .build();
        System.out.println(manual);

        // 2. Director — quick regular-season report.
        MatchReportDirector director = new MatchReportDirector();
        MatchReport regularSeason = director.buildRegularSeasonReport(
                new MatchReportBuilder(),
                "Karaganda Miners", "Shymkent Leopards",
                101, 97
        );
        System.out.println(regularSeason);

        // 3. Director — full finals report with highlights.
        MatchReport finals = director.buildFinalsReport(
                new MatchReportBuilder(),
                "Astana Hawks", "Karaganda Miners",
                95, 90,
                "National Arena, Astana",
                "D. Nurlanov",
                "Game-winning block in final seconds",
                "MVP scores 32 points"
        );
        System.out.println(finals);

        // 4. Validation in action — invalid report is rejected clearly.
        try {
            new MatchReportBuilder()
                    .withHomeTeam("Astana Hawks")
                    .withScore(-5, 10)
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("Build rejected as expected: " + e.getMessage());
        }
    }
}
