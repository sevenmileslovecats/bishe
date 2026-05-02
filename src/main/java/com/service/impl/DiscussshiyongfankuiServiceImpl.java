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


import com.dao.DiscussshiyongfankuiDao;
import com.entity.DiscussshiyongfankuiEntity;
import com.service.DiscussshiyongfankuiService;
import com.entity.vo.DiscussshiyongfankuiVO;
import com.entity.view.DiscussshiyongfankuiView;

@Service("discussshiyongfankuiService")
public class DiscussshiyongfankuiServiceImpl extends ServiceImpl<DiscussshiyongfankuiDao, DiscussshiyongfankuiEntity> implements DiscussshiyongfankuiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DiscussshiyongfankuiEntity> page = this.selectPage(
                new Query<DiscussshiyongfankuiEntity>(params).getPage(),
                new EntityWrapper<DiscussshiyongfankuiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DiscussshiyongfankuiEntity> wrapper) {
		  Page<DiscussshiyongfankuiView> page =new Query<DiscussshiyongfankuiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<DiscussshiyongfankuiVO> selectListVO(Wrapper<DiscussshiyongfankuiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DiscussshiyongfankuiVO selectVO(Wrapper<DiscussshiyongfankuiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DiscussshiyongfankuiView> selectListView(Wrapper<DiscussshiyongfankuiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DiscussshiyongfankuiView selectView(Wrapper<DiscussshiyongfankuiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
