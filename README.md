# 青橙日历

### 功能
* 1、支持农历、节气、常用节假日
* 2、跳转到指定日期
* 2、查看历史上的任意日期发生了那些大事
* 2、查看老黄历信息
* 5、通过自定义属性定制日期外观，以及简单的日期item布局配置

#### [apk下载](https://www.coolapk.com/apk/166643)

### 效果图：

![](https://github.com/ykh001/qingcheng-calendar/tree/master/screenshot/1.jpg)
![](https://github.com/ykh001/qingcheng-calendar/tree/master/screenshot/2.jpg)
![](https://github.com/ykh001/qingcheng-calendar/tree/master/screenshot/3.jpg)
![](https://github.com/ykh001/qingcheng-calendar/tree/master/screenshot/4.jpg)



###CalendarView来自于网上的一个开源项目，该demo可以自定义诸多属性，因此copy
### CalendarView相关方法：
|方法名|描述
|---|---|
|today()| 跳转到今天
|toSpecifyDate(int year, int month, int day)|跳转到指定年月日
|nextMonth()|跳转到下个月
|lastMonth()|跳转到上个月
|nextYear()|跳转到下一年的当前月
|lastYear()|跳转到上一年的当前月
|toStart()|跳转到日历的开始年月
|toEnd()|跳转到日历的结束年月
|getDateInit()|得到设置的默认选中日期（设置则返回当天日期）

### CalendarView的自定义属性
namespace：xmlns:calendarview="http://schemas.android.com/apk/res-auto"

|属性名|格式|描述|默认值
|---|---|---|---|
|show_lunar|boolean|是否显示农历|true
|show_last_next|boolean|是否在MonthView显示上月和下月日期|true
|show_holiday|boolean|是否显示节假日|true
|show_term|boolean|是否显示节气|true
|date_start|string|日历的开始年月（例如：1990.5）|1900.1
|date_end|string|日历的结束年月（例如：2025.12）|2049.12
|date_init|string|日历默认展示、选中的日期(例如：2017.5.20)，不设置则为当天
|disable_before|boolean|是否禁用默认选中日期前的所有日期|false
|switch_choose|boolean|单选时切换月份，是否选中上次的日期|true
|color_solar|color|阳历日期的颜色
|size_solar|integer|阳历的日期尺寸|14
|color_lunar|color|农历的日期颜色
|size_lunar|integer|农历的日期尺寸|8
|color_holiday|color|节假日、节气的颜色
|color_choose|color|选中的日期颜色
|day_bg|reference|选中的日期背景(图片)

### WeekView的自定义属性
namespace：xmlns:weekview="http://schemas.android.com/apk/res-auto"

|属性名|格式|描述|默认值
|---|---|---|---|
|week_str|string|周的表示形式，用点隔开（例如：日.一.二.三.四.五.六）
|week_color|color|周的颜色
|week_size|integer|周的尺寸|12
