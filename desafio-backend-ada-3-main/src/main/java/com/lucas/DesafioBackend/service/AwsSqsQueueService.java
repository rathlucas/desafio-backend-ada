package com.lucas.DesafioBackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.DesafioBackend.model.cliente.Cliente;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AwsSqsQueueService {

    @Value("${sqs.queue.name}")
    private String SERVICE_QUEUE;

    private final SqsTemplate sqsTemplate;

    private final ObjectMapper objectMapper;

    public AwsSqsQueueService(SqsTemplate sqsTemplate, ObjectMapper objectMapper) {
        this.sqsTemplate = sqsTemplate;
        this.objectMapper = objectMapper;
    }

    public SendResult<String> publishToServiceQueue(Cliente cliente) throws RuntimeException {

        try {
            var payload = objectMapper.writeValueAsString(cliente);
            return sqsTemplate.send((to) ->
                    to.queue(SERVICE_QUEUE).payload(payload));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getNextClientFromServiceQueue() {

        Optional<Message<Object>> message = sqsTemplate.receive((from) ->
                from.queue(SERVICE_QUEUE).maxNumberOfMessages(1), Object.class);

        return message.map(Message::getPayload).orElse(null);

    }
}
