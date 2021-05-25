package com.example.coinrabit.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.coinrabit.R;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private PieChart pieChart;
    private BarChart barChart;
    private String[] months=new String[]{"Ingresos","Egresos"};
    private int[] sale=new int[]{3200,1300};
    private int[] colors=new int[]{Color.GREEN,Color.RED};
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        pieChart=(PieChart)findViewById(R.id.pieChartLayout);
        createCharts();
        return root;
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