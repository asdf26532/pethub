package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressVO {
    private int id, member_id, zip_code;
    private String primary_address, address_details;

    public AddressVO(){};
}
