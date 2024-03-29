//自动配置中批量导入的机制-------springboot的SPI机制（一种***规范***）
//在Jar包中有META-INF目录，找到spring factories文件，有很多的KEY，记录着该Jar包中有多少configuration类（自动配置类），如果没有这个文件，该Jar包中肯定没有自动配置类
//解析org.springframework.boot.autoconfigure.enableautoconfiguration，得到一个map
static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
Enumeration<URL> urls = classLoader.getResources(FACTORIES_RESOURCE_LOCATION);//然后遍历文件和解析文件，要经过过滤filter，最后放入map中，比如说有一些技术，但是没有配置依赖，那就是不需要，需要过滤

//如何判断需不需要一个自动配置类,添加注解，例如ConditionOnClass({RabbitTemplate.class,Channel.class})，解析注解，如果这两个类都加载到了，则表明pom文件有他的依赖，将该自动配置类返回到spring容器
//如何优化过滤器，减少遍历？   ----------注意properties文件，只需加载类一次，扫描一次，或者可以开多个线程来判断处理
