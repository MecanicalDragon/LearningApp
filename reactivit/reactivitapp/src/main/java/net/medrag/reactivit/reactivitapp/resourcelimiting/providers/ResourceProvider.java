package net.medrag.reactivit.reactivitapp.resourcelimiting.providers;

/**
 * Provider of application resources.
 * Purpose of this interface is to provide API to acquire and release resources independently of number and cost of resources.
 *
 * @author Stanislav Tretyakov
 * 22.12.2022
 */
public interface ResourceProvider {

    /**
     * Make an attempt to acquire resources.
     * If resource acquiring cannot be completed because of resources lack, all partially acquired resources will be released.
     *
     * @return true if resources have been acquired, false otherwise.
     */
    boolean tryToAcquireResources();

    /**
     * Release resources.
     */
    void releaseResources();
}
