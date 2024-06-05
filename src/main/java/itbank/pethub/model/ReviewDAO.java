package itbank.pethub.model;

import itbank.pethub.vo.ReviewVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewDAO {

    // 아이템별 리뷰 가져오기
    @Select("select * from review_view where item_id = #{id} order by id desc")
    List<ReviewVO> getReviews(int id);

    // 리뷰 작성
    @Insert("insert into review (item_id, member_id, contents, rating) values (#{item_id}, #{member_id}, #{contents}, #{rating})")
    int addReview(ReviewVO input);

    // 리뷰 삭제
    @Delete("delete from review where id = #{id}")
    int deleteReview(int id);

    // 리뷰 수정
    @Update("update review set contents = #{contents} where id = #{id}")
    int updateReview(ReviewVO input);

    @Select("select id from review_view where id = #{id} ")
    ReviewVO selectReview(int id);

}
