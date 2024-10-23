package com.coma.app.biz.gym;

import java.util.List;

public interface GymService {
	List<GymDTO> selectAll(GymDTO gymDTO);
	GymDTO selectOne(GymDTO gymDTO);
	boolean insert(GymDTO gymDTO);
	boolean update(GymDTO gymDTO);
	boolean delete(GymDTO gymDTO);
}
