package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.JuanzengwuziDao;
import com.entity.JuanzengwuziEntity;
import com.service.JuanzengwuziService;
import com.entity.vo.JuanzengwuziVO;
import com.entity.view.JuanzengwuziView;

@Service("juanzengwuziService")
public class JuanzengwuziServiceImpl extends ServiceImpl<JuanzengwuziDao, JuanzengwuziEntity> implements JuanzengwuziService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JuanzengwuziEntity> page = this.selectPage(
                new Query<JuanzengwuziEntity>(params).getPage(),
                new EntityWrapper<JuanzengwuziEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JuanzengwuziEntity> wrapper) {
		  Page<JuanzengwuziView> page =new Query<JuanzengwuziView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<JuanzengwuziVO> selectListVO(Wrapper<JuanzengwuziEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JuanzengwuziVO selectVO(Wrapper<JuanzengwuziEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JuanzengwuziView> selectListView(Wrapper<JuanzengwuziEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JuanzengwuziView selectView(Wrapper<JuanzengwuziEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

    @Override
    public List<Map<String, Object>> selectValue(Map<String, Object> params, Wrapper<JuanzengwuziEntity> wrapper) {
        return baseMapper.selectValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectTimeStatValue(Map<String, Object> params, Wrapper<JuanzengwuziEntity> wrapper) {
        return baseMapper.selectTimeStatValue(params, wrapper);
    }

    @Override
    public List<Map<String, Object>> selectGroup(Map<String, Object> params, Wrapper<JuanzengwuziEntity> wrapper) {
        return baseMapper.selectGroup(params, wrapper);
    }




}
