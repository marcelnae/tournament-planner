package com.marcel.tournament.backend.tournament;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TournamentController.class)
class TournamentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITournamentService tournamentService;

    @Autowired
    private ObjectMapper objectMapper;

    private TournamentDTO sampleTournament;

    @BeforeEach
    void setUp() {
        sampleTournament = new TournamentDTO();
        sampleTournament.setId(1L);
        sampleTournament.setName("Test Tournament");
    }

    @Nested
    @DisplayName("GET /tournaments/{name}")
    class GetTournamentTests {
        @Test
        void shouldReturnTournamentsByName() throws Exception {
            Mockito.when(tournamentService.getTournamentByName("Test Tournament"))
                    .thenReturn(List.of(sampleTournament));

            mockMvc.perform(get("/tournaments/Test Tournament"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(1)))
                    .andExpect(jsonPath("$[0].name", is("Test Tournament")));
        }

        @Test
        void shouldReturn404WhenNoTournamentsFound() throws Exception {
            Mockito.when(tournamentService.getTournamentByName("Unknown"))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/tournaments/Unknown"))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertInstanceOf(TournamentException.class, result.getResolvedException()));
        }
    }

    @Nested
    @DisplayName("POST /tournaments/")
    class PostTournamentTests {
        @Test
        void shouldCreateTournament() throws Exception {
            Mockito.when(tournamentService.createTournament(any()))
                    .thenReturn(sampleTournament);

            mockMvc.perform(post("/tournaments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleTournament)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", is(1)))
                    .andExpect(jsonPath("$.name", is("Test Tournament")));
        }

        @Test
        void shouldReturn400OnInvalidInput() throws Exception {
            Mockito.when(tournamentService.createTournament(any()))
                    .thenThrow(new RuntimeException("Invalid"));

            mockMvc.perform(post("/tournaments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleTournament)))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertInstanceOf(TournamentException.class, result.getResolvedException()));
        }
    }

    @Nested
    @DisplayName("PUT /tournaments/")
    class PutTournamentTests {
        @Test
        void shouldUpdateTournament() throws Exception {
            Mockito.doNothing().when(tournamentService).updateTournament(eq(1L), eq("Test Tournament"));

            mockMvc.perform(put("/tournaments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleTournament)))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturn404WhenUpdateFails() throws Exception {
            Mockito.doThrow(new RuntimeException("Not found"))
                    .when(tournamentService).updateTournament(anyLong(), anyString());

            mockMvc.perform(put("/tournaments/")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sampleTournament)))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertInstanceOf(TournamentException.class, result.getResolvedException()));
        }
    }

    @Nested
    @DisplayName("DELETE /tournaments/delete/{id}")
    class DeleteTournamentTests {
        @Test
        void shouldDeleteTournament() throws Exception {
            Mockito.doNothing().when(tournamentService).deleteTournamentById(1L);

            mockMvc.perform(delete("/tournaments/delete/1"))
                    .andExpect(status().isOk());
        }

        @Test
        void shouldReturn404WhenDeleteFails() throws Exception {
            Mockito.doThrow(new RuntimeException("Not found"))
                    .when(tournamentService).deleteTournamentById(anyLong());

            mockMvc.perform(delete("/tournaments/delete/99"))
                    .andExpect(status().isNotFound())
                    .andExpect(result -> assertInstanceOf(TournamentException.class, result.getResolvedException()));
        }
    }
}