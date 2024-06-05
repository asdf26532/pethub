package itbank.pethub.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class CouponVO {

    private int member_id, coupon_id, discount, minimum_price, discount_limit;
    private String member_name, user_id, nickname, email, coupon_code;
    private Date coupon_reg_date, coupon_end_date;
}