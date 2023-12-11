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

import com.telegrambot.deepl.config.ChatIdHolder;
import com.telegrambot.deepl.service.SendMessageServiceInterface;
import com.telegrambot.deepl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class DeleteCommand implements CommandInterface {

    private final SendMessageServiceInterface sendMessageServiceInterface;
    private final UserService userService;

    private final static String DELETE_MESSAGE_EN = "✅Your data were successfully deleted✅\n" +
                                                    "            \n" +
                                                    "            If you want to go back, just type /start again.\n" +
                                                    "            \n" +
                                                    "            To clear your chat history with this bot, please follow these steps:\n" +
                                                    "            1. Tap on the bot's name at the top of the chat.\n" +
                                                    "            2. Tap on 'Clear Messages' (on mobile) or 'Clear Chat History' (on desktop).\n" +
                                                    "            3. Confirm the action.";
    private final static String DELETE_MESSAGE_RU = "✅您的数据已成功删除✅✅\n" +
                                                    "\n" +
                                                    "如果想返回，只需再次输入 /start。\n" +
                                                    "\n" +
                                                    "要清除与该机器人的聊天记录，请按以下步骤操作：\n" +
                                                    "1.点击聊天顶部的机器人名称。\n" +
                                                    "2.点击 \"删除通信\"（手机）或 \"清除历史记录\"（桌面）。\n" +
                                                    "3.确认操作。";

    public DeleteCommand(SendMessageServiceInterface sendMessageServiceInterface, UserService userService) {
        this.sendMessageServiceInterface = sendMessageServiceInterface;
        this.userService = userService;
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

            userService.removeUserLanguage(chatId);
            userService.removeUserLanguagePair(chatId);
            ChatIdHolder chatIdHolder = new ChatIdHolder(chatId);
            userService.deleteUser(chatIdHolder);

            setTranslateButtonDelete(chatId);
        }
    }

    @Override
    public void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        CommandUtility.handleTranslateCallbackQuery(sendMessageServiceInterface,
                "translate_chinese_delete",
                callbackQuery,
                DELETE_MESSAGE_RU);
    }

    private void setTranslateButtonDelete(Long chatId) {
        CommandUtility.setTranslateButton(sendMessageServiceInterface,
                "翻译成中文",
                "translate_chinese_delete",
                chatId,
                DELETE_MESSAGE_EN);
    }
}
