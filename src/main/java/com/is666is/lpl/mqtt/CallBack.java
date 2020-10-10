package com.is666is.lpl.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * mqtt回调
 */
@Component("callBack")
public class CallBack implements MqttCallback {
    /**
     * 断开连接执行
     * @param throwable
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println(String.format("断开连接,%s--%s",throwable.getMessage(),throwable));
    }

    /**
     * publish 发布成功后执行
     * @param iMqttDeliveryToken
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("发布成功!-----------------------");
    }

    /**
     * subscribe 订阅后得到的消息会执行
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println(String.format("收到来自[%s]的消息: %s",topic,new String(mqttMessage.getPayload())));
    }


}
