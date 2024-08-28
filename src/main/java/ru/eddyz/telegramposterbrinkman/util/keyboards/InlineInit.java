package ru.eddyz.telegramposterbrinkman.util.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.eddyz.telegramposterbrinkman.util.enums.InlineButtons;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InlineInit {

    private static List<List<InlineKeyboardButton>> createListButton(InlineKeyboardButton... inlineKeyboardButtons) {
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        Arrays.stream(inlineKeyboardButtons).toList().
                forEach(inlineKeyboardButton ->
                        rowsInLine.add(Collections.singletonList(inlineKeyboardButton)));

        return rowsInLine;
    }

    private static InlineKeyboardButton createButton(String text, Enum<?> idButton) {
        var courierCategory = new InlineKeyboardButton();

        courierCategory.setText(text);
        courierCategory.setCallbackData(idButton.name());

        return courierCategory;
    }

    public static InlineKeyboardMarkup postSetting() {
        var select = createButton(InlineButtons.SELECT_POST.toString(),
                InlineButtons.SELECT_POST);
        var one = createButton(PostMode.ONE.toString(),
                PostMode.ONE);
        var two = createButton(PostMode.TWO.toString(),
                PostMode.TWO);
        var three = createButton(PostMode.THREE.toString(),
                PostMode.THREE);
        var delete = createButton(InlineButtons.DELETE_POST.toString(), InlineButtons.DELETE_POST);

        return InlineKeyboardMarkup.builder()
                .keyboard(createListButton(select, one, two, three, delete))
                .build();
    }
}
