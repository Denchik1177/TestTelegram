package org.example.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.zeroone3010.yahueapi.Room;
import org.example.BotConfig;
import org.example.hue.HueController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

public class HueHandlers extends TelegramLongPollingBot {

    private static final String HELP_TEXT = "Тут ты можешь выполнить управление умным домом";
    private static final String CANCEL_COMMAND = "/stop";
    private static final String STATE_COMMAND = "/state";
    private static final String ERROR_MESSAGE_TEXT = "There was an error sending the message to channel *%s*, the error was: ```%s```";
    private final HueController hueController = new HueController();




    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message != null && message.hasText() && (message.getFrom().getId() == 518008985 || message.getFrom().getId() == 550634338))
                 {
                    try {
                        handleIncomingMessage(message);
                    } catch (InvalidObjectException e) {
                        e.printStackTrace();
                    }
                }
            } else if (update.hasCallbackQuery()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(update.getCallbackQuery().getData());
               if (update.getCallbackQuery().getData().contains("turnON")){
                   String name = jsonNode.get("turnON").asText();
                   hueController.turnLightInRoom(name);
                   sendReplyText(update.getCallbackQuery().getMessage()
                           , "Свет в "+name + " включен \uD83D\uDC9A");
               }else{
                   String name = jsonNode.get("turnOFF").asText();
                   hueController.turnOffLightInRoom(name);
                   sendReplyText(update.getCallbackQuery().getMessage(), "Свет в "+name + " выключен \uD83D\uDC9A");

               }

                System.out.println(update.getUpdateId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessage(Message message) throws InvalidObjectException {

        String command = message.getText();
        if (STATE_COMMAND.equals(command)) {
            sendStateMessage(message.getChatId(), message.getMessageId(), null);
        }

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
            e.printStackTrace();
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

        sendMessage.setText(hueController.getEnvironmentDescription());

        ArrayList<Room> rooms = (ArrayList<Room>) hueController.getRooms();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rooms.forEach(room -> {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("\uD83D\uDFE2 " + room.getName());
            inlineKeyboardButton.setCallbackData("{\"turnON\": \""+ room.getName()+"\"}");
            rowInline.add(inlineKeyboardButton);

            InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton("\uD83D\uDD34 " + room.getName());
            inlineKeyboardButton2.setCallbackData("{\"turnOFF\": \""+ room.getName()+"\"}");
            rowInline2.add(inlineKeyboardButton2);
        });

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }





    private void sendReplyText(Message message, String context) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());

        sendMessage.setText(context);
        sendMessage.enableMarkdown(true);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
