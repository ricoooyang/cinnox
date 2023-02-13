package cinnox.message.repository;


import cinnox.message.model.Event;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
    @Aggregation(pipeline = {
        "{ $match: {type:message, source:{type:user, userId:?0}, timestamp:{$gte:?1}}}",
        "{ $sort : { timestamp : 1 } }",
        "{ $limit : ?2}"
    }
    )
    List<Event> findMessageEvents(String userId, Long timestamp, int limit);
}
