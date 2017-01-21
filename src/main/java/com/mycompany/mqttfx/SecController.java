///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.mqttfx;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//
///**
// *
// * @author NL
// */
//public class SecController implements Initializable, MyInterFace {
//
//    MqttClient myMQClient;
//    @FXML
//    private LineChart<?, ?> lineChart;
//    @FXML
//    private NumberAxis lineCharty;
//    @FXML
//    private NumberAxis lineChartx;
//
//    private XYChart.Series line_series;
//    ObservableList<XYChart.Data<Integer, Integer>> line_obList;
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println("com.mycompany.mqttfx.SecController.initialize()");
//       initLineChartData();    //初始化折线图
//        // mqttCofig();            //设置mqtt
//    }
//
//    public void advertiseTo(MainApp app) {
//        app.setListener2(this);
//    }
//
//    private void mqttCofig() {
//        try {
//            myMQClient = MyMQClient.getInstance();
//            myMQClient.subscribe("he");
//            myMQClient.setCallback(new MqttCallback() {
//                @Override
//                public void connectionLost(Throwable thrwbl) {
//                }
//
//                @Override
//                public void messageArrived(String string, final MqttMessage mm) throws Exception {
//                    if (mm != null && !mm.equals("")) {
//                        final int data = Integer.parseInt(mm.toString());
//                        System.err.println("第二张图接受的数据" + data + "  主题：" + string);
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                reflashDate(data);
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void deliveryComplete(IMqttDeliveryToken imdt) {
//                }
//            });
//
//        } catch (MqttException ex) {
//        }
//    }
//
//    @Override
//    public void reflashDate(int d) {
//        System.err.println("SecController：" + d);
//        if (line_obList != null) {
//            for (int i = 0; i < line_obList.size(); i++) {
//                line_obList.get(i).setXValue(line_obList.get(i).getXValue() + 1);
//            }
//            line_obList.add(0, new XYChart.Data<>(1, d));
//            line_obList.remove(line_obList.size() - 1);
//
//        }
//    }
//
//    public void initLineChartData() {
//        System.out.println("+++++++++++++++++++++++++++++++++");
//        lineChartx.setLabel("Number of Month");
//        lineChart.setTitle("第二个图");
//
//        line_series = new XYChart.Series();
//        line_obList = line_series.getData();
//
//        lineChart.getData().addAll(line_series);
//
//        for (int j = 0; j < 12; j++) {
//
//            XYChart.Data<Integer, Integer> d = new XYChart.Data<>(j + 1, line_Romdom());
//            line_obList.add(d);
//        }
//
//    }
//
//    public int line_Romdom() {
//        return (int) (Math.random() * 20);
//    }
//
//}
