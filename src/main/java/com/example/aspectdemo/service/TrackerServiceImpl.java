package com.example.aspectdemo.service;

import com.example.aspectdemo.model.Tracker;
import com.example.aspectdemo.model.type.MethodExecutionType;
import com.example.aspectdemo.repository.TrackerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TrackerServiceImpl implements TrackerService {

    private final TrackerRepository trackerRepository;

    public TrackerServiceImpl(TrackerRepository trackerRepository) {
        this.trackerRepository = trackerRepository;
    }

    @Override
    public Tracker save(String methodSignature, Long duration, MethodExecutionType methodExecutionType) {
        return trackerRepository.save(Tracker.builder()
                        .methodSignature(methodSignature)
                        .duration(duration)
                        .type(methodExecutionType)
                        .build());
    }

    @Override
    public List<Tracker> getAllByType(String type) {
        return trackerRepository.findAllByType(MethodExecutionType.valueOf(type));
    }

    @Override
    public Tracker getLongestExecutionByType(String type) {
        return trackerRepository.findLongestExecutionByType(MethodExecutionType.valueOf(type));
    }
}
