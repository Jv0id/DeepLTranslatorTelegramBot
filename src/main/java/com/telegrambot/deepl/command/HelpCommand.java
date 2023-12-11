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

import com.telegrambot.deepl.service.SendMessageServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class HelpCommand implements CommandInterface {

    private final SendMessageServiceInterface sendMessageServiceInterface;

    private final static String HELP_MESSAGE_EN = "ℹ\uFE0F HELP MENU ℹ\uFE0F\n" +
                                                  "            \n" +
                                                  "            \uD83D\uDD3B Here you can see commands what I can understand \uD83D\uDD3B\n" +
                                                  "            \n" +
                                                  "            ❇\uFE0F /start - Display greeting message\n" +
                                                  "            \n" +
                                                  "            \uD83E\uDDE0 /help - Display info about acceptable commands\n" +
                                                  "            \n" +
                                                  "            \uD83C\uDF10 /translate - This command will automatically detect the language of the message you have sent and translate it into the language of your choice\n" +
                                                  "            \n" +
                                                  "            \uD83D\uDC40 /set_languages - The command displays a menu of languages for translation\n" +
                                                  "                        \n" +
                                                  "            \uD83D\uDCD9 /languages - To see a list of available languages that the bot understands\n" +
                                                  "            \n" +
                                                  "            \uD83D\uDCAD /support - View bot administrator contacts\n" +
                                                  "            \n" +
                                                  "            ⛔\uFE0F /delete_my_data - This command will delete all data about you, as well as terminate the bot";
    private final static String HELP_MESSAGE_RU = "ℹ\uFE0F HELP ℹ\uFE0F\n" +
                                                  "\n" +
                                                  "\uD83D\uDD3B 在这里您可以看到我理解的命令\uD83D\uDD3B。\n" +
                                                  "\n" +
                                                  "❇\uFE0F /start - 欢迎信息\n" +
                                                  "\n" +
                                                  "\uD83E\uDDE0 /help - 显示有关可用命令的信息\n" +
                                                  "\n" +
                                                  "\uD83C\uDF10 /translate - 该命令将自动检测您发送的信息的语言，并将其翻译成您选择的语言\n" +
                                                  "\n" +
                                                  "\uD83D\uDC40 /set_languages - 该命令显示一个菜单，用于选择翻译语言\n" +
                                                  "\n" +
                                                  "\uD83D\uDCD9 /languages - 查看机器人可理解的可用语言列表\n" +
                                                  "\n" +
                                                  "\uD83D\uDCAD /support - 查看机器人管理员联系方式\n" +
                                                  "\n" +
                                                  "⛔\uFE0F /delete_my_data - 该命令将删除有关您的所有数据，并终止机器人。";

    public HelpCommand(SendMessageServiceInterface sendMessageServiceInterface) {
        this.sendMessageServiceInterface = sendMessageServiceInterface;
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
            setTranslateButtonHelp(chatId);
        }
    }

    @Override
    public void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        CommandUtility.handleTranslateCallbackQuery(sendMessageServiceInterface,
                "translate_chinese_help",
                callbackQuery,
                HELP_MESSAGE_RU);
    }

    private void setTranslateButtonHelp(Long chatId) {
        CommandUtility.setTranslateButton(sendMessageServiceInterface,
                "翻译成中文",
                "translate_chinese_help",
                chatId,
                HELP_MESSAGE_EN);
    }
}
