package com.marcel.tournament.backend.service.impl;

import com.marcel.tournament.backend.dto.TournamentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TournamentServiceTest {

    TournamentService uut;

    @BeforeEach
    void setUp() {
        uut = new TournamentService();
        TournamentDTO tournament = new TournamentDTO();
        tournament.setId(1);
        tournament.setName("Tournament");

        uut.createTournament(tournament);
    }

    @Test
    void createTournament() {
        TournamentDTO tournamentDto = new TournamentDTO();
        tournamentDto.setName("Tournament Create Test");
        TournamentDTO actual = uut.createTournament(tournamentDto);

        assertEquals("Tournament Create Test", actual.getName());
        assertEquals(2, uut.getAllTournaments().size());
    }

    @Test
    void getTournamentByName() {

    }

    @Test
    void updateTournament() {
    }

    @Test
    void deleteTournamentById() {
    }

    @Test
    void getAllTournaments() {
    }
}
