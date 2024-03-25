package com.indinner.wallserver.service.impl;

import cn.hutool.json.JSONObject;
import com.indinner.wallserver.entity.AdminUser;
import com.indinner.wallserver.entity.CreatePreviewImg;
import com.indinner.wallserver.entity.School;
import com.indinner.wallserver.entity.User;
import com.indinner.wallserver.pojo.AuditRequest;
import com.indinner.wallserver.pojo.CreateImgRequest;
import com.indinner.wallserver.pojo.SelectPreviewImgRequest;
import com.indinner.wallserver.service.WallService;
import com.indinner.wallserver.utils.CreateImgByWallUtils;
import com.indinner.wallserver.utils.MyUtil;
import com.indinner.wallserver.utils.Result;
import com.indinner.wallserver.utils.WechatMessageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author indinner
 * @Date 2023/5/31 16:53
 * @Version 1.0
 * @Doc:
 */
@Log4j2
@Service
public class WallServiceImpl implements WallService {

    @Resource
    CreateImgByWallUtils createImgByWallUtils;

    @Resource
    MongoTemplate mongoTemplate;

    /**
     * 生成预览图
     * @return
     */
    @Override
    public Result<String> createImg(CreateImgRequest createimgRequest) throws Exception {
        log.info("入参:{}",createimgRequest.toString());

        //查询对应学校信息
        School school = mongoTemplate.findOne(new Query(Criteria.where("schoolCode").is(createimgRequest.getSchoolCode())), School.class);
        CreatePreviewImg createPreviewImg=new CreatePreviewImg();
        createPreviewImg.setHeadImg(school.getHeadImg());
        createPreviewImg.setQrCode(school.getQrCode());
        BeanUtils.copyProperties(createimgRequest,createPreviewImg);
        String url= createImgByWallUtils.createImg(createPreviewImg);
        log.info("生成的url如下:{}",url);
        return Result.success(url);
    }

    /**
     * 发布稿件给管理员审核
     * @param createImgRequest
     * @return
     */
    @Override
    public Result<String> toMessage(CreateImgRequest createImgRequest) {
        CreatePreviewImg createPreviewImg=new CreatePreviewImg();
        BeanUtils.copyProperties(createImgRequest,createPreviewImg);
        mongoTemplate.save(createPreviewImg);
        School school = mongoTemplate.findOne(new Query(Criteria.where("schoolCode").is(createImgRequest.getSchoolCode())), School.class);
        Query query=new Query(Criteria.where("schoolCode").is(school.getSchoolCode()).and("status").is(0));
        long count = mongoTemplate.count(query, CreatePreviewImg.class);
        String msg="当前("+school.getSchoolName()+")有"+count+"条稿件!";
        WechatMessageUtils.barkPush(school.getBarkID(),msg,school.getHeadImg());
        return Result.success(school.getSuccessMsg());
    }

    /**
     * 根据稿件状态查询某学校的稿件
     * @param selectPreviewImgRequest
     * @return
     */
    @Override
    public Result<List<CreatePreviewImg>> selectPreviewImg(SelectPreviewImgRequest selectPreviewImgRequest) {
        List<AdminUser> users = mongoTemplate.find(new Query(Criteria.where("openid").is(selectPreviewImgRequest.getOpenid())), AdminUser.class);
        if(users.size()==0&&selectPreviewImgRequest.getStatus()!=1){
            return Result.error(604,"无权限");
        }
        if(selectPreviewImgRequest.getStatus()!=1&&!users.get(0).getSchoolCodes().contains(selectPreviewImgRequest.getSchoolCode())){
            return Result.error(604,"无权限");
        }


        Query query=new Query(Criteria.where("schoolCode").is(selectPreviewImgRequest.getSchoolCode())
                .and("status").is(selectPreviewImgRequest.getStatus()));
        if(null!=selectPreviewImgRequest.getPageNum()&&null!=selectPreviewImgRequest.getPageSize()){
            Integer skip=(selectPreviewImgRequest.getPageNum()-1)*selectPreviewImgRequest.getPageSize();
            query.skip(skip);
            query.limit(selectPreviewImgRequest.getPageSize());
        }
        List<CreatePreviewImg> createPreviewImgs = mongoTemplate.find(query, CreatePreviewImg.class);
        return Result.success(createPreviewImgs);
    }

    /**
     * 查询学校
     * @param adminID
     * @return
     */
    @Override
    public Result<List<School>> selectSchool(String adminID) {
        Query query=new Query(Criteria.where("QID").is(adminID));
        AdminUser adminUser = mongoTemplate.findOne(query, AdminUser.class);
        query=new Query(Criteria.where("adminID").is(adminUser.getOpenid()));
        List<School> schools = mongoTemplate.find(query, School.class);
        return Result.success(schools);
    }

    /**
     * 审核并下发通知信息
     *
     * @param auditRequest
     * @return
     */
    @Override
    public Result<String> audit(AuditRequest auditRequest) {
        System.out.println(auditRequest.toString());
        JSONObject data=initMessage(auditRequest.getStatus());
        Query query=new Query(Criteria.where("_id").is(auditRequest.getPreviewID()));
        Update update = new Update();
        if(auditRequest.getStatus()==1){
            //审核通过
            update=Update.update("status",1);
        }else if(auditRequest.getStatus()==-1){
            //审核不通过
            update=Update.update("status",-1);
        }
        mongoTemplate.updateFirst(query,update,CreatePreviewImg.class);
        WechatMessageUtils.toMessage(auditRequest.getOpenid(),auditRequest.getFormId(),"8a171cf28540a461ba8f05cb5ffd4b98",data);
        return Result.success("success");
    }

    /**
     * 判断是否是管理员
     *
     * @param openid
     * @return
     */
    @Override
    public Result<Boolean> isAdmin(String openid) {
        Query query=new Query(Criteria.where("openid").is(openid));
        List<AdminUser> users = mongoTemplate.find(query, AdminUser.class);
        if(users.size()>0){
            return Result.success(true);
        }else {
            return Result.success(false);
        }
    }

    /**
     * 添加用户，将用户openid和QID绑定
     *
     * @param openid
     * @param QID
     * @return
     */
    @Override
    public Result<String> addUser(String openid, String QID) {
        Query query=new Query(Criteria.where("openid").is(openid));
        List<User> users = mongoTemplate.find(query, User.class);
        if(users.size()>0){
            mongoTemplate.remove(query,User.class);
        }
        User user=new User();
        user.setQID(QID);
        user.setOpenid(openid);
        mongoTemplate.save(user);
        return Result.success("绑定成功!");
    }

    /**
     * 查询QID
     *
     * @param openid
     * @return
     */
    @Override
    public Result<String> selectQID(String openid) {
        Query query=new Query(Criteria.where("openid").is(openid));
        User user = mongoTemplate.findOne(query, User.class);
        if(null==user){
            return Result.error(201,"当前用户未绑定QID");
        }else {
            return Result.success(user.getQID());
        }
    }

    /**
     * 查询管理员openid绑定的QID
     *
     * @param openid
     * @return
     */
    @Override
    public Result<String> selectAdminQID(String openid) {
        Query query=new Query(Criteria.where("openid").is(openid));
        AdminUser adminUser = mongoTemplate.findOne(query, AdminUser.class);
        if(null==adminUser){
            return Result.error(404,"无权限");
        }else {
            return Result.success(adminUser.getQID());
        }
    }

    /**
     * 根据状态构建下发的用户消息
     * @param status
     * @return
     */
    private JSONObject initMessage(Integer status){
        String phrase1 = "";
        String time2= MyUtil.getDate();
        String thing4 = "";
        if(status==1){
            phrase1="审核通过";
            thing4="投稿审核通过";
        }else if(status==-1){
            phrase1="审核不通过";
            thing4="投稿被驳回,请修改内容后重新发布";
        }
        JSONObject json = new JSONObject();
        json.set("keyword1", new JSONObject().set("value", phrase1));
        json.set("keyword2", new JSONObject().set("value", "投稿"));
        json.set("keyword3", new JSONObject().set("value", time2));
        json.set("keyword4", new JSONObject().set("value", thing4));
        json.set("keyword5", new JSONObject().set("value", ""));
        return json;
    }
}
