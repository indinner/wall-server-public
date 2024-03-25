package com.indinner.wallserver.service;

import com.indinner.wallserver.entity.CreatePreviewImg;
import com.indinner.wallserver.entity.School;
import com.indinner.wallserver.pojo.AuditRequest;
import com.indinner.wallserver.pojo.CreateImgRequest;
import com.indinner.wallserver.pojo.SelectPreviewImgRequest;
import com.indinner.wallserver.utils.Result;

import java.util.List;

/**
 * @Author indinner
 * @Date 2023/5/31 16:53
 * @Version 1.0
 * @Doc:
 */
public interface WallService {

    /**
     * 生成预览图
     * @return
     */
    Result<String> createImg(CreateImgRequest createimgRequest) throws Exception;

    /**
     * 发布稿件给管理员审核
     * @param createImgRequest
     * @return
     */
    Result<String> toMessage(CreateImgRequest createImgRequest);


    /**
     * 根据稿件状态查询某学校的稿件
     * @return
     */
    Result<List<CreatePreviewImg>> selectPreviewImg(SelectPreviewImgRequest selectPreviewImgRequest);

    /**
     * 查询学校
     * @param adminID
     * @return
     */
    Result<List<School>> selectSchool(String adminID);


    /**
     * 审核并下发通知信息
     * @param auditRequest
     * @return
     */
    Result<String> audit(AuditRequest auditRequest);

    /**
     * 判断是否是管理员
     * @param openid
     * @return
     */
    Result<Boolean> isAdmin(String openid);

    /**
     * 添加用户，将用户openid和QID绑定
     * @param openid
     * @param QID
     * @return
     */
    Result<String> addUser(String openid,String QID);

    /**
     * 查询QID
     * @param openid
     * @return
     */
    Result<String> selectQID(String openid);

    /**
     * 查询管理员openid绑定的QID
     * @param openid
     * @return
     */
    Result<String> selectAdminQID(String openid);

}
