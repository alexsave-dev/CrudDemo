package com.example.aspectdemo.model;

import com.example.aspectdemo.model.type.MethodExecutionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String methodSignature;
    private Long duration;
    @Enumerated(EnumType.STRING)
    private MethodExecutionType type;
}
