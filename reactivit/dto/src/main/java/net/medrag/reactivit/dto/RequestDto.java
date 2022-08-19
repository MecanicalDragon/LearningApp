package net.medrag.reactivit.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Stanislav Tretyakov
 * 22.05.2022
 */
@Data
@RequiredArgsConstructor
public final class RequestDto {
    private final String name;
    private final String surname;
}
