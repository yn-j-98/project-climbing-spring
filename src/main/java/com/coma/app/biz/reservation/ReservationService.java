package com.coma.app.biz.reservation;

import java.util.List;

public interface ReservationService {
	List<ReservationDTO> selectAll(ReservationDTO reservationDTO);
	ReservationDTO selectOne(ReservationDTO reservationDTO);
	boolean insert(ReservationDTO reservationDTO);
	boolean update(ReservationDTO reservationDTO);
	boolean delete(ReservationDTO reservationDTO);
}
