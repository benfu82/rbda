/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.probability;

import org.rbda.tools.XLS;

/**
 *
 * @author kewin
 */
public final class DistributionInformation {
    static String filename = "./data/probfundata.xls";
    static int distributions_number = 0;//excel表格的左上角放的就是表格中收录的分布函数个数
    public static String[] distributions_name;//分布函数的名字
    public static int[] parameters_number; //分布函数所需参数个数
    public static String[][] parameters_names;//分布函数中参数名字
    
    public static void DistributionInformationInit(){
        distributions_number = Integer.parseInt(XLS.Readsheet0cell(filename, 1, 2));
        distributions_name = new String[distributions_number];
        parameters_number = new int[distributions_number];
        parameters_names = new String[distributions_number][];
        for ( int i = 0; i < distributions_number; i++){
            distributions_name[i] = XLS.Readsheet0cell(filename, 2+i, 2);
            parameters_number[i] = Integer.parseInt(XLS.Readsheet0cell(filename, i+2, 6));
            parameters_names[i] = new String[parameters_number[i]];
            for ( int j = 0; j < parameters_number[i]; j++){
                parameters_names[i][j] = XLS.Readsheet0cell(filename, 2+i, 7+j);
            }
            //还可以继续读第3列是介绍，第4列是图片名。。。。。。
            //System.out.println(equations_name[i]);
        }
    }
    
}
