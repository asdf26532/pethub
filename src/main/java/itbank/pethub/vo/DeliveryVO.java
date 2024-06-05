package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryVO {
    private int id, post, status_id;
    private String address;

    public DeliveryVO(){};
}