package com.example.diploma.model.fundraiser;

import com.example.diploma.model.fundraiser.Fundraiser;
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

    @Column(columnDefinition = "TEXT")
    private String description;
    private String content;
    private String fileUrl;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "fundraiser_id")
    private Fundraiser fundraiser;

}
