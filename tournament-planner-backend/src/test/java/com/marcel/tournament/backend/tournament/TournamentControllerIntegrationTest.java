package com.marcel.tournament.backend.tournament;

import com.marcel.tournament.backend.TournamentBackendApplication;
import org.junit.jupiter.api.Nested;
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

    @Nested
    class CreateTournamentTests {
        @Test
        void testCreateAndGetTournament() {
            TournamentDTO dto = new TournamentDTO();
            dto.setName("Spring Cup Created");
            dto.setStartDate(ZonedDateTime.now());
            dto.setEndDate(ZonedDateTime.now().plusDays(1));

            // Create
            ResponseEntity<TournamentDTO> createResponse = restTemplate.postForEntity(
                    "/tournaments/", dto, TournamentDTO.class);
            assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            TournamentDTO created = createResponse.getBody();
            assertThat(created).isNotNull();
            assertThat(created.getId()).isNotNull();
            assertThat(created.getName()).isEqualTo("Spring Cup Created");

            // Get by name
            ResponseEntity<TournamentDTO[]> getResponse = restTemplate.getForEntity(
                    "/tournaments/{name}", TournamentDTO[].class, "Spring Cup Created");
            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            TournamentDTO[] tournaments = getResponse.getBody();
            assertThat(tournaments).isNotNull();
            assertThat(tournaments.length).isGreaterThan(0);
            assertThat(tournaments[0].getId()).isEqualTo(created.getId());
            assertThat(tournaments[0].getName()).isEqualTo("Spring Cup Created");
        }
    }

    @Nested
    class GetTournamentTests {
        @Test
        void testGetTournamentNotFound() {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "/tournaments/{name}", String.class, "NonExistent");
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

    @Nested
    class UpdateTournamentTests {
        @Test
        void testUpdateTournament() {
            // Use tournament from SQL file ("Example Tournament")
            Tournament example = tournamentRepository.findAll().stream()
                    .filter(t -> "Example Tournament".equals(t.getName()))
                    .findFirst().orElseThrow();

            TournamentDTO updateDto = new TournamentDTO();
            updateDto.setId(example.getId());
            updateDto.setName("Updated Example Tournament");
            updateDto.setStartDate(example.getStartDate());
            updateDto.setEndDate(example.getEndDate());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<TournamentDTO> request = new HttpEntity<>(updateDto, headers);

            ResponseEntity<Void> updateResponse = restTemplate.exchange(
                    "/tournaments/", HttpMethod.PUT, request, Void.class);
            assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

            ResponseEntity<TournamentDTO[]> getResponse = restTemplate.getForEntity(
                    "/tournaments/{name}", TournamentDTO[].class, "Updated Example Tournament");
            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            TournamentDTO[] tournaments = getResponse.getBody();
            assertThat(tournaments).isNotNull();
            assertThat(tournaments[0].getId()).isEqualTo(example.getId());
            assertThat(tournaments[0].getName()).isEqualTo("Updated Example Tournament");
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
    }

    @Nested
    class DeleteTournamentTests {
        @Test
        void testDeleteTournament() {
            // Use tournament from SQL file ("Spring Cup")
            Tournament toDelete = tournamentRepository.findAll().stream()
                    .filter(t -> "Spring Cup".equals(t.getName()))
                    .findFirst().orElseThrow();

            ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                    "/tournaments/delete/{id}", HttpMethod.DELETE, null, Void.class, toDelete.getId());
            assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

            ResponseEntity<String> getResponse = restTemplate.getForEntity(
                    "/tournaments/{name}", String.class, "Spring Cup");
            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void testDeleteTournamentNotFound() {
            ResponseEntity<String> response = restTemplate.exchange(
                    "/tournaments/delete/{id}", HttpMethod.DELETE, null, String.class, 9999L);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
