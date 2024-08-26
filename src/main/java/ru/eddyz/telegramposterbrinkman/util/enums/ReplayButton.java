package ru.eddyz.telegramposterbrinkman.util.enums;

public enum ReplayButton {
    CREATE_POST("Создать пост ➕"),
    SELECT_ACTIVE_POST("Выбрать пост для постинга 📃");


    private final String cmd;

    ReplayButton(String cmd) {
        this.cmd = cmd;
    }


    @Override
    public String toString() {
        return cmd;
    }
}
