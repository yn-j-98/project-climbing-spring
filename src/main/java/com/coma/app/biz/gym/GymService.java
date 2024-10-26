package com.coma.app.biz.gym;

import java.util.List;


public interface GymService {
	List<GymDTO> selectAll(GymDTO gymDTO);
	List<GymDTO> selectAllLocationCountAdmin(GymDTO gymDTO);
	List<GymDTO> selectAllAdmin(GymDTO gymDTO);

	GymDTO selectOne(GymDTO gymDTO);
	GymDTO selectOneCount(GymDTO gymDTO);

	boolean insert(GymDTO gymDTO);
	boolean insertAdmin(GymDTO gymDTO);

	boolean update(GymDTO gymDTO);

	boolean delete(GymDTO gymDTO);
}
