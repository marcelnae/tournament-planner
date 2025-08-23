package com.marcel.tournament.backend.tournament;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @Query("select t from Tournament t where t.name like %:name%")
    List<Tournament> getTournamentByName(String name);

    @Modifying
    @Query("update Tournament t set t.name = :name where t.id = :id")
    int updateNameById(@NonNull Long id, String name);


}
