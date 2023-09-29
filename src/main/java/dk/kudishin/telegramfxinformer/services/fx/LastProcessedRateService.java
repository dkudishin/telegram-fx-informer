package dk.kudishin.telegramfxinformer.services.fx;

import dk.kudishin.telegramfxinformer.domain.FxRate;
import dk.kudishin.telegramfxinformer.repositories.FxRateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("cache")
public class LastProcessedRateService implements FxRateService {

    private final FxRateRepository repository;

    public LastProcessedRateService(FxRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public FxRate getFxRate() {
        List<FxRate> fxRates = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        if(!fxRates.isEmpty()) {
            return fxRates.get(0);
        }
        return null;
    }
}