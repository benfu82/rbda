package org.rbda.logging;

public class Logger {
	private static Logger logger;
	public static Logger getInstance(){
		if (logger==null){
			logger = new Logger();
		}
		
		return logger;
	}
	
	public void debug(String msg){
		trace(msg);
	}
	
	public void info(String msg){
		trace(msg);
	}
	
	public void warn(String msg){
		trace(msg);
	}
	
	public void error(String msg){
		trace(msg);
	}
	
	public void fatal(String msg){
		trace(msg);
	}
		
	private void trace(String msg){
		System.out.println(msg);
	}
	
	
	private Logger(){}
}
