package com.coma.app.biz.reply;

import java.util.List;

public interface ReplyService {
	List<ReplyDTO> selectAll(ReplyDTO replyDTO);

	ReplyDTO selectOne(ReplyDTO replyDTO);

	boolean insert(ReplyDTO replyDTO);

	boolean update(ReplyDTO replyDTO);

	boolean delete(ReplyDTO replyDTO);
}
