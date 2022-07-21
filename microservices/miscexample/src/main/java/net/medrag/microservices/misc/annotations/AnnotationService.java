package net.medrag.microservices.misc.annotations;

import net.medrag.microservices.myannotations.StrictName;
import org.springframework.stereotype.Service;

/**
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Service
@StrictName("AnnotationService")
public class AnnotationService {

    private static final String SAMPLE_NAME = "sampleName";

    @StrictName(SAMPLE_NAME)
    private String sampleName;

    @StrictName(SAMPLE_NAME)
    void sampleName(@StrictName(SAMPLE_NAME) String sampleName) {
        System.out.println(sampleName);
    }
}
