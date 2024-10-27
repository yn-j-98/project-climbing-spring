package com.coma.app.biz.reply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReplyDTO {
	private int reply_num;                //댓글 ID
	private String reply_content;         //댓글 내용
	private String reply_writer_id;       //댓글 작성자
	private int reply_board_num;          //댓글이 속한 게시판 ID
}