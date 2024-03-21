Bean
//UserService.class ----> 无参数(***推断***)构造方法（反射）多种构造方法调用带注解的构造器（@2autowired） (deCreateBean) 站在spring的角度去思考 -------> 普通对象(obj) 
//----------> 注入依赖(autowired)(属性填充)   ------>  初始化(spring的初始化 != JVM初始化) ----->  初始化后(post)AOP  ，需要spring事务的时候需要aop
//------> (用) 代理对象 ------->  放入Map<BeanName,Bean对象>单例池  如果需要AOP，则Map存放的是代理对象，如果不需要AOP则map存放的是普通对象
//spring事务会给普通对象生成代理对象，从而产生bean

//注入依赖(@autowired)是实现属性，初始化是实现方法,  *********************只有autowired注解才可以用到构造方法上  @autowired   ****************
@Component
@Configuration //***************Configuration注解，自动加入代理对象（动态代理），先走代理逻辑   -------   底层用的CGLIB   --------- 动态代理，代理对象（super）
//AOP产生的代理对象产生的target对象即普通对象，Configuration动态代理产生的是代理对象，调用的super方法
public class UserService{
  private OrderService orderService;

  @Autowired
  private JdbcTemplate jdbctemplate；

  @Transactional
  public void test(){
    jdbcTemplate.execute(sql:"insert into t1 values(6,6,4,4,'f')");  //此时因为是JdbcTemplate.execute方法，和spring没有关系，不在spring的控制之下，所以必须AOP，执行切面逻辑，将autocommit = true（自动提交） 改为  false，将sql变为在spring的控制之下
    throw new NullPointerException();  //抛出异常数据库要回滚ROLLBACK，取消刚才数据库的操作         //用的是spring创建的数据库连接
    a();  //new UserService().a()  用的普通对象的方法
    //AOP后，执行***spring事务***的切面逻辑
    //解析注解,代理方法去解析
    //新建一个数据库连接connection  ThrealLocal<Map<DataSource对象，connection>>放置创建的连接,Map解决多数据元的问题K-V   有一个扩展机制
    //connection.autocommit = false;

    //target.test   //jdbcTemplate 在threadLocal中找到  conn 执行SQL
    //conn.commit()  conn.rollback()
  }
  //*********方法的注解会不会被解析，会不会有用，看是什么对象调用的，如果是代理对象调用的则有用（spring事务触发），如果是普通对象调用的，则没有太大的关系
  @Transactional(propagation = Propagation.NEVER)  //传播机制
  public void a(){
      jdbcTemplate.execute(sql:"insert into t1 values(2,2,4,4,'2')"); 
  }
  

  public UserService(OrderService orderService1){  //构造方法  OrderServiced对象来源自Map<BeanName, Bean对象>（byType，根据类型去找），Map没有则 ------->创建Bean对象（必须有Component注解），若无Component注解声明对象为Bean对象，则传递失败，spring也不返回null
    this.orderService = orderService1;             //若无Component注解声明对象为Bean对象，则传递失败，spring也不返回null,会报错，因为这是构造方法---> 强制依赖，*****注意循环依赖，加Lazy注解解决
    System.out.println(1);                        //hashmap<>  key唯一  value不唯一    Map<BeanName,Bean对象>  *******先ByType (如果找到多个) ---------> 再ByName*************,***如果只有一个Type的Bean对象，就不用再ByName了，有多个则必确明确指出BeanName
  }
  //public void test(){}//执行普通对象
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
//main()
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//单例Bean   单例模式   orderservice1,2,3都是单例Bean，对应的名字是单例Bean，而不是对应类是单例Bean
applicationContext,getBean(name:"orderservice1"); 
applicationContext,getBean(name:"orderservice2"); 
applicationContext,getBean(name:"orderservice3"); 

//依赖注入的地方，无论是构造注入，字段注入，接口注入（看实现类是什么情况，都是找bean对象），还是Set方法注入，都是先byname再bytype，这是@Autowired注解，@Resource相反，先byname再bytype

//AOP  切面注解定义    *****CGLIB,基于继承*******，会针对UserService类生成***代理类***，即class UserServiceProxy extends UserService {}
@Aspect//切面
@Component
public class WzhAspect{

  @Before("execution(public void com.Wzh.service.UserService.test())")//切一下UserService的test方法
  public void wzhBefore(JoinPoint joinPoint){
    System.out.println("wzh-nb");  //先执行前面切面（Before）的逻辑，再执行UserService的逻辑
    //joinPoint会执行两个方法，一个是joinPoint.getTarget()拿到普通对象,  另一个是joinPoint.this()拿到代理对象
  }
}
//UserService代理对象，先生成代理类，即UserSerciceProxy类,***************但是spring不对代理对象进行依赖注入
//UserService代理对象.test()方法
//UserSerciceProxy对象==UserService代理对象------->UserService代理对象.target = 普通对象（一开始就创建出来的，代理对象代理的就是普通对象）
class UserServiceProxy extends UserService {   //为什么要继承，因为不继承用的时候不知道代理类的名字叫什么，CGLIB创建代理类有一定规则，每次运行不一样，所以只用关心UserService，不关心其代理类

  UserService target;
  //重写UserService类的test方法，AOP执行普通对象的逻辑之前额外执行一些逻辑  ==》加强版的test方法
  
  public void test(){
    //执行切面逻辑，但是与orderservice的属性无关，因为代理对象没有进行依赖注入（不赋值），精髓是额外执行的切面逻辑
    //target.test();    //target对象的属性，就是UserService普通对象的的属性（有值，因为经过了依赖注入）
    //super.test();    //调用后会打印orderservice属性，但是打印的代理对象的orderService属性，是没有值的
  }
}




