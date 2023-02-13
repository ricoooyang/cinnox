package cinnox.message.web;

import cinnox.message.model.UserMessages;
import cinnox.message.model.WebhookEvent;
import cinnox.message.service.MessageService;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageWebService {

    @Autowired
    private MessageService messageService;

    @PostMapping("/webhook-event")
    public void receiveWebhookEvent(@RequestBody WebhookEvent webhookEvent) {
        messageService.receiveWebhookEvent(webhookEvent);
    }

    @GetMapping("/user/{userId}/message")
    public UserMessages userMessages(@PathVariable("userId") String userId, @RequestParam("from") Long from) {
        return messageService.userMessages(userId, from);
    }

    @GetMapping(value = "/message/{messageId}/content", produces = MediaType.APPLICATION_CBOR_VALUE)
    public byte[] messageContent(@PathVariable("messageId") String messageId) throws InterruptedException, ExecutionException, TimeoutException, IOException {
        return messageService.messageContent(messageId);
    }

}
