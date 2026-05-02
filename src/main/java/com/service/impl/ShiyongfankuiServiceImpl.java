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


import com.dao.ShiyongfankuiDao;
import com.entity.ShiyongfankuiEntity;
import com.service.ShiyongfankuiService;
import com.entity.vo.ShiyongfankuiVO;
import com.entity.view.ShiyongfankuiView;

@Service("shiyongfankuiService")
public class ShiyongfankuiServiceImpl extends ServiceImpl<ShiyongfankuiDao, ShiyongfankuiEntity> implements ShiyongfankuiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShiyongfankuiEntity> page = this.selectPage(
                new Query<ShiyongfankuiEntity>(params).getPage(),
                new EntityWrapper<ShiyongfankuiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ShiyongfankuiEntity> wrapper) {
		  Page<ShiyongfankuiView> page =new Query<ShiyongfankuiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ShiyongfankuiVO> selectListVO(Wrapper<ShiyongfankuiEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ShiyongfankuiVO selectVO(Wrapper<ShiyongfankuiEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ShiyongfankuiView> selectListView(Wrapper<ShiyongfankuiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ShiyongfankuiView selectView(Wrapper<ShiyongfankuiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
