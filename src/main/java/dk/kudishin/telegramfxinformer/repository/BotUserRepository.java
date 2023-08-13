package dk.kudishin.telegramfxinformer.repository;

import dk.kudishin.telegramfxinformer.domain.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {
}