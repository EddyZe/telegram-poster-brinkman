package ru.eddyz.telegramposterbrinkman.util.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.eddyz.telegramposterbrinkman.util.enums.ReplayButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplayInit {

    public static ReplyKeyboardMarkup defaultKey() {
        List<KeyboardRow> rows = createRows(
                createdRow(ReplayButton.CREATE_POST.toString(), ReplayButton.SELECT_ACTIVE_POST.toString())
        );
        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .keyboard(rows)
                .build();
    }

    private static KeyboardRow createdRow(String... command) {
        KeyboardRow row = new KeyboardRow();
        Arrays.stream(command).forEach(row::add);
        return row;
    }

    private static List<KeyboardRow> createRows(KeyboardRow... row) {
        return new ArrayList<>(Arrays.asList(row));
    }
}
