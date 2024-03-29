/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.gui;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.format.Border;
import org.rbda.auxclasses.ProjectTreePanel;
import org.rbda.calculate.MenteCarlo;
import org.rbda.calculate.ParametersGroup;
import org.rbda.limitstateequation.EquationInformation;
import org.rbda.pipeline.Pipeline;
import org.rbda.pipeline.Pipeseg;
import org.rbda.probability.Distribution;
import org.rbda.probability.DistributionInformation;
import org.rbda.tools.JPlotdata;
import org.rbda.tools.PDF;

/**
 *
 * @author kewin
 */
public class MainFrame extends javax.swing.JFrame {
    
    public Pipeline pipeline;
    public ProjectTreePanel newprojecttree_panel; 
    public NewPipesegPanel newpipeseg_panel;
    public Pipeseg selectedPipeseg = null;
    public int simu_times = 0;
    
    public JTextArea prog_infor_area = new JTextArea();
    JPanel information_panel = new JPanel();

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        this.setLayout(new BorderLayout());  
        
        //information_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
        MyInit();
        JScrollPane infor_scrollpane = new  JScrollPane(prog_infor_area);        
        infor_scrollpane.setVisible(true);        
        this.add(BorderLayout.SOUTH, infor_scrollpane);
        prog_infor_area.append("成功启动!\n");
    }
    
    public void MyInit(){
        DistributionInformation.DistributionInformationInit();//读分布函数基础信息
        EquationInformation.EquationInformationInit();//读极限状态方程基础信息
    }
    
    public void InitProjecttree_Panel(){
        newprojecttree_panel = new ProjectTreePanel(pipeline, this);//将管道同树图关联
        newprojecttree_panel.setPreferredSize(null);
        newprojecttree_panel.setVisible(true);
        this.add(BorderLayout.WEST, newprojecttree_panel);
        this.validate();
    }
    /**
    public void InitPipeseg_Panel(){
        newpipeseg_panel = new NewPipesegPanel();
        newpipeseg_panel.setLocation(0, 0);
        newpipeseg_panel.setSize(300, 200);
        newpipeseg_panel.setBackground(Color.GREEN);
        newpipeseg_panel.setVisible(false);
        JScrollPane testScrollPanel1 = new JScrollPane(newpipeseg_panel);
        testScrollPanel1.setSize(500, 200);
        testScrollPanel1.setVisible(true);
        jPanel1.add(testScrollPanel1);
    }
    */
   
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("文件");

        jMenuItem1.setText("新建工程");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem10.setText("打开工程");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem2.setText("新建管段");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("新建极限状态方程");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("增加管段参数");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem13.setText("管段防护措施");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuItem9.setText("保存工程");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("编辑");
        jMenuBar1.add(jMenu2);

        jMenu5.setText("显示信息");

        jMenuItem7.setText("显示管段信息");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuBar1.add(jMenu5);

        jMenu3.setText("计算");

        jMenuItem5.setText("获取参数");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuItem8.setText("设置计算次数");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem6.setText("计算管段");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("结果");

        jMenuItem11.setText("生成报告");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setText("显示图线结果");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        pipeline = new Pipeline(JOptionPane.showInputDialog(null, "输入管线名称", "新建管线", JOptionPane.QUESTION_MESSAGE));
        InitProjecttree_Panel();//初始化项目面板
        newprojecttree_panel.Update_tree();//只要pipeline有变化变更新projecttree_panel 
        
        prog_infor_area.append("新建管线"+pipeline.getName()+".\n");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Pipeseg pipeseg = new Pipeseg();
        NewPipesegFrame new_pipeseg_frame = new NewPipesegFrame(pipeseg, this);
        new_pipeseg_frame.setLocationRelativeTo(null);
        new_pipeseg_frame.setVisible(true);                
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        if(selectedPipeseg != null){
        String name = JOptionPane.showInputDialog(null, "管线参数名字", "输入", JOptionPane.QUESTION_MESSAGE);
        Distribution distribution = new Distribution();
        NewDistributionFrame new_distribution_frame = new NewDistributionFrame(distribution);
        new_distribution_frame.setLocationRelativeTo(null);
        new_distribution_frame.setVisible(true);
        selectedPipeseg.properties.put(name, distribution);  
        
        prog_infor_area.append("增加属性"+name+".\n");
        }
        else{
             JOptionPane.showMessageDialog( null , "请先选择管段" ,"操作提示" , JOptionPane.ERROR_MESSAGE) ;
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        if(selectedPipeseg != null){        
        NewEquationFrame new_equation_frame = new NewEquationFrame(this); 
        new_equation_frame.setLocationRelativeTo(null);
        new_equation_frame.setVisible(true);    
        }
        else{
            JOptionPane.showMessageDialog( null , "请先选择管段" ,"操作提示" , JOptionPane.ERROR_MESSAGE) ;
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        if(selectedPipeseg != null){  
  
        information_panel.removeAll();
        information_panel.setLayout(new GridLayout(2,1));
        this.add(BorderLayout.CENTER, information_panel);           
           
        PipesegInforPanel pipeseginfor_panel = new PipesegInforPanel(selectedPipeseg); 
        pipeseginfor_panel.PipesegInforShow();
        JScrollPane jscrollpane = new JScrollPane(pipeseginfor_panel);
        jscrollpane.setVisible(true);
        information_panel.add(jscrollpane);
        
        PipesegPropertyAddPanel pipesegpropertyadd_panel = new PipesegPropertyAddPanel(selectedPipeseg);
        JScrollPane jscrollpane2 = new JScrollPane(pipesegpropertyadd_panel);
        jscrollpane2.setVisible(true);
        information_panel.add(jscrollpane2);
        
        information_panel.setVisible(true);
        this.validate();
        }
        else{
            JOptionPane.showMessageDialog( null , "请先选择管段" ,"操作提示" , JOptionPane.ERROR_MESSAGE) ;
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        if(selectedPipeseg != null){        
            selectedPipeseg.Map_Equations_Properties();
            
            prog_infor_area.append("参数映射完成!\n");
            //JOptionPane.showMessageDialog( null , "参数映射完成" ,"操作提示" , JOptionPane.INFORMATION_MESSAGE) ;
        }
        else{
            JOptionPane.showMessageDialog( null , "请先选择管段" ,"操作提示" , JOptionPane.ERROR_MESSAGE) ;
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        simu_times = Integer.parseInt(JOptionPane.showInputDialog(null, "仿真次数", "输入", JOptionPane.QUESTION_MESSAGE));
        prog_infor_area.append("仿真次数设定为："+simu_times+"次!\n");
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        if(selectedPipeseg != null){  
            //information_panel.removeAll();             
            //information_panel.setVisible(false);
            //this.validate();
            if(selectedPipeseg.parameters_groups!=null){     
                int i = selectedPipeseg.parameters_groups.size();
                prog_infor_area.append("共有"+i+"组参数需要进行蒙特卡罗计算.\n");
                i = 0;
                for (ParametersGroup p:selectedPipeseg.parameters_groups){
                    try {//这里面的语句是关键，其它的都是自动生成的
                        i ++;
                        prog_infor_area.append("正在计算第"+i+"组参数......\n");
                        p.equation_fail_probability = MenteCarlo.MenteCarloCalculation(p, simu_times);                       
                        prog_infor_area.append("第"+i+"组参数失效概率为："+p.equation_fail_probability+".\n");
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        else{
            JOptionPane.showMessageDialog( null , "请先选择管段" ,"操作提示" , JOptionPane.ERROR_MESSAGE) ;
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    FileDialog fileDialog = new FileDialog(this, "保存管段信息");
    fileDialog.setVisible(true);
    fileDialog.setMode(FileDialog.SAVE);// 设置对话框的模式是打开LOAD还是保存SAVE模式
    pipeline.objectWrite(fileDialog.getDirectory()+fileDialog.getFile());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
    FileDialog fileDialog = new FileDialog(this, "打开已有管段信息");
    fileDialog.setVisible(true);
    fileDialog.setMode(FileDialog.LOAD);// 设置对话框的模式是打开LOAD还是保存SAVE模式
    pipeline = new Pipeline();
    pipeline = pipeline.objectRead(fileDialog.getDirectory()+fileDialog.getFile());
    InitProjecttree_Panel();//初始化项目面板
    newprojecttree_panel.Update_tree();//只要pipeline有变化变更新projecttree_panel 
    prog_infor_area.append("打开管线"+pipeline.getName()+".\n");
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:    
            FileDialog fileDialog = new FileDialog(this, "生成PDF报告");
            fileDialog.setVisible(true);
            fileDialog.setMode(FileDialog.SAVE);// 设置对话框的模式是打开LOAD还是保存SAVE模式
            PDF.WritePdf(fileDialog.getDirectory()+fileDialog.getFile());
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        
        information_panel.removeAll();
        this.add(BorderLayout.CENTER, information_panel); 
        
        Pipeseg pipeseg;
        Iterator iter = pipeline.pipesegs.iterator();
        
        float[][]data = new float[2][pipeline.pipesegs.size()*2];
        int i =0;
        while(iter.hasNext()){
            pipeseg = (Pipeseg) iter.next();
            pipeseg.Add_all_equation_failprobability();
            data[0][i] = (float)pipeseg.start_point;
            data[1][i]=  (float)pipeseg.fail_probability;
            i ++ ;
            data[0][i] = (float)pipeseg.end_point;
            data[1][i]=  (float)pipeseg.fail_probability; 
            i ++ ;
        }    
         
        JPlotdata.probLineplot(data,information_panel); 
        /**
        PlotPanel plotpanel = new PlotPanel();
        plotpanel.PlotPipeline(pipeline);
        plotpanel.setVisible(true);   
        information_panel.add(plotpanel);      
        information_panel.setVisible(true);
        */
        this.validate();           
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed

       if(selectedPipeseg != null){        
        MechanicalFault mechanicalfault_frame = new MechanicalFault(selectedPipeseg); 
        mechanicalfault_frame.setLocationRelativeTo(null);
        mechanicalfault_frame.setVisible(true);    
        }
        else{
            JOptionPane.showMessageDialog( null , "请先选择管段" ,"操作提示" , JOptionPane.ERROR_MESSAGE) ;
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                MainFrame mainframe = new MainFrame();
                mainframe.setTitle("RBDA软件");
                mainframe.setLocationRelativeTo(null);
                //mainframe.pack();
                mainframe.setVisible(true);
                        
                
                
                //mainframe.jScrollPane2.setViewportView(jtree);
                //projecttreepanel.AddTree();

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}
