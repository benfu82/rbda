/*
 * 依据
 * 极限状态方程构建——管体无缺陷的极限状态函数(A版)
 * 2013-3-5
 * 编制
 * g1 ＝2B1tσy－PDi
 * g2 ＝2B2tσu－PDi
 * 其中：
 * P—管道内压力，MPa；
 * Di—管道内直径，mm，为管道外径-壁厚t ；
 * t—管道壁厚，mm；
 * σy—钢管的屈服强度，MPa；
 * σu—钢管的抗拉强度，MPa；
 * B1—模型偏差系数，均值为1.03，标准偏差为0.059;
 * B2—模型偏差系数，均值为1.0，标准偏差为0.050。
 */
package org.rbda.equations;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
//方程编号18
public class DefectFree_CPPE extends Equation{
    public final int parameters_number = 5;//参数个数，应该与变量parameters
                                           //大小相同,作为冗余判断

    @Override
    public EquationResult EquationValue() {
        
        EquationResult result = new EquationResult();
        if (parameters.length == parameters_number){
            
            //方程输入参数 
            double diameter  = parameters[0];
            double thickness = parameters[1];
            double pressure  = parameters[2];
            double sigma_y   = parameters[3];
            double sigma_u   = parameters[4];
            
            
            //方程模型误差
            double model_error1 = StdRandom.gaussian(1.03, 0.059); //模型误差
            double model_error2 = StdRandom.gaussian(1.00, 0.050); //模型误差
            //中间计算结果
            double diameter_in;
            double g1;
            double g2;
            
            diameter_in = diameter - thickness;           
            g1 = 2*model_error1*thickness*sigma_y-pressure*diameter_in;
            g2 = 2*model_error2*thickness*sigma_u-pressure*diameter_in; 
            
            if ( g2<0 ){
                result.value = g2;
                result.type= 2;
            }
            else if ( g1<0 ){
                result.value = g1 ; 
                result.type = 1;
            }
    
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
