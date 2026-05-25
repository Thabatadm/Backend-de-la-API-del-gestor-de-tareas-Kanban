package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CardRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ProjectUserRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.CardRequest;
import com.example.demo.dto.CardUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ProjectUserService projectUserService;
    private final ProjectRepository projectRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository,
                       ProjectUserService projectUserService, ProjectRepository projectRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.projectUserService = projectUserService;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Card createCard(Long projectId, CardRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        projectUserService.validateMasterPermission(project.getId());


        Card card = new Card();
        card.setTitle(request.getTitle());
        card.setDescription(request.getDescription());
        card.setStatus(request.getStatus() != null ? request.getStatus() : Status.TO_DO);
        card.setPriority(request.getPriority());

        card.setProject(project);
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());

        return cardRepository.save(card);
    }

    @Transactional
    public Card updateCardDetails(Long cardId, CardUpdate dto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        if (dto.getTitle() != null) card.setTitle(dto.getTitle());
        if (dto.getDescription() != null) card.setDescription(dto.getDescription());
        if (dto.getStatus() != null) card.setStatus(dto.getStatus());
        if (dto.getPriority() != null) card.setPriority(dto.getPriority());
        if (dto.getDueDate() != null) card.setDueDate(dto.getDueDate());
        if (dto.getPosition() != null) {
        card.setPosition(dto.getPosition());
        }

        if (dto.getAssigneeId() != null) {
            User user = userRepository.findById(dto.getAssigneeId()).orElse(null);
            card.setAssignee(user);
        }

        card.setUpdatedAt(LocalDateTime.now());
        return cardRepository.save(card);
    }

    public void deleteCard(Long cardId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new RuntimeException("Card not found"));
        projectUserService.validateMasterPermission(card.getProject().getId());

        cardRepository.delete(card);
    }


    public List<Card> getCardsByProject(Long projectId){
        return cardRepository.findByProjectIdOrderByPositionAsc(projectId);
    }
}