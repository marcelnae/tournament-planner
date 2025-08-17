package com.marcel.tournament.backend.tournament;

import java.util.List;

/**
 * Service Interface for Tournament operations.
 */
public interface ITournamentService {

    /**
     * Create a new tournament.
     * @param tournament dto for a Tournament.
     * @return the Tournament created.
     */
    TournamentDTO createTournament(TournamentDTO tournament);

    /**
     * Fetch all tournaments that contains a certain string.
     * @param tournamentName the name (also a substring of the name of the tournament to fetch.
     * @return list of all tournaments that match the name given.
     */
    List<TournamentDTO> getTournamentByName(String tournamentName);

    /**
     * Update a specific tournament.
     * @param tournament the dto of the tournament to update.
     */
    void updateTournament(TournamentDTO tournament);

    /**
     * Delete a tournament by id.
     * @param id the id of the tournament to be deleted.
     * @return true, if specified tournament was deleted, false otherwise.
     */
    boolean deleteTournamentById(Integer id);

    /**
     * Fetch all available tournaments.
     * @return list of all tournaments.
     */
    List<TournamentDTO> getAllTournaments();

}
