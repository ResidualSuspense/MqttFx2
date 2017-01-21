package com.xs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

import java.util.Map;

/**
 * Created by xiaosong on 2017/1/22.
 */
public interface ServiceApi {
  //  type=png&ids=1377788|-1&color=1296db&size=200&ctoken=LANDAHbv2l9xDSVPRIFYicon-font

    //下载文件
    @GET("api/icon/downloadIcon/")
    Observable<ResponseBody> downloadPicFromNet(@QueryMap Map<String ,String> parms);
}
