package dk.kudishin.telegramfxinformer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RateAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int alertId;
    private LocalDateTime alertTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = false)
    private BotUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id", unique = false)
    private FxRate rate;

    /*
    SELECT * FROM RATE_ALERT A
    INNER JOIN FX_RATE F ON A.RATE_ID = F.ID
    INNER JOIN PUBLIC.BOT_USER BU ON BU.USER_ID = A.USER_ID
     */

    public RateAlert() {
    }

    public RateAlert(BotUser user, FxRate rate) {
        this.alertTime = LocalDateTime.now();
        this.user = user;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RateAlert rateAlert = (RateAlert) o;

        return alertId == rateAlert.alertId;
    }

    @Override
    public int hashCode() {
        return alertId;
    }

    @Override
    public String toString() {
        return "RateAlert{" +
                "alertId=" + alertId +
                ", alertTime=" + alertTime +
                ", user=" + user +
                ", rate=" + rate +
                '}';
    }
}