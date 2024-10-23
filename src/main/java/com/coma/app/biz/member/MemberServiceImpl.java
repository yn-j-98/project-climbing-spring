package com.coma.app.biz.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberDAO memberDAO;

	@Override
	public List<MemberDTO> selectAllSearchRank(MemberDTO memberDTO) {
		return this.memberDAO.selectAllSearchRank(memberDTO);
	}

	@Override
	public List<MemberDTO> selectAllCrewRank(MemberDTO memberDTO) {
		return this.memberDAO.selectAllCrewRank(memberDTO);
	}

	@Override
	public List<MemberDTO> selectAllNew(MemberDTO memberDTO) {
		return this.memberDAO.selectAllNew(memberDTO);
	}

	@Override
	public List<MemberDTO> selectAllTop10CrewRank(MemberDTO memberDTO) {
		return this.memberDAO.selectAllTop10CrewRank(memberDTO);
	}

	@Override
	public List<MemberDTO> selectAllTop10Rank(MemberDTO memberDTO) {
		return this.memberDAO.selectAllTop10Rank(memberDTO);
	}


	@Override
	public List<MemberDTO> selectAllSearchCrewMemberName(MemberDTO memberDTO) {
		return this.memberDAO.selectAllSearchCrewMemberName(memberDTO);
	}

	@Override
	public MemberDTO selectOneSearchId(MemberDTO memberDTO) {
		return this.memberDAO.selectOneSearchId(memberDTO);
	}

	@Override
	public MemberDTO selectOneSearchIdPassword(MemberDTO memberDTO) {
		return this.memberDAO.selectOneSearchIdPassword(memberDTO);
	}

	@Override
	public MemberDTO selectOneSearchMyCrew(MemberDTO memberDTO) {
		return this.memberDAO.selectOneSearchMyCrew(memberDTO);
	}

	@Override
	public boolean insert(MemberDTO memberDTO) {
		return this.memberDAO.insert(memberDTO);
	}

	@Override
	public boolean updateAll(MemberDTO memberDTO) {
		return this.memberDAO.updateAll(memberDTO);
	}

	@Override
	public boolean updateWithoutProfile(MemberDTO memberDTO) {
		return this.memberDAO.updateWithoutProfile(memberDTO);
	}

	@Override
	public boolean updateCrew(MemberDTO memberDTO) {
		return this.memberDAO.updateCrew(memberDTO);
	}

	@Override
	public boolean updateAdmin(MemberDTO memberDTO) {
		return this.memberDAO.updateAdmin(memberDTO);
	}

	@Override
	public boolean updateCurrentPoint(MemberDTO memberDTO) {
		return this.memberDAO.updateCurrentPoint(memberDTO);
	}

	@Override
	public boolean delete(MemberDTO memberDTO) {
		return this.memberDAO.delete(memberDTO);
	}
}
