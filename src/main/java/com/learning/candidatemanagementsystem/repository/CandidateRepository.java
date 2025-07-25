package com.learning.candidatemanagementsystem.repository;

import com.learning.candidatemanagementsystem.model.Candidate;
import com.learning.candidatemanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {
    Optional<Candidate> findByUser(User user);
}
