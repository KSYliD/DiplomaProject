package com.example.diploma.model.fundraiser;

import com.example.diploma.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToOne(mappedBy = "fundraiser", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Report report;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private User owner;
    @ManyToMany(mappedBy = "subscriptions")
    @JsonBackReference(value = "user-subscriptions")
    private List<User> subscribers;
}