package dk.kudishin.telegramfxinformer.repositories;

import dk.kudishin.telegramfxinformer.domain.FxRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FxRateRepository extends JpaRepository<FxRate, Integer> {
}