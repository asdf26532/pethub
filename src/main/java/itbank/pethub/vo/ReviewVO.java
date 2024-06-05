package itbank.pethub.vo;

import lombok.Data;

@Data
public class ReviewVO {
    private int id, item_id, member_id, rating;
    private String contents;

    private String nick, item;

}
