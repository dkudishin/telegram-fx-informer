package dk.kudishin.telegramfxinformer.services;

import dk.kudishin.telegramfxinformer.domain.FxRate;
import dk.kudishin.telegramfxinformer.domain.FxRateJsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class FxRateServiceTest {

    @Autowired
    private FxRateService service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getFxRate() throws Exception {
        FxRateJsonObject fxRate = service.queryForFxRate();
        System.out.println(fxRate);
        assertNotEquals(0, fxRate.getAskPrice());
    }

    @Test
    void savesRate() {
        FxRateJsonObject fxRate = new FxRateJsonObject();

        fxRate.setExchangeRate(1.0);
        fxRate.setFromCurrencyCode("USD");
        fxRate.setToCurrencyCode("USD");
        fxRate.setLastRefreshed(LocalDateTime.now());
        FxRate rate = service.saveFxRate(fxRate);

        //jdbcTemplate.queryForList("select * from fx_rate");
        assertNotEquals(0, rate.getId());
        assertEquals( 1.0, rate.getRate());
    }
}