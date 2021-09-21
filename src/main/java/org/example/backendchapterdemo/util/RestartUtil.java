package org.example.backendchapterdemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RestartUtil {

    private final RestartEndpoint restartEndpoint;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestartUtil.class);

    @Autowired
    public RestartUtil(RestartEndpoint restartEndpoint) {
        this.restartEndpoint = restartEndpoint;
    }

    @KafkaListener(groupId = "restartId", topics = KafkaConstants.RESTART_TOPIC)
    private void restart(String restartMessage) {
        LOGGER.info("Restart requested, restart message: " + restartMessage);
        restartEndpoint.restart();
    }
}
