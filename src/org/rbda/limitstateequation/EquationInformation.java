/*
 * 该类是一个静态类，目的是保存极限状态方程excel表格信息，这样可以增加程序速度
 */
package org.rbda.limitstateequation;

import java.util.HashMap;
import org.rbda.probability.Distribution;
import org.rbda.tools.XLS;


/**
 *
 * @author kewin
 */
public final class EquationInformation {
    static String filename = "./data/limitstate.xls";
    static int equations_number = 0;//excel表格的左上角放的就是表格中收录的方程个数
    public static String[] equations_name;//方程的名字
    public static int[] parameters_number; //方程所需参数个数
    public static String[][] parameters_names;//方程中参数名字
    //！！这个HASH表很重要，将函数名和类名对应，可以采用反射机制创建对象。其中key同equations_name是相同的内容
    public static HashMap equations_name2fun_map = new HashMap();
    public static Distribution[] model_error;
                                                            
    
    public static void EquationInformationInit(){
        equations_number = Integer.parseInt(XLS.Readsheet0cell(filename, 1, 2));
        model_error = new Distribution[equations_number];
        equations_name = new String[equations_number];
        parameters_number = new int[equations_number];
        parameters_names = new String[equations_number][];
        for ( int i = 0; i < equations_number; i++){
            equations_name[i] = XLS.Readsheet0cell(filename, 2+i, 2);
            equations_name2fun_map.put(equations_name[i], XLS.Readsheet0cell(filename, 2+i, 4));
            parameters_number[i] = Integer.parseInt(XLS.Readsheet0cell(filename, i+2, 6));
            parameters_names[i] = new String[parameters_number[i]];
            for ( int j = 0; j < parameters_number[i]; j++){
                parameters_names[i][j] = XLS.Readsheet0cell(filename, 2+i, 7+j);
            }
            //还可以继续读第3列是介绍，第4列是图片名。。。。。。
            //System.out.println(equations_name[i]);
        }
        
        //作为程序内嵌的一部分，这里初步将模型误差的分布直接赋值而不放到excel表格中，而表格中也不出现模型误差这一项
        for ( int i = 0; i < equations_number; i++){
            model_error[i] = new Distribution();
            model_error[i].id = 9;//都先默认为常数 1
            model_error[i].parameters = new double[1];
            model_error[i].parameters[0] = 1;
        }
        //无缺陷管道破裂极限状态方程(CFER)
    }
    
}
