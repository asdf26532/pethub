package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemVO {
    private int id, type, category, price, total_rating;
    private String name;
}
