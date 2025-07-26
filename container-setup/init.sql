-- TEAM table
CREATE TABLE Team (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL UNIQUE,
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
                            name VARCHAR(100) NOT NULL UNIQUE,
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
