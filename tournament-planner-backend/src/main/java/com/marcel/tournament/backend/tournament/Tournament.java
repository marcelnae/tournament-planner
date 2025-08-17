package com.marcel.tournament.backend.tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

/**
 * Representation of Tournament entity.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

    @Id
    private Integer id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate; // Optional
}
