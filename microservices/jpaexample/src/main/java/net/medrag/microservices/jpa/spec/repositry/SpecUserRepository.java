package net.medrag.microservices.jpa.spec.repositry;

import net.medrag.microservices.jpa.spec.entity.SpecUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SpecUserRepository extends JpaRepository<SpecUser, Long>, JpaSpecificationExecutor<SpecUser> {
    @Transactional
    @Modifying
    @Query("delete from SpecUser s where s.id = :id")
    int deleteEntryById(Long id);

    Page<SpecUser> findAll(Pageable pageable);
}
