package com.coma.app.biz.reply;

public class ReplyDTO {
	private int reply_num;                //댓글 ID
	private String reply_content;         //댓글 내용
	private String reply_writer_id;       //댓글 작성자
	private int reply_board_num;          //댓글이 속한 게시판 ID
	
	private String reply_condition;       //개발자 데이터

	public int getReply_num() {
		return reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public String getReply_writer_id() {
		return reply_writer_id;
	}

	public void setReply_writer_id(String reply_writer_id) {
		this.reply_writer_id = reply_writer_id;
	}

	public int getReply_board_num() {
		return reply_board_num;
	}

	public void setReply_board_num(int reply_board_num) {
		this.reply_board_num = reply_board_num;
	}

	public String getReply_condition() {
		return reply_condition;
	}

	public void setReply_condition(String reply_condition) {
		this.reply_condition = reply_condition;
	}

	@Override
	public String toString() {
		return "ReplyDTO{" +
				"reply_num=" + reply_num +
				", reply_content='" + reply_content + '\'' +
				", reply_writer_id='" + reply_writer_id + '\'' +
				", reply_board_num=" + reply_board_num +
				", reply_condition='" + reply_condition + '\'' +
				'}';
	}
}