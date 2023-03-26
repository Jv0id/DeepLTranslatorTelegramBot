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
import com.telegrambot.deepl.service.TranslateMessageServiceInterface;
import com.telegrambot.deepl.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChangeLanguageCommand  extends TranslateCommand {

    public ChangeLanguageCommand(TranslateMessageServiceInterface translateMessageServiceInterface,
                                 SendMessageServiceInterface sendMessageServiceInterface,
                                 UserService userService) {
        super(translateMessageServiceInterface, sendMessageServiceInterface, userService);
    }

    @Override
    public void execute(Update update) throws InterruptedException {
        Long chatId = update.getMessage().getChatId();
        Integer messageId = update.getMessage().getMessageId();
        sendLanguageSelectionMessage(chatId, messageId);
    }
}
