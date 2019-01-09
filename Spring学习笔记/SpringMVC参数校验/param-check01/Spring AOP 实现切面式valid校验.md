# `Spring AOP` 实现"切面式"`valid`校验



>  **Why:**
>  为什么要用`aop`实现校验？
>
>  **`Answer`:**
>  `Spring mvc` 默认自带的校验机制`@Valid + BindingResult`,
>  但这种默认实现都得在`Controller`方法的中去接收`BindingResult`，从而进行校验.
>
>  `eg:`
>
> ```
> if (result.hasErrors()) {
>   List<ObjectError> allErrors = result.getAllErrors();
>   List<String> errorlists = new ArrayList<>();
>     for (ObjectError objectError : allErrors) {
>         errorlists.add(objectError.getDefaultMessage());
>     }
>  }
> ```
>
> 获取`errorlists`。这样实现的话，每个需要校验的方法都得重复调用，即使封装也是。



可能上面那么说还不能表明`Spring` 的`@Valid + BindingResult`实现，我先举个“栗子”。

## 1. 栗子（旧版本）

### 1.1 接口层（`IDAL`）

`eg`: 简单的`POST`请求，`@RequestBody`接收请求数据，`@Valid + BindingResult`进行校验

- `httpMethid`: `POST`
- `parameters`：`@RequestBody`接收请求数据
- `valid`：`@Valid +BindingResult`

```
 @ResponseBody
 @PostMapping("body")
 public ResponseVO bodyPost(@RequestBody @Valid TestVO body,BindingResult result){
   //校验到错误
   if (result.hasErrors()) {
      List<ObjectError> allErrors = result.getAllErrors();
      List<String> lists = new ArrayList<>();
      for (ObjectError objectError : allErrors) {
          lists.add(objectError.getDefaultMessage());
      }
      return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", lists);
  }
     return new ResponseVO(HttpStatus.OK.value(), "bodyPost", null);
}
```



### 1.2 实体（`vo`）校验内容

`@Valid + BindingResult`的校验注解一大堆，网上一摸就有的！

```
public class TestVO {
    @Getter
    @Setter
    @Min(value = 0,message = "请求参数isString不能小于0")
    private Integer isInt;
    @Getter
    @Setter
    @NotBlank(message = "请求参数isString不能为空")
    private String isString;
}
```



### 1.3 测试结果



**参数正确的输出：**

```
{
    "returnCode": 200,
    "returnMessage": "bodyPost"
}
```

**参数错误的输出：**

```
{
    "returnCode": 400,
    "returnMessage": "parameter empty",
    "errorMessages": [
        "请求参数isString不能小于0",
        "请求参数isString不能为空"
    ]
}
```



## 2. `Aop`校验（升级版）

​	可以看到若是多个像`bodyPost`一样都需要对`body`进行校验的话，那么有一坨代码就必须不断复现，即使改为父类可复用方法，也得去调用。所以左思右想还是觉得不优雅。所以有了`Aop`进行切面校验。



### 2.1 接口层（`IDAL`）

是的！你没看错，上面那一坨代码没了，也不需要调用父类的的共用方法。就单单一个注解就完事了：`@ParamValid`

```
@ParamValid
@ResponseBody
@PostMapping("body")
public ResponseVO bodyPost(@RequestBody @Valid TestVO body,BindingResult result){
    return new ResponseVO(200,"bodyPost", null);
}
```



### 2.2 自定义注解（`annotation`）

这个注解也是简简单单的用于方法的注解。

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamValid {}
```



### 2.3 重点！切面实现（`Aspect`）

切面详解：

- `@Before`: 使用注解方式`@annotation(XX)`,凡是使用到所需切的注解（`@ParamValid`），都会调用该方法,这块一定要注意其中`@annotation(XX)`的`XX`必须要跟

  `public void paramValid(JoinPoint point, ParamValid XX) {...}`里面的参数一致

- `JoinPoint`： 通过`JoinPoint`获取方法的参数，以此获取`BindingResult`所校验到的内容

- 迁移校验封装: 将原先那一坨校验迁移到`Aspect`中：`validRequestParams`

- 响应校验结果： 

  - 通过`RequestContextHolder`获取`response`
  - 获取响应`OutputStream`
  - 将`BindingResult`封装响应

```
@Aspect
@Component
public class ParamValidAspect {

    private static final Logger log = LoggerFactory.getLogger(ParamValidAspect.class);

    @Before("@annotation(paramValid)")
    public void paramValid(JoinPoint point, ParamValid paramValid) {
        Object[] paramObj = point.getArgs();
        if (paramObj.length > 0) {
            if (paramObj[1] instanceof BindingResult) {
                BindingResult result = (BindingResult) paramObj[1];
                ResponseVO errorMap = this.validRequestParams(result);
                if (errorMap != null) {
                    ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    HttpServletResponse response = res.getResponse();
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.BAD_REQUEST.value());

                    OutputStream output = null;
                    try {
                        output = response.getOutputStream();
                        //errorMap.setCode(null);
                        String error = new Gson().toJson(errorMap);
                        log.info("aop 检测到参数不规范" + error);
                        output.write(error.getBytes("UTF-8"));
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    } finally {
                        try {
                            if (output != null) {
                                output.close();
                            }
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 校验
     */
    private ResponseVO validRequestParams(BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            List<String> lists = new ArrayList<>();
            for (ObjectError objectError : allErrors) {
                lists.add(objectError.getDefaultMessage());
            }
            return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "parameter empty", lists);
        }
        return null;
    }
}
```

### 2.4 测试结果

**参数正确的输出：**

```
{
    "returnCode": 200,
    "returnMessage": "bodyPost"
}
```



**参数错误的输出：**

```
{
    "returnCode": 400,
    "returnMessage": "parameter empty",
    "errorMessages": [
        "请求参数isString不能为空",
        "请求参数isString不能小于0"
    ]
}
```



看了上面两种结果，就可以对比出使用`Spring AOP` 配合`@Valid + BindingResult`进行校验的优点:

- 去除代码冗余
- AOP异步处理
- 优化代码实现

