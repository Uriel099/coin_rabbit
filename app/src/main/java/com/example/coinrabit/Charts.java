package com.example.coinrabit;

import android.graphics.Color;
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

public class Charts {
    private PieChart pieChart;
    private BarChart barChart;
    private String[] months=new String[]{"Ingresos","Egresos"};
    private int[] sale = new int[]{2000,1300};
    private int[] colors = new int[]{Color.parseColor("#5CB300"),Color.parseColor("#FFF44336")};



    public Charts() {
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public void setPieChart(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    public BarChart getBarChart() {
        return barChart;
    }

    public void setBarChart(BarChart barChart) {
        this.barChart = barChart;
    }

    public String[] getMonths() {
        return months;
    }

    public void setMonths(String[] months) {
        this.months = months;
    }

    public int[] getSale() {
        return sale;
    }

    public void setSale(int[] sale) {
        this.sale = sale;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
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
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(13);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entries=new ArrayList<>();
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
        pieChart=(PieChart)getSameChart(pieChart,"",Color.GRAY,Color.WHITE,3000);
        pieChart.setHoleRadius(10);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData());
        pieChart.invalidate();
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(20);
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
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueFormatter(new PercentFormatter(pieChart));
        PieData pd = new PieData(pieDataSet);
        return pd;
    }
}

