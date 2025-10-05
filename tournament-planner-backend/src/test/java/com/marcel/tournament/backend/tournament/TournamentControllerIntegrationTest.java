package com.marcel.tournament.backend.tournament;

import com.marcel.tournament.backend.TournamentBackendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = TournamentBackendApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Sql(
    scripts = "/test-tournaments-data.sql",
    config = @SqlConfig(encoding = "utf-8"),
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class TournamentControllerIntegrationTest extends PostgresTestContainerConfig {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    void testCreateAndGetTournament() {
        TournamentDTO dto = new TournamentDTO();
        dto.setName("Spring Cup");
        dto.setStartDate(ZonedDateTime.now());
        dto.setEndDate(ZonedDateTime.now().plusDays(1));

        // Create
        ResponseEntity<TournamentDTO> createResponse = restTemplate.postForEntity(
                "/tournaments/", dto, TournamentDTO.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TournamentDTO created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Spring Cup");

        // Get by name
        ResponseEntity<TournamentDTO[]> getResponse = restTemplate.getForEntity(
                "/tournaments/{name}", TournamentDTO[].class, "Spring Cup");
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TournamentDTO[] tournaments = getResponse.getBody();
        assertThat(tournaments).isNotNull();
        assertThat(tournaments.length).isGreaterThan(0);
        assertThat(tournaments[0].getId()).isEqualTo(created.getId());
        assertThat(tournaments[0].getName()).isEqualTo("Spring Cup");
    }

    @Test
    void testGetTournamentNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/tournaments/{name}", String.class, "NonExistent");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateTournament() {
        Tournament saved = tournamentRepository.save(new Tournament(null, "Old Name", ZonedDateTime.now(), null));
        TournamentDTO updateDto = new TournamentDTO();
        updateDto.setId(saved.getId());
        updateDto.setName("New Name");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TournamentDTO> request = new HttpEntity<>(updateDto, headers);

        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                "/tournaments/", HttpMethod.PUT, request, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<TournamentDTO[]> getResponse = restTemplate.getForEntity(
                "/tournaments/{name}", TournamentDTO[].class, "New Name");
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TournamentDTO[] tournaments = getResponse.getBody();
        assertThat(tournaments).isNotNull();
        assertThat(tournaments[0].getId()).isEqualTo(saved.getId());
        assertThat(tournaments[0].getName()).isEqualTo("New Name");
    }

    @Test
    void testUpdateTournamentNotFound() {
        TournamentDTO updateDto = new TournamentDTO();
        updateDto.setId(9999L);
        updateDto.setName("Doesn't Exist");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TournamentDTO> request = new HttpEntity<>(updateDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/tournaments/", HttpMethod.PUT, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteTournament() {
        Tournament saved = tournamentRepository.save(new Tournament(null, "ToDelete", ZonedDateTime.now(), null));

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/tournaments/delete/{id}", HttpMethod.DELETE, null, Void.class, saved.getId());
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate.getForEntity(
                "/tournaments/{name}", String.class, "ToDelete");
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testDeleteTournamentNotFound() {
        ResponseEntity<String> response = restTemplate.exchange(
                "/tournaments/delete/{id}", HttpMethod.DELETE, null, String.class, 9999L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetTournamentFromSqlFile() {
        ResponseEntity<TournamentDTO[]> response = restTemplate.getForEntity(
                "/tournaments/{name}", TournamentDTO[].class, "Example Tournament");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        TournamentDTO[] tournaments = response.getBody();
        assertThat(tournaments).isNotNull();
        assertThat(tournaments.length).isGreaterThan(0);
        assertThat(tournaments[0].getName()).isEqualTo("Example Tournament");
    }
}
