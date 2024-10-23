package com.coma.app.biz.reply;

public class ReplyDTO {
	private int model_reply_num;                //댓글 ID
	private String model_reply_content;         //댓글 내용
	private String model_reply_writer_id;       //댓글 작성자
	private int model_reply_board_num;          //댓글이 속한 게시판 ID
	
	private String model_reply_condition;       //개발자 데이터
	
	public int getModel_reply_num() {
		return model_reply_num;
	}
	public void setModel_reply_num(int model_reply_num) {
		this.model_reply_num = model_reply_num;
	}
	public String getModel_reply_content() {
		return model_reply_content;
	}
	public void setModel_reply_content(String model_reply_content) {
		this.model_reply_content = model_reply_content;
	}
	public String getModel_reply_writer_id() {
		return model_reply_writer_id;
	}
	public void setModel_reply_writer_id(String model_reply_writer_id) {
		this.model_reply_writer_id = model_reply_writer_id;
	}
	public int getModel_reply_board_num() {
		return model_reply_board_num;
	}
	public void setModel_reply_board_num(int model_reply_board_num) {
		this.model_reply_board_num = model_reply_board_num;
	}
	public String getModel_reply_condition() {
		return model_reply_condition;
	}
	public void setModel_reply_condition(String model_reply_condition) {
		this.model_reply_condition = model_reply_condition;
	}
	@Override
	public String toString() {
		return "ReplyDTO [model_reply_num=" + model_reply_num + ", model_reply_content=" + model_reply_content
				+ ", model_reply_writer_id=" + model_reply_writer_id + ", model_reply_board_num="
				+ model_reply_board_num + ", model_reply_condition=" + model_reply_condition + "]";
	}

	
}