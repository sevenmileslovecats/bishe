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


import com.dao.ChukufenboDao;
import com.entity.ChukufenboEntity;
import com.service.ChukufenboService;
import com.entity.vo.ChukufenboVO;
import com.entity.view.ChukufenboView;

@Service("chukufenboService")
public class ChukufenboServiceImpl extends ServiceImpl<ChukufenboDao, ChukufenboEntity> implements ChukufenboService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ChukufenboEntity> page = this.selectPage(
                new Query<ChukufenboEntity>(params).getPage(),
                new EntityWrapper<ChukufenboEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ChukufenboEntity> wrapper) {
		  Page<ChukufenboView> page =new Query<ChukufenboView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<ChukufenboVO> selectListVO(Wrapper<ChukufenboEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ChukufenboVO selectVO(Wrapper<ChukufenboEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ChukufenboView> selectListView(Wrapper<ChukufenboEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ChukufenboView selectView(Wrapper<ChukufenboEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
