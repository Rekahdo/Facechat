package com.rekahdo.facechat.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class PackagePointcutConfig {

	@Pointcut("execution(* com.rekahdo..*(..))")
	public void rootPackagePointcut() {}

//	@Pointcut("execution(* com.rekahdo.project.spring_boot.*.*(..))")
//	public void projectPackagePointcut() {}
//
//	@Pointcut("execution(* com.rekahdo.project.spring_boot.*.*.*(..))")
//	public void singlePackagePointcut() {}
//
//	@Pointcut("execution(* com.rekahdo.project.spring_boot.*.*.*.*(..))")
//	public void doublePackagesPointcut() {}
	
}
