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


import com.dao.YiyifankuiDao;
import com.entity.YiyifankuiEntity;
import com.service.YiyifankuiService;
import com.entity.vo.YiyifankuiVO;
import com.entity.view.YiyifankuiView;

@Service("yiyifankuiService")
public class YiyifankuiServiceImpl extends ServiceImpl<YiyifankuiDao, YiyifankuiEntity> implements YiyifankuiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<YiyifankuiEntity> page = this.selectPage(
                new Query<YiyifankuiEntity>(params).getPage(),
                new EntityWrapper<YiyifankuiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<YiyifankuiEntity> wrapper) {
		  Page<YiyifankuiView> page =new Query<YiyifankuiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<YiyifankuiVO> selectListVO(Wrapper<YiyifankuiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public YiyifankuiVO selectVO(Wrapper<YiyifankuiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<YiyifankuiView> selectListView(Wrapper<YiyifankuiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public YiyifankuiView selectView(Wrapper<YiyifankuiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
