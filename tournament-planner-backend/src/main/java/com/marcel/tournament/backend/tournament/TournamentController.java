package com.marcel.tournament.backend.tournament;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for managing tournaments.
 */
@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final ITournamentService tournamentService;

    @GetMapping("/{name}")
    public List<TournamentDTO> getTournament(@PathVariable(required = false, name = "name") String name) {
        return tournamentService.getTournamentByName(name);
    }

    @PostMapping("/")
    public TournamentDTO postTournament(@RequestBody TournamentDTO tournament) {
        return tournamentService.createTournament(tournament);
    }

    @PutMapping("/")
    public void putTournament(@RequestBody TournamentDTO tournament) {
        tournamentService.updateTournament(tournament.getId(), tournament.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournamentById(id);
    }

}
