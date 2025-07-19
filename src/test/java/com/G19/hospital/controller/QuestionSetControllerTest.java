package com.G19.hospital.controller;

import com.G19.hospital.TestBase;
import com.G19.hospital.model.Role;
import com.G19.hospital.model.User;
import com.G19.hospital.model.Questionner.Question;
import com.G19.hospital.model.Questionner.QuestionSet;
import com.G19.hospital.repository.RoleRepository;
import com.G19.hospital.repository.UserRepository;
import com.G19.hospital.repository.questionnere.QuestionRepository;
import com.G19.hospital.repository.questionnere.QuestionSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class QuestionSetControllerTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private QuestionSetRepository questionSetRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private User testUser;
    private QuestionSet testQuestionSet;
    private Question testQuestion;

    @BeforeEach
    void setUp() {
        cleanupTestData();
        
        // Create role
        Role userRole = createTestRole("ROLE_USER");
        userRole = roleRepository.save(userRole);
        
        // Create user
        testUser = createTestUser("testuser", "test@example.com", "1234567890");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);
        testUser = userRepository.save(testUser);
        
        // Create test question set
        testQuestionSet = new QuestionSet();
        testQuestionSet.setName("Test Question Set");
        testQuestionSet.setDescription("Test Description");
        testQuestionSet.setActive(true);
        testQuestionSet = questionSetRepository.save(testQuestionSet);
        
        // Create test question
        testQuestion = new Question();
        testQuestion.setText("Do you feel anxious most of the time?");
        testQuestion.setQuestionSet(testQuestionSet);
        testQuestion = questionRepository.save(testQuestion);
    }

    @Test
    void testCreateQuestionSet_Success() throws Exception {
        String questionSetRequest = """
            {
                "name": "Mental Health Assessment",
                "description": "Assessment for mental health conditions",
                "questions": [
                    {
                        "text": "Do you feel anxious most of the time?"
                    },
                    {
                        "text": "Do you have trouble sleeping?"
                    }
                ]
            }
            """;

        mockMvc.perform(post("/api/question-sets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(questionSetRequest))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllQuestionSets_Success() throws Exception {
        mockMvc.perform(get("/api/question-sets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void testGetQuestionSetById_Success() throws Exception {
        mockMvc.perform(get("/api/question-sets/{id}", testQuestionSet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Question Set"));
    }

    @Test
    void testGetQuestionSetById_NotFound() throws Exception {
        mockMvc.perform(get("/api/question-sets/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetSubmissionsBySet_Success() throws Exception {
        mockMvc.perform(get("/api/question-sets/{id}/submissions", testQuestionSet.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "patient")
    void testSubmitAnswers_AuthenticationError() throws Exception {
        String submissionRequest = """
            {
                "answers": [
                    {
                        "questionId": %d,
                        "response": "Yes"
                    }
                ]
            }
            """.formatted(testQuestion.getId());

        mockMvc.perform(post("/api/question-sets/{id}/submit", testQuestionSet.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(submissionRequest))
                .andExpect(status().isInternalServerError());
    }
} 