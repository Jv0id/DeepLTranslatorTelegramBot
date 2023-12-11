/*
- Copyright 2023 Aleksandr Shabalin
-
- Licensed under the Apache License, Version 2.0 (the "License");
- you may not use this file except in compliance with the License.
- You may obtain a copy of the License at
-
- `<http://www.apache.org/licenses/LICENSE-2.0>`
-
- Unless required by applicable law or agreed to in writing, software
- distributed under the License is distributed on an "AS IS" BASIS,
- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
- See the License for the specific language governing permissions and
- limitations under the License.
*/

package com.telegrambot.deepl.command;

import com.telegrambot.deepl.bot.DeepLTelegramBot;
import com.telegrambot.deepl.service.SendMessageServiceInterface;
import com.telegrambot.deepl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StartCommand implements CommandInterface {

    private final SendMessageServiceInterface sendMessageServiceInterface;
    private final UserService userService;
    private final DeepLTelegramBot deeplBot;

    private final static String START_MESSAGE_EN = "\uD83D\uDD25Greetings\uD83D\uDD25\n" +
                                                   "            \n" +
                                                   "            My name is DeepLTranslatorBot, as you may have understood from my name I am designed to translate text from one language to another.s\n" +
                                                   "            \n" +
                                                   "            \uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\n" +
                                                   "            \n" +
                                                   "            Write /help and you will find out what I can do.\n" +
                                                   "            \n" +
                                                   "            I hope you will enjoy working with me.\uD83D\uDE07";
    private final static String START_MESSAGE_RU = "\uD83D\uDD25问候\uD83D\uDD25\n" +
                                                   "\n" +
                                                   "我的名字是 DeepLTranslatorBot，正如您已经从我的名字中了解到的，我的工作是将文本从一种语言翻译成另一种语言。\n" +
                                                   "\n" +
                                                   "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\n" +
                                                   "\n" +
                                                   "写 /help 你就会知道我能做什么。\n" +
                                                   "\n" +
                                                   "希望您喜欢与我合作 \uD83D\uDE07";

    public StartCommand(SendMessageServiceInterface sendMessageServiceInterface,
                        UserService userService,
                        DeepLTelegramBot deeplBot) {
        this.sendMessageServiceInterface = sendMessageServiceInterface;
        this.userService = userService;
        this.deeplBot = deeplBot;
    }

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                handleCallbackQuery(update.getCallbackQuery());
            } catch (TelegramApiException e) {
                log.error("Error occurred: " + e.getMessage());
            }
        } else if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();

            userService.registerUser(update.getMessage());
            setupBotMenu();
            setTranslateButtonStart(chatId);
        }
    }

    @Override
    public void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        CommandUtility.handleTranslateCallbackQuery(sendMessageServiceInterface,
                "translate_chinese_start",
                callbackQuery,
                START_MESSAGE_RU);
    }

    private void setTranslateButtonStart(Long chatId) {
        CommandUtility.setTranslateButton(sendMessageServiceInterface,
                "翻译成中文",
                "translate_chinese_start",
                chatId,
                START_MESSAGE_EN);
    }

    public void setupBotMenu() {
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/start", "Get a welcome message"));
        botCommands.add(new BotCommand("/help", "Info about commands"));
        botCommands.add(new BotCommand("/translate", "Translate your message with auto-detection"));
        botCommands.add(new BotCommand("/set_languages", "Language selection"));
        botCommands.add(new BotCommand("/languages", "List of available languages"));
        botCommands.add(new BotCommand("/support", "Admin contacts"));
        botCommands.add(new BotCommand("/delete_my_data", "Delete your account"));

        try {
            deeplBot.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
