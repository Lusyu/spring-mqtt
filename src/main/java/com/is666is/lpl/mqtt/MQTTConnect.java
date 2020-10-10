package com.is666is.lpl.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * MQTT 工具类
 */
@Component
public class MQTTConnect {
    private String HOST="tcp://localhost:1883";
    private final String clientId="DC"+(int)(Math.random()*100000000);
    private MqttClient mqttClient;
    private String userName="admin";
    private String password="public";

    /**
     *客户端连接mqtt服务器
     * @throws MqttException
     */
    public MQTTConnect(){
        MqttConnectOptions mqttConnectOptions = mqttConnectOptions();
        try {
            mqttClient = new MqttClient(HOST,clientId,new MemoryPersistence());
            mqttClient.setCallback(new CallBack());
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布消息
     * @param topic 发布的主题  默认qos 1
     * @param msg   发布的消息
     */
    public void pub(String topic,String msg){
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        try {
            MqttDeliveryToken mqttToken = mqttTopic.publish(mqttMessage);
            mqttToken.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布消息
     * @param topic 发布的主题
     * @param msg   发布的消息
     * @param qos   消息质量 0，1，2
     */
    public void pub(String topic,String msg,int qos){
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        try {
           MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
           token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 订阅某个主题
     * @param topic 主题
     */
    public void sub(String topic){
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题
     * @param topic 主题
     * @param qos 消息质量
     */
    public void sub(String topic,int qos){
        try {
            mqttClient.subscribe(topic,qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 配置mqtt属性
     * @return
     */
    public MqttConnectOptions mqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setConnectionTimeout(10);//设置超时时间 单位秒 默认30
        //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setAutomaticReconnect(true);//是否允许重新连接
        return mqttConnectOptions;
    }
    /**
     * 关闭 mqtt连接
     * @throws MqttException
     */
    public void close(){
        try {
            mqttClient.close();
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
