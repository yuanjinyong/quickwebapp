package com.quickwebapp.sys.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickwebapp.core.entity.MapEntity;
import com.quickwebapp.core.exception.BusinessException;
import com.quickwebapp.core.mapper.BaseMapper;
import com.quickwebapp.core.service.BaseService;
import com.quickwebapp.core.utils.HelpUtil;
import com.quickwebapp.sys.entity.UrlEntity;
import com.quickwebapp.sys.mapper.UrlMapper;

@Service
@Transactional
public class UrlService extends BaseService<String, UrlEntity> {
    @Autowired
    private UrlMapper urlMapper;

    @Override
    protected BaseMapper<String, UrlEntity> getMapper() {
        return urlMapper;
    }

    @Override
    public void insertEntity(UrlEntity entity) {
        if (HelpUtil.isEmptyString(entity.getF_methods())) {
            entity.setF_methods("[]");
        }
        entity.setF_id(DigestUtils.md5Hex(entity.getF_url() + entity.getF_methods()));
        super.insertEntity(entity);
    }

    @Override
    public void updateEntity(UrlEntity entity) {
        if (entity.getF_auto() == 1) {
            throw new BusinessException("扫描RequestMapping自动生成的URL记录，不能修改！");
        }

        super.updateEntity(entity);
    }

    public void updateAutoEntities(List<UrlEntity> entityList) {
        MapEntity mapEntity = new MapEntity();
        mapEntity.put("f_auto", true);
        urlMapper.deleteEntities(mapEntity);
        urlMapper.insertEntities(entityList);
    }

    @Override
    public void deleteEntity(String id) {
        UrlEntity entity = super.selectEntity(id);
        if (entity.getF_auto() == 1) {
            throw new BusinessException("扫描RequestMapping自动生成的URL记录，不能删除！");
        }

        super.deleteEntity(id);
    }
}
