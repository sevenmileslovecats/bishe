package com.entity.vo;

import com.entity.JuanzengwuziEntity;
import com.entity.YanshoujiluEntity;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ZhuishuchaxunVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private JuanzengwuziEntity<?> juanzengwuzi;
	private List<YanshoujiluEntity<?>> yanshoujiluList;
	private List<ZhuishuchaxunFenZhiVO> fenZhiList;
	private List<String> warnings;
}

