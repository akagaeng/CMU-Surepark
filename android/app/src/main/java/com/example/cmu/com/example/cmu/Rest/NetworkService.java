package com.example.cmu.com.example.cmu.Rest;

import android.util.Log;

import com.example.cmu.object.Result_Resv_Info;
import com.example.cmu.object.Send_Resv_Info;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by ajou on 2016-07-05.
 */

public class NetworkService {

    public final String TAG = "HTTPTEST";
    public final String IP_ADDRESS = "192.168.1.166";
    public final int PORT_NUM=4567;

    //Definition about http connection
    public final String reserv_path = "/api/reservation";
    public final String cancel_path = "/api/cancel";
    public final String check_path ="/api/reservationcheck";
    private final String MIME = "application/json";

    public int flag=0;

    public NetworkService() {
        flag=0;
    }

    public String HttpSendData(String input,String path){

        String result = null;
        flag=0;
        Log.d("Send flag:",""+ flag);

        HttpHost target = new HttpHost(IP_ADDRESS, PORT_NUM);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(path);
        post.setHeader("Content-Type", MIME);
        HttpEntity entity = null;
        StringEntity se;

        try {
            se = new StringEntity(input, HTTP.UTF_8);
            //Send the json data to server
            post.setEntity(se);

            Log.d("abc", "send data"+input);
            HttpResponse response = client.execute(target, post);

            // Status code 204 is success with "No content".
            // This happens when a RESTful method returns void.
            if (response.getStatusLine().getStatusCode() != 200) {
                return "False";
            }else {
                flag=1;
                entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
                Log.i(TAG, result);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    //Parsing reservation object to json
    public String getGsonFromResv(Send_Resv_Info send_info) {
        Gson gson = new Gson();
        String result = gson.toJson(send_info);
        return result;
    }
    //Parsing json to reservation object
    public Result_Resv_Info setDataFromGson(String json) {
        Gson gson = new Gson();
        Result_Resv_Info rio = gson.fromJson(json, Result_Resv_Info.class);

        return rio;
    }

}

