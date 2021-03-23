package turntabl.io.client_connectivity.reporting;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class ReportingPublisher {
    RedisTemplate<?, ?> template;
    ChannelTopic topic;

    public ReportingPublisher(RedisTemplate<?, ?> template, ChannelTopic topic) {
        this.template = template;
        this.topic = topic;
    }

    public void publish(ReportingModel msg ) throws JsonProcessingException {
//        LOGGER.info("Sending message to Receiver"+msg);
        template.convertAndSend(topic.getTopic(), msg);
    }
}
