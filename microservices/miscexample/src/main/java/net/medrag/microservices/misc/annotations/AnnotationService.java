package net.medrag.microservices.misc.annotations;

import net.medrag.microservices.myannotations.StrictName;
import org.springframework.stereotype.Service;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Service
public class AnnotationService {
    @StrictName("sampleName")
    private String sampleName;
}
