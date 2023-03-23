package org.example.handlers;

import com.lilittlecat.chatgpt.offical.ChatGPT;
import org.example.BotConfig;
import org.example.hue.HueController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.io.InvalidObjectException;
import java.util.List;

public class HueHandlers extends TelegramLongPollingBot {
    private static final String LOGTAG = "HUEBOT";
    private static final int WAITINGCHANNEL = 1;
    private static final String HELP_TEXT = "Тут ты можешь выполнить управление умным домом";
    private static final String CANCEL_COMMAND = "/stop";
    private static final String STATE_COMMAND = "/state";
    //    private static final String AFTER_CHANNEL_TEXT = "A message to provided channel will be sent if the bot was added to it as admin.";
//    private static final String WRONG_CHANNEL_TEXT = "Wrong username, please remember to add *@* before the username and send only the username.";
//    private static final String CHANNEL_MESSAGE_TEXT = "This message was sent by *@updateschannelbot*. Enjoy!";
    private static final String ERROR_MESSAGE_TEXT = "There was an error sending the message to channel *%s*, the error was: ```%s```";
    private final HueController hueController = new HueController();

//    private final ConcurrentHashMap<Integer, Integer> userState = new ConcurrentHashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                try {
                    handleIncomingMessage(message);
                } catch (InvalidObjectException e) {
                    BotLogger.severe(LOGTAG, e);
                }
            }
        } catch (Exception e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private void handleIncomingMessage(Message message) throws InvalidObjectException {

        String command = message.getText();
        if (STATE_COMMAND.equals(command)) {
            sendStateMessage(message.getChatId(), message.getMessageId(), null);
        } else {
            gptReaction(message.getChatId(), message.getMessageId(), null, command);
        }
//        int state = userState.getOrDefault(message.getFrom().getId(), 0);
//        switch(state) {

//            case HELP_TEXT:
//                sendHelpMessage(message.getChatId(), message.getMessageId(), null);

//            default:
//                sendHelpMessage(message.getChatId(), message.getMessageId(), null);
//                userState.put(message.getFrom().getId(), WAITINGCHANNEL);
//                break;
//        }
    }

    private void sendHelpMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        sendMessage.setText(HELP_TEXT);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private void gptReaction(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        String output;
        ChatGPT gpt = new ChatGPT("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");


        while (true) {
            try {
                output = gpt.ask(message);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sendMessage.setText(output);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private void sendStateMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }

        // FIXME: 20.03.2023

        sendMessage.setText(hueController.getAllLights());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private void sendErrorMessage(Message message, String errorText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(String.format(ERROR_MESSAGE_TEXT, message.getText().trim(), errorText.replace("\"", "\\\"")));
        sendMessage.enableMarkdown(true);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return BotConfig.EVE_USER;
    }

    @Override
    public String getBotToken() {
        return BotConfig.EVE_TOKEN;
    }
}
