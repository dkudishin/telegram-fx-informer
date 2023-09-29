package dk.kudishin.telegramfxinformer;

import dk.kudishin.telegramfxinformer.domain.BotUser;
import dk.kudishin.telegramfxinformer.domain.FxRate;
import dk.kudishin.telegramfxinformer.domain.RateAlert;
import dk.kudishin.telegramfxinformer.services.BotUserService;
import dk.kudishin.telegramfxinformer.services.RateAlertService;
import dk.kudishin.telegramfxinformer.services.fx.FxRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class FxInformerBot extends TelegramLongPollingBot {


    @Value("${bot.token}")
    private String token;
    @Value("${bot.name}")
    private String name;

    private final FxRateService fxRateService;
    private final BotUserService botUserService;
    private final RateAlertService rateAlertService;

    public FxInformerBot(@Qualifier("chain") FxRateService fxRateService,
                         BotUserService botUserService,
                         RateAlertService rateAlertService) {
        this.fxRateService = fxRateService;
        this.botUserService = botUserService;
        this.rateAlertService = rateAlertService;
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onRegister() {
        log.warn("Bot started");
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            processUser(update);

            processMessage(update);
        }
    }

    private void processMessage(Update update) {
        if(update.getMessage().getText().startsWith("Get Fx Rate!")) {
            try {
                FxRate rate = sendFxRateMessage(update.getMessage().getChatId());
                rateAlertService.saveAlertToDb(
                        new RateAlert(BotUser.from(update.getMessage().getFrom()),
                                rate));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.getMessage().getText().startsWith("/start")){
            try {
                sendDefaultMessage(update.getMessage().getChatId());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("Ignoring unsupported message");
        }
    }

    private void processUser(Update update) {
        User apiUser = update.getMessage().getFrom();
        if (botUserService.isUserRegistered(apiUser)) {
            log.info("Message from existing user with id "+ apiUser.getId());
        } else {
            log.warn("Message from a new user with id "+ apiUser.getId());
            botUserService.saveUser(apiUser);
        }
    }


    private FxRate sendFxRateMessage(long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        try {
            FxRate fxRate = fxRateService.getFxRate();
            message.setText(fxRate.getPrintableMessage());
            message.setReplyMarkup(getKeyboardForText("Get Fx Rate!"));
            execute(message);
            return fxRate;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void sendDefaultMessage(long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Welcome! This bot sends Fx Rate updates. Hit the button to start.");
        ReplyKeyboardMarkup replyKeyboardMarkup = getKeyboardForText("Get Fx Rate!");
        message.setReplyMarkup(replyKeyboardMarkup);
        execute(message);
    }

    private ReplyKeyboardMarkup getKeyboardForText(String... buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        Arrays.stream(buttons).forEach(keyboardFirstRow::add);
        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

}