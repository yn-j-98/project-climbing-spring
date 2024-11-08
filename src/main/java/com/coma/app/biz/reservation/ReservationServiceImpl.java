package com.coma.app.biz.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reservationService")
public class ReservationServiceImpl implements ReservationService{

	@Autowired
	ReservationDAO reservationDAO;

	@Override
	public List<ReservationDTO> selectAll(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectAll(reservationDTO);
	}

	@Override
	public List<ReservationDTO> selectAllCountMonthAdmin(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectAllCountMonthAdmin(reservationDTO);
	}

	@Override
	public List<ReservationDTO> selectAllAdmin(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectAllAdmin(reservationDTO);
	}
	@Override
	public List<ReservationDTO> selectAllAdminSearchGymName(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectAllAdminSearchGymName(reservationDTO);
	}
	@Override
	public List<ReservationDTO> selectAllAdminSearchMemberId(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectAllAdminSearchMemberId(reservationDTO);
	}

	@Override
	public ReservationDTO selectOne(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOne(reservationDTO);
	}

	@Override
	public ReservationDTO selectOneCount(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOneCount(reservationDTO);
	}

	@Override
	public ReservationDTO selectOneReservation(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOneReservation(reservationDTO);
	}

	@Override
	public ReservationDTO selectOneCountYearAdmin(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOneCountYearAdmin(reservationDTO);
	}

	@Override
	public ReservationDTO selectOneCountAdmin(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOneCountAdmin(reservationDTO);
	}

	@Override
	public ReservationDTO selectOneCountSearchGymNameAdmin(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOneCountSearchGymNameAdmin(reservationDTO);
	}

	@Override
	public ReservationDTO selectOneCountSearchMemberIdAdmin(ReservationDTO reservationDTO) {
		return this.reservationDAO.selectOneCountSearchMemberIdAdmin(reservationDTO);
	}

	@Override
	public boolean insert(ReservationDTO reservationDTO) {
		return this.reservationDAO.insert(reservationDTO);
	}

	@Override
	public boolean update(ReservationDTO reservationDTO) {
		return this.reservationDAO.update(reservationDTO);
	}

	@Override
	public boolean delete(ReservationDTO reservationDTO) {
		return this.reservationDAO.delete(reservationDTO);
	}

	

}
