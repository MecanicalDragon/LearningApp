package net.medrag.microservices.jaxbexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Stanislav Tretyakov
 * 07.01.2021
 */
@Service
@RequiredArgsConstructor
public class SkillService {

    private final RemoteRequestService remoteRequestService;
    private final DocumentService documentService;

    public String getAllSkills() {
        return documentService.buildUnit(remoteRequestService.getRemoteSkills());
    }
}
