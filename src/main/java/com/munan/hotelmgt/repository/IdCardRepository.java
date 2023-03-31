package com.munan.hotelmgt.repository;

import com.munan.hotelmgt.model.IdCard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdCardRepository extends JpaRepository<IdCard, Long> {

    public Optional<IdCard> findByType(String type);
}
