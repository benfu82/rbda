/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.gui;

import java.awt.Color;
import java.awt.RenderingHints;
import java.util.Iterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.rbda.pipeline.Pipeline;
import org.rbda.pipeline.Pipeseg;

/**
 *
 * @author kewin
 */
public class PlotPanel extends javax.swing.JPanel {

    /**
     * Creates new form PlotPanel
     */
    public PlotPanel() {
        initComponents();
        
    }
    
    public void PlotPipeline(Pipeline pipeline){
        
        XYSeries xyseries = new XYSeries("First");
        Pipeseg pipeseg;
        Iterator iter = pipeline.pipesegs.iterator();
        while(iter.hasNext()){
            pipeseg = (Pipeseg) iter.next();
            xyseries.add(pipeseg.start_point, pipeseg.fail_probability);
            xyseries.add(pipeseg.end_point, pipeseg.fail_probability);
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
	xyseriescollection.addSeries(xyseries);
        JFreeChart jfreechart = ChartFactory.createXYLineChart(
				pipeline.getName()+"fail_probability", // chart title
				"X", // x axis label
				"Y", // y axis label
				(XYDataset)xyseriescollection, // data
				PlotOrientation.VERTICAL,
				true, // include legend
				true, // tooltips
				false // urls
				);
        //jfreechart.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
         //JPlotsavapic(chart,"mytest.jpg");//可以先保存图片 
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
	xyplot.setBackgroundPaint(Color.lightGray);
	xyplot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
	xyplot.setDomainGridlinePaint(Color.white);
	xyplot.setRangeGridlinePaint(Color.white);
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
	xylineandshaperenderer.setShapesVisible(true);
	xylineandshaperenderer.setShapesFilled(true);
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
