package com.keukentafelprototype.repository;

import com.keukentafelprototype.model.MtgCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MtgCardRepository extends JpaRepository<MtgCard, Long> {
}
