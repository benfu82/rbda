/*
 * 依据
 * 极限状态方程构建——管体无缺陷的极限状态函数(A版)
 * 2013-3-5
 * 编制
 * g1 ＝2Btσy－PDi
 * 其中：
 * P—管道内压力，MPa；
 * Di—管道内直径，mm，为管道外径-壁厚t ；
 * t—管道壁厚，mm；
 * σy—钢管的屈服强度，MPa；
 * B—模型偏差系数，均值为1.03，标准偏差为0.059。
 */
package org.rbda.limitstateequation;

import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
public class DefectFreeYield extends LimitStateEquation{
    
        public final int parameters_number = 4;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断

    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double pressure  = parameters[0];
            double diameter_in = parameters[1] - parameters[2];
            double thickness  = parameters[2];
            double sigma_y   = parameters[3];
            double model_error = StdRandom.gaussian(1.03, 0.059); //模型误差        
            result = 2*model_error*thickness*sigma_y-pressure*diameter_in;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }    
}