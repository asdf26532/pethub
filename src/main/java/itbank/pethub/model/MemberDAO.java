package itbank.pethub.model;

import itbank.pethub.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberDAO {
    @Select("select * from Member where userid = #{userid} and userpw=#{userpw}")
    MemberVO selectOne(MemberVO input);
}
