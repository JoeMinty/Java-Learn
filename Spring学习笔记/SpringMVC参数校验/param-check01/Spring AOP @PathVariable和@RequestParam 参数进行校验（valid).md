# `Spring AOP` `@PathVariable`和`@RequestParam`参数进行校验`(valid）`

> 在**`Spring AOP` 实现切面式`valid`校验**文章中，通过`AOP`对`@RequestBody`注解进行的参数进行校验
>  那么对于 `@PathVariable`和`@RequestParam` 却没有对应的`Spring mvc` 默认自带的校验机制 `@Valid + BindingResult`。那么此时该校验的话，只能代码上逐一进行校验。

先说明一下`@PathVariable`和`@RequestParam`；两个注解的用法。

## 1.原版本讲解

### 1.1 `@PathVariable`

#### 1.1.1 `RESTful`风格

格式：`path/1/moons`
 `eg:`

```
@ResponseBody
@GetMapping("path/{isInt}/{isString}")
public ResponseVO pathGet(@PathVariable Integer isInt,
                          @PathVariable String isString) {
   log.info("int:" + isInt);
   log.info("String:" + isString);
  JSONObject resultJson = new JSONObject();
  resultJson.put("isInt", isInt);
  resultJson.put("isString", isString);
  return new ResponseVO(HttpStatus.OK.value(), "pathGet", resultJson);
}
```

**`Request`：**
http://localhost:8080/path/3/moons

#### 1.1.2 校验

代码式校验

```
if(isInt < 2){
  return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "pathGet", "isInt must be more than 2");
}
```

代码式校验的话，个人觉得就有点儿不必要，除非是一些特殊需求

### 1.2 `@RequestParam`

#### 1.2.1 表单提交（`query`）

格式：`query?isInt=2&isString=moons`

```
@ResponseBody
@GetMapping("query")
public ResponseVO queryGet(@RequestParam Integer isInt,
                           @RequestParam String isString) {
    log.info("int:" + isInt);
    log.info("String:" + isString);
    JSONObject resultJson = new JSONObject();
    resultJson.put("isInt", isInt);
    resultJson.put("isString", isString);
    return new ResponseVO(HttpStatus.OK.value(), "queryGet", resultJson);
}
```

#### 1.2.2 校验

同样也需要代码式校验

```
if(isInt < 2){
  return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "queryGet", "isInt must be more than 2");
}
```

因为`@Valid + BindingResult`只能用于`@ResponseBody`这种类型注解。

