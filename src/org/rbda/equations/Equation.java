/*
 * 该类用于抽象定义失效原因相关的极限状态方程
 * 极限状态方程包括编号，参数列表以及一个用于计算方程在指定
 * 参数下的值。
 * 
 */
package org.rbda.equations;

/**
 *
 * @author kewin
 */
public abstract class Equation {
    public int Id; //每类极限状态方程对应一个号，与EXCEL中一致 
    public double[] parameters;//第一次使用极限状态方程前先要开内存
    public void Init(int Id){
        this.Id = Id;
        parameters = new double[EquationInformation.parameters_number[Id]];//
    }
    
    public abstract EquationResult EquationValue();//极限参数计算极限状态方程结果
}