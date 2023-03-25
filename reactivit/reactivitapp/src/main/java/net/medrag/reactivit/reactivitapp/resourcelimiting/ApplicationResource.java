package net.medrag.reactivit.reactivitapp.resourcelimiting;

/**
 * Any arbitrary application resource identifier.
 * Not bound to anything in reality, may be any string, that is convenient to specify in properties and understand what it means.
 *
 * @author Stanislav Tretyakov
 * 21.12.2022
 */
public enum ApplicationResource {
    CPU,
    MEMORY,
    WEBCLIENT_CONNECTIONS,
    REDIS_CONNECTIONS
}
