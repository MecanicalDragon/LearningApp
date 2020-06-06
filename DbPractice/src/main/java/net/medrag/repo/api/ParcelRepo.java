package net.medrag.repo.api;

import net.medrag.model.Parcel;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 11.05.2020
 */
public interface ParcelRepo {
    List<Parcel> findAll();
    Parcel findById(Long id);
    Parcel addNew(Parcel customer);
}
