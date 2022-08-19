package net.medrag.reactivit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Stanislav Tretyakov
 * 22.05.2022
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public final class ResponseDto {
    private final String seniority;
    private final String duty;
}
