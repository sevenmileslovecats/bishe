package com.entity.vo;

import com.entity.ChukufenboEntity;
import com.entity.JieshouxinxiEntity;
import com.entity.ShiyongfankuiEntity;
import com.entity.WuzishenlingEntity;
import com.entity.YiyifankuiEntity;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ZhuishuchaxunFenZhiVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private WuzishenlingEntity<?> wuzishenling;
	private List<ChukufenboEntity<?>> chukufenboList;
	private List<JieshouxinxiEntity<?>> jieshouxinxiList;
	private List<ShiyongfankuiEntity<?>> shiyongfankuiList;
	private List<YiyifankuiEntity<?>> yiyifankuiList;
	private List<String> warnings;
}

