package com.coma.app.biz.member;

import com.coma.app.biz.board.BoardDTO;

import java.util.List;

public interface MemberService {
	List<MemberDTO> selectAllSearchRank(MemberDTO memberDTO);
	List<MemberDTO> selectAllCrewRank(MemberDTO memberDTO);
	List<MemberDTO> selectAllNew(MemberDTO memberDTO);
	List<MemberDTO> selectAllTop10CrewRank(MemberDTO memberDTO);
	List<MemberDTO> selectAllTop10Rank(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchCrew(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchCrewMemberName(MemberDTO memberDTO);

	MemberDTO selectOneSearchId(MemberDTO memberDTO);
	MemberDTO selectOneSearchIdPassword(MemberDTO memberDTO);
	MemberDTO selectOneSearchMyCrew(MemberDTO memberDTO);

	boolean insert(MemberDTO memberDTO);

	boolean updateAll(MemberDTO memberDTO);
	boolean updateWithoutProfile(MemberDTO memberDTO);
	boolean updateCrew(MemberDTO memberDTO);
	boolean updateAdmin(MemberDTO memberDTO);
	boolean updateCurrentPoint(MemberDTO memberDTO);

	boolean delete(MemberDTO memberDTO);
}
