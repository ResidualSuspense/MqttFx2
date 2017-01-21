/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mqttfx;

import com.mycompany.mqttfx.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * FXML Controller class
 *
 * @author xiaosong
 */
public class MqttFXMLController implements Initializable {

    @FXML
    private TextField tf_broker;
    @FXML
    private TextField tf_clientID;
    @FXML
    private TextField tf_topic;
    @FXML
    private TextArea ta_data;
    @FXML
    private Button btn_start;
    @FXML
    private TextField tf_period;
    @FXML
    private Button btn_reset;
    @FXML
    private Button btn_mqtt;
    @FXML
    private Button btn_stop;
    @FXML
    private Text tv_status;

    private String broker;
    private String clientID;
    private String sendPeriod;
    private String topic;
    private String data;

    MqttUtils mqttUtils;
    private static int RUNCOUNT = 100000;
    private static Timer timer = new Timer();

    private MqttClient mqttClient;
    TimerTask task;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initViewData();
        btn_mqtt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    initeMqtt();
                } catch (MqttException ex) {
                    Logger.getLogger(MqttFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btn_start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                initViewData();
                int i = cheackParm();
                if (i == 0) {
                    return;
                }
                if (mqttClient == null) {
                    tv_status.setText("请初始化mqtt！");
                    return;
                }
                if (task != null) {
                    tv_status.setText("任务已经开始，请stop publish 之后完成新的推送");
                    return;
                }
                task = new TimerTask() {
                    @Override
                    public void run() {

                        MqttMessage message = new MqttMessage();
                        String textArea = ta_data.getText();
                        message.setPayload(textArea.getBytes());
                        message.setQos(0);
                        try {
                            mqttClient.publish(topic, message);
                            System.out.println("发送了....." + textArea);
                        } catch (MqttException ex) {
                            Logger.getLogger(MqttFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                Long period;
                try {
                    period=Long.valueOf(sendPeriod);
                } catch (Exception e) {
                      tv_status.setText("发送间隔应该为数字！");
                    return;
                }
                 tv_status.setText("mqtt消息发送中......！");
                timer.schedule(task, 0, Long.valueOf(sendPeriod));
            }
        });

        btn_stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tv_status.setText("停止发送");
                if (task != null) {
                    task.cancel();
                }
                task = null;
            }
        });
    }

    private void initViewData() {
        broker = tf_broker.getText().trim();
        clientID = tf_clientID.getText().trim();
        topic = tf_topic.getText().trim();
        data = ta_data.getText();
        sendPeriod = tf_period.getText();
    }

    private int cheackParm() {
        if (isEmpty(broker)) {
            tv_status.setText("broker不能为空！");
            return 0;
        }
        if (isEmpty(clientID)) {
            tv_status.setText("clientID不能为空！");
            return 0;
        }
        if (isEmpty(topic)) {
            tv_status.setText("topic不能为空！");
            return 0;
        }
        if (isEmpty(data)) {
            tv_status.setText("data不能为空！");
            return 0;
        }
        if (isEmpty(sendPeriod)) {
            tv_status.setText("发送间隔时间不能为空！");
            return 0;
        }
        return 1;
    }

    public boolean isEmpty(String s) {
        if (s != null && !s.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void initeMqtt() throws MqttException {
        cheackParm();
        tv_status.setText("mqtt初始化完毕！");
        if (mqttClient != null) {
            tv_status.setText("mqtt已经初始化！");
        }
        MemoryPersistence persistence = new MemoryPersistence();
        mqttClient = new MqttClient(broker, clientID, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        mqttClient.connect(connOpts);
    }

}
