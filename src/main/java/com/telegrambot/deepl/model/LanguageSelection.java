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

package com.telegrambot.deepl.model;

import java.util.Objects;

public final class LanguageSelection {
    private final String targetLanguage;

    public LanguageSelection(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String targetLanguage() {
        return targetLanguage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        LanguageSelection that = (LanguageSelection) obj;
        return Objects.equals(this.targetLanguage, that.targetLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetLanguage);
    }

    @Override
    public String toString() {
        return "LanguageSelection[" +
               "targetLanguage=" + targetLanguage + ']';
    }
}
