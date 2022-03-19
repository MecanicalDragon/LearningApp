package net.medrag.microservices.jpa.spec.repositry;

import net.medrag.microservices.jpa.spec.entity.SpecEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SpecRepository extends JpaRepository<SpecEntity, Long>, JpaSpecificationExecutor<SpecEntity> {
    @Transactional
    @Modifying
    @Query("delete from SpecEntity s where s.id = :id")
    int deleteEntryById(Long id);

    Page<SpecEntity> findAll(Pageable pageable);
}
