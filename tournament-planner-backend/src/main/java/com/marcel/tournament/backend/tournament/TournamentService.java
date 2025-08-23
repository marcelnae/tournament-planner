package com.marcel.tournament.backend.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public TournamentDTO createTournament(TournamentDTO tournament) {
        return tournamentRepository.save(tournament.toEntity()).toDto();
    }

    @Override
    public List<TournamentDTO> getTournamentByName(String tournamentName) {
        return tournamentRepository.getTournamentByName(Optional.ofNullable(tournamentName).orElse("")).stream()
                .map(Tournament::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateTournament(long id, String name) {
        int result = tournamentRepository.updateNameById(id, name);
    }

    @Override
    public void deleteTournamentById(Long id) {
        tournamentRepository.deleteById(id);
    }
}
