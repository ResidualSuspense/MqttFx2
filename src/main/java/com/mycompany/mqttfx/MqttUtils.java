/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mqttfx;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author xs
 */
public class MqttUtils {

    private int QOS = 0;
    private volatile static MqttUtils mInstance;
    private static MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient mMqttClient, mMqttClientStatus;

    public MqttUtils(MqttClient mqttClient, String serverURI, String clientId) throws MqttException {
        if (mqttClient == null) {
            mMqttClient = new MqttClient(serverURI, clientId, persistence);
        } else {
            mMqttClient = mqttClient;
        }
    }

    public static MqttUtils initClient(MqttClient mqttClient, String serverURI, String clientId) throws MqttException {
        if (mInstance == null) {
            synchronized (MqttUtils.class) {
                if (mInstance == null) {
                    mInstance = new MqttUtils(mqttClient, serverURI, clientId);
                }
            }
        }
        return mInstance;
    }

    public static MqttUtils getInstance() throws MqttException {
        return initClient(null, null, null);
    }

    public void connect() throws MqttException {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);

        mMqttClient.connect(mqttConnectOptions);
        mMqttClientStatus.connect(mqttConnectOptions);
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
            mMqttClient.publish(topic, mqttMessage);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 订阅主题
     *
     * @param topic
     */
    public void subscribe(String topic) throws MqttException {
        mMqttClient.subscribe(topic);
    }
    
    /**
     * 设置Mqtt回调
     * @param mqttCallback 
     */
     public void setCallback(MqttCallback mqttCallback) {
        mMqttClient.setCallback(mqttCallback);
    }
     
        public void disConnect() {
        if (mMqttClient != null && mMqttClient.isConnected()) {
            try {
                mMqttClient.disconnect();
                mMqttClientStatus.disconnect();
            }
            catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    } 

}
