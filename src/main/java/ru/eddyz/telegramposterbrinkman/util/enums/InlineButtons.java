package ru.eddyz.telegramposterbrinkman.util.enums;

public enum InlineButtons {
    SELECT_POST("Изменить статус 🔁"),
    DELETE_POST("Удалить пост ❌");

    private final String cmd;

    InlineButtons(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }
}
