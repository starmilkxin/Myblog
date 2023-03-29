package com.xin.myblog.event;

import com.alibaba.fastjson.JSONObject;
import com.xin.myblog.pojo.Message;
import com.xin.myblog.service.CommentService;
import com.xin.myblog.service.MessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @Autowired
    private MessageService messageService;

    @KafkaListener(topics = {"TOPIC_COMMENT"})
    public void handleComment(ConsumerRecord record, Acknowledgment ack) {
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空");
            ack.acknowledge();
            return;
        }
        Message message = JSONObject.parseObject(record.value().toString(), Message.class);
        if (message == null) {
            logger.error("消息格式错误");
            ack.acknowledge();
            return;
        }
        messageService.addMessage(message);
        ack.acknowledge();
    }
}
