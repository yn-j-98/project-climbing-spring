package com.coma.app.biz.gym;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GymService")
public class GymServiceImpl implements GymService {
	
	@Autowired
	private GymDAO gymDAO;
	//(페이지 네이션) 암벽장 전체출력
	@Override
	public List<GymDTO> selectAll(GymDTO gymDTO) {
		return this.gymDAO.selectAll(gymDTO);
	}

	@Override
	public List<GymDTO> selectAllLocationCountAdmin(GymDTO gymDTO) {
		return this.gymDAO.selectAllLocationCountAdmin(gymDTO);
	}
	@Override
	public List<GymDTO> selectAllAdminVerified(GymDTO gymDTO) {
		return this.gymDAO.selectAllAdminVerified(gymDTO);
	}

	@Override
	public List<GymDTO> selectAllAdmin(GymDTO gymDTO) {
		return this.gymDAO.selectAllAdmin(gymDTO);
	}
	//암벽장 PK로 검색 GYM_NUM
	@Override
	public GymDTO selectOne(GymDTO gymDTO) {
		return this.gymDAO.selectOne(gymDTO);
	}
	//암벽장 총 개수 // TODO 관리자 메인 페이지
	@Override
	public GymDTO selectOneCount(GymDTO gymDTO) {
		return this.gymDAO.selectOneCount(gymDTO);
	}
	//암벽장 등록 GYM_NAME, GYM_PROFILE, GYM_DESCRIPTION, GYM_LOCATION
	@Override
	public boolean insert(GymDTO gymDTO) {
		return this.gymDAO.insert(gymDTO);
	}

	@Override
	public boolean insertAdmin(GymDTO gymDTO) {
		return this.gymDAO.insertAdmin(gymDTO);
	}
	//예약가능 개수 업데이트 GYM_RESERVATION_CNT, GYM_NUM
	@Override
	public boolean update(GymDTO gymDTO) {
		return this.gymDAO.update(gymDTO);
	}

	@Override
	public boolean updateAdminBattleVerified(GymDTO gymDTO) {
		if(gymDTO.getGym_admin_battle_verified().equals("T")) {
			return false;
		}
		return this.gymDAO.updateAdminBattleVerified(gymDTO);
	}

	@Override
	public boolean delete(GymDTO gymDTO) {
		return this.gymDAO.delete(gymDTO);
	}
}
