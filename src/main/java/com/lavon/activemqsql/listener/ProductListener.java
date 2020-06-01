package com.lavon.activemqsql.listener;

import com.lavon.activemqsql.api.v1.model.ProductDTO;
import com.lavon.activemqsql.config.JmsConfig;
import com.lavon.activemqsql.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import java.util.Map;

@Slf4j
@Component
public class ProductListener {

    private final JmsTemplate jmsTemplate;
    private final ProductService productService;

    public ProductListener(JmsTemplate jmsTemplate, ProductService productService) {
        this.jmsTemplate = jmsTemplate;
        this.productService = productService;
    }

    @JmsListener(destination = JmsConfig.PRODUCT_MESSAGE_QUEUE)
    public void messagesListener(@Payload Map<String, String> message,
                                 @Headers MessageHeaders headers,
                                 Message jmsMessage){

        Long messageId = Long.valueOf(message.get("id"));
        productService.updateCount(messageId);
    }
}
