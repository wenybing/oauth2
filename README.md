oauth2的授权模式
一、密码授权模式 密码式（password）
1、先启动redis、再启动springboot项目，发一个post请求访问地址如下：
http://localhost:8080/oauth/token?username=zhangsan&password=123&grant_type=password&client_id=client_1&scope=all&client_secret=123
请求地址中包含的参数有用户名、密码、授权模式、客户端 id scope 及客户端密码，基本
就是授权服务器中所配的数据，请求结果下：
{
    "access_token": "d2c1067e-f212-4dd8-be66-a91ec65c610f",
    "token_type": "bearer",
    "refresh_token": "bc736c87-8ea3-447a-89dc-00fc40c17f93",
    "expires_in": 1799,
    "scope": "all"
}

2、返回结果有 access_token、 token_type、 refresh_token、expires_in以及scope ，其中 access_token 
是获取其他资源时要用的令牌， refresh_token用来刷新令牌， expires_in 表示 access_token 的过期时间，
当 access_token 过期后，使用 refresh_token 重新获取新的 access_token （前提是 refresh_token未过期)，
请求地址如下（注意这里也是 POST 请求)：
http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=bc736c87-8ea3-447a-89dc-00fc40c17f93&client_id=password&client_secret=123

3、获取新的 access_token 需要携带上 refresh_token ，同时授权模式设置为 refresh_token ，在获
取的结果中 access_token 会变化，同时 access_token 有效期也会变化，如下所示。
{
    "access_token": "44717294-b1bc-4a69-a40f-be8ac9a6147e",
    "token_type": "bearer",
    "refresh_token": "bc736c87-8ea3-447a-89dc-00fc40c17f93",
    "expires_in": 1793,
    "scope": "all"
}

4、接下来访问所有资源携带上 access_token 参数即可， 例如“ user/hello “接口：
http://localhost:8080/user/hello?access_token=44717294-b1bc-4a69-a40f-be8ac9a6147e
结果如图1所示

5、如果非法访问资源，例zhangsan用户访“/admin/hello ”接口， 结果如图2所示。

6、最后再来看一下redis中的数据见如图3


到此， password 模式得OAuth2 认证体系就搭建成功了



三、客户端授权模式 客户端模式（Client Credentials Grant）

client模式：http://localhost:8080/oauth/token?client_id=client_3&scope=all&grant_type=client_credentials&client_secret=123

响应结果：
{
    "access_token": "c8ea4041-92c2-42cb-84c1-5372d4b15768",
    "token_type": "bearer",
    "expires_in": 1799,
    "scope": "all"
}

