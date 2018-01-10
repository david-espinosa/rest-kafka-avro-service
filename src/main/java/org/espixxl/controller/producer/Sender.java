package org.espixxl.controller.producer;

import org.espixxl.kafka.avro.Contribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Value("${kafka.topic.avro}")
    private String avroTopic;

    @Autowired
    private KafkaTemplate<String, Contribution> kafkaTemplate;

    public void send(Contribution contribution) {
        LOGGER.info("sending contribution='{}'", contribution.toString());
        kafkaTemplate.send(avroTopic, contribution);
    }
}
