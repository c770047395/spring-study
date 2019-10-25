## IOC的本质
### 分析实现
1. 先写一个UserDao接口
```java
public interface UserDao {
     public void getUser();
 }
```
2. 再去写Dao的实现类
```java
public class UserDaoImpl implements UserDao {
     @Override
     public void getUser() {
         System.out.println("获取用户数据");
     }
 }
```
3. 然后去屑UserService的接口
```java
public class UserService{
    void getUser();
}
```
4. 最后写Service的实现类
```java
public class UserServiceImpl implements UserService {
     private UserDao userDao = new UserDaoImpl();
 ​
     @Override
     public void getUser() {
         userDao.getUser();
     }
 }
```
5. 这个时候，如果要修改UserDao的实现，例如更换数据库源，需要在Service实现中修改Dao
```java
public class UserServiceImpl implements UserService {
     private UserDao userDao = new UserDaoXXXImpl();
 ​
     @Override
     public void getUser() {
         userDao.getUser();
     }
 }
```
6. 如果我们不去实现UserDao，而是将他留出一个接口：
```java
public class UserServiceImpl implements UserService {
     private UserDao userDao;
 ​
     public void setUserDao(UserDao userDao){
        this.userDao = userDao;
     }    

     @Override
     public void getUser() {
         userDao.getUser();
     }
 }
```
7. 测试一下
```java
@Test
 public void test(){
     UserServiceImpl service = new UserServiceImpl();
     service.setUserDao( new UserDaoMySqlImpl() );
     service.getUser();
     //那我们现在又想用Oracle去实现呢
     service.setUserDao( new UserDaoOracleImpl() );
     service.getUser();
 }
```
8. 此时，我们如果再添加一个UserDao的实现类，想使用新的实现类的时候就不必再去修改UserService里的代码了，可以由service的调用者自行选择创建的对象，大大降低了耦合性，这就是IOC的原型





### 本质
控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法，也有人认为DI只是IoC的另一种说法。没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。

![](https://img2018.cnblogs.com/blog/1418974/201907/1418974-20190726112837054-601677158.png)

IoC是Spring框架的核心内容，使用多种方式完美的实现了IoC，可以使用XML配置，也可以使用注解，新版本的Spring也可以零配置实现IoC。

Spring容器在初始化时先读取配置文件，根据配置文件或元数据创建与组织对象存入容器中，程序使用时再从Ioc容器中取出需要的对象。

![](https://img2018.cnblogs.com/blog/1418974/201907/1418974-20190726112855074-669998796.png)

采用XML方式配置Bean的时候，Bean的定义信息是和实现分离的，而采用注解的方式可以把两者合为一体，Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）

## 常用依赖
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
</dependency>
```

## Spring配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">

<!--    注解支持-->
    <context:annotation-config/>


</beans>
```
## @Autowired和@Resource
- 相同点：都是自动装配
- 不同点：@Autowired是优先byType装配，@Resource是优先byName装配
- @Autowired:如果不唯一，可以加上@Qualifier(value="xxx")注解指定beanid
- @Resource:如果不唯一，可以通过@Resource(name="xxx")指定beanid

## 注解

注解的引入
```java
@Component
public class User {
    @Value("cp1")
    public String name;
}
```

### @Component
component（组件）等价于```<bean id="user" class="com.cp.pojo.User">```

@Component还有以下衍生注解，按照MVC三层架构分层：
- dao[@Repository]
- service[@Servcie]
- controller[@Controller]

这四个注解的功能相同，都是将某个类注册到容器中


### @Value
等价于```<property name="name" value="cp11">```

### @Scope
@Scope('xxxx')，bean的作用范围


## 小结

### xml与注解
- xml更加万能，适用于任何场合，维护方便简单。
- 注解不是自己的类使用不了，维护相对复杂
### xml与注解最佳实践
- xml用来管理bean
- 注解只负责完成属性的注入
- 我们在使用的过程中，只需要注意一个问题，开启注解的支持
```xml
<!--    指定要扫描的包，这个包下的注解就生效-->
<context:component-scan base-package="com.cp"/>
<!--    注解支持-->
<context:annotation-config/>
```

## 使用Java的方式配置Spring
我们现在完全不适用spring的xml配置，全权交给java来做
javaConfig是spring的一个子项目，在spring4之后，他成为了一个核心功能

实体类
```java
@Component
public class User {
    @Value("cp")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```
配置文件
```java
@Configuration
@ComponentScan("com.cp.pojo")//显式扫描包
@Import(MyConfig2.class)
public class MyConfig {

    //注册一个bean,相当于我们之前写的一个bean标签
    //这个方法的名字相当于bean的id
    //返回值相当于bean中的class属性
    @Bean
    public User getUser(){
        return new User();
    }
}

```
测试类
```java
public class MyTest {
    public static void main(String[] args) {
        //如果完全使用配置类方式去做，我们只能通过AnnocationConfig上下文来获取容器，通过配置类的class对象加载
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        User user = context.getBean("getUser",User.class);
        System.out.println(user.getName());
    }
}

```
这种存Java的配置方式，在SpringBoot中随处可见

## 代理模式
为什么要学习代理模式？因为SpringAOP的底层是代理模式
代理模式的分类：
- 静态代理
- 动态代理

### 静态代理
角色分析：
- 抽象角色：一般使用接口或者抽象类
- 真实角色：被代理的角色
- 代理角色：代理真实角色，代理真实角色后，一般会做一些附属操作
- 客户：访问代理对象的人

代码步骤：
1. 接口
```java
//租房
public interface Rent {
    public void rent();
}

```
2. 真实角色
```java
//房东
public class Host implements Rent {
    public void rent() {
        System.out.println("房东要出租房子");
    }
}

```
3. 代理角色
```java
public class Proxy implements Rent{
    private Host host;

    public Proxy() {
    }

    public Proxy(Host host) {
        this.host = host;
    }

    public void rent() {
        seeHouse();
        host.rent();
        hetong();
        fare();
    }

    public void seeHouse(){
        System.out.println("中介带你看房");
    }

    public void fare(){
        System.out.println("收中介费");
    }
    public void hetong(){
        System.out.println("签合同");
    }
}

```
4.客户访问代理角色
```java
public class Client {
    public static void main(String[] args) {
        //房东要租房子
        Host host = new Host();
        //代理，中介帮房东租房子，但是代理有一些附属操作
        Proxy proxy = new Proxy(host);
        //不用面对房东，直接找中介租房即可
        proxy.rent();
    }
}

```
代理模式的好处：
- 可以使真是角色的操作更加纯粹，不用去关注一些公共业务
- 公共业务交给代理角色，实现业务的分工
- 公共业务发生拓展的时候，方便集中管理

缺点：
- 一个真是角色就会产生给一个代理角色；代码量会翻倍，开发效率变低


### 动态代理
- 动态代理和静态代理角色一样
- 动态代理的代理类是动态生成的，不是我们直接写好的
- 动态代理分为两大类：基于接口的动态代理，基于类的动态代理
   - 基于接口：JDK 动态代理
   - 基于类：cglib
   - java字节码实现： javassist
 
需要了解两个类：Proxy（代理），InvocationHandler（调用处理程序）

Proxy类中的newProxyInstance方法用来获得代理对象，需要传入类加载器、接口数组以及处理器
InvacationHandler即是处理器接口，重写invoke方法创建一个处理器类，原理是通过反射实现动态加载。

万能动态代理类
```java
//自动生成代理类
public class ProxyInvocationHandler implements InvocationHandler {
    //处理代理实例，并返回结果

    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //动态代理的本质就是使用反射机制实现
        log(method.getName());
        Object result = method.invoke(target,args);

        return result;
    }

    public void log(String msg){
        System.out.println("执行了"+msg+"方法");
    }


}
```

动态代理的好处：
- 可以使真是角色的操作更加纯粹，不用去关注一些公共业务
- 公共业务交给代理角色，实现业务的分工
- 公共业务发生拓展的时候，方便集中管理
- 一个动态代理类代理的是一个接口，一般是对应一个业务
- 一个动态代理类可以代理多个类，只要实现同一个接口即可

