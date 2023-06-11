package org.example;

import org.example.handlers.HueHandlers;
import org.example.mock.HueMock;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Hello world!
 */
public class App {
    private static final String LOGTAG = "MAIN";

    public static void main(String[] args) {


        HueMock hueMock = new HueMock();
        hueMock.startServer();




        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            try {

                telegramBotsApi.registerBot(new HueHandlers());

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * @return TelegramBotsApi to register the bots.
     * @brief Creates a Telegram Bots Api to use Long Polling (getUpdates) bots.
     */

}
