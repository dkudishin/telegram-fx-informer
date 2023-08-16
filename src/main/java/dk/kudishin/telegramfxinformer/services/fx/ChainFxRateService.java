package dk.kudishin.telegramfxinformer.services.fx;

import dk.kudishin.telegramfxinformer.domain.FxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("chain")
public class ChainFxRateService implements FxRateService {

    private final FxRateService primary;
    private final FxRateService backup;

    private final Logger log = LoggerFactory.getLogger(ChainFxRateService.class);

    public ChainFxRateService(@Qualifier("online") FxRateService primary, @Qualifier("cache") FxRateService backup) {
        this.primary = primary;
        this.backup = backup;
    }

    @Override
    public FxRate getFxRate() throws Exception {
        FxRate result;
        try {
            result = primary.getFxRate();
        } catch (Exception e) {
            log.error("Could not get fx rate from " + primary.getClass().getName() + ". Providing backup value from " + backup.getClass().getName());
            result = backup.getFxRate();
        }
        return result;
    }
}