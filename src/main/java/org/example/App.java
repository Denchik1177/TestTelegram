package org.example;
import com.lilittlecat.chatgpt.offical.ChatGPT;
import org.example.handlers.HueHandlers;
import org.example.hue.HueController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;
import org.telegram.telegrambots.meta.logging.BotsFileHandler;


import java.io.IOException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String LOGTAG = "MAIN";

    public static void main(String[] args) {


//        HueController hueController = new HueController();
//        List<String> descriptions = hueController.getRooms();

        BotLogger.setLevel(Level.ALL);
        BotLogger.registerLogger(new ConsoleHandler());
        try {
            BotLogger.registerLogger(new BotsFileHandler());
        } catch (IOException e) {
            BotLogger.severe(LOGTAG, e);
        }

        try {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = createTelegramBotsApi();
            try {
                // Register long polling bots. They work regardless type of TelegramBotsApi we are creating
                telegramBotsApi.registerBot(new HueHandlers());

            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
        } catch (Exception e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private static TelegramBotsApi createTelegramBotsApi() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi;
//        if (!BuildVars.useWebHook) {
//            // Default (long polling only)
            telegramBotsApi = createLongPollingTelegramBotsApi();
//        } else if (!BuildVars.pathToCertificatePublicKey.isEmpty()) {
//            // Filled a path to a pem file ? looks like you're going for the self signed option then, invoke with store and pem file to supply.
//            telegramBotsApi = createSelfSignedTelegramBotsApi();
//            telegramBotsApi.registerBot(new WebHookExampleHandlers());
//        } else {
//            // Non self signed, make sure you've added private/public and if needed intermediate to your cert-store.
//            telegramBotsApi = createNoSelfSignedTelegramBotsApi();
//            telegramBotsApi.registerBot(new WebHookExampleHandlers());
//        }
        return telegramBotsApi;
    }

    /**
     * @brief Creates a Telegram Bots Api to use Long Polling (getUpdates) bots.
     * @return TelegramBotsApi to register the bots.
     */
    private static TelegramBotsApi createLongPollingTelegramBotsApi() {
        return new TelegramBotsApi();
    }
//
//    /**
//     * @brief Creates a Telegram Bots Api to use Long Polling bots and webhooks bots with self-signed certificates.
//     * @return TelegramBotsApi to register the bots.
//     *
//     * @note https://core.telegram.org/bots/self-signed#java-keystore for generating a keypair in store and exporting the pem.
//     *  @note Don't forget to split the pem bundle (begin/end), use only the public key as input!
//     */
//    private static TelegramBotsApi createSelfSignedTelegramBotsApi() throws TelegramApiException {
//        return new TelegramBotsApi(BuildVars.pathToCertificateStore, BuildVars.certificateStorePassword, BuildVars.EXTERNALWEBHOOKURL, BuildVars.INTERNALWEBHOOKURL, BuildVars.pathToCertificatePublicKey);
//    }
//
//    /**
//     * @brief Creates a Telegram Bots Api to use Long Polling bots and webhooks bots with no-self-signed certificates.
//     * @return TelegramBotsApi to register the bots.
//     *
//     * @note Coming from a set of pem files here's one way to do it:
//     * @code{.sh}
//     * openssl pkcs12 -export -in public.pem -inkey private.pem > keypair.p12
//     * keytool -importkeystore -srckeystore keypair.p12 -destkeystore server.jks -srcstoretype pkcs12
//     * #have (an) intermediate(s) to supply? first:
//     * cat public.pem intermediate.pem > set.pem (use set.pem as -in)
//     * @endcode
//     */
//    private static TelegramBotsApi createNoSelfSignedTelegramBotsApi() throws TelegramApiException {
//        return new TelegramBotsApi(BuildVars.pathToCertificateStore, BuildVars.certificateStorePassword, BuildVars.EXTERNALWEBHOOKURL, BuildVars.INTERNALWEBHOOKURL);
//    }
}
