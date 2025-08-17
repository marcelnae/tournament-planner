package com.marcel.tournament.backend.tournament;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service implementation for tournament operations.
 */
@Service
public class TournamentService implements ITournamentService {

    private final List<TournamentDTO> tournaments = new ArrayList<>();

    @Override
    public TournamentDTO createTournament(TournamentDTO tournament) {
        tournaments.add(tournament);
        return tournament;
    }

    @Override
    public List<TournamentDTO> getTournamentByName(String tournamentName) {
        return tournaments.stream().filter(getTournamentByNamePredicate(tournamentName)).collect(Collectors.toList());
    }

    @Override
    public void updateTournament(TournamentDTO tournament) {
        tournaments.stream().filter(getTournamentByIdPredicate(tournament)).findFirst().ifPresent(
                getTournamentUpdateConsumer(tournament));
    }

    @Override
    public boolean deleteTournamentById(Integer id) {
        return tournaments.removeIf(tournament -> tournament.getId().equals(id));
    }

    @Override
    public List<TournamentDTO> getAllTournaments() {
        return tournaments;
    }

    private static Consumer<TournamentDTO> getTournamentUpdateConsumer(TournamentDTO tournament) {
        return t -> { // todo only update if field is not null/empty
            t.setName(tournament.getName());
            t.setStartDate(tournament.getStartDate());
            t.setEndDate(tournament.getEndDate());
        };
    }

    private static Predicate<TournamentDTO> getTournamentByIdPredicate(TournamentDTO tournament) {
        return t -> t.getId().equals(tournament.getId());
    }

    private static Predicate<TournamentDTO> getTournamentByNamePredicate(String tournamentName) {
        return tournament -> tournament.getName().equals(tournamentName);
    }
}
