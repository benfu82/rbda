/*
 * 该类用于抽象定义极限状态方程
 * 极限状态方程包括编号，参数列
 * 表以及一个用于计算方程在指定
 * 参数下的值。
 */
package org.rbda.limitstateequation;

import org.rbda.probability.Distribution;

/**
 *
 * @author kewin
 */
public abstract class LimitStateEquation {
    public int Id; //每类极限状态方程对应一个号，与EXCEL中一致 
    //先不考虑模型误差单独处理
    //public Distribution Model_error;//在EXCEL中没有，而是存在这里
    public double[] parameters;//第一次使用极限状态方程前先要开内存
    
    public void Init(int Id){
        this.Id = Id;
        parameters = new double[EquationInformation.parameters_number[Id]];//+1多出来这一个是模型误差
    }

    
    public abstract double EquationValue();//极限参数计算极限状态方程结果
    
}
