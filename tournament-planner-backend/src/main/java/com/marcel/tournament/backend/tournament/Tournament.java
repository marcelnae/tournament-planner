package com.marcel.tournament.backend.tournament;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Representation of Tournament entity.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate; // Optional

    public TournamentDTO toDto() {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        return dto;
    }
}
