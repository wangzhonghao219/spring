Bean
//UserService.class ----> 无参数(***推断***)构造方法（反射）多种构造方法调用带注解的构造器（@2autowired） (deCreateBean) 站在spring的角度去思考 -------> 普通对象(obj) 
//----------> 注入依赖(autowired)(属性填充)   ------>  初始化(spring的初始化 != JVM初始化) ----->  初始化后(post)AOP  ，需要spring事务的时候需要aop
//------> (用) 代理对象 ------->  放入Map<BeanName,Bean对象>单例池  如果需要AOP，则MAP存放的是代理对象，如果不需要AOP则map存放的是普通对象


//注入依赖(@autowired)是实现属性，初始化是实现方法,  *********************只有autowired注解才可以用到构造方法上  @autowired   ****************
@Component
public class UserService{
  private OrderService orderService;

  public UserService(OrderService orderService1){  //构造方法  OrderServiced对象来源自Map<BeanName, Bean对象>（byType，根据类型去找），Map没有则 ------->创建Bean对象（必须有Component注解），若无Component注解声明对象为Bean对象，则传递失败，spring也不返回null
    this.orderService = orderService1;             //若无Component注解声明对象为Bean对象，则传递失败，spring也不返回null,会报错，因为这是构造方法，表示强制依赖，*****注意循环依赖，加Lazy注解解决
    System.out.println(1);                        //hashmap<>  key唯一  value不唯一    Map<BeanName,Bean对象>  *******先ByType ---------> 再ByName*************,***如果只有一个Type的Bean对象，就不用再ByName了，有多个则必确明确指出BeanName
  }
}


//
@Component//声明为Bean对象
public class OrderService{
  @Bean
  public OrderService orederService2(){
    return new OrderService()
  }

  @Bean
  public OrderService orederService3(){
    return new OrderService()
  }
}

//单例Bean   单例模式   orderservice1,2,3都是单例Bean，对应的名字是单例Bean，而不是对应类是单例Bean
applicationContext,getBean(name:"orderservice1"); 
applicationContext,getBean(name:"orderservice2"); 
applicationContext,getBean(name:"orderservice3"); 










