package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MODCVO {
    private int order_id, member_id, cart_id, order_item, order_price, count, origin_price, discount_price, coupoun_check, delivery_id, delivery_post;
    private String member_name, order_status, member_userid, member_nick, member_email, member_address,delivery_status, delivery_address, member_phone;
    private Date order_date;
}
