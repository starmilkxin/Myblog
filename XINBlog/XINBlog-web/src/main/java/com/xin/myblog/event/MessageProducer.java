package com.xin.myblog.event;

import com.alibaba.fastjson.JSONObject;
import com.xin.myblog.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireMessage(Message message) {
        kafkaTemplate.setProducerListener(new ProducerListener() {
            @Override
            public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
                 log.info("消息投递成功");
            }

            @Override
            public void onError(ProducerRecord producerRecord, RecordMetadata recordMetadata, Exception exception) {
                //将事件发布到指定的主题
                kafkaTemplate.send("TOPIC_COMMENT", JSONObject.toJSONString(message));
            }
        });
        //将事件发布到指定的主题
        kafkaTemplate.send("TOPIC_COMMENT", JSONObject.toJSONString(message));
    }
}
