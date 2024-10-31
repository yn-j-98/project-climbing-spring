package com.coma.app.biz.reservation;

import java.util.List;

public interface ReservationService {
	List<ReservationDTO> selectAll(ReservationDTO reservationDTO);
	List<ReservationDTO> selectAllCountMonthAdmin(ReservationDTO reservationDTO);
	List<ReservationDTO> selectAllAdmin(ReservationDTO reservationDTO);
	List<ReservationDTO> selectAllAdminSearchGymName(ReservationDTO reservationDTO);
	List<ReservationDTO> selectAllAdminSearchMemberId(ReservationDTO reservationDTO);

	ReservationDTO selectOne(ReservationDTO reservationDTO);
	ReservationDTO selectOneCount(ReservationDTO reservationDTO);
	ReservationDTO selectOneReservation(ReservationDTO reservationDTO);
	ReservationDTO selectOneCountYearAdmin(ReservationDTO reservationDTO);
	ReservationDTO selectOneCountAdmin(ReservationDTO reservationDTO);
	ReservationDTO selectOneCountSearchGymNameAdmin(ReservationDTO reservationDTO);
	ReservationDTO selectOneCountSearchMemberIdAdmin(ReservationDTO reservationDTO);

	boolean insert(ReservationDTO reservationDTO);

	boolean update(ReservationDTO reservationDTO);

	boolean delete(ReservationDTO reservationDTO);
}
