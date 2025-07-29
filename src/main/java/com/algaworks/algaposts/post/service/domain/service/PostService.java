package com.algaworks.algaposts.post.service.domain.service;

import com.algaworks.algaposts.post.service.api.model.PostInput;
import com.algaworks.algaposts.post.service.api.model.PostProcessingData;
import com.algaworks.algaposts.post.service.api.model.PostProcessingResultData;
import com.algaworks.algaposts.post.service.api.model.PostSummaryOutput;
import com.algaworks.algaposts.post.service.common.IdGenerator;
import com.algaworks.algaposts.post.service.domain.exception.EntityNotFoundException;
import com.algaworks.algaposts.post.service.infrastructure.rabbitmq.RabbitMQConfig;
import com.algaworks.algaposts.post.service.domain.model.Post;
import com.algaworks.algaposts.post.service.domain.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public Post createPost(PostInput postInput) {
        UUID id = IdGenerator.generateTimeBasedUUID();

        Post post = newPost(postInput, id);

        postRepository.saveAndFlush(post);

        log.info("Saving incomplete Post");

        sendPostProcessingData(post);

        log.info("Sending Post to Text Processor Service");

        return post;
    }

    @Transactional
    public void processPostResult(PostProcessingResultData resultData) {
        Post post = findByIdOrFail(resultData.getPostId());
        post.setWordCount(resultData.getWordCount());
        post.setCalculatedValue(resultData.getCalculatedValue());

        postRepository.saveAndFlush(post);

        log.info("Saving complete Post");
    }

    public Post findByIdOrFail(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(postId));
    }

    @Transactional
    public Page<PostSummaryOutput> findAllPostsSummary(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(this::convertToSummaryOutput);
    }

    private Post newPost(PostInput postInput, UUID id) {
        return Post.builder()
                .id(id)
                .author(postInput.getAuthor())
                .body(postInput.getBody())
                .title(postInput.getTitle())
                .build();
    }

    private void sendPostProcessingData(Post post) {
        PostProcessingData postProcessingData = PostProcessingData.builder()
                .postId(post.getId())
                .postBody(post.getBody())
                .build();

        String exchange = RabbitMQConfig.DIRECT_EXCHANGE_POST_PROCESSING;
        String routingKey = RabbitMQConfig.ROUTING_KEY_PROCESS_TEXT;
        Object payLoad = postProcessingData;

        rabbitTemplate.convertAndSend(exchange, routingKey, payLoad);
    }

    private PostSummaryOutput convertToSummaryOutput(Post post) {
        return PostSummaryOutput.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .summary(extractSummary(post.getBody()))
                .build();
    }

    private String extractSummary(String body) {
        return body.lines().limit(3).collect(Collectors.joining(System.lineSeparator()));
    }
}
