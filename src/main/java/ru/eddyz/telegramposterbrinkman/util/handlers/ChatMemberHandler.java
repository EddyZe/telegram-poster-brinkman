package ru.eddyz.telegramposterbrinkman.util.handlers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;

public interface ChatMemberHandler {

    BotApiMethod<?> handle(ChatMemberUpdated chatMember);
}
