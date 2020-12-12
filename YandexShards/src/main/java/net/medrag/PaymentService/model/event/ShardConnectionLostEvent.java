package net.medrag.PaymentService.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event, that describes if shard has lost or obtained connection
 * {@author} Stanislav Tretyakov
 * 09.03.2020
 */
@Getter
public class ShardConnectionLostEvent extends ApplicationEvent {

    private ShardEnum shardEnum;

    public ShardConnectionLostEvent(Object source, ShardEnum shardEnum) {
        super(source);
        this.shardEnum = shardEnum;
    }
}
