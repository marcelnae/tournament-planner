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

    @GetMapping("/")
    public List<TournamentDTO> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    @GetMapping("/{name}")
    public List<TournamentDTO> getTournament(@PathVariable String name) {
        return tournamentService.getTournamentByName(name);
    }

    @PostMapping("/")
    public TournamentDTO postTournament(@RequestBody TournamentDTO tournament) {
        return tournamentService.createTournament(tournament);
    }

    @PutMapping("/")
    public void putTournament(@RequestBody TournamentDTO tournament) {
        tournamentService.updateTournament(tournament);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTournament(@PathVariable Integer id) {
        return tournamentService.deleteTournamentById(id);
    }

}
