/*
 * 依据
 * CFER报告——附录A2.2－－无缺陷管道破裂的极限状态函数
 * 2013-4-24
 * 编制
 * g2 ＝2btσf－PD
 * 其中：
 * P—管道内压力，MPa；
 * D—管道直径，mm；
 * t—管道壁厚，mm；
 * σf—钢管的流变应力，定义为0.953σu，MPa；
 * b—模型偏差系数，正态分布，均值为1.0，标准偏差为0.04。
 */
package org.rbda.limitstateequation;

import org.rbda.probability.Distribution;
import org.rbda.probability.StdRandom;

/**
 *
 * @author kewin
 */
public class DefectFreeBurst_CFER extends LimitStateEquation{
    
        public final int parameters_number = 4;//参数个数，应该与变量parameters
                                               //大小相同,作为冗余判断
        /**
        public DefectFreeBurst_CFER(){
            this.Model_error = new Distribution();
            this.Model_error.id = 2;//正态分布
            this.Model_error.parameters = new double[2];
            this.Model_error.parameters[0] = 1;//均值为1
            this.Model_error.parameters[1] = 0.04;//变异系数为4%
        }
        */

    @Override
    public double EquationValue() {
        double result = 0;
        if (parameters.length == parameters_number){
            double sigma_u   = parameters[0];
            double thickness = parameters[1];
            double pressure  = parameters[2];
            double diameter  = parameters[3];
            double model_error = StdRandom.gaussian(1, 0.04); //模型误差      
            result = 1.906*model_error*sigma_u*thickness-pressure*diameter;  
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }    
}