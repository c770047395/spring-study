## IOC的本质
###分析实现
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

### 本质
控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法，也有人认为DI只是IoC的另一种说法。没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。

IoC是Spring框架的核心内容，使用多种方式完美的实现了IoC，可以使用XML配置，也可以使用注解，新版本的Spring也可以零配置实现IoC。

Spring容器在初始化时先读取配置文件，根据配置文件或元数据创建与组织对象存入容器中，程序使用时再从Ioc容器中取出需要的对象。


采用XML方式配置Bean的时候，Bean的定义信息是和实现分离的，而采用注解的方式可以把两者合为一体，Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在Spring中实现控制反转的是IoC容器，其实现方法是依赖注入（Dependency Injection,DI）
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
