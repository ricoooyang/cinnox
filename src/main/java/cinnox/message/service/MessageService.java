package cinnox.message.service;

import cinnox.message.model.UserMessages;
import cinnox.message.model.WebhookEvent;
import cinnox.message.repository.EventRepository;
import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private LineMessagingClient lineMessagingClient;
    @Autowired
    private LineBlobClient lineBlobClient;


    public void receiveWebhookEvent(WebhookEvent webhookEvent) {
        Optional.ofNullable(webhookEvent.events)
            .filter(events -> !events.isEmpty())
            .ifPresent(events -> eventRepository.saveAll(events));
    }

    public UserMessages userMessages(String userId, Long from) {
        UserMessages userMessages = new UserMessages();
        userMessages.messages = eventRepository.findMessageEvents(userId, from, 30).stream()
            .map(event -> event.message).collect(Collectors.toList());
        return userMessages;
    }

    public void pushMessage(String userId, cinnox.message.model.PushMessage message) {
        lineMessagingClient.pushMessage(new PushMessage(userId, new TextMessage(message.message)));
    }

    public byte[] messageContent(String messageId) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        MessageContentResponse file = lineBlobClient.getMessageContent(messageId).get(180L, TimeUnit.SECONDS);
        return file.getStream().readAllBytes();
    }

}
