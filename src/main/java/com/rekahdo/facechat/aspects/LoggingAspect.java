package com.rekahdo.facechat.aspects;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class LoggingAspect {

//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Before("com.rekahdo.project.spring_boot.pointcuts.PackagePointcutConfig.rootPackagePointcut()")
//	public void logBeforeMethodCall_RootPackage(JoinPoint joinPoint) {
//		logger.info("ENTER (-> {} <-)", joinPoint.getSignature());
//	}

//	@Before("com.rekahdo.project.spring_boot.pointcuts.PackagePointcutConfig.projectPackagePointcut()")
//	public void logBeforeMethodCall_ProjectPackage(JoinPoint joinPoint) {
//		logger.info("BEFORE ASPECT (-> {} <-)", joinPoint.getSignature());
//	}
//
//	@Before("com.rekahdo.project.spring_boot.pointcuts.PackagePointcutConfig.singlePackagePointcut()")
//	public void logBeforeMethodCall_SinglePackage(JoinPoint joinPoint) {
//		logger.info("BEFORE ASPECT (-> {} <-)", joinPoint.getSignature());
//	}
//
//	@Before("com.rekahdo.project.spring_boot.pointcuts.PackagePointcutConfig.doublePackagesPointcut()")
//	public void logBeforeMethodCall_DoublePackages(JoinPoint joinPoint) {
//		logger.info("BEFORE ASPECT (-> {} <-)", joinPoint.getSignature());
//	}
	
}
