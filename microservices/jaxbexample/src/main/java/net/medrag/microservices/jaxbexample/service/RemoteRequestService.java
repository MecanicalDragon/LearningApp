package net.medrag.microservices.jaxbexample.service;

import lombok.RequiredArgsConstructor;
import net.medrag.microservices.openapiexample.api.SkillApiClient;
import net.medrag.microservices.openapiexample.api.model.Unit;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Stanislav Tretyakov
 * 31.12.2020
 */
@Service
@RequiredArgsConstructor
public class RemoteRequestService {
    private final SkillApiClient skillApiClient;

    public Unit getRemoteSkills() {
        return new Unit().name("Vasiliy-" + UUID.randomUUID()).skillSet(skillApiClient.getSkills().getBody());
    }
}
