/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import org.rbda.pipeline.Pipeseg;

/**
 *
 * @author kewin
 */
public class ProtectionFrame extends javax.swing.JFrame implements ItemListener{
    
    public double B1,B2,B3,B4,B5,B6,B7,B8,B9,B10,B11,B12;
    public Pipeseg pipeseg;
    /**
     * Creates new form ProtectionFrame
     */
    
    public ProtectionFrame() {
        initComponents();
jComboBox1.addItemListener(this);
jComboBox2.addItemListener(this);
jComboBox3.addItemListener(this);
jComboBox4.addItemListener(this);
jComboBox5.addItemListener(this);
jComboBox6.addItemListener(this);
jComboBox7.addItemListener(this);
jComboBox8.addItemListener(this);
jComboBox9.addItemListener(this);
jComboBox10.addItemListener(this);
jComboBox11.addItemListener(this);
jComboBox12.addItemListener(this);
jComboBox13.addItemListener(this);
jComboBox14.addItemListener(this);
jComboBox15.addItemListener(this);
   
    }
  //该构造函数关联管段，可以将计算结果赋给管段中的pipeseg.protection_factor作为碰撞概率  
    public ProtectionFrame(Pipeseg pipeseg) {
        initComponents();
jComboBox1.addItemListener(this);
jComboBox2.addItemListener(this);
jComboBox3.addItemListener(this);
jComboBox4.addItemListener(this);
jComboBox5.addItemListener(this);
jComboBox6.addItemListener(this);
jComboBox7.addItemListener(this);
jComboBox8.addItemListener(this);
jComboBox9.addItemListener(this);
jComboBox10.addItemListener(this);
jComboBox11.addItemListener(this);
jComboBox12.addItemListener(this);
jComboBox13.addItemListener(this);
jComboBox14.addItemListener(this);
jComboBox15.addItemListener(this);
   this.pipeseg = pipeseg;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jComboBox9 = new javax.swing.JComboBox();
        jComboBox10 = new javax.swing.JComboBox();
        jComboBox11 = new javax.swing.JComboBox();
        jComboBox12 = new javax.swing.JComboBox();
        jComboBox13 = new javax.swing.JComboBox();
        jComboBox14 = new javax.swing.JComboBox();
        jComboBox15 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p1.jpg")); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p2.jpg")); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p3.jpg")); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p4.jpg")); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p5.jpg")); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p6.jpg")); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon("I:\\电脑D盘\\基于可靠性的设计和评价方法\\prog\\RBDA\\RBDAsoftv_1\\image\\p7.jpg")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "一级地区", "二级地区", "三级地区", "四级地区" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "管道埋深0.8 m ", "管道埋深1.0 m", "管道埋深1.2 m", "管道埋深1.5 m", "管道埋深1.8 m" }));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "提供管道线路资料 ", "定位 / 标记 ", "定位 / 标记 / 现场监督 ", "由管线部门开挖" }));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "每半日巡查 ", "每日巡查 ", "隔日巡查 ", "每周巡查 ", "隔周巡查 ", "每月巡查 ", "每半年巡查 ", "每年巡查 " }));

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "不适用" }));

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "无埋地标志", "有埋地标志" }));

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "直接向联系人寄发广告" }));

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "有选择地在交叉点设立标志 ", "在所有交叉点设立标志 ", "在所有交叉点并在管道沿线按一定间隔设立标志 " }));

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "无埋地标志", "有埋地标志" }));

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "自愿", "强制但无强制措施", "强制加民事处罚 ", "签订管道用地协议" }));

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "不适用" }));

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "当日内作出回应 ", "两日内作出回应" }));

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "不适用" }));

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "按公司资料 ", "管道探测仪", "人工开挖" }));

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "不适用" }));

        jButton1.setText("确定");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("取消");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap(75, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(75, 75, 75)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(3, 3, 3))))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //点击确定按钮，获取各选项的状态，得到B变量值，并计算出相应的碰撞概率
        switch(jComboBox1.getSelectedIndex()){
            case 0:
                B1= 0.06;
                break;
            case 1:
                B1=0.2;
                break;
            case 2:
                B1=0.33;
                break;
            case  3:
                B1 = 0.25;
                break;
    }
        
        
        pipeseg.protection_factor = B1;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ProtectionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProtectionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProtectionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProtectionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProtectionFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource()==jComboBox1){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框1的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox2){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框2的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox3){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框3的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox4){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框4的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox5){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框5的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox6){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框6的事件"); 
               jComboBox9.setSelectedIndex(jComboBox6.getSelectedIndex());
            }            
        }
        
        if (e.getSource()==jComboBox7){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框7的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox8){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框8的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox9){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框9的事件"); 
               jComboBox6.setSelectedIndex(jComboBox9.getSelectedIndex());
            }            
        }
        
        if (e.getSource()==jComboBox10){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框10的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox11){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框11的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox12){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框12的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox13){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框12的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox14){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框12的事件"); 
            }            
        }
        
        if (e.getSource()==jComboBox15){
            if(e.getStateChange() == ItemEvent.SELECTED){
               System.out.println("处理组合框12的事件"); 
            }            
        }
        
     
    }
}
