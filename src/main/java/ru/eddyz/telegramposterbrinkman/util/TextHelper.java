package ru.eddyz.telegramposterbrinkman.util;

import ru.eddyz.telegramposterbrinkman.entities.Post;

import java.util.Arrays;

public class TextHelper {

    public static Long getIdFromText(String text) {
        return Arrays.stream(text.split("\n"))
                .filter(s -> s.startsWith("Номер"))
                .mapToLong(s -> Long.parseLong(s.split(":")[1].trim()))
                .findFirst()
                .getAsLong();
    }

    public static String generatePostMessage(Post post) {
        String status = post.isStatus() ? "Активен ✅" : "Не активен ❌";
        return """
                <b>Номер поста</b>: %d
                <b>Статус</b>: %s
                <b>Частота отправлений:</b> %s
                
                %s""".formatted(post.getId(), status, post.getPostMode().toString(), post.getPostText() == null ? "" : post.getPostText());
    }
}
