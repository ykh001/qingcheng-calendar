package com.ykh.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.calendarview.CalendarView;
import com.ykh.calendarview.DateBean;
import com.ykh.calendarview.listener.OnMonthItemClickListener;
import com.ykh.calendarview.listener.OnPagerChangeListener;
import com.show.api.ShowApiRequest;


public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    protected Handler mHandler =  new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView title = (TextView) findViewById(R.id.title);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.init();
//        calendarView.setOnCalendarViewAdapter(R.layout.item_layout, new CalendarViewAdapter() {
//            @Override
//            public TextView[] convertView(View view, DateBean date) {
//                TextView solarDay = (TextView) view.findViewById(R.id.solar_day);
//                TextView lunarDay = (TextView) view.findViewById(R.id.lunar_day);
//                return new TextView[]{solarDay, lunarDay};
//            }
//        });

        DateBean d = calendarView.getDateInit();

        //刚进入主页时显示下方默认日期的农历
        final TextView textView = (TextView)findViewById(R.id.almanactitle);
        textView.setText("农历"+d.getLunar()[0]+d.getLunar()[1]+"\n"+d.getTerm());
        final TextView textView2 = (TextView)findViewById(R.id.calendar_display);

            String month = back0(d.getSolar()[1]);
            String day = back0(d.getSolar()[2]);

        textView2.setText(d.getSolar()[0] + month + day);

        title.setText(d.getSolar()[0] + "年" + d.getSolar()[1] + "月" + d.getSolar()[2] + "日");

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月" + date[2] + "日");
                textView2.setText(date[0]+back0(date[1])+back0(date[2]));

//                new Thread(){
//                    //在新线程中发送网络请求
//                    public void run() {
//                        String appid="43995";//要替换成自己的
//                        String secret="06bb75a7672b4b6b949dec894d2cce7c";//要替换成自己的
//                        String date = textView2.getText().toString();
//                        final String res=new ShowApiRequest("http://route.showapi.com/856-1",appid,secret)
//                                .addTextPara("date",date)
//                                .post();
//                        //反序列化用阿里的fastjson
//                        final com.alibaba.fastjson.JSONObject jsonObject =
//                                com.alibaba.fastjson.JSONObject.parseObject(res);
//                        //把返回内容通过handler对象更新到界面
//                        mHandler.post(new Thread(){
//                            public void run() {
//
//                                gongli.setText(jsonObject.getJSONObject("showapi_res_body").getString("gongli"));
//                                nongli.setText(jsonObject.getJSONObject("showapi_res_body").getString("nongli"));
//                                yi.setText(jsonObject.getJSONObject("showapi_res_body").getString("yi"));
//                                ji.setText(jsonObject.getJSONObject("showapi_res_body").getString("ji"));
//                                jieri.setText(jsonObject.getJSONObject("showapi_res_body").getString("jieri"));
//                                ganzhi.setText(jsonObject.getJSONObject("showapi_res_body").getString("ganzhi"));
//                                nayin.setText(jsonObject.getJSONObject("showapi_res_body").getString("nayin"));
//
//                            }
//                        });
//                    }
//                }.start();
                textView.setText("点我翻看老黄历");

            }
        });

        calendarView.setOnItemClickListener(new OnMonthItemClickListener() {
            @Override
            public void onMonthItemClick(View view, DateBean date) {

                String month2 = back0(date.getSolar()[1]);
                String day2 = back0(date.getSolar()[2]);

                //切换日期时修改上方日期
                title.setText(date.getSolar()[0] + "年" + month2 + "月" + day2 + "日");
                //
                textView.setText("农历"+date.getLunar()[0]+date.getLunar()[1]+"\n"
                        +date.getTerm()+date.getSolarHoliday()+date.getLunarHoliday()+"\n");
//                final TextView textView2 = (TextView)findViewById(R.id.calendar_display);
                textView2.setText(date.getSolar()[0] + month2 + day2);
            }
        });



    }

    public void someday(View v) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.input_layout, null);
        final EditText year = (EditText) view.findViewById(R.id.year);
        final EditText month = (EditText) view.findViewById(R.id.month);
        final EditText day = (EditText) view.findViewById(R.id.day);
        final TextView textView = (TextView)findViewById(R.id.almanactitle);
        final TextView textView2 = (TextView)findViewById(R.id.calendar_display);
        final TextView title = (TextView) findViewById(R.id.title);

        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(year.getText())
                                || TextUtils.isEmpty(month.getText())
                                || TextUtils.isEmpty(day.getText())) {
                            Toast.makeText(MainActivity.this, "请完善日期！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        calendarView.toSpecifyDate(Integer.valueOf(year.getText().toString()),
                                Integer.valueOf(month.getText().toString()),
                                Integer.valueOf(day.getText().toString()));
                        dialog.dismiss();

//                        //切换日期时修改上方日期
//                        title.setText(year + "年" + month + "月" + day + "日");
                        //切换日期时修改下方农历信息
//                        textView.setText("农历"+date.getLunar()[0]+date.getLunar()[1]+"\n"
//                                +date.getTerm()+date.getSolarHoliday()+date.getLunarHoliday()+"\n");

                        String month2 = back0(Integer.parseInt(month.getText()+""));
                        String day2 = back0(Integer.parseInt(day.getText()+""));

                        textView2.setText(year.getText()+""+month2+day2);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    public void almanac(View v) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.almanac_layout, null);
        final TextView textView2 = (TextView)findViewById(R.id.calendar_display);
        final TextView gongli = (TextView)view.findViewById(R.id.gongli);
        final TextView nongli = (TextView)view.findViewById(R.id.nongli);
        final TextView yi = (TextView)view.findViewById(R.id.yi);
        final TextView ji = (TextView)view.findViewById(R.id.ji);
        final TextView jieri = (TextView)view.findViewById(R.id.jieri);
        final TextView ganzhi = (TextView)view.findViewById(R.id.ganzhi);
        final TextView nayin = (TextView)view.findViewById(R.id.nayin);
        final TextView shengxiao = (TextView)view.findViewById(R.id.shengxiao);
        final TextView xingzuo = (TextView)view.findViewById(R.id.xingzuo);
        final TextView sheng12 = (TextView)view.findViewById(R.id.sheng12);
        final TextView zhiri = (TextView)view.findViewById(R.id.zhiri);
        final TextView chongsha = (TextView)view.findViewById(R.id.chongsha);
        final TextView tszf = (TextView)view.findViewById(R.id.tszf);
        final TextView pzbj = (TextView)view.findViewById(R.id.pzbj);
        final TextView jrhh = (TextView)view.findViewById(R.id.jrhh);
        final TextView jsyq = (TextView)view.findViewById(R.id.jsyq);
        final TextView jieqi24 = (TextView)view.findViewById(R.id.jieqi24);
        final TextView t23 = (TextView)view.findViewById(R.id.t23);
        final TextView t1 = (TextView)view.findViewById(R.id.t1);
        final TextView t3 = (TextView)view.findViewById(R.id.t3);
        final TextView t5 = (TextView)view.findViewById(R.id.t5);
        final TextView t7 = (TextView)view.findViewById(R.id.t7);
        final TextView t9 = (TextView)view.findViewById(R.id.t9);
        final TextView t11 = (TextView)view.findViewById(R.id.t11);
        final TextView t13 = (TextView)view.findViewById(R.id.t13);
        final TextView t15 = (TextView)view.findViewById(R.id.t15);
        final TextView t17 = (TextView)view.findViewById(R.id.t17);
        final TextView t19 = (TextView)view.findViewById(R.id.t19);
        final TextView t21 = (TextView)view.findViewById(R.id.t21);

        new AlertDialog.Builder(this).setView(view).show();
//        textView.setText(Html.fromHtml(getString(R.string.my_text)));

        new Thread(){
            //在新线程中发送网络请求
            public void run() {
                String appid="43995";//要替换成自己的
                String secret="06bb75a7672b4b6b949dec894d2cce7c";//要替换成自己的
                String date = textView2.getText().toString();
                final String res=new ShowApiRequest("http://route.showapi.com/856-1",appid,secret)
                        .addTextPara("date",date)
                        .post();
                //反序列化用阿里的fastjson
                final com.alibaba.fastjson.JSONObject jsonObject =
                        com.alibaba.fastjson.JSONObject.parseObject(res);
                //把返回内容通过handler对象更新到界面
                mHandler.post(new Thread(){
                    public void run() {

                        gongli.setText(jsonObject.getJSONObject("showapi_res_body").getString("gongli"));
                        nongli.setText(jsonObject.getJSONObject("showapi_res_body").getString("nongli"));
                        yi.setText(jsonObject.getJSONObject("showapi_res_body").getString("yi"));
                        ji.setText(jsonObject.getJSONObject("showapi_res_body").getString("ji"));
                        jieri.setText(jsonObject.getJSONObject("showapi_res_body").getString("jieri"));
                        ganzhi.setText(jsonObject.getJSONObject("showapi_res_body").getString("ganzhi"));
                        nayin.setText(jsonObject.getJSONObject("showapi_res_body").getString("nayin"));
                        shengxiao.setText(jsonObject.getJSONObject("showapi_res_body").getString("shengxiao"));
                        xingzuo.setText(jsonObject.getJSONObject("showapi_res_body").getString("xingzuo"));
                        sheng12.setText(jsonObject.getJSONObject("showapi_res_body").getString("sheng12"));
                        zhiri.setText(jsonObject.getJSONObject("showapi_res_body").getString("zhiri"));
                        chongsha.setText(jsonObject.getJSONObject("showapi_res_body").getString("chongsha"));
                        tszf.setText(jsonObject.getJSONObject("showapi_res_body").getString("tszf"));
                        pzbj.setText(jsonObject.getJSONObject("showapi_res_body").getString("pzbj"));
                        jrhh.setText(jsonObject.getJSONObject("showapi_res_body").getString("jrhh"));
                        jsyq.setText(jsonObject.getJSONObject("showapi_res_body").getString("jsyq"));
                        jieqi24.setText(jsonObject.getJSONObject("showapi_res_body").getString("jieqi24"));
                        t23.setText(jsonObject.getJSONObject("showapi_res_body").getString("t23"));
                        t1.setText(jsonObject.getJSONObject("showapi_res_body").getString("t1"));
                        t3.setText(jsonObject.getJSONObject("showapi_res_body").getString("t3"));
                        t5.setText(jsonObject.getJSONObject("showapi_res_body").getString("t5"));
                        t7.setText(jsonObject.getJSONObject("showapi_res_body").getString("t7"));
                        t9.setText(jsonObject.getJSONObject("showapi_res_body").getString("t9"));
                        t11.setText(jsonObject.getJSONObject("showapi_res_body").getString("t11"));
                        t13.setText(jsonObject.getJSONObject("showapi_res_body").getString("t13"));
                        t15.setText(jsonObject.getJSONObject("showapi_res_body").getString("t15"));
                        t17.setText(jsonObject.getJSONObject("showapi_res_body").getString("t17"));
                        t19.setText(jsonObject.getJSONObject("showapi_res_body").getString("t19"));
                        t21.setText(jsonObject.getJSONObject("showapi_res_body").getString("t21"));
                    }
                });
            }
        }.start();


    }

    public void historyToday(View v) {
//        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.historytoday_layout, null);
//        new AlertDialog.Builder(this).setView(view).show();
        Intent intent = new Intent(MainActivity.this, HistoryTodayActivity.class);
        //传参
        final TextView textView2 = (TextView)findViewById(R.id.calendar_display);
        Bundle bundle=new Bundle();
        bundle.putString("date",textView2.getText().toString());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void today(View view) {
        calendarView.today();

    }
    public String back0(int date ) {
        String string = null;
        if(date<10){
            string ="0"+date;
        }else{
            string=""+date;
        }
        return string;
    }

    public void lastMonth(View view) {
        calendarView.lastMonth();
    }

    public void nextMonth(View view) {
        calendarView.nextMonth();
    }

    public void start(View view) {
        calendarView.toStart();
    }

    public void end(View view) {
        calendarView.toEnd();
    }

    public void lastYear(View view) {
        calendarView.lastYear();
    }

    public void nextYear(View view) {
        calendarView.nextYear();
    }

    public void multiChoose(View view) {
        startActivity(new Intent(MainActivity.this, MultiChooseActivity.class));
    }

}
