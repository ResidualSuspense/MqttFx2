/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mqttfx;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author NL
 */
public class MyMQClient {

    private static MqttClient instance1 = null;
    private static final int QOS = 0;



    //整个系统中只存在一个MQTTClient对象
    public static MqttClient getInstance() throws MqttException {
        MemoryPersistence mPersistence = new MemoryPersistence();
        if (instance1 == null) {
            synchronized (MyMQClient.class) {
                if (null == instance1) {
                    instance1 = new MqttClient("tcp://127.0.0.1:1883", "小松client1", mPersistence);//只用于配置一些默认配置参数，不用于创建实质MQTT客户端
                    MqttConnectOptions conOpt = new MqttConnectOptions();
                    conOpt.setCleanSession(true);
                    // 设置超时connOpts时间 单位为秒
                    conOpt.setConnectionTimeout(10);
                    // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
                    conOpt.setKeepAliveInterval(20);
                    instance1.connect(conOpt);
                }
            }
        }
        return instance1;
    }
    

    
    /**
     * 发布消息
     *
     * @param topic
     * @param message
     */
    public void publish(String topic, String message) {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(QOS);

        try {
            instance1.publish(topic, mqttMessage);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
