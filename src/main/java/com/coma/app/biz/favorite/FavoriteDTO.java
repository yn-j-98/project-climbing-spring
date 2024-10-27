package com.coma.app.biz.favorite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FavoriteDTO {
	private int favorite_num; 	        // 좋아요 PK
	private String favorite_member_id;  // 좋아요 누른 사용자 FK
	private int favorite_gym_num;       // 좋아요 누른 암벽장 FK
}
