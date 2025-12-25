package com.marcel.tournament.backend.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for tournament operations.
 */
@Service
@RequiredArgsConstructor
public class TournamentService implements ITournamentService {

    private final TournamentRepository tournamentRepository;

    /**
     * Creates a new tournament.
     *
     * @param tournament the tournament DTO
     * @return the created TournamentDTO
     */
    @Override
    public TournamentDTO createTournament(TournamentDTO tournament) {
        return tournamentRepository.save(tournament.toEntity()).toDto();
    }

    /**
     * Retrieves tournaments by name.
     *
     * @param tournamentName the name to search for
     * @return list of TournamentDTO
     */
    @Override
    public List<TournamentDTO> getTournamentByName(String tournamentName) {
        return tournamentRepository.getTournamentByName(Optional.ofNullable(tournamentName).orElse("")).stream()
                .map(Tournament::toDto).collect(Collectors.toList());
    }

    /**
     * Updates the name of a tournament by ID.
     *
     * @param id the tournament ID
     * @param name the new name
     * @throws TournamentException if tournament is not found
     */
    @Override
    @Transactional
    public void updateTournament(long id, String name) {
        int updated = tournamentRepository.updateNameById(id, name);
        if (updated == 0) {
            throw new TournamentException("Tournament not found for update: " + id, org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a tournament by ID.
     *
     * @param id the tournament ID
     * @throws TournamentException if tournament is not found
     */
    @Override
    public void deleteTournamentById(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new TournamentException("Tournament not found for delete: " + id, org.springframework.http.HttpStatus.NOT_FOUND);
        }
        tournamentRepository.deleteById(id);
    }
}
