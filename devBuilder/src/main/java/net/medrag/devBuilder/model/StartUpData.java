package net.medrag.devBuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.medrag.schema.RaceType;

import java.util.Map;

/**
 * {@author} Stanislav Tretyakov
 * 21.10.2019
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartUpData {
    private RaceType[] raceTypes;
    private Map<String, Request> requests;
    private int status;
}
