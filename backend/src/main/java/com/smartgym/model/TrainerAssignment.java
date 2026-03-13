package com.smartgym.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trainer_assignments")
public class TrainerAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String performanceNotes;

    public Long getId() { return id; }
    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public String getPerformanceNotes() { return performanceNotes; }
    public void setPerformanceNotes(String performanceNotes) { this.performanceNotes = performanceNotes; }
}
