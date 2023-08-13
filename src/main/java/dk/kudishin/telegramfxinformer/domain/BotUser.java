package dk.kudishin.telegramfxinformer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.User;

@Entity(name = "bot_user")
@Getter
@Setter
@ToString
public class BotUser {
    @Id
    private long userId;
    private String firstName;
    private boolean subscribed;

    public static BotUser from(User u) {
        BotUser bu = new BotUser();
        bu.setUserId(u.getId());
        bu.setFirstName(u.getFirstName());
        return bu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BotUser botUser = (BotUser) o;

        return userId == botUser.userId;
    }

    @Override
    public int hashCode() {
        return (int) (userId ^ (userId >>> 32));
    }
}