package com.marcel.tournament.backend.tournament;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for managing tournaments.
 */
@Tag(name = "Tournament API", description = "Endpoints for managing tournaments")
@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final ITournamentService tournamentService;

    @Operation(summary = "Get tournaments by name", description = "Returns a list of tournaments matching the given name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval"),
        @ApiResponse(responseCode = "404", description = "Tournaments not found")
    })
    @GetMapping("/{name}")
    public List<TournamentDTO> getTournament(
        @Parameter(description = "Name of the tournament to search for", required = true)
        @PathVariable(required = false, name = "name") String name) {
        return tournamentService.getTournamentByName(name);
    }

    @Operation(summary = "Create a new tournament", description = "Creates a new tournament with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tournament created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/")
    public TournamentDTO postTournament(
        @Parameter(description = "Tournament object to create", required = true)
        @RequestBody TournamentDTO tournament) {
        return tournamentService.createTournament(tournament);
    }

    @Operation(summary = "Update an existing tournament", description = "Updates the details of an existing tournament")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tournament updated successfully"),
        @ApiResponse(responseCode = "404", description = "Tournament not found")
    })
    @PutMapping("/")
    public void putTournament(
        @Parameter(description = "Tournament object with updated details", required = true)
        @RequestBody TournamentDTO tournament) {
        tournamentService.updateTournament(tournament.getId(), tournament.getName());
    }

    @Operation(summary = "Delete a tournament", description = "Deletes the tournament with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tournament deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Tournament not found")
    })
    @DeleteMapping("/delete/{id}")
    public void deleteTournament(
        @Parameter(description = "ID of the tournament to delete", required = true)
        @PathVariable("id") Long id) {
        tournamentService.deleteTournamentById(id);
    }

}
