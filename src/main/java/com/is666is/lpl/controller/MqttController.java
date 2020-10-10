package com.is666is.lpl.controller;

import com.is666is.lpl.mqtt.MQTTConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    MQTTConnect mqttConnect;

    /**
     * 发布消息
     * @param topic 主题
     * @param msg   消息
     * @return
     */
    @RequestMapping("/publish/{topic}/{msg}/")
    public Object pubMsg(@PathVariable("topic") String topic,@PathVariable("msg") String msg){
        mqttConnect.pub(topic,msg,0);
        return "OK";
    }

    /**
     * 订阅消息
     * @return
     */
    @GetMapping("/subscribe/")
    public Object subMsg(){
        mqttConnect.sub("test",1);
        return "OK";
    }
}
