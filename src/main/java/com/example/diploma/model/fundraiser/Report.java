package com.example.diploma.model.fundraiser;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String fileUrl;
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "fundraiser_id")
    @JsonBackReference
    private Fundraiser fundraiser;

}
