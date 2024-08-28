package ru.eddyz.telegramposterbrinkman.util.enums;

public enum PostMode {
    ONE("Раз в час"),
    TWO("Два раза в час"),
    THREE("Три раза в час");

    private final String mode;

    PostMode(String mode) {
        this.mode = mode;
    }


    @Override
    public String toString() {
        return mode;
    }
}
