package com.example.aspectdemo.repository;

import com.example.aspectdemo.model.Tracker;
import com.example.aspectdemo.model.type.MethodExecutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackerRepository extends JpaRepository<Tracker, Long> {
    List<Tracker> findAllByType(MethodExecutionType type);

    @Query(value = "SELECT t FROM Tracker t WHERE t.type = :type ORDER BY t.duration DESC LIMIT 1" )
    Tracker findLongestExecutionByType(MethodExecutionType type);
}
