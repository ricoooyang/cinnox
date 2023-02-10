package cinnox;

import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.LineMessagingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineMessageConfig {

    public static final String CHANNEL_SECRET = "c8f450300759f2c79f2465bdcd8812c0";

    public static final String CHANNEL_ACCESS_TOKEN = "JUgSjjKwAp4kNLpVN3gRfB8JAWx8cr2SRu0MXMnzfcd7Cey54+TW0qBsLuEGKLmovnQw1/AygyqbqG9RhWmi66dK/7sRqfzQALa7ZeVOCSMidG9D2GjvZIqtMFHogyHBO5jrTWr7M9JGZlWbPk8qsQdB04t89/1O/w1cDnyilFU=";

    @Bean
    public LineMessagingClient lineMessagingClient() throws Exception {
        return LineMessagingClient.builder(CHANNEL_ACCESS_TOKEN).build();
    }

    @Bean
    public LineBlobClient lineBlobClient() throws Exception {
        return LineBlobClient.builder(CHANNEL_ACCESS_TOKEN).build();
    }

}
