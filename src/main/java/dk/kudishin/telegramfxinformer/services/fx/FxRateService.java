package dk.kudishin.telegramfxinformer.services.fx;


import dk.kudishin.telegramfxinformer.domain.FxRate;

public interface FxRateService {

    FxRate getFxRate() throws Exception;
}