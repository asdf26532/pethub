package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberVO {
    private int id;
    private String userid, userpw, address;
}