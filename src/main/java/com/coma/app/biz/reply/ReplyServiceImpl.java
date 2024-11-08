package com.coma.app.biz.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	ReplyDAO replyDAO;
	
	@Override
	public List<ReplyDTO> selectAll(ReplyDTO replyDTO) {
		return this.replyDAO.selectAll(replyDTO);
	}

	@Override
	public ReplyDTO selectOne(ReplyDTO replyDTO) {
		return this.replyDAO.selectOne(replyDTO);
	}

	@Override
	public boolean insert(ReplyDTO replyDTO) {
		if (replyDTO.getReply_writer_id() == null || replyDTO.getReply_content() == null || replyDTO.getReply_content().trim().isEmpty()) {
			return false;
		}
		return this.replyDAO.insert(replyDTO);
	}


	@Override
	public boolean update(ReplyDTO replyDTO) {
		if (replyDTO.getReply_content() == null || replyDTO.getReply_content().trim().isEmpty()) {
			return false;
		}
		return this.replyDAO.update(replyDTO);
	}


	@Override
	public boolean delete(ReplyDTO replyDTO) {
		return this.replyDAO.delete(replyDTO);
	}
}
