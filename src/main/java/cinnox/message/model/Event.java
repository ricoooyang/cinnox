package cinnox.message.model;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("events")
public class Event {

    public String type;
    public String mode;
    public Long timestamp;
    public Source source; // Not always included
    @Id
    public String webhookEventId;
    public DeliveryContext deliveryContext;

    public String replyToken; // Reply token used to send reply message to this event
    public Message message; // Message event

    public static class Source {
        public String type;
        public String groupId;
        public String roomId;
        @Indexed
        public String userId;
    }

    public static class DeliveryContext {
        public Boolean isRedelivery;
    }

    public static class Message {
        public String id;
        public String type;

        // message type = text, sticker
        public String text;
        public List<Emoji> emojis;
        public Mention mention;

        // message type = image
        public ImageSet imageSet;

        // message type = image, video, audio
        public ContentProvider contentProvider;

        // message type = file
        public String fileName;
        public Long fileSize;

        // message type = location
        public String title;
        public String address;
        public BigDecimal latitude;
        public BigDecimal longitude;

        // message type = sticker
        public String packageId;
        public String stickerId;
        public String stickerResourceType;
        public List<String> keywords;
    }

    // message type = text
    public static class Emoji {
        public int index;
        public int length;
        public String productId;
        public String emojiId;
    }

    public static class Mention {
        public List<Mentionee> mentionees;
    }

    public static class Mentionee {
        public int index;
        public int length;
        public String userId;
    }

    // message type = image, video, audio
    public static class ContentProvider {
        public String type;
        public String originalContentUrl; // URL of the image file. Only included when contentProvider.type is external.
        public String previewImageUrl; // URL of the preview image. Only included when contentProvider.type is external.
    }

    public static class ImageSet {
        public String id;
        public String index;
    }



}
