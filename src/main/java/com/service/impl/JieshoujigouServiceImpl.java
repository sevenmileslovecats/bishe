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


import com.dao.JieshoujigouDao;
import com.entity.JieshoujigouEntity;
import com.service.JieshoujigouService;
import com.entity.vo.JieshoujigouVO;
import com.entity.view.JieshoujigouView;

@Service("jieshoujigouService")
public class JieshoujigouServiceImpl extends ServiceImpl<JieshoujigouDao, JieshoujigouEntity> implements JieshoujigouService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JieshoujigouEntity> page = this.selectPage(
                new Query<JieshoujigouEntity>(params).getPage(),
                new EntityWrapper<JieshoujigouEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JieshoujigouEntity> wrapper) {
		  Page<JieshoujigouView> page =new Query<JieshoujigouView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<JieshoujigouVO> selectListVO(Wrapper<JieshoujigouEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JieshoujigouVO selectVO(Wrapper<JieshoujigouEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JieshoujigouView> selectListView(Wrapper<JieshoujigouEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JieshoujigouView selectView(Wrapper<JieshoujigouEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
