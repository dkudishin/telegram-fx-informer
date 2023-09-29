package dk.kudishin.telegramfxinformer.services;

import dk.kudishin.telegramfxinformer.domain.RateAlert;
import dk.kudishin.telegramfxinformer.repositories.RateAlertRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RateAlertService {

    private final RateAlertRepository repository;

    public RateAlertService(RateAlertRepository repository) {
        this.repository = repository;
    }

    public RateAlert saveAlertToDb(RateAlert alert) {
        RateAlert rateAlert = repository.save(alert);
        log.info("saved an alert to the DB: "+ rateAlert);
        return rateAlert;
    }

}