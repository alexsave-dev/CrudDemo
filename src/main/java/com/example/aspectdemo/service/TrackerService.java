package com.example.aspectdemo.service;

import com.example.aspectdemo.model.Tracker;
import com.example.aspectdemo.model.type.MethodExecutionType;

import java.util.List;

public interface TrackerService {

    Tracker save(String methodSignature, Long duration, MethodExecutionType methodExecutionType);

    List<Tracker> getAllByType(String type);
    Tracker getLongestExecutionByType(String type);

}
