package dk.kudishin.telegramfxinformer.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "fx_rate")
@Getter
@Setter
@ToString
public class FxRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fromCurrency;
    private String toCurrency;
    private double rate;
    private LocalDateTime rateTime;

    public static FxRate from(FxRateJsonObject o) {
        FxRate fx = new FxRate();
        fx.setFromCurrency(o.getFromCurrencyCode());
        fx.setToCurrency(o.getToCurrencyCode());
        fx.setRate(o.getExchangeRate());
        fx.setRateTime(o.getLastRefreshed());
        return fx;
    }

    public String getPrintableMessage() {
        return fromCurrency + "/" + toCurrency+ " = " + rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FxRate rate = (FxRate) o;

        return id == rate.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}