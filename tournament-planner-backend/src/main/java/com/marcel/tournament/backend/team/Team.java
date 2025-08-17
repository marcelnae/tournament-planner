package com.marcel.tournament.backend.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    private Integer id;
    private String name;
    private String coach;
}
