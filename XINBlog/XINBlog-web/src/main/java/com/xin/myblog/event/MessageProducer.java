package com.xin.myblog.event;

import com.alibaba.fastjson.JSONObject;
import com.xin.myblog.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireMessage(Message message) {
        //将事件发布到指定的主题
        kafkaTemplate.send("TOPIC_COMMENT", JSONObject.toJSONString(message));
    }
}
