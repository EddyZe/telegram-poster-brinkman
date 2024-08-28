package ru.eddyz.telegramposterbrinkman.data;

import ru.eddyz.telegramposterbrinkman.entities.Post;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    public final static Map<Long, Enum<?>> currentCommand = new HashMap<>();
    public final static Map<Long, Enum<?>> currentStateUser = new HashMap<>();
    public final static Map<Long, Post> currentStatePost = new HashMap<>();

    public static void clear(Long chatId) {
        currentCommand.remove(chatId);
        currentStateUser.remove(chatId);
        currentStatePost.remove(chatId);
    }
}
