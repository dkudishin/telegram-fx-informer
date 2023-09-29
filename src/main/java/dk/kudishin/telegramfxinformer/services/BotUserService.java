package dk.kudishin.telegramfxinformer.services;

import dk.kudishin.telegramfxinformer.domain.BotUser;
import dk.kudishin.telegramfxinformer.repositories.BotUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BotUserService {

    private final BotUserRepository repository;

    public BotUserService(BotUserRepository repository) {
        this.repository = repository;
    }

    public BotUser saveUser(User apiUser) {
        BotUser user = repository.save(BotUser.from(apiUser));
        log.info("saved a user to the DB: "+ user);
        return user;
    }

    public boolean isUserRegistered(User apiUser) {
        Optional<BotUser> botUserOptional = repository.findById(apiUser.getId());
        return botUserOptional.isPresent();
    }

    public Optional<BotUser> loadUser(long id) {
        return repository.findById(id);
    }

    public List<BotUser> findAllUsers() {
        return repository.findAll();
    }
}