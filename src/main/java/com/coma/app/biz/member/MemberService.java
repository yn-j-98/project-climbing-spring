package com.coma.app.biz.member;

import java.util.List;

public interface MemberService {
	List<MemberDTO> selectAllSearchRank(MemberDTO memberDTO);
	List<MemberDTO> selectAllCrewRank(MemberDTO memberDTO);
	List<MemberDTO> selectAllNew(MemberDTO memberDTO);
	List<MemberDTO> selectAllTop10CrewRank(MemberDTO memberDTO);
	List<MemberDTO> selectAllTop10Rank(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchCrew(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchCrewMemberName(MemberDTO memberDTO);
	List<MemberDTO> selectAllMonthCountAdmin(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchAdmin(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchIdAdmin(MemberDTO memberDTO);
	List<MemberDTO> selectAllSearchDateAdmin(MemberDTO memberDTO);

	MemberDTO selectOneSearchId(MemberDTO memberDTO);
	MemberDTO selectOneSearchIdPassword(MemberDTO memberDTO);
	MemberDTO selectOneSearchMyCrew(MemberDTO memberDTO);
	MemberDTO selectOneCountAdmin(MemberDTO memberDTO);
	MemberDTO selectOneSearchCountAdmin(MemberDTO memberDTO);
	MemberDTO selectOneSearchIdCountAdmin(MemberDTO memberDTO);
	MemberDTO selectOneSearchDateCountAdmin(MemberDTO memberDTO);

	boolean insert(MemberDTO memberDTO);

	boolean updateAll(MemberDTO memberDTO);
	boolean updateWithoutProfile(MemberDTO memberDTO);
	boolean updateCrew(MemberDTO memberDTO);
	boolean updateAdmin(MemberDTO memberDTO);
	boolean updateCurrentPoint(MemberDTO memberDTO);
	boolean updatePassword(MemberDTO memberDTO);

	boolean delete(MemberDTO memberDTO);
}
