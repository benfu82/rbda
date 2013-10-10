/*
 * 依据
 * 极限状态方程构建——过量塑性变形的极限状态函数
 * 2013-3-16
 * 编制
 * g1 = sigma_y-sigma_eff
 * g2 = epsilon_a-epsilon_ph
 * 其中：
 */
package org.rbda.equations;

/**
 *
 * @author kewin
 */
//方程编号23
public class ExcessivePlasticDeformation extends Equation{
    public final int parameters_number = 9;//参数个数，应该与变量parameters
                                           //大小相同,作为冗余判断

    @Override
    public EquationResult EquationValue() {
        
        EquationResult result = new EquationResult();
        if (parameters.length == parameters_number){
            
        }
        
        else{
            System.out.println("参数个数应为"+parameters_number+"个，请检查是否遗漏！");
        }
        return result;
    }
    
}
