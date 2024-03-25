
## 搭建
* 主要关注local.prop配置文件，里面需要配置小程序appid,小程序secret，cos存储桶需要的secretId，secretKey，regional，bucketName
* utils/cosB文件夹里的cosclient也需要配置secretId，secretKey
* utils/cosB/putfile文件的upfile方法返回值前缀改成你自己的cos配置的cdn地址（或者使用默认源站地址）
* 其他一些小方法需要填一写存储🪣参数的你跑起来测试的时候知道怎么写


首先创建学校
```java
void addSchool(){
        School school=new School();
        school.setSchoolCode(10002);
        school.setSchoolName("学校名");
        school.setAdminID("管理员openid");
        school.setHeadImg("头像地址");
        school.setSuccessMsg("投稿发布成功,墙墙审核通过后会发布到空间!届时会有消息提醒~");
        mongoTemplate.save(school);
    }
```
学校要设置一个学校代码,名字,管理员openid,头像,投稿后的提示消息

然后创建管理员用户
```java
void addAdminUser(){
        AdminUser adminUser=new AdminUser();
        adminUser.setOpenid("oYTvu5GKKNqfx8cnfbphK-wVrzFY");
        List<Integer> list=new ArrayList<>();
        list.add(1001);
        adminUser.setQID("QID1001");
        adminUser.setSchoolCodes(list);
        mongoTemplate.save(adminUser);
    }
```
管理员openid,管理员管理的校区的代码列表,管理员管理的QID

系统逻辑是利用QID将用户和学校绑定起来,用户每次打开后,根据openid查找到用户绑定的QID,
然后根据QID查询到当前QID所属的管理员的openid,然后根据openid,查询到对应的学校校区列表

如果修改管理员,则首先应修改原管理员用户的openid为新管理员用户的openid,然后修改校区列表绑定的adminID
