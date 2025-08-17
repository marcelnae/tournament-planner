package com.marcel.tournament.backend.tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation of join table entity for tournaments x locations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentLocation {
    private Integer tournamentId;
    private Integer locationId;
}
