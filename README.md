# gt3-server-java-servlet-sdk

## 示例部署环境
条目|说明
----|----
操作系统|ubuntu 16.04.6 lts
jdk版本|openjdk version 1.8.0_252 64-bit
tomcat版本|apache-tomcat-8.5.55
maven版本|apache maven 3.6.3

## 部署流程

### 下载sdk demo
```
git clone https://github.com/GeeTeam/gt3-server-java-servlet-sdk.git
```

### 配置密钥，修改请求参数
> 配置密钥

从[极验管理后台](https://auth.geetest.com/login/)获取公钥（id）和私钥（key）, 并在代码中配置。配置文件的相对路径如下：
```
src/main/java/com/geetest/demo/GeetestConfig.java
```

> 修改请求参数（可选）

名称|说明
----|------
user_id|客户端用户的唯一标识，作用于提供进阶数据分析服务，可在register和validate接口传入，不传入也不影响验证服务的使用；若担心用户信息风险，可作预处理(如哈希处理)再提供到极验
client_type|客户端类型，web：电脑上的浏览器；h5：手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生sdk植入app应用的方式；unknown：未知
ip_address|客户端请求sdk服务器的ip地址

### 关键文件说明
名称|说明|相对路径
----|----|----
FirstRegisterServlet.java|处理验证初始化接口请求|src/main/java/com/geetest/demo/
SecondValidateServlet.java|处理二次验证接口请求|src/main/java/com/geetest/demo/
GeetestConfig.java|配置id和key|src/main/java/com/geetest/demo/
GeetestLib.java|核心sdk，处理各种业务|src/main/java/com/geetest/sdk/
GeetestLibResult.java|核心sdk返回数据的包装对象|src/main/java/com/geetest/sdk/
web.xml|server配置，主要路由配置|src/main/webapp/WEB-INF/
index.html|demo示例首页|src/main/webapp/
pom.xml|maven依赖管理配置文件|

### 运行demo
tomcat部署启动项目，在浏览器中访问`http://localhost:8080`即可看到demo界面。

## 发布日志

### tag：20200701
- 统一各语言sdk标准
- 版本：jave-servlet:3.1.0

