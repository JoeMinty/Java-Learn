# `Spring`框架使用了哪些设计模式


- `BeanFactory`和`ApplicationContext`应用了**工厂模式**。

- 在 `Bean` 的创建中，`Spring` 也为不同 `scope` 定义的对象，提供了**单例和原型等模式**实现。

- `AOP` 领域则是使用了**代理模式、装饰器模式、适配器模式**等。

- 各种事件监听器，是**观察者模式**的典型应用。

- 类似 `JdbcTemplate` 等则是应用了**模板模式**。
