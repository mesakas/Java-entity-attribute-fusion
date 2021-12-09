# Java-entity-attribute-fusion
Java 的同名字段赋值工具

一个非常简单的同名字段赋值工具，需要getter和setter


请注意，如果使用了mybatis-plus，那么你需要在application.yml中将mybatis-plus的驼峰自动转换的功能关闭：
``` yml
# 关闭自动将下划线命名方式转换成驼峰命名方式的功能
mybatis-plus:
  configuration:
    map-underscore-to-camel-case: false
```



### DAO
``` java


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId(type = IdType.AUTO)
    Integer id;

    @ApiModelProperty("用户等级分数")
    Integer grade;
    @ApiModelProperty("用户名")
    String username;
    @ApiModelProperty("密码")
    String password;
    @ApiModelProperty("昵称")
    String nickname;
    @ApiModelProperty("邮箱")
    String email;
    @ApiModelProperty("手机号")
    String phone;
    @ApiModelProperty("年龄")
    Integer age;

    Date create_time;
    Date update_time;

    @ApiModelProperty("状态")
    Integer state;

    @ApiModelProperty("账户权限")
    Integer jurisdiction;

}



```



### VO

``` java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    public UserVO(User user) {
        // 自动将同名字段赋值
        AttributeFusion.intersectionAssign(this, user);
    }

    @TableId(type = IdType.AUTO)
    Integer id;

    @ApiModelProperty("用户等级分数")
    Integer grade;
    @ApiModelProperty("用户名")
    String username;
    @ApiModelProperty("昵称")
    String nickname;


}
```
