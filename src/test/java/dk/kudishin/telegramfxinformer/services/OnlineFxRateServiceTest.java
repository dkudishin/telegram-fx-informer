package dk.kudishin.telegramfxinformer.services;

import dk.kudishin.telegramfxinformer.domain.FxRate;
import dk.kudishin.telegramfxinformer.services.fx.OnlineFxRateService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Disabled
class OnlineFxRateServiceTest {

    @Autowired
    private OnlineFxRateService service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getFxRate() throws Exception {
        FxRate fxRate = service.getFxRate();
        System.out.println(fxRate);
        assertNotEquals(0, fxRate.getRate());
    }
}