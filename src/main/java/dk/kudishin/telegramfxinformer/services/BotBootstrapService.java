package dk.kudishin.telegramfxinformer.services;


import dk.kudishin.telegramfxinformer.FxInformerBot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
//@ConditionalOnExpression("#{!environment.getProperty('spring.profiles.active').contains('ci')}")
@Profile("!ci")
public class BotBootstrapService implements CommandLineRunner {


    private final FxInformerBot bot;

    public BotBootstrapService(FxInformerBot bot) {
        this.bot = bot;
    }

    @Override
    public void run(String... args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}