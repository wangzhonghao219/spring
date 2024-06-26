    //Ioc表示控制反转
//1.什么是控制，控制了什么
    在用到spring的时候，我们通常需要创建一些类，如orderservice，userservice，用到一些注解，如@AutoWired，但是当程序运行时，用到的是userservice对象，那这个对象是什么时候创建的，谁创建的，包括那些对象里的属性
  是什么时候赋的值，那这些都是spring做的
    spring控制对象的创建，控制对象内属性的赋值，我们要做的只是定义类，以及定义那些属性需要spring来赋值
//2.什么是反转，反转之前是怎么控制的？反转之后是谁控制的？如何控制的
    反转表示一种对对象控制权的转移，可以减轻程序员的负担
//3.为什么要反转，反转之前有什么问题，反转之后又什么好处？
    spring负责创建对象，以及给对象内的属性赋值，也就是用spring，对象的控制权会转交给spring


        ioc是一种设计思路，我们联系bean的生命周期来回答，我们去创建一些类，用到一些注解，但是我们没有创建这些类的对象，而是spring通过无参数的构造方法（反射）来创建这些类的对象，并经过属性填充（注入依赖），再初始化，可能会用到aop
        生成代理类，启动spring事务等，其实是把普通对象生成的代理类称为bean对象，放入ioc容器中
        传统的java se设计思路呢，是程序员主动去new一个对象，这个叫做正转，但是spring不同，我们用到一个对象时是从beanfactory中获取，当然beanfactory开发人员不能使用，我们用到的是applicationcontext容器，他是beanfactory的
        一个子类接口，从容器中get到一个bean对象，这个bean对象是spring创建好的，也就是说对象的控制权交到了spring手中，从而减轻了开发人员的工作量，并实现了松散解耦（工厂设计模式），这个就叫做反转，表示对对象控制权的转移


        1 什么是Spring IoC容器
        Spring IoC负责创建对象、管理对象（通过依赖注入（DI）、装配对象、配置对象，并且管理这些对象的整个生命周期。

        2 IoC的优点是什么
        IoC或依赖注入把应用的代码量降到最低；
        它使应用容易测试，单元测试不再需要单例和JNDI查找机制；
        最小的代价和最小的侵入性使松散耦合得以实现；
        IoC容器支持加载服务时的饿汉式初始化和懒加载。
        3 IoC是什么
        Ioc—Inversion of Control，即“控制反转”，不是什么技术，而是一种设计思想。在Java开发中，Ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制，对于spring框架来说，就是由Spring来负责控制对象的生命周期和对象间的关系。如何理解好Ioc呢？理解好Ioc的关键是要明确“谁控制谁，控制什么，为何是反转（有反转就应该有正转了），哪些方面反转了”，那我们来深入分析一下：

        谁控制谁，控制什么：
        
        传统Java SE程序设计，我们直接在对象内部通过new进行创建对象，是程序主动去创建依赖对象；而IoC是有专门一个容器来创建这些对象，即由Ioc容器来控制对象的创建；谁控制谁？当然是IoC 容器控制了对象；控制什么？那就是主要控制了外部资源获取（不只是对象包括比如文件等）。
        为何是反转，哪些方面反转了：

        有反转就有正转，传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而反转则是由容器来帮忙创建及注入依赖对象；为何是反转？因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些方面反转了？依赖对象的获取被反转了。









































        
