package com.marcel.tournament.backend.tournament;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception for tournament CRUD operations.
 * Used to signal errors specific to tournament handling.
 */
@Getter
public class TournamentException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Constructs a new TournamentException with the specified detail message and HTTP status.
     *
     * @param message the detail message
     * @param status the HTTP status to return
     */
    public TournamentException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
