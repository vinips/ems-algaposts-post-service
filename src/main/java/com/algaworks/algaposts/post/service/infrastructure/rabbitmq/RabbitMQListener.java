package com.algaworks.algaposts.post.service.infrastructure.rabbitmq;

import com.algaworks.algaposts.post.service.api.model.PostProcessingResultData;
import com.algaworks.algaposts.post.service.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final PostService postService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_POST_PROCESSING_RESULT)
    public void handlePostProcessingResult(@Payload PostProcessingResultData postProcessingResultData) {
        postService.processPostResult(postProcessingResultData);
    }

}
