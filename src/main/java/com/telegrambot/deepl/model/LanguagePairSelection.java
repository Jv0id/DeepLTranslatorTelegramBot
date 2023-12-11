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

public final class LanguagePairSelection {
    private final String sourceLanguage;
    private final String targetLanguage;

    public LanguagePairSelection(String sourceLanguage, String targetLanguage) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public String sourceLanguage() {
        return sourceLanguage;
    }

    public String targetLanguage() {
        return targetLanguage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        LanguagePairSelection that = (LanguagePairSelection) obj;
        return Objects.equals(this.sourceLanguage, that.sourceLanguage) &&
               Objects.equals(this.targetLanguage, that.targetLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceLanguage, targetLanguage);
    }

    @Override
    public String toString() {
        return "LanguagePairSelection[" +
               "sourceLanguage=" + sourceLanguage + ", " +
               "targetLanguage=" + targetLanguage + ']';
    }
}
