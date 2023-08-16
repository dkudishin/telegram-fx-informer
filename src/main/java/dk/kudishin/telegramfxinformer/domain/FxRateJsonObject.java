package dk.kudishin.telegramfxinformer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@JsonRootName(value = "Realtime Currency Exchange Rate")
public class FxRateJsonObject {

    @JsonProperty("1. From_Currency Code")
    private String fromCurrencyCode;
    @JsonProperty("2. From_Currency Name")
    private String fromCurrencyName;
    @JsonProperty("3. To_Currency Code")
    private String toCurrencyCode;
    @JsonProperty("4. To_Currency Name")
    private String toCurrencyName;
    @JsonProperty("5. Exchange Rate")
    private double exchangeRate;
    @JsonProperty("6. Last Refreshed")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastRefreshed;
    @JsonProperty("7. Time Zone")
    private String timeZone;
    @JsonProperty("8. Bid Price")
    private double bidPrice;
    @JsonProperty("9. Ask Price")
    private double askPrice;

}