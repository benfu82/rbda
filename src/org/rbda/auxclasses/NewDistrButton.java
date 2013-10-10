/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.auxclasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.rbda.gui.NewDistributionFrame;
import org.rbda.pipeline.Pipeseg;
import org.rbda.probability.Distribution;

/**
 *
 * @author kewin
 */
public class NewDistrButton  extends javax.swing.JButton{
    public Pipeseg pipeseg;
    public String parameters_name;
    public NewDistrButton ( String parameters_name, Pipeseg pipeseg){
        this.setText("选择参数分布");
        this.parameters_name = parameters_name;
        this.pipeseg = pipeseg;
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonActionPerformed(e);
                
    }
        });
        
    }
    
    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        Distribution distribution = new Distribution();
        NewDistributionFrame new_distribution_frame = new NewDistributionFrame(distribution);
        new_distribution_frame.setLocationRelativeTo(null);
        new_distribution_frame.setVisible(true);
        pipeseg.properties.put(parameters_name, distribution);
        }
    
}
