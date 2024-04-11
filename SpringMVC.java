/**
  父子容器，tomcat有一个spring容器（父容器，主要是共享功能），分发给不同的DispatcherServlet
  父容器的配置
  <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

  Spring MVC的中控制器是DispatcherServlet，拦截匹配的请求，并将请求分发到具体的控制器来处理（具体的控制器就是在servlet-mapping中定义的），
  具体的控制器是通过DispatcherServlet的contextConfigLocation参数所定义的配置文件来决定的
   
    非常重要的 DispatcherServlet类的实例化对象能够   -----  帮助你去处理请求，找到方法并且产生响应
    DispatcherServlet存在于tomcat中的一个stack中

    1.启动tomcat
    2.解析WEB.XML文件
    3.解析listener节点   ---->  contextInitialized()  ------>   spring父容器   ----->  存放到servletContext容器，一种map  存起来xxx.root，进行父子绑定
    4.创建DispatcherServlet对象，他的父类FrameworkServlet有一个属性WebApplicationContext（wac），就是spring容器，也就是说DispatcherServlet的内部有spring容器
    5.DispatcherServlet对象.init()方法  ------>    创建spring子容器（独立的，有几个DispatcherServlet就有几个spring容器） ---> CreateWebApplicationContext()  
        ---->   configerAndRefresWebApplicationContext(wav)方法启动spring容器（解析spring.xml文件），创建每个子容器都会绑定父容器
    
  tomcat只会调用service方法，比如说请求是GET，调用doGET方法，请求是post就调用doPOST方法


    
    pom.xml文件配置
      servlet-mapping


    tomcat  ---->   启动一个应用（由pom文件中的servlet——mapping去定义）   ----
    <servlet-mapping>
      <servlet-name>Wzh_app</servlet-name>
      <url-pattern>/Wzh_app/*</url-pattern>
    </servlet-mapping>

     <servlet-mapping>
      <servlet-name>Wzh_ask</servlet-name>
      <url-pattern>/Wzh_ask/*</url-pattern>
    </servlet-mapping>
**/
