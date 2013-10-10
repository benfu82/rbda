package org.rbda.equation;

/**
 * 极限方程接口定义。所有失效分析方程必须实现该接口。
 * @author fujunwei
 *
 */
public interface IEquation {
	
	/**
	 * 获取ID
	 * @return
	 */
	public int getID();
	
	/**
	 * 获取方程名称
	 * @return
	 */
	public String getName();
	
	public void setName(String name);
	
	
	/**
	 * 获取方程描述
	 * @return
	 */
	public String getDescription();
	
	
	/**
	 * 获取方程参数个数
	 * @return
	 */
	public int getParameterCount();
	
	
	/**
	 * 获取指定索引下的方程参数名称
	 * @param index 参数索引
	 * @return
	 */
	public String getParameterNameAt(int index);
	
	
	/**
	 * 获取指定索引下的方程参数描述
	 * @param index 参数索引
	 * @return
	 */
	public String getParameterDescAt(int index);
	
	public IEquation cloneEquation();
}
