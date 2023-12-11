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
public class LanguagesCommand implements CommandInterface {

    private final SendMessageServiceInterface sendMessageServiceInterface;

    private final static String LIST_OF_LANGUAGES_MESSAGE_EN = "\n" +
                                                               "Here is a list of available languages:\n" +
                                                               "\n" +
                                                               "\uD83C\uDDFA\uD83C\uDDF8 - English\n" +
                                                               "\uD83C\uDDE9\uD83C\uDDEA - German\n" +
                                                               "\uD83C\uDDE8\uD83C\uDDFF - Czech\n" +
                                                               "\uD83C\uDDEA\uD83C\uDDF8 - Spanish\n" +
                                                               "\uD83C\uDDEB\uD83C\uDDF7 - French\n" +
                                                               "\uD83C\uDDEE\uD83C\uDDF9 - Italian\n" +
                                                               "\uD83C\uDDF7\uD83C\uDDFA - Russian\n" +
                                                               "\uD83C\uDDE8\uD83C\uDDF3 - Chinese\n" +
                                                               "You can use all of these languages in auto-define language mode with /translates\n" +
                                                               "You can also use these languages with the command /set_languages.";
    private final static String LIST_OF_LANGUAGES_MESSAGE_RU = "\n" +
                                                               "以下是可用语言列表：\n" +
                                                               "\n" +
                                                               "\uD83C\uDDFA\uD83C\uDDF8 - 英语\n" +
                                                               "\uD83C\uDDE9\uD83C\uDDEA - 德语\n" +
                                                               "\uD83C\uDDE8\uD83C\uDDFF - 捷克语\n" +
                                                               "\uD83C\uDDEA\uD83C\uDDF8 - 西班牙语\n" +
                                                               "\uD83C\uDDEB\uD83C\uDDF7 - 法语\n" +
                                                               "\uD83C\uDDEE\uD83C\uDDF9 - 意大利语\n" +
                                                               "\uD83C\uDDF7\uD83C\uDDFA - 俄语\n" +
                                                               "\uD83C\uDDE8\uD83C\uDDF3 - 中文\n" +
                                                               "您可以使用 /translates 命令在自动语言检测模式下使用所有这些语言\n" +
                                                               "此外，还可以使用 /set_languages 命令使用这些语言。";

    public LanguagesCommand(SendMessageServiceInterface sendMessageServiceInterface) {
        this.sendMessageServiceInterface = sendMessageServiceInterface;
    }

    @Override
    public void execute(Update update) throws InterruptedException {
        if (update.hasCallbackQuery()) {
            try {
                handleCallbackQuery(update.getCallbackQuery());
            } catch (TelegramApiException e) {
                log.error("Error occurred: " + e.getMessage());
            }
        } else if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            setTranslationButtonLanguage(chatId);
        }

    }

    @Override
    public void handleCallbackQuery(CallbackQuery callbackQuery) throws TelegramApiException {
        CommandUtility.handleTranslateCallbackQuery(sendMessageServiceInterface,
                "translate_chinese_lang",
                callbackQuery,
                LIST_OF_LANGUAGES_MESSAGE_RU);
    }

    private void setTranslationButtonLanguage(Long chatId) {
        CommandUtility.setTranslateButton(sendMessageServiceInterface,
                "翻译成中文",
                "translate_chinese_lang",
                chatId,
                LIST_OF_LANGUAGES_MESSAGE_EN);
    }
}
