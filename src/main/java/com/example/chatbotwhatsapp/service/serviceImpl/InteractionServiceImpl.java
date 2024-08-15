package com.example.chatbotwhatsapp.service.serviceImpl;



import com.example.chatbotwhatsapp.dtos.OpportuniteDTO;
import com.example.chatbotwhatsapp.dtos.ProjetDTO;
import com.example.chatbotwhatsapp.entities.Utilisateur;
import com.example.chatbotwhatsapp.enums.ChatState;
import com.example.chatbotwhatsapp.enums.statutOpp;
import com.example.chatbotwhatsapp.enums.statutPrj;
import com.example.chatbotwhatsapp.repositories.UtilisateurRepository;
import com.example.chatbotwhatsapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class InteractionServiceImpl implements InteractionService {

    private DetailsProjetService detailsProjetService;
    private DetailsOpportuniteService detailsOpportuniteService;
    private SenderStatusService senderStatusService;
    private UtilisateurRepository utilisateurRepository;
    private messageSenderService senderService;
    private AboutMeService aboutMeService;
    private OpportuniteService opportuniteService;
    private ProjetService projetService;
    private ValidateOpportunity validateOpportunity;



    @Override
    public void handleFirstInteraction(String phoneNumber) {
        // Create or reset the user
        Utilisateur utilisateur = utilisateurRepository.findByPhoneNumber(phoneNumber)
                .orElse(new Utilisateur());
        utilisateur.setPhoneNumber(phoneNumber);
        utilisateur.setCurrentState(ChatState.START.name()); // Set initial state
        utilisateur.setLastInteractionTime(LocalDateTime.now());
        utilisateurRepository.save(utilisateur);

        // Send bonjour_menu template
        senderService.sendTemplateMessage();
    }

    @Override
    public void handleInteraction(String phoneNumber, String incomingMessage) {
        // Fetch the user based on phone number
        Utilisateur utilisateur = utilisateurRepository.findByPhoneNumber(phoneNumber).orElse(null);

        if (utilisateur == null) {
            // User not found, handle as a first interaction
            handleFirstInteraction(phoneNumber);
            return;
        }
        // Calculate the time difference since the last interaction
        Duration duration = Duration.between(utilisateur.getLastInteractionTime(), LocalDateTime.now());

        long sessionTimeoutMinutes = 10;

        if (duration.toMinutes() > sessionTimeoutMinutes) {
                handleFirstInteraction(phoneNumber);
                return;
            }

        // Update the last interaction time
            utilisateur.setLastInteractionTime(LocalDateTime.now());
        // Convert the current state from String to ChatState enum
            ChatState currentState = ChatState.valueOf(utilisateur.getCurrentState());
        // Determine the new state based on the current state and user input
            ChatState newState = determineNewState(currentState, incomingMessage);
        // Update the user's state in the database
            utilisateur.setCurrentState(newState.name());
            utilisateurRepository.save(utilisateur);
        // Send appropriate response based on the new state
             sendResponseBasedOnState(newState.name(), phoneNumber,incomingMessage);
    }

    @Override
    public ChatState determineNewState(ChatState currentState, String userInput) {

        return switch (currentState) {
            case START -> switch (userInput) {
                case "1" -> ChatState.PROJECTS;
                case "2" -> ChatState.OPPORTUNITIES;
                case "3" -> ChatState.SHOW_PERSONAL_INFO;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case PROJECTS -> switch (userInput) {
                case "0" -> ChatState.START;
                case "1" -> ChatState.PROJECTS_COMMERCIAL_OU_CHEF;
                case "2" -> ChatState.SAISIR_NOM_PROJET;
                case "3" -> ChatState.SAISIR_STATUS_PROJET;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case OPPORTUNITIES -> switch (userInput) {
                case "0" -> ChatState.START;
                case "1" -> ChatState.SHOW_OPPORTUNITIES;
                case "2" -> ChatState.SAISIR_NOM_OPPORTUNITE;
                case "3" -> ChatState.SAISIR_STATUS_OPPORTUNITE;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case PROJECTS_COMMERCIAL_OU_CHEF -> switch (userInput){
                case "0" -> ChatState.START;
                case "1" -> ChatState.SHOW_PROJECTS_CHEFPROJET;
                case "2" -> ChatState.SHOW_PROJECTS_COMMERCIAL;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case SHOW_PERSONAL_INFO, UNKNOWN_RESPONSE -> switch (userInput) {
                case "0" -> ChatState.START;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case NO_OPPORTUNITIES, NO_OPPORTUNITIES_BY_STATUS, NO_OPPORTUNITIES_NAME_DETAILS,
                 SHOW_OPPORTUNITIES_BY_STATUS, SHOW_OPPORTUNITIES_NAME_DETAILS -> switch (userInput) {
                case "0" -> ChatState.START;
                case "1" -> ChatState.OPPORTUNITIES;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case NO_PROJECTS, NO_PROJECTS_BY_STATUS, NO_PROJECTS_NAME_DETAILS, SHOW_PROJECTS_BY_STATUS,
                 SHOW_PROJECTS_NAME_DETAILS ->
                    switch (userInput) {
                        case "0" -> ChatState.START;
                        case "1" -> ChatState.PROJECTS;
                        default -> ChatState.UNKNOWN_RESPONSE;
            };

            case SHOW_PROJECTS_COMMERCIAL, SHOW_PROJECTS_CHEFPROJET -> switch (userInput) {
                case "0" -> ChatState.START;
                case "1" -> ChatState.SAISIR_STATUS_PROJET;
                case "2" -> ChatState.SAISIR_NOM_PROJET;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case SHOW_OPPORTUNITIES -> switch (userInput) {
                case "0" -> ChatState.START;
                case "1" -> ChatState.SAISIR_STATUS_OPPORTUNITE;
                case "2" -> ChatState.SAISIR_NOM_OPPORTUNITE;
                default -> ChatState.UNKNOWN_RESPONSE;
            };

            case SAISIR_NOM_OPPORTUNITE -> switch (userInput.trim()) {
                case "0" -> ChatState.START;
                default -> {
                    // Check if the user input is not empty
                    if (!userInput.isEmpty()) {
                        // Search for the opportunity based on the user input
                        OpportuniteDTO opportunite = opportuniteService.searchByName(userInput);
                        if (opportunite != null) {
                            // Opportunity found, transition to SHOW_OPPORTUNITIES_NAME_DETAILS state
                            yield ChatState.SHOW_OPPORTUNITIES_NAME_DETAILS;
                        } else {
                            // Opportunity not found, transition to NO_OPPORTUNITIES_NAME_DETAILS state
                            yield ChatState.NO_OPPORTUNITIES_NAME_DETAILS;
                        }
                    } else {
                        // User input is empty, prompt for input again
                        yield ChatState.SAISIR_NOM_OPPORTUNITE;
                    }
                }
            };

            case SAISIR_NOM_PROJET -> switch (userInput.trim()) {
                case "0" -> ChatState.START;
                default -> {
                    // Check if the user input is not empty
                    if (!userInput.isEmpty()) {
                        // Search for the opportunity based on the user input
                        ProjetDTO projet = projetService.searchByName(userInput);
                        if (projet != null) {
                            // Opportunity found, transition to SHOW_OPPORTUNITIES_NAME_DETAILS state
                            yield ChatState.SHOW_PROJECTS_NAME_DETAILS;
                        } else {
                            // Opportunity not found, transition to NO_OPPORTUNITIES_NAME_DETAILS state
                            yield ChatState.NO_PROJECTS_NAME_DETAILS;
                        }
                    } else {
                        // User input is empty, prompt for input again
                        yield ChatState.SAISIR_NOM_PROJET;
                    }
                }
            };

            case SAISIR_STATUS_OPPORTUNITE -> switch (userInput.trim().toUpperCase()) {
                case "0" -> ChatState.START;
                case "APPROUVE", "REJETE", "EN_ATTENTE", "GAGNE", "PERDU" -> {
                    statutOpp status;
                    try {
                        status = statutOpp.valueOf(userInput.trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        // Invalid status, prompt for input again
                        yield ChatState.SAISIR_STATUS_OPPORTUNITE;
                    }

                    List<OpportuniteDTO> opportunities = opportuniteService.searchByStatus(status);
                    if (!opportunities.isEmpty()) {
                        yield ChatState.SHOW_OPPORTUNITIES_BY_STATUS;
                    } else {
                        yield ChatState.NO_OPPORTUNITIES_BY_STATUS;
                    }
                }
                default -> ChatState.SAISIR_STATUS_OPPORTUNITE; // Invalid input, prompt for input again
            };

            case SAISIR_STATUS_PROJET -> switch (userInput.trim().toUpperCase()) {
                case "0" -> ChatState.START;
                case "ANNULE", "CLOTURE", "EN_ATTENTE", "FINALISATION_EN_COURS", "LANCEMENT", "REALISATION_EN_COURS", "RECEPTIONNE" -> {
                    statutPrj status;
                    try {
                        status = statutPrj.valueOf(userInput.trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        // Invalid status, prompt for input again
                        yield ChatState.SAISIR_STATUS_PROJET;
                    }

                    List<ProjetDTO> projects = projetService.searchByStatus(status);
                    if (!projects.isEmpty()) {
                        yield ChatState.SHOW_PROJECTS_BY_STATUS;
                    } else {
                        yield ChatState.NO_PROJECTS_BY_STATUS;
                    }
                }
                default -> ChatState.SAISIR_STATUS_PROJET; // Invalid input, prompt for input again
            };

            default -> currentState;
        };
    }

    @Override
    public void sendResponseBasedOnState(String state, String phoneNumber, String userInput) {
         switch (state) {
            case "START":
                senderService.sendTemplateMessage();
                break;

            case "PROJECTS":
                senderService.sendChoixProjet();
                break;

                case "PROJECTS_COMMERCIAL_OU_CHEF":
                   senderService.projetCommercialouChefProjet();
                    break;
                case "SHOW_PROJECTS_CHEFPROJET":
                    senderService.sendTemplateMessageWithUserAndProjetchefProjet(phoneNumber);
                    break;
                case "SHOW_PROJECTS_COMMERCIAL":
                    senderService.sendTemplateMessageWithUserAndProjetcommercial(phoneNumber);
                    break;
                case "NO_PROJECTS_CHEFPROJECT", "NO_PROJECTS_COMMERCIAL":
                    senderService.sendTemplateMessageWithUserNoProjet(phoneNumber);
                    break;
            case "SHOW_PROJECTS_NAME_DETAILS":
                detailsProjetService.sendProjetDetails(userInput);
                break;
            case "SHOW_PROJECTS_BY_STATUS":
                statutPrj statusPrj = statutPrj.valueOf(userInput.toUpperCase());
                senderStatusService.sendProjetStatus(statusPrj);
                break;
            case "SAISIR_STATUS_PROJET":
                senderStatusService.saisirProjetStatus();
                break;
            case "SAISIR_NOM_PROJET":
                detailsProjetService.saisirNomProjet();
                break;
            case "NO_PROJECTS_BY_STATUS":
                statutPrj statusprj = statutPrj.valueOf(userInput.toUpperCase());
                senderStatusService.sendNoProjetStatus(statusprj);
                break;
            case "NO_PROJECTS_NAME_DETAILS":
                detailsProjetService.NoProjetFound(userInput);
                break;


            case "OPPORTUNITIES":
                senderService.sendChoixOpportunite();
                break;
                case "SHOW_OPPORTUNITIES":
                    senderService.sendTemplateMessageWithUserAndOpportunite(phoneNumber);
                    break;
                case "NO_OPPORTUNITIES":
                    senderService.sendTemplateMessageWithUserNoOpportunite(phoneNumber);
                    break;
                case "SHOW_OPPORTUNITIES_NAME_DETAILS":
                    detailsOpportuniteService.sendOpportuniteDetails(userInput);
                    break;
                case "SHOW_OPPORTUNITIES_BY_STATUS":
                    statutOpp StatusOpp = statutOpp.valueOf(userInput.toUpperCase());
                    senderStatusService.sendOpportuniteStatus(StatusOpp);
                    break;
                case "SAISIR_STATUS_OPPORTUNITE":
                    senderStatusService.saisirOpportuniteStatus();
                    break;
                case "SAISIR_NOM_OPPORTUNITE":
                    detailsOpportuniteService.saisirNomOpportunite();
                    break;
                case "NO_OPPORTUNITIES_BY_STATUS":
                    statutOpp noStatusOpp = statutOpp.valueOf(userInput.toUpperCase());
                    senderStatusService.sendNoOpportuniteStatus(noStatusOpp);
                    break;
                case "NO_OPPORTUNITIES_NAME_DETAILS":
                    detailsOpportuniteService.NoOpportuniteFound(userInput);
                    break;

            case "SHOW_PERSONAL_INFO":
                aboutMeService.sendTemplateMessageWithUser(phoneNumber);
                break;

            default:
                senderService.sendInconnuMessage();
                break;
        }
    }
}
