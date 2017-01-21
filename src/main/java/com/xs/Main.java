package com.xs;

import okhttp3.ResponseBody;
import rx.functions.Action1;
import rx.functions.Func1;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaosong on 2017/1/22.
 */
public class Main {
    public static void main(String[] args) {
        downIcon();
    }
    //type=png&ids=1377788|-1&color=1296db&size=200&ctoken=LANDAHbv2l9xDSVPRIFYicon-font
    private static void downIcon() {
        Map map = new HashMap();
        map.put("type","png");
        map.put("ids","1377788|-1");
        map.put("color","1296db");
        map.put("size","200");
        map.put("ctoken","LANDAHbv2l9xDSVPRIFYicon-font");


      ServiceApi serviceApi=  HttpMethods.getInstance().getServiceApi();
        serviceApi.downloadPicFromNet(map)
                .map(new Func1<ResponseBody, String>() {
                    @Override
                    public String call(ResponseBody responseBody) {
                        try {
                            return responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return "wu";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("====> "+s);
                    }
                });
    }
}
