package dk.kudishin.telegramfxinformer.repository;

import dk.kudishin.telegramfxinformer.domain.RateAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateAlertRepository extends JpaRepository<RateAlert, Integer> {
}