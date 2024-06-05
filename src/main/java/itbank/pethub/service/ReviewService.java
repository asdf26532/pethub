package itbank.pethub.service;

import itbank.pethub.model.ReviewDAO;
import itbank.pethub.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDAO rd;

    // 리뷰 목록
    public List<ReviewVO> getReviews(int id) {
        return rd.getReviews(id);
    }

    // 리뷰 추가
    public int addReview(ReviewVO input) {
        return rd.addReview(input);
    }

    // 리뷰 삭제
    public int deleteReview(int id) {
        return rd.deleteReview(id);
    }

    // 리뷰 수정
    public int updateReview(ReviewVO input) {
        return  rd.updateReview(input);
    }

    // 수정하기 위한 리뷰 가져오기
    public ReviewVO getReview(int id) {
        return rd.selectReview(id);
    }

}
