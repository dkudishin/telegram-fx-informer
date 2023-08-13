package dk.kudishin.telegramfxinformer.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.kudishin.telegramfxinformer.domain.FxRate;
import dk.kudishin.telegramfxinformer.domain.FxRateJsonObject;
import dk.kudishin.telegramfxinformer.repository.FxRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FxRateService {

    private final String url;
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private final FxRateRepository repository;

    private final Logger log = LoggerFactory.getLogger(FxRateService.class);

    public FxRateService(@Value("${api.url}") String url, FxRateRepository repository) {
        this.url = url;
        this.repository = repository;
    }

    public FxRateJsonObject queryForFxRate() throws Exception {

        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        FxRateJsonObject fxRateJsonObject = mapper
                .readValue(response.getBody(), FxRateJsonObject.class);
        log.info("got a new fx rate object from the API: " + fxRateJsonObject);
        return fxRateJsonObject;
    }

    public FxRate saveFxRate(FxRateJsonObject fxRateJsonObject) {
        FxRate fxRate = repository.save(FxRate.from(fxRateJsonObject));
        log.info("saved an fx rate to a DB: "+ fxRate);
        return fxRate;
    }
}