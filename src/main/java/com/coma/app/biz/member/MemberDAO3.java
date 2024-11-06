package com.coma.app.biz.member;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAO3 {

    @Autowired
    private SqlSessionTemplate mybatis;


    public boolean insert(MemberDTO memberDTO) {
        //회원가입 MEMBER_ID ,MEMBER_NAME, MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION
        int result = mybatis.insert("MemberDAO.insert", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean updateAll(MemberDTO memberDTO) {
        //회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
        int result = mybatis.update("MemberDAO.updateAll", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean updatePassword(MemberDTO memberDTO) {
        //회원정보 업데이트 MEMBER_PASSWORD, MEMBER_PROFILE, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
        int result = mybatis.update("MemberDAO.updatePassword", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean updateWithoutProfile(MemberDTO memberDTO) {
        //회원정보 업데이트 (profile X) MEMBER_PASSWORD, MEMBER_PHONE, MEMBER_LOCATION, MEMBER_ID
        int result =  mybatis.update("MemberDAO.updateWithoutProfile", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean updateCrew(MemberDTO memberDTO) {
        //크루가입 (크루가입시 가입날짜입력때문에 분리) MEMBER_ID
        int result = mybatis.update("MemberDAO.updateCrew", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean updateAdmin(MemberDTO memberDTO) {
        //관리자 권한 변경 MEMBER_NAME = ?,
        //                  MEMBER_CURRENT_POINT = ?,
        //                  MEMBER_LOCATION = ?,
        //                  MEMBER_CREW_NUM = ?,
        //                  MEMBER_PHONE = ?,
        //                  MEMBER_ROLE = ?
        int result = mybatis.update("MemberDAO.updateAdmin", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean updateCurrentPoint(MemberDTO memberDTO) {
        //사용자 포인트 업데이트 MEMBER_CURRENT_POINT, MEMBER_ID
        int result = mybatis.update("MemberDAO.updateCurrentPoint", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public boolean delete(MemberDTO memberDTO) {
        //회원탈퇴 MEMBER_ID
        int result =  mybatis.delete("MemberDAO.delete", memberDTO);
        if(result <= 0) {
            return false;
        }
        return true;
    }

    public MemberDTO selectOneSearchId(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneSearchId", memberDTO);

    }

    public MemberDTO selectOneSearchIdPassword(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneSearchIdPassword", memberDTO);

    }

    public MemberDTO selectOneSearchMyCrew(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneSearchMyCrew", memberDTO);

    }

    public MemberDTO selectOneCountAdmin(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneCountAdmin", memberDTO);

    }

    public MemberDTO selectOneSearchCountAdmin(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneSearchCountAdmin", memberDTO);

    }

    public MemberDTO selectOneSearchIdCountAdmin(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneSearchIdCountAdmin", memberDTO);

    }

    public MemberDTO selectOneSearchDateCountAdmin(MemberDTO memberDTO) {

        return mybatis.selectOne("MemberDAO.selectOneSearchDateCountAdmin", memberDTO);

    }

    public List<MemberDTO> selectAllSearchRank(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllSearchRank", memberDTO);

    }

    public List<MemberDTO> selectAllCrewRank(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllCrewRank", memberDTO);

    }

    public List<MemberDTO> selectAllNew(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllNew", memberDTO);

    }

    public List<MemberDTO> selectAllTop10CrewRank(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllTop10CrewRank", memberDTO);

    }

    public List<MemberDTO> selectAllTop10Rank(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllTop10Rank", memberDTO);

    }


    public List<MemberDTO> selectAllSearchCrew(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllSearchCrew", memberDTO);

    }

    public List<MemberDTO> selectAllSearchCrewMemberName(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllSearchCrewMemberName", memberDTO);

    }

    public List<MemberDTO> selectAllMonthCountAdmin(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllMonthCountAdmin", memberDTO);

    }

    public List<MemberDTO> selectAllSearchAdmin(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllSearchAdmin", memberDTO);

    }

    public List<MemberDTO> selectAllSearchIdAdmin(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllSearchIdAdmin", memberDTO);

    }

    public List<MemberDTO> selectAllSearchDateAdmin(MemberDTO memberDTO) {

        return mybatis.selectList("MemberDAO.selectAllSearchDateAdmin", memberDTO);

    }


}
