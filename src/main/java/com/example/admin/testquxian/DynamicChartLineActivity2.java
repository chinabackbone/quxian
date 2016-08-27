/*
 * 动态方式和静态折线图方式完全类似，只是在最初并没有为 XYSeries 对象add值，
 * 而是后面动态添加值，并且添加新值后，刷新即可。
 * 此处使用 Timer 和TimerTask进行定时添加新数据
 * 
 * 两种折线图实现几乎完全一样，只是添加点时的处理不同，即方法 initLine(XYSeries series) 中实现不同 
 * 
 */

package com.example.admin.testquxian;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DynamicChartLineActivity2 extends Activity {
    private RelativeLayout dynamic_chart_line_layout2;
    private String title = "温度实时显示图表2";
    private Button btn_pause,btn_follow;
    //0 不跟踪；1跟踪
    static int FOLLOW_FLAG;
    // 用于存放每条折线的点数据
    private XYSeries line1, line2;
    // 用于存放所有需要绘制的XYSeries
    private XYMultipleSeriesDataset mDataset;
    // 用于存放每条折线的风格
    private XYSeriesRenderer renderer1, renderer2;
    // 用于存放所有需要绘制的折线的风格
    private XYMultipleSeriesRenderer mXYMultipleSeriesRenderer;
    private GraphicalView chart;

    // 以下属性用于initLine(XYSeries series)方法中更新数据
    private int count;
    private double xTemp, yTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_chart_line_activity2);

        FOLLOW_FLAG =1;
        btn_pause = (Button)findViewById(R.id.btn_pasue);
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            FOLLOW_FLAG =0;

            }
        });
        btn_follow = (Button)findViewById(R.id.btn_follow);
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FOLLOW_FLAG =1;
            }
        });


        dynamic_chart_line_layout2 = (RelativeLayout) findViewById(R.id.dynamic_chart_line_layout2);
        initChart();
        refreshChart();

    }

    private void initChart() {
        // 初始化，必须保证XYMultipleSeriesDataset对象中的XYSeries数量和
        // XYMultipleSeriesRenderer对象中的XYSeriesRenderer数量一样多
        line1 = new XYSeries("折线1");
        line2 = new XYSeries("折线2");
        renderer1 = new XYSeriesRenderer();
        renderer2 = new XYSeriesRenderer();
        mDataset = new XYMultipleSeriesDataset();
        mXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();

        // 对XYSeries和XYSeriesRenderer的对象的参数赋值
        // initLine(line1);
        // initLine(line2);
        initRenderer(renderer1, Color.GREEN, PointStyle.CIRCLE, false);
        initRenderer(renderer2, Color.CYAN, PointStyle.TRIANGLE, true);

        // 将XYSeries对象和XYSeriesRenderer对象分别添加到XYMultipleSeriesDataset对象和XYMultipleSeriesRenderer对象中。
        mDataset.addSeries(line1);
        mDataset.addSeries(line2);
        mXYMultipleSeriesRenderer.addSeriesRenderer(renderer1);
        mXYMultipleSeriesRenderer.addSeriesRenderer(renderer2);

        // 配置chart参数
        double d =1 ;
        setChartSettings(mXYMultipleSeriesRenderer, "X", "Y", 0, 50, -50, 200, Color.RED,
                Color.WHITE,d);

        // 通过该函数获取到一个View 对象
        chart = ChartFactory.getLineChartView(this, mDataset, mXYMultipleSeriesRenderer);


        // 将该View 对象添加到layout中。
        dynamic_chart_line_layout2.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

    }

    private XYSeriesRenderer initRenderer(XYSeriesRenderer renderer, int color,
            PointStyle style, boolean fill) {
        // 设置图表中曲线本身的样式，包括颜色、点的大小以及线的粗细等
        renderer.setColor(color);
        renderer.setPointStyle(style);
        renderer.setFillPoints(fill);
        renderer.setLineWidth(1);
        return renderer;
    }

    protected void setChartSettings(XYMultipleSeriesRenderer mXYMultipleSeriesRenderer,
            String xTitle, String yTitle, double xMin, double xMax,
            double yMin, double yMax, int axesColor, int labelsColor,double time_i) {
        // 有关对图表的渲染可参看api文档


        mXYMultipleSeriesRenderer.setBackgroundColor(Color.BLACK);
        mXYMultipleSeriesRenderer.setApplyBackgroundColor(true);
        mXYMultipleSeriesRenderer.setZoomEnabled(false,false);



        mXYMultipleSeriesRenderer.setChartTitle(title);
        mXYMultipleSeriesRenderer.setXTitle(xTitle);
        mXYMultipleSeriesRenderer.setYTitle(yTitle);
        mXYMultipleSeriesRenderer.setXAxisMin(xMin);
        mXYMultipleSeriesRenderer.setAxisTitleTextSize(30);
        mXYMultipleSeriesRenderer.setChartTitleTextSize(50);
        mXYMultipleSeriesRenderer.setLabelsTextSize(15);
        mXYMultipleSeriesRenderer.setXAxisMax(xMax);
        mXYMultipleSeriesRenderer.setYAxisMin(yMin);
        mXYMultipleSeriesRenderer.setYAxisMax(yMax);
        mXYMultipleSeriesRenderer.setAxesColor(axesColor);
        mXYMultipleSeriesRenderer.setLabelsColor(labelsColor);
        //mXYMultipleSeriesRenderer.setShowGrid(true);
        mXYMultipleSeriesRenderer.setGridColor(Color.GRAY);
        mXYMultipleSeriesRenderer.setXLabels(20);
        mXYMultipleSeriesRenderer.setYLabels(10);
        String time_s =getNowTime()+ time_i+"";
        mXYMultipleSeriesRenderer.setXTitle(time_s);
        mXYMultipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
        mXYMultipleSeriesRenderer.setPointSize((float) 5);
        mXYMultipleSeriesRenderer.setShowLegend(true);
        mXYMultipleSeriesRenderer.setLegendTextSize(20);
    }

    class RefreshSeriesTask extends TimerTask {
        public void run() {
            time_i=time_i+1;
            initLine(line1);
            initLine(line2);

            //当画的线快要到达手机边缘的时候，经过40秒。图像开始移动。
            if(time_i>-10&&FOLLOW_FLAG==1){

                setChartSettings(mXYMultipleSeriesRenderer, "X", "Y", time_i+10, time_i+60, -50, 200, Color.RED,Color.WHITE,time_i);
            }
            else if(time_i<=-10&&FOLLOW_FLAG==1) {
                setChartSettings(mXYMultipleSeriesRenderer, "X", "Y", 0, 50, -50, 200, Color.RED,Color.WHITE,time_i);
            }
            chart.postInvalidate();
        }
    }

    private void initLine(XYSeries series) {

        count = series.getItemCount();

        Random r = new Random();
        if (count != 0) {
            xTemp = series.getX(count - 1) + 1;

        }
        else {
            xTemp = 0;
        }
        yTemp = r.nextInt(100);
        series.add(xTemp, yTemp);

    }
    static double time_i;
    Timer timer = new Timer();
    private void refreshChart() {
        time_i=-50;
        timer.schedule(new RefreshSeriesTask(), 0, 1 * 1000);

    }

    String getNowTime(){
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month+1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int second = t.second;
        String s="";
        s=year+"年"+month+"月"+date+"日"+hour+"时"+minute+"分"+second+"秒";
        return s;
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
