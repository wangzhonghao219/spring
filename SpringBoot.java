//启动类，spring boot在做整合，是基于spring的
@WzhSpringBootApplication
//@Import( WebServerAutoConfiguration.class)
//@ConponentScan("com.Wzh")  //可以转移到WzhSpringBootApplication的注解定义类中区,扫描很耗费性能
public class MyApplica{

  /**@Bean
  public TomcatWebServer tomcatWebServer(){
    retunr new TomcatWebServer();
  }**/

  /**@Bean
  public JettyWebServer jettyWebServer(){
    retunr new JettyWebServer();
  }**/
  
  public static void mian(){
    WzhSpringBootApplica.run(MyApplication.class); //解析配置类，伴随解析类的注解
  }
}

public class WzhSpringBootApplication{

  public static void run();   //run(Class clazz);

  //创建一个spring容器
  AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();  
  //配置spring容器，通过注解的方式------》配置类(一般是启动类)------run方法传进来的类，即上面的calzz
  applicationContext.register(MyApplication);//注册容器
  applicationContext.refresh();//刷新配置类

  WebServer webServer = getWebServer();
  webServer.start();

  private static WebServer getWebServer(WebApplicationContext webApplicationContext){
    //根据pom.xml  依赖  
    //去spring容器里拿bean对象(根据类型)
    Map<String,WebServer> webServers = applicationContext.getBeanOfType(WebServer.calss);
    if(webServers.isEmpty()){
      throw new NullPointerException();
    }
    if(webServers.size() > 1){
      throw new IllegalStateException();
    }
    //返回唯一一个
    return webServers.values().stream().findFirst().get();
  }

  //处理请求
  //启动webserver服务器-------Tomcat  Jetty等servlet容器处理请求,可插拔
  //pom.xml  依赖  
  startTomcat();  //在pom文件中配置Tomcat
  //startJetty();

  private static void startTomcat(){

    //处理请求，在DispatcherServlet中注入spring容器，来扫描其中的请求（controller）
    tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet(applicationContext));  //用的是spring mvc中的dispatcher
    context.addServletMappingDecoded("/*","dispatcher");
  }
  
}
//抽象出一个启动webserver的接口
public interface WebServer{

    public void start();
}

public class TomcatWebServer implements WebServer{
  @override
  public void start(){
    //启动tomcat
  }
}

public class JettyWebServer implements WebServer{
  @override
  public void start(){
    //启动Jetty
  }
}

//定义注解；
@Target(ElementType.TYPE)
@ConponentScan("com.Wzh")
@Retention(RetentionPolicy.RUNTIME)
@Import( WebServerAutoConfiguration.class)
public @interface WzhSpringBootApplication{
  
}
 //发送请求后要处理请求
@RestController
public class UserController{
  @GetMapping("/test")
  public String test(){
    return "Wzh_nb";
  }
  
}

@Configuration  //自动配置
public class WebServerAutoConfiguration{
  @Bean
  @Conditional(TomcatCondition.class)//条件注解
  public TomcatWebServer tomcatWebServer(){
    retunr new TomcatWebServer();
  }

  @Bean
  @Conditional(JettyCondition.class)//条件注解
  public JettyWebServer jettyWebServer(){
    retunr new JettyWebServer();
  }
}

public class TomcatCondition implements Condition{
  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata){

    //有没有Tomcat依赖
    try{
      context.getClassLoader().loadClass("org.apache.catalina.startup.Tomcat");
      return true;
    } catch(classNotFoundException e){
      return false;
    }
    
  }
}
































































