package com.ykh.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.ykh.calendar.entry.HistoryData;
import com.show.api.ShowApiRequest;

import java.util.ArrayList;
import java.util.List;


public class HistoryTodayActivity extends AppCompatActivity {

    protected Handler mHandler =  new Handler();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historytoday_layout);

        final TextView titleDate = (TextView)findViewById(R.id.titleDate);
        //接受参数
        Intent intent=getIntent();
        String date=intent.getStringExtra("date");
        date = date.substring(4);

        titleDate.setText(date+" ( 历史上的今天 )");


        searchToday(date);

    }
    public void searchToday(final String date){

        View view = LayoutInflater.from(HistoryTodayActivity.this).inflate(R.layout.almanac_layout, null);
//        final TextView datt = (TextView)findViewById(R.id.date);
        final List<HistoryData> messageList = new ArrayList<HistoryData>();


        new Thread(){
            //在新线程中发送网络请求
            public void run() {
                String appid="43995";//要替换成自己的
                String secret="06bb75a7672b4b6b949dec894d2cce7c";//要替换成自己的


                final String res=new ShowApiRequest("http://route.showapi.com/119-42",appid,secret)
                        .addTextPara("date",date)
                        .post();
                //反序列化用阿里的fastjson
                final com.alibaba.fastjson.JSONObject jsonObject =
                        com.alibaba.fastjson.JSONObject.parseObject(res);
                //把返回内容通过handler对象更新到界面
                mHandler.post(new Thread(){
                    public void run() {
                        JSONObject body = jsonObject.getJSONObject("showapi_res_body");
                        JSONArray list= body.getJSONArray("list");
                        for (int i=0;i<list.size();i++){
                            JSONObject job = list.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象

                            HistoryData historyData = new HistoryData();
                            historyData.setTitle(job.getString("title"));
                            historyData.setImg(job.getString("img"));
                            historyData.setDate(job.getString("year")+"年"+job.getString("month")+"月"+job.getString("day")+"日");
                            messageList.add(historyData);

                        }
//                        JSONObject job = list.getJSONObject(0);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//                        datt.setText(job.getString("img"));
// baseAdapter
                        lv = (ListView)findViewById(R.id.listview);

                        lv.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return messageList.size();
                            }

                            @Override
                            public Object getItem(int i) {
                                return null;
                            }

                            @Override
                            public long getItemId(int i) {
                                return 0;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = null;
                                //布局不变，数据变

                                //如果缓存为空，我们生成新的布局作为1个item
                                if(convertView==null){
                                    Log.i("info:", "没有缓存，重新生成"+position);
                                    LayoutInflater inflater = HistoryTodayActivity.this.getLayoutInflater();
                                    //因为getView()返回的对象，adapter会自动赋给ListView
                                    view = inflater.inflate(R.layout.listview_item, null);
                                }else{
                                    Log.i("info:", "有缓存，不需要重新生成"+position);
                                    view = convertView;
                                }

                                HistoryData historyData = messageList.get(position);
//                                ImageView img = (ImageView)view.findViewById(R.id.imgUrl);
//                                try {
//                                    URL url = new URL(historyData.getImg());
//                                    Drawable thumb_d = Drawable.createFromStream(url.openStream(), "src");
//                                    img.setImageDrawable(thumb_d);
//                                }
//                                catch (Exception e) {
//                                    // handle it
//                                }

                                TextView dateText = (TextView)view.findViewById(R.id.dateText);
                                dateText.setText(  historyData.getDate()  );
                                dateText.setTextSize(13);

                                TextView titleText = (TextView)view.findViewById(R.id.titleText);
                                titleText.setText(  historyData.getTitle()  );
                                titleText.setTextSize(16);


                                return view;
                            }
                        });



                    }
                });
            }
        }.start();



    }


}
