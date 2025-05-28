package com.example.diploma.model.fundraiser;

import com.example.diploma.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "fundraisers")
public class Fundraiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String avatar;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String URL;

    private long targetAmount;
    private long currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private UrgencyLevel urgencyLevel;

    @Enumerated(EnumType.STRING)
    private FundraiserStatus status;

    @Enumerated(EnumType.STRING)
    private FundraiserCategory fundraiserCategory;

    private boolean fromVolunteer;

    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "fundraiser", cascade = CascadeType.ALL)
    private Report report;

}