Bean
//UserService.class ----> 无参数(***推断***)构造方法（反射）多种构造方法调用带注解的构造器（@2autowired） (deCreateBean) 站在spring的角度去思考 -------> 普通对象(obj) 
//----------> 注入依赖(autowired)(属性填充)   ------>  初始化(spring的初始化 != JVM初始化) ----->  初始化后(post)AOP  ，需要spring事务的时候需要aop
//------> (用) 代理对象 ------->  放入Map<beanName,Bean对象>单例池  如果需要AOP，则MAP存放的是代理对象，如果不需要AOP则map存放的是普通对象


//注入依赖(@autowired)是实现属性，初始化是实现方法,  *********************只有autowired注解才可以用到构造方法上  @autowired   ****************
