package builder;

import java.util.Collections;
import java.util.List;

/**
 * Product.
 *
 * Represents a finished media report about a basketball game:
 * teams, final score, MVP and highlight clips ready to publish.
 *
 * Clean Code #5 — Immutability of the Product:
 * all fields are final and there are no public setters, so once a
 * MatchReport is built it cannot be silently mutated by other code.
 * The only way to create one is through MatchReportBuilder#build().
 */
public final class MatchReport {

    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final String venue;
    private final String mvp;
    private final List<String> highlights;

    // Package-private: only MatchReportBuilder is allowed to construct it.
    MatchReport(String homeTeam, String awayTeam, int homeScore, int awayScore,
                String venue, String mvp, List<String> highlights) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.venue = venue;
        this.mvp = mvp;
        this.highlights = Collections.unmodifiableList(highlights);
    }

    public String winner() {
        if (homeScore == awayScore) {
            return "Draw";
        }
        return homeScore > awayScore ? homeTeam : awayTeam;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== MATCH REPORT ===\n");
        sb.append(homeTeam).append(" ").append(homeScore)
          .append(" : ").append(awayScore).append(" ").append(awayTeam).append("\n");
        sb.append("Venue: ").append(venue).append("\n");
        sb.append("MVP: ").append(mvp == null ? "-" : mvp).append("\n");
        sb.append("Winner: ").append(winner()).append("\n");
        sb.append("Highlights:\n");
        if (highlights.isEmpty()) {
            sb.append("  (no highlights recorded)\n");
        } else {
            for (String h : highlights) {
                sb.append("  - ").append(h).append("\n");
            }
        }
        return sb.toString();
    }
}
