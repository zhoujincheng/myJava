package com.zjc.aspect;

import java.util.Arrays;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;



/**
 * 切面
 * @author 周金城
 *
 */
@Aspect
@Component
public class LogAspect {
	
	private final Logger logger= LoggerFactory.getLogger(this.getClass());
	
	//监听所以类所有包下的所有类
	@Pointcut("execution(* com.zjc.web.*.*(..))")
	public void log() {
		
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
		
		String URL=((HttpServletRequest) request).getRequestURL().toString();
		String ip = ((ServletRequest) request).getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." 
        				   + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();        
        RequestLog requestLog= new RequestLog(URL, ip, classMethod, args);
        
        logger.info("Request : {}", requestLog);
	}
	
	@After("log()")
	public void doAfter() {
		//logger.info("----doAfter------");
	}
	
	@AfterReturning(returning ="result",pointcut = "log()" )
	public void doAfterReturn(Object result) {
		logger.info("Result : {}"+result);
	}
	
	//请求类，记录请求的路径 ip 调用了什么类 返回了什么数据
	private class RequestLog{
		private String url;
		private String ip;
		private String classMethod;
		private Object[] args;
		
		
		public RequestLog(String url, String ip, String classMethod, Object[] args) {
			super();
			this.url = url;
			this.ip = ip;
			this.classMethod = classMethod;
			this.args = args;
		}

		@Override
		public String toString() {
			return " [url=" + url + ", ip=" + ip + ", "
					+ "classMethod=" + classMethod 
					+ ", args="+ Arrays.toString(args) + "]";
		}
		
		
	}
}
