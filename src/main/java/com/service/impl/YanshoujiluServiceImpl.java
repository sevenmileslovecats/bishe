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


import com.dao.YanshoujiluDao;
import com.entity.YanshoujiluEntity;
import com.service.YanshoujiluService;
import com.entity.vo.YanshoujiluVO;
import com.entity.view.YanshoujiluView;

@Service("yanshoujiluService")
public class YanshoujiluServiceImpl extends ServiceImpl<YanshoujiluDao, YanshoujiluEntity> implements YanshoujiluService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<YanshoujiluEntity> page = this.selectPage(
                new Query<YanshoujiluEntity>(params).getPage(),
                new EntityWrapper<YanshoujiluEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<YanshoujiluEntity> wrapper) {
		  Page<YanshoujiluView> page =new Query<YanshoujiluView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
 	}

    
    @Override
	public List<YanshoujiluVO> selectListVO(Wrapper<YanshoujiluEntity> wrapper) {
 		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public YanshoujiluVO selectVO(Wrapper<YanshoujiluEntity> wrapper) {
 		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<YanshoujiluView> selectListView(Wrapper<YanshoujiluEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public YanshoujiluView selectView(Wrapper<YanshoujiluEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
