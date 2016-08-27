package com.example.admin.testquxian;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import org.achartengine.ChartFactory;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private Button button,button_dongtai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();//创建你需要的图表最下面的图层
        XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();//创建你需要在图层上显示的具体内容的图层
        mRenderer.addSeriesRenderer(seriesRenderer);//添加进去

        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();//创建数据层
        XYSeries series = new XYSeries("标题");//创建具体的数据层
        Random random = new Random();
        int j,k;
        for(int i=0 ;i<100;i++){

            j = random.nextInt(100);
           // k= random.nextInt(100);
            Log.d("111",j+"jjjj");
            //Log.d("111",k+"kkk");
            series .add(i, j);//添加数据,一般都是for循环数据不断操作这一步添加的
        }
        mDataset.addSeries(series);

        final Intent intent =ChartFactory.getLineChartIntent(getApplicationContext(), mDataset, mRenderer);




        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(intent);
            }
        });

//        XYMultipleSeriesRenderer mRenderer1 = new XYMultipleSeriesRenderer();//创建你需要的图表最下面的图层
//        XYSeriesRenderer seriesRenderer1 = new XYSeriesRenderer();//创建你需要在图层上显示的具体内容的图层
//        mRenderer1.addSeriesRenderer(seriesRenderer1);//添加进去
//
//        XYMultipleSeriesDataset mDataset1 = new XYMultipleSeriesDataset();//创建数据层
//        XYSeries series1 = new XYSeries("标题");//创建具体的数据层
//        Random random1 = new Random();
//        int j1,k1;
//        for(int i=0 ;i<3600;i++){
//
//            j1 = random1.nextInt(100);
//            // k= random.nextInt(100);
//            Log.d("111",j1+"jjjj");
//            //Log.d("111",k+"kkk");
//            series1 .add(10, i);//添加数据,一般都是for循环数据不断操作这一步添加的
//        }
//        mDataset1.addSeries(series1);
//
//        final Intent intent1 =ChartFactory.getLineChartIntent(getApplicationContext(), mDataset1, mRenderer1);


        button_dongtai=(Button)findViewById(R.id.button1);
        button_dongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(getApplicationContext(),DynamicChartLineActivity2.class);
                startActivity(intent2);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
