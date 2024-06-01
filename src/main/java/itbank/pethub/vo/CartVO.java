package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartVO {
    private int id, order_id, order_item, order_price, count, origin_price, discount_price, coupon_check;
}
