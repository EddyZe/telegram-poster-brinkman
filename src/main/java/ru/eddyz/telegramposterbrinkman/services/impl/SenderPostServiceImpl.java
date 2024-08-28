package ru.eddyz.telegramposterbrinkman.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.eddyz.telegramposterbrinkman.entities.Channel;
import ru.eddyz.telegramposterbrinkman.entities.Post;
import ru.eddyz.telegramposterbrinkman.services.ChannelService;
import ru.eddyz.telegramposterbrinkman.services.PostService;
import ru.eddyz.telegramposterbrinkman.services.SenderPostsService;
import ru.eddyz.telegramposterbrinkman.util.Sender;
import ru.eddyz.telegramposterbrinkman.util.enums.PostMode;

import java.util.List;


@Component
@Slf4j
public class SenderPostServiceImpl implements SenderPostsService {

    private final AbsSender sender;
    private final PostService postService;
    private final ChannelService channelService;

    public SenderPostServiceImpl(@Lazy AbsSender sender, PostService postService, ChannelService channelService) {
        this.sender = sender;
        this.postService = postService;
        this.channelService = channelService;
    }


    @Override
    @Scheduled(cron = "0 * * * * *")
    public void sendPosts() {
        var activePost = postService.findByStatus(true);

        if (activePost.isEmpty())
            return;

        boolean lastElement = false;


        List<Long> channelIds = channelService.findAll()
                .stream()
                .map(Channel::getChannelId)
                .toList();

        for (int i = 0; i < channelIds.size(); i++) {
            if (i == channelIds.size() -1)
                lastElement = true;

            sendPost(channelIds.get(i), activePost.get(), lastElement);
        }
    }

    private void sendPost(Long channelId, Post activePost, boolean lastElement) {
        try {
            switch (activePost.getPostType()) {
                case TEXT -> sendTextPostMode(channelId, activePost, lastElement);
                case ANIMATION -> sendAnimationPostMode(channelId, activePost, lastElement);
                case VIDEO -> sendVideoPostMode(channelId, activePost, lastElement);
                case PHOTO -> sendPhotoPostMode(channelId, activePost, lastElement);
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка при автоматической отправке поста: %s".formatted(e.getMessage()));
        }
    }

    private void sendPhotoPostMode(Long channelId, Post activePost, boolean lastElement) throws TelegramApiException {
        if (activePost.getPostMode() == PostMode.ONE)
            sender.execute(
                Sender.sendPhoto(
                        channelId,
                        activePost.getPostText(),
                        activePost.getFileId(),
                        null
                )
        );
        else if (activePost.getPostMode() == PostMode.TWO) {
            if (activePost.getCount() == 1) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(
                        Sender.sendPhoto(
                                channelId,
                                activePost.getPostText(),
                                activePost.getFileId(),
                                null
                        )
                );
                postService.save(activePost);
            } else if (lastElement){
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        } else if (activePost.getPostMode() == PostMode.THREE) {
            if (activePost.getCount() == 2) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(
                        Sender.sendPhoto(
                                channelId,
                                activePost.getPostText(),
                                activePost.getFileId(),
                                null
                        )
                );
                postService.save(activePost);
            } else if (lastElement){
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        }
    }

    private void sendVideoPostMode(Long channelId, Post activePost, boolean lastElement) throws TelegramApiException {
        if (activePost.getPostMode() == PostMode.ONE)
            sender.execute(
                Sender.sendVideo(
                        channelId,
                        activePost.getPostText(),
                        activePost.getFileId(),
                        null
                )
        );
        else if (activePost.getPostMode() == PostMode.TWO) {
            if (activePost.getCount() == 1) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(Sender.sendVideo(
                        channelId,
                        activePost.getPostText(),
                        activePost.getFileId(),
                        null
                ));
                postService.save(activePost);
            } else if (lastElement) {
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        } else if (activePost.getPostMode() == PostMode.THREE) {
            if (activePost.getCount() == 2) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(Sender.sendVideo(
                        channelId,
                        activePost.getPostText(),
                        activePost.getFileId(),
                        null
                ));
                postService.save(activePost);
            } else if (lastElement) {
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        }
    }

    private void sendAnimationPostMode(Long channelId, Post activePost, boolean lastElement) throws TelegramApiException {
        if (activePost.getPostMode() == PostMode.ONE) {
            sender.execute(
                    Sender.sendAnimation(
                            channelId,
                            activePost.getPostText(),
                            activePost.getFileId(),
                            null));
        } else if (activePost.getPostMode() == PostMode.TWO) {
            if (activePost.getCount() == 1) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(
                        Sender.sendAnimation(
                                channelId,
                                activePost.getPostText(),
                                activePost.getFileId(),
                                null
                        ));
                postService.save(activePost);
            } else if (lastElement) {
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        } else if (activePost.getPostMode() == PostMode.THREE) {
            if (activePost.getCount() == 2) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(
                        Sender.sendAnimation(
                                channelId,
                                activePost.getPostText(),
                                activePost.getFileId(),
                                null
                        ));
                postService.save(activePost);
            } else if (lastElement) {
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        }
    }

    private void sendTextPostMode(Long channelId, Post activePost, boolean lastElement) throws TelegramApiException {
        if (activePost.getPostMode() == PostMode.ONE)
            sender.execute(
                    Sender.sendMessage(
                            channelId,
                            activePost.getPostText(),
                            null
                    )
            );
        else if (activePost.getPostMode() == PostMode.TWO) {
            if (activePost.getCount() == 1) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(
                        Sender.sendMessage(
                                channelId,
                                activePost.getPostText(),
                                null
                        )
                );
                postService.save(activePost);
            } else if (lastElement) {
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        } else if (activePost.getPostMode() == PostMode.THREE) {
            if (activePost.getCount() == 2) {
                if (lastElement)
                    activePost.setCount(0);
                sender.execute(
                        Sender.sendMessage(
                                channelId,
                                activePost.getPostText(),
                                null
                        )
                );
                postService.save(activePost);
            } else if (lastElement) {
                var currentCount = activePost.getCount();
                currentCount++;
                activePost.setCount(currentCount);
                postService.save(activePost);
            }
        }
    }
}
