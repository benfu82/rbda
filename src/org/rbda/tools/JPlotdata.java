package org.rbda.tools;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.RenderingHints;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.data.category.CategoryDataset;


import java.io.*;
import org.jfree.chart.ChartUtilities;


import javax.swing.*;
import java.awt.Container;
import javax.swing.JFrame;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataset; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
/**
 *
 * @author yaoyin
 */
public class JPlotdata {
   
    
    public static void probScatterplot (float[][] data, JPanel jPaneldest){
        
        final NumberAxis domainAxis = new NumberAxis("X");
        domainAxis.setAutoRangeIncludesZero(false);
        final NumberAxis rangeAxis = new NumberAxis("Y");
        rangeAxis.setAutoRangeIncludesZero(false);
        final FastScatterPlot plot = new FastScatterPlot(data, domainAxis, rangeAxis);
        final JFreeChart chart = new JFreeChart("随机数散点图", plot);
        chart.getRenderingHints().put
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);     
        //JPlotsavapic(chart,"mytest.jpg");//可以先保存图片        
        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        
        panel.setLocation(jPaneldest.getLocation());
        panel.setSize(jPaneldest.getSize());
        panel.setVisible(true); 
    }
    
    public static void  probLineplot (float[][] data, JPanel jPaneldest){
        

        // 生成数据,可以生成多条曲线数据
        XYSeries xyseries1 = new XYSeries("ULS"); //第一条曲线数据 
        for (int i = 0; i < data[0].length; i++) {
        xyseries1.add(data[0][i], data[1][i]); 
        }
        /**      
        XYSeries xyseries2 = new XYSeries("Two");//第二条曲线数据     
        xyseries2.add(1987, 20);      
        xyseries2.add(1997, 10D);      
        xyseries2.add(2007, 40D);                
        XYSeries xyseries3 = new XYSeries("Three");//第三条曲线数据      
        xyseries3.add(1987, 40);      
        xyseries3.add(1997, 30.0008);      
        xyseries3.add(2007, 38.24);      
        */      
     
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();           
        xySeriesCollection.addSeries(xyseries1);      
        //xySeriesCollection.addSeries(xyseries2);      
        //xySeriesCollection.addSeries(xyseries3);  
        
        //也可以改成createScatterPlot等别的图形，
        JFreeChart chart = ChartFactory.createXYLineChart("Pipeline Fail Probability", // 标题，中文乱码问题需要上网查      
                "distance", // categoryAxisLabel （category轴，横轴，X轴标签）      
                "Fail Probability", // valueAxisLabel（value轴，纵轴，Y轴的标签）      
                (XYDataset)xySeriesCollection, // dataset      
                PlotOrientation.VERTICAL,   
                true, // legend      
                false, // tooltips      
                false); // URLs   
        chart.getRenderingHints().put
            (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);     
        //JPlotsavapic(chart,"mytest.jpg");//可以先保存图片        
        final ChartPanel panel = new ChartPanel(chart, true);
        panel.setMinimumDrawHeight(10);
        panel.setMaximumDrawHeight(2000);
        panel.setMinimumDrawWidth(20);
        panel.setMaximumDrawWidth(2000);
        
        panel.setLocation(jPaneldest.getLocation());
        panel.setSize(jPaneldest.getSize());
        jPaneldest.add(panel);
        panel.setVisible(true);
    }
    
    
   
    
    public static void JPlotsavapic(JFreeChart chart,String filename){

        FileOutputStream fos_jpg = null;
        try {
            fos_jpg = new FileOutputStream("./pic/"+filename);
        } catch (FileNotFoundException ex) {
           
        }
        try {
            ChartUtilities.writeChartAsJPEG(fos_jpg,0.1f, chart, 500, 400);
        } catch (IOException ex) {
         
        }
        try {
            fos_jpg.close();
        } catch (IOException ex) {
            
        }


    }
}
