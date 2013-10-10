/*
 * 方程计算的返回值，包括方程计算的结果和失效类型
 */
package org.rbda.equations;

/**
 *
 * @author lx
 */
public class EquationResult {
    double value = 0;
    int type = 0;//不同的失效结果返回不的失效代码
             //管道不发生失效： 0
             //服役极限（SLS）：1 
             //泄漏极限（LLS）：2
             //极端极限（ULS）：31 大泄漏
             //极端极限（ULS）：32 破裂
}
