package com.marcel.tournament.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentLocation {
    private Integer tournamentId;
    private Integer locationId;
}
