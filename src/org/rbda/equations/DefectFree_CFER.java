/*
 * 依据
 * CFER报告——附录A2.2－－无缺陷管道破裂的极限状态函数
 * 2013-4-24
 * 编制
 * 屈服：g1 = 2sigma_y*t-PD
 * 破裂：g2 = 2b*sigma_f*t-PD
 * 其中：
 * P—管道内压力，MPa；
 * D—管道直径，mm；
 * t—管道壁厚，mm；
 * σf—钢管的流变应力，定义为0.953σu，MPa；
 * b—模型偏差系数，正态分布，均值为1.0，标准偏差为0.04。
 */
package org.rbda.equations;

import org.rbda.probability.StdRandom;

/**
 *
 * @author lx
 */
//方程编号17
public class DefectFree_CFER  extends Equation{
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
            double model_error = StdRandom.gaussian(1, 0.04); //模型误差  
            //中间计算结果
            double g1;
            double g2;
            //方程计算过程
            g1 = 2*sigma_y*thickness-pressure*diameter; 
            g2 = 1.906*model_error*sigma_u*thickness-pressure*diameter;  
            //失效类型判定
            if ( g2<=0 ){
                result.value = g2;
                result.type= 2;
            }
            else if(g1<=0){
                result.value = g1; 
                result.type = 1;
            }
    
        }
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
