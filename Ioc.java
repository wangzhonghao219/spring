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
        一个子类接口，从容器中get到一个bean对象，这个bean对象是spring创建好的，也就是说对象的控制权交到了spring手中，从而减轻了开发人员的工作量，这个就叫做反转，表示对对象控制权的转移









































        
