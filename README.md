首先创建学校
```java
void addSchool(){
        School school=new School();
        school.setSchoolCode(10002);
        school.setSchoolName("锦绣校区");
        school.setAdminID("oYTvu5HqpO0jNN-Uw_mK5thbqlLM");
        school.setHeadImg("https://cdn.indinner.com/fig/headimg/WechatIMG62.jpeg");
        school.setSuccessMsg("投稿发布成功,墙墙审核通过后会发布到空间!届时会有消息提醒~\n 墙姐(滨湖):2411441 \n墙姐(锦绣):783490125 \n 墙姐(微信):hfnuwall");
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
