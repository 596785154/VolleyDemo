package com.zcn.volleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用Volley实现网络传输步骤
 * 1. 创建一个RequestQueue对象。
 * 2. 创建一个StringRequest对象。
 * 3. 将StringRequest对象添加到RequestQueue里面。
 * 
 *  由于Volley是要访问网络的，因此不要忘记在AndroidManifest.xml中添加网络权限
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "Chunna.zheng";
    public static String registerUrl = "http://192.168.2.128/app/index.php/Home/Index/register";
    public static String loginUrl = "http://192.168.2.128/app/index.php/Home/Index/login";

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RequestQueue内部的设计就是非常合适高并发的，因此我们不必为每一次HTTP请求都创建一个RequestQueue对象，这是非常浪费资源的，
        // 基本上在每一个需要和网络交互的Activity中创建一个RequestQueue对象就足够了。
        mQueue = Volley.newRequestQueue(this);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                getString();
                break;
            case R.id.button2:
                postString();
                break;
            case R.id.button3:
                getJson();
                break;
            case R.id.button4:
                postJson();
                break;
        }
    }

    //使用get方法，从服务器获取String类型数据
    private void getString() {
        //StringRequest的构造函数需要传入三个参数，第一个参数就是目标服务器的URL地址，
        // 第二个参数是服务器响应成功的回调，第三个参数是服务器响应失败的回调。
        StringRequest stringRequest = new StringRequest("https://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                error.printStackTrace();
            }
        });
        //将这个StringRequest对象添加到RequestQueue里面就可以了
        mQueue.add(stringRequest);
    }

    //使用post方法，从服务器获取String类型数据
    private void postString() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("mobile", "18299999999");
                map.put("password", "000000");
                map.put("b_type", "大型车");
                map.put("user_area", "黑龙江-哈尔滨-南岗区");
                map.put("f_type", "汽油");
                map.put("car_number", "黑A9999");
                map.put("c_type", "奔驰");
                map.put("user_name", "张三");
                map.put("insurer_number", "123445666");
                map.put("create_time", "1503718611288");
                return map;
            }
        };
        mQueue.add(stringRequest);
    }

    //使用get方法，从服务器获取Json类型数据 login
    private void getJson() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://www.weather.com.cn/data/cityinfo/101010100.html", null,
                listener, errorListener);
        Log.d(TAG, "ccccccccccccccc");
        mQueue.add(jsonObjectRequest);
    }

    //使用post方法，从服务器获取Json类型数据
    private void postJson() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", "18299999999");
        map.put("password", "000000");
        map.put("b_type", "大型车");
        map.put("user_area", "黑龙江-哈尔滨-南岗区");
        map.put("f_type", "汽油");
        map.put("car_number", "黑A9999");
        map.put("c_type", "奔驰");
        map.put("user_name", "张三");
        map.put("insurer_number", "123445666");
        map.put("create_time", "1503718611288");
        JSONObject object = new JSONObject(map);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, registerUrl, object, listener, errorListener) {
            //注意此处override的getParams()方法,在此处设置post需要提交的参数根本不起作用
            //必须象上面那样,构成JSONObject当做实参传入JsonObjectRequest对象里
            //所以这个方法在此处是不需要的
            /*@Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name1", "value1");
                map.put("name2", "value2");

                return map;
            }*/

            //有时有必要添加额外的HTTP请求头,一种常见的情况是添加一个“授权”请求头用来实现HTTP基本身份验证。
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }*/
        };
        mQueue.add(jsonObjectRequest);
    }

    private void getArray() {
        String URL = "/volley/resource/all?count=20";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d(TAG, "aaaaaaaaaaaa");
                    VolleyLog.v("Response:%n %s", response.toString(4));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "bbbbbbbbbbbbbb");
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        Log.d(TAG, "ccccccccccccccc");
        mQueue.add(jsonArrayRequest);
    }

    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {
            Log.d(TAG, "aaaaaaaaaaaa");
            Log.d(TAG, jsonObject.toString());
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.d(TAG, "bbbbbbbbbbbbbbbbbbb");
            Log.e(TAG, volleyError.getMessage(), volleyError);
        }
    };
}