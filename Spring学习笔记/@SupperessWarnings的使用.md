# `@SuppressWarnings`的使用



​	在开发中，有时候`IDE/`编译器会给出一些没有必要的告警。可以通过`@SuppressWarnings`来屏蔽。`@SuppressWarnings`有很多种面向场景，下面一一列出：

​	而`@SuppressWarnings("")`里面的字符串""有多种选择，以下是常见的：

| name                     | using                                                        |
| ------------------------ | ------------------------------------------------------------ |
| all                      | to suppress all warnings （将方法块里面所有的warning都取消） |
| cast                     | to suppress warnings relative to cast operations             |
| dep-ann                  | to suppress warnings relative to deprecated annotation （取消对已弃用的注释的警告） |
| deprecation              | to suppress warnings relative to deprecation（  使用了不赞成使用的类或方法时的警告） |
| fallthrough              | to suppress warnings relative to missing breaks in switch statements（当 Switch 程序块直接通往下一种情况而没有 Break 时的警告。） |
| finally                  | to suppress warnings relative to finally block that don’t return（任何 finally 子句不能正常完成时的警告） |
| hiding                   | to suppress warnings relative to locals that hide variable（取消对隐藏变量的警告） |
| incomplete-switch        | to suppress warnings relative to missing entries in a switch statement (enum case) （取消对switch里面缺少case条目的警告） |
| null                     | to suppress warnings relative to null analysis（取消对null分析的警告） |
| nls                      | to suppress warnings relative to non-nls string literals （取消对 non-nls字符串的警告） |
| path                     | 在类路径、源文件路径等中有不存在的路径时的警告。             |
| rawtypes                 | to suppress warnings relative to un-specific types when using generics on class params （当在类参数中使用非特定的泛型时，取消警告） |
| restriction              | to suppress warnings relative to usage of discouraged or forbidden references （取消使用不鼓励或禁止的引用的警告） |
| serial                   | to suppress warnings relative to missing serialVersionUID field for a serializable class（当在可序列化的类上缺少 serialVersionUID 定义时的警告。） |
| static-access            | to suppress warnings relative to incorrect static access（取消不正常的静态访问的警告） |
| synthetic-access         | to suppress warnings relative to unoptimized access from inner classes |
| unchecked                | to suppress warnings relative to unchecked operations（执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型。） |
| unqualified-field-access | to suppress warnings relative to field access unqualified    |
| unused                   | to suppress warnings relative to unused code （将未使用的方法的warning取消） |
| WeakerAccess             | 禁止“Access can be private”的警告                            |

