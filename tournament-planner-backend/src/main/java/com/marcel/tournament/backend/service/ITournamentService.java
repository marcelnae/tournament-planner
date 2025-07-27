package com.marcel.tournament.backend.service;

import com.marcel.tournament.backend.dto.TournamentDTO;

import java.util.List;

public interface ITournamentService {

    TournamentDTO createTournament(TournamentDTO tournament);

    List<TournamentDTO> getTournamentByName(String tournamentName);

    void updateTournament(TournamentDTO tournament);

    boolean deleteTournamentById(Integer id);

    List<TournamentDTO> getAllTournaments();

}
