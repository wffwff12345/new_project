package com.news.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cl.common.dto.ResponseResult;
import com.news.wemedia.dto.WmMaterialDto;
import com.news.wemedia.entity.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 自媒体图文素材信息表 服务类
 * </p>
 *
 * @author mcm
 * @since 2022-03-17
 */
public interface IWmMaterialService extends IService<WmMaterial> {

    ResponseResult upload(MultipartFile file);

    ResponseResult listByCollection(WmMaterialDto dto);

    ResponseResult deleteById(Integer id);

    ResponseResult updateStatus(Integer id, int i);
}
