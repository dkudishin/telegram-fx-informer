package dk.kudishin.telegramfxinformer.services;

import dk.kudishin.telegramfxinformer.domain.RateAlert;
import dk.kudishin.telegramfxinformer.repository.RateAlertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RateAlertService {

    private final RateAlertRepository repository;

    private final Logger log = LoggerFactory.getLogger(RateAlertService.class);

    public RateAlertService(RateAlertRepository repository) {
        this.repository = repository;
    }

    public RateAlert saveAlertToDb(RateAlert alert) {
        RateAlert rateAlert = repository.save(alert);
        log.info("saved an alert to the DB: "+ rateAlert);
        return rateAlert;
    }

}