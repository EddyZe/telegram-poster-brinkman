package ru.eddyz.telegramposterbrinkman.util.handlers.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.eddyz.telegramposterbrinkman.commands.DeletePostCommand;
import ru.eddyz.telegramposterbrinkman.commands.EditPostModeCommand;
import ru.eddyz.telegramposterbrinkman.commands.SelectPostCommand;
import ru.eddyz.telegramposterbrinkman.util.enums.InlineButtons;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;
import ru.eddyz.telegramposterbrinkman.util.handlers.CallBackQueryHandler;




@Component
public class CallBackHandlerImpl implements CallBackQueryHandler {

    private final SelectPostCommand selectPostCommand;
    private final DeletePostCommand deletePostCommand;
    private final EditPostModeCommand editPostModeCommand;

    public CallBackHandlerImpl(SelectPostCommand selectPostCommand, DeletePostCommand deletePostCommand, EditPostModeCommand editPostModeCommand) {
        this.selectPostCommand = selectPostCommand;
        this.deletePostCommand = deletePostCommand;
        this.editPostModeCommand = editPostModeCommand;
    }

    @Override
    public BotApiMethod<?> handel(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData();

        if (data.equals(InlineButtons.SELECT_POST.name())) {
            return selectPostCommand.execute(message);
        }

        if (data.equals(InlineButtons.DELETE_POST.name()))
            return deletePostCommand.execute(message);

        if (data.equals(PostMode.ONE.name()))
            return editPostModeCommand.execute(message, PostMode.ONE);

        if (data.equals(PostMode.TWO.name()))
            return editPostModeCommand.execute(message, PostMode.TWO);

        if (data.equals(PostMode.THREE.name()))
            return editPostModeCommand.execute(message, PostMode.THREE);

        return null;
    }
}
