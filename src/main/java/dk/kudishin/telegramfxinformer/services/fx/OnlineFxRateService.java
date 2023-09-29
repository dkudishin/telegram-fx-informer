package dk.kudishin.telegramfxinformer.services.fx;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.kudishin.telegramfxinformer.domain.FxRate;
import dk.kudishin.telegramfxinformer.domain.FxRateJsonObject;
import dk.kudishin.telegramfxinformer.repositories.FxRateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Qualifier("online")
public class OnlineFxRateService implements FxRateService {

    private final String url;
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private final FxRateRepository repository;


    public OnlineFxRateService(@Value("${api.url}") String url, FxRateRepository repository) {
        this.url = url;
        this.repository = repository;
    }

    @Override
    public FxRate getFxRate() throws Exception {
        FxRateJsonObject fxo = queryForFxRate();
        return saveFxRate(fxo);
    }

    private FxRateJsonObject queryForFxRate() throws Exception {

        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        FxRateJsonObject fxRateJsonObject = mapper
                .readValue(response.getBody(), FxRateJsonObject.class);
        log.info("got a new fx rate object from the API: " + fxRateJsonObject);
        return fxRateJsonObject;
    }

    private FxRate saveFxRate(FxRateJsonObject fxRateJsonObject) {
        FxRate fxRate = repository.save(FxRate.from(fxRateJsonObject));
        log.info("saved an fx rate to the DB: "+ fxRate);
        return fxRate;
    }
}