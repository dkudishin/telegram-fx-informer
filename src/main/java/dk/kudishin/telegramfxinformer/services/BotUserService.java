package dk.kudishin.telegramfxinformer.services;

import dk.kudishin.telegramfxinformer.domain.BotUser;
import dk.kudishin.telegramfxinformer.repository.BotUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
public class BotUserService {

    private final BotUserRepository repository;

    private final Logger log = LoggerFactory.getLogger(BotUserService.class);

    public BotUserService(BotUserRepository repository) {
        this.repository = repository;
    }

    public BotUser saveUser(User apiUser) {
        BotUser user = repository.save(BotUser.from(apiUser));
        log.info("saved a user into the DB: "+ user);
        return user;
    }

    public boolean isUserRegistered(User apiUser) {
        Optional<BotUser> botUserOptional = repository.findById(apiUser.getId());
        return botUserOptional.isPresent();
    }

    public Optional<BotUser> loadUser(long id) {
        return repository.findById(id);
    }
}