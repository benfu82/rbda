/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.root;

import org.apache.commons.math3.distribution.*;
import org.rbda.gui.MainFrame;
import org.rbda.limitstateequation.*;
import org.rbda.probability.*;

/**
 *
 * @author kewin
 */
public class Main {
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
                MainFrame mainframe = new MainFrame();
                mainframe.setTitle("RBDA软件");
               mainframe.setLocationRelativeTo(null);
                mainframe.setVisible(true);
       
        //UniformRealDistribution uniformdistribution = new UniformRealDistribution(1,2);
        //System.out.println(uniformdistribution.sample());
        
        
        
    }
}
