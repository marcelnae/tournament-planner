-- TEAM table
CREATE TABLE Team (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      coach VARCHAR(100)
);

-- LOCATION table
CREATE TABLE Location (
                          id SERIAL PRIMARY KEY,
                          city VARCHAR(100) NOT NULL,
                          venue VARCHAR(100) NOT NULL,
                          UNIQUE(city, venue)
);

-- TOURNAMENT table
CREATE TABLE Tournament (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            start_date DATE NOT NULL,
                            end_date DATE
);

-- TOURNAMENT_LOCATION (many-to-many join table)
CREATE TABLE TournamentLocation (
                                    tournament_id INTEGER NOT NULL,
                                    location_id INTEGER NOT NULL,
                                    PRIMARY KEY (tournament_id, location_id),
                                    FOREIGN KEY (tournament_id) REFERENCES Tournament(id) ON DELETE CASCADE,
                                    FOREIGN KEY (location_id) REFERENCES Location(id) ON DELETE CASCADE
);

-- Insert example teams
INSERT INTO Team (name, coach) VALUES
('Team A', 'Coach X'),
('Team B', 'Coach Y');

-- Insert example locations
INSERT INTO Location (city, venue) VALUES
('Berlin', 'Olympiastadion'),
('Munich', 'Allianz Arena');

-- Insert example tournaments
INSERT INTO Tournament (name, start_date, end_date) VALUES
('Elfmeterturnier', '2025-07-01', '2025-07-02'),
('EURO 2024', '2025-07-01', '2025-07-10'),
('Champions Cup - Final', '2025-07-01', '2025-07-12');

-- Link tournaments to locations
INSERT INTO TournamentLocation (tournament_id, location_id) VALUES
(1, 1), -- Elfmeterturnier at Berlin Olympiastadion
(2, 1), -- EURO 2024 at Berlin Olympiastadion
(3, 1); -- Champions Cup - Final at Berlin Olympiastadion
