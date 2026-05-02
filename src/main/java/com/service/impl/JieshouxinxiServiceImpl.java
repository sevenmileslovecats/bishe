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


import com.dao.JieshouxinxiDao;
import com.entity.JieshouxinxiEntity;
import com.service.JieshouxinxiService;
import com.entity.vo.JieshouxinxiVO;
import com.entity.view.JieshouxinxiView;

@Service("jieshouxinxiService")
public class JieshouxinxiServiceImpl extends ServiceImpl<JieshouxinxiDao, JieshouxinxiEntity> implements JieshouxinxiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<JieshouxinxiEntity> page = this.selectPage(
                new Query<JieshouxinxiEntity>(params).getPage(),
                new EntityWrapper<JieshouxinxiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<JieshouxinxiEntity> wrapper) {
		  Page<JieshouxinxiView> page =new Query<JieshouxinxiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<JieshouxinxiVO> selectListVO(Wrapper<JieshouxinxiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public JieshouxinxiVO selectVO(Wrapper<JieshouxinxiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<JieshouxinxiView> selectListView(Wrapper<JieshouxinxiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public JieshouxinxiView selectView(Wrapper<JieshouxinxiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
