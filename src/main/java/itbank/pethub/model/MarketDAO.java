package itbank.pethub.model;

import itbank.pethub.vo.ItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface MarketDAO {
    @Select("select * from Item order by id desc")
    List<ItemVO> selectAll();

    @Select("select * from Item where id = #{id}")
    ItemVO selectOne(int id);
}