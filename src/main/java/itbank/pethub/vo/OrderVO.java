package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.Date;

@Setter
@Getter
public class OrderVO {
    private int id, member_id, delivery_id, order_status;
    private Date order_date;

    public OrderVO () {};
}