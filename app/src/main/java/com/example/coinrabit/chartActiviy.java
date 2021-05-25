package com.example.coinrabit;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class chartActiviy extends AppCompatActivity {
    private PieChart pieChart;
    private BarChart barChart;
    private String[] months=new String[]{"Ingresos","Egresos"};
    private int[] sale=new int[]{3200,1300};
    private int[] colors=new int[]{Color.GREEN,Color.RED};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_activiy);
        //barChart=(BarChart)findViewById(R.id.barChart);
        pieChart=(PieChart)findViewById(R.id.pieChartLayout);
        createCharts();
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateTime){
        //Se agrega descripcion a la grafica
        chart.getDescription().setText(description);
        //Se agrega el tamaño de texto
        chart.getDescription().setTextSize(15);
        //Se agrega el color de fondo
        chart.setBackgroundColor(background);
        //Se agrega el tiempo de animacion
        chart.getDescription().setTextColor(textColor);
        chart.animateY(animateTime);
        //retorna animacion
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry>entries=new ArrayList<>();
        for(int i=0;i< months.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=colors[i];
            entry.label=months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries= new ArrayList<>();
        for(int i=0;i<sale.length;i++){
            entries.add(new BarEntry(i,sale[i]));
        }
        return entries;
    }
    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry> entries= new ArrayList<>();
        for(int i=0;i<sale.length;i++){
            entries.add(new PieEntry(sale[i],sale[i]));

        }
        return entries;
    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
        axis.setEnabled(false);
    }
    /*private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }*/

    public void createCharts(){
       //barChart=(BarChart)getSameChart(barChart,"Ventas",Color.RED,Color.WHITE,3000);
        //barChart.setDrawGridBackground(true);
        //barChart.setDrawBarShadow(true);
        //barChart.setData(getBarData());
        //barChart.invalidate();
        //axisX(barChart.getXAxis());
        //axisLeft(barChart.getAxisLeft());
        //axisRight(barChart.getAxisRight());
        pieChart=(PieChart)getSameChart(pieChart,"Ventas",Color.GRAY,Color.WHITE,3000);
        pieChart.setHoleRadius(10);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData());
        pieChart.invalidate();
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(15);
        return dataSet;
    }
    /*private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }*/

    private PieData getPieData(){

        PieDataSet pieDataSet=(PieDataSet)getData(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(0);
        pieDataSet.setValueFormatter(new PercentFormatter(pieChart));
        PieData pd = new PieData(pieDataSet);
        return pd;
    }
}