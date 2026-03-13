package com.smartgym.service;

import com.smartgym.model.Member;
import com.smartgym.model.Trainer;
import com.smartgym.model.TrainerAssignment;
import com.smartgym.repository.MemberRepository;
import com.smartgym.repository.TrainerAssignmentRepository;
import com.smartgym.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerAssignmentService {

    private final TrainerAssignmentRepository assignmentRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    public TrainerAssignmentService(TrainerAssignmentRepository assignmentRepository,
                                    TrainerRepository trainerRepository,
                                    MemberRepository memberRepository) {
        this.assignmentRepository = assignmentRepository;
        this.trainerRepository = trainerRepository;
        this.memberRepository = memberRepository;
    }

    public TrainerAssignment assign(Long trainerId, Long memberId, String notes) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();

        TrainerAssignment assignment = new TrainerAssignment();
        assignment.setTrainer(trainer);
        assignment.setMember(member);
        assignment.setPerformanceNotes(notes == null ? "" : notes);
        return assignmentRepository.save(assignment);
    }

    public List<TrainerAssignment> list() {
        return assignmentRepository.findAll();
    }
}
