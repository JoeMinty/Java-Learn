# `web.xml`中配置`session`属性

## 为什么要在`web.xml`配置`JSP`属性

在许多情况下，都可以在`Java EE`中直接使用`HTTP`会话，不需要添加显示地配置。不过可以在部署描述符中配置它们，并且出于安全地目的也应该配置。在部署描述符中使用<session-config>标签配置会话。
样例

```
<session-config>
	<session-timeout>30</session-timeout>
	<cookie-config>
		<name>JSESSIONID</name>
		<domain>example.org</domain>
		<path>/shop</path>
		<comment>
			<!-- some information -->
		</comment>
		<http-only>true</http-only>
		<secure>false</secure>
		<max-age>1800</max-age>
	</cookie-config>
	<tracking-mode>COOKIE</tracking-mode>
	<tracking-mode>URL</tracking-mode>
	<tracking-mode>SSL</tracking-mode>
</session-config>
```


## 了解会话属性组

所有`<session-config>`和`<cookie>`中的标签都是可选的，但如果使用了这些标签，那么必须按照本例中的顺序添加到部署描述符中（除了被忽略的标签）。标签`<session-timeout>`指定了会话在无效之前，可以保持不活跃状态的时间，以分钟为单位。如果该值小于等于0，那么会话将永远也不过期。
`<tracking-mode>`用于表示容器应该使用哪种技术追踪会话`ID`，它的合法值有：
- `URL`----容器将只在`URL`中内嵌会话`ID` 。不使用`cookie`或`SSL`会话`ID`。这种方式非常不安全。
- `COOKIE` -----容器将使用会话`cookie`追踪会话`ID `。该技术非常安全。
- `SSL` ----容器将使用`SSL`会话`ID`作为`HTTP`会话ID。该方法是最安全的方式，但要求使用的所有请求都必须是`HTTPS`请求。

可以为`<tracking-mode>`配置多个值，表示容器可以使用多种策略。
只有在追踪模式中使用了`COOKIE`时，才可以使用`<cookie-config>`标签。

- 通过标签`<name>`可以自定义会话`cookie`的名字。默认值为`JSESSIONID`
- 标签`<domain>`和`<path>`对应着`cookie`的`Domain`和`Path`特性。`Web`容器已经设置了正确的默认值，因此通常不需要自定义它们。
- 标签`<comment>`将在会话`ID cookie`中添加`Comment`特性，在其中可以添加任意文本。这通常用于解释`cookie`的目的，并告诉用户网站的隐私策略。
- 标签`<http-only>`和`<secure>`对应着`cookie`的`HttpOnly`的`Secure`特性，它们的默认值都是假
- 最后一个标签`<max-age>`指定了`cookie`的`Max-Age`特性，用于控制`cookie`何时过期。默认情况下，`cookie`没有过期日期，这意味着它将在浏览器关闭时过期。可以自定义该值，单位为秒（`<session-timeout>`以分钟为单位）。 
