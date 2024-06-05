package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.service.ReviewService;
import itbank.pethub.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/shop")
public class ShopController {

    private final OrderService os;
    private final ReviewService rs;

    // 상품 리스트 불러오기
    @GetMapping("/Items")
    public ModelAndView market() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("product", os.selectAll());
        mav.setViewName("/shop/Items");
        return mav;
    }



    @GetMapping("/DetailPage/{id}")
    public ModelAndView detailPage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", os.selectOne(id));

        // 상품 리뷰 불러오기
        List<ReviewVO> review = rs.getReviews(id);
        mav.addObject("review", review);

        mav.setViewName("/shop/DetailPage");
        return mav;
    }

    // 리뷰 추가
    @PostMapping("/addReview")
    public ModelAndView addReview(ReviewVO input, HttpSession session) {
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        // 로그인 한 멤버 정보 + 아이디 + 주소 가져오기
        MemberVO user = (MemberVO) session.getAttribute("user");
        int member_id = user.getId();

        input.setMember_id(member_id);

        rs.addReview(input);

        mav.setViewName("redirect:/shop/DetailPage/" + input.getItem_id());

        return mav;
    }

    // 리뷰 삭제
    @PostMapping("/deleteReview/{id}")
    public ModelAndView deleteReview(@PathVariable int id, @RequestParam("item_id") int itemId) {
        ModelAndView mav = new ModelAndView();
        rs.deleteReview(id);
        mav.setViewName("redirect:/shop/DetailPage/" + itemId);

        return mav;
    }

    // 수정 버튼 클릭시
    @GetMapping("/editReview/{id}")
    public ModelAndView editReview(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        ReviewVO review = rs.getReview(id);
        mav.addObject("review", review);
        mav.setViewName("shop/editReview");
        return mav;
    }

    // 수정 완료 버튼 클릭시
    @PostMapping("/editReview/{id}")
    public String processEditReview(@PathVariable("id") int id, @RequestParam("contents") String contents,
                                    @RequestParam("rating") int rating) {
        ReviewVO input = new ReviewVO();
        input.setId(id);
        input.setContents(contents);
        input.setRating(rating);
        rs.updateReview(input);

        return "redirect:/shop/DetailPage/" + id;
    }



    // 상세페이지 정보를 받아 주문페이지로 이동
    @PostMapping("/DetailPage/{id}")
    public ModelAndView processOrder(@PathVariable("id") int productId, @RequestParam("quantity") int quantity,
                                     HttpSession session) {
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        // 로그인 한 멤버 정보 + 아이디 + 주소 가져오기
        MemberVO user = (MemberVO) session.getAttribute("user");
        int memberId = user.getId();


        // 주문이 이미 존재하는지 확인 - order 페이지에만 있는지 확인
        int existingOrderId = os.getExistingOrderId(memberId, productId);
        if (existingOrderId != -1) {
            // 주문이 이미 존재하면 수량을 업데이트
            CartVO cartVO = new CartVO();
            cartVO.setId(existingOrderId);
            cartVO.setCount(quantity);
            os.countUp(cartVO);

        } else {
            AddressVO av=os.getAddress(memberId);
            DeliveryVO dv=new DeliveryVO();

            String add=av.getPrimary_address();
            String add_detail=av.getAddress_details();

            String address=add+" "+add_detail;
            int post=av.getZip_code();

            dv.setAddress(address);
            dv.setPost(post);
            os.makeDelivery(dv);

            int delivery_id=os.getdelivery_id();

            OrderVO ov=new OrderVO();
            ov.setMember_id(memberId);
            ov.setDelivery_id(delivery_id);
            os.makeOrder(ov);

            ItemVO iv=os.getItem(productId);

            int orderid = os.getorderid();

            CartVO cv=new CartVO();
            cv.setOrder_id(orderid);
            cv.setOrder_item(productId);
            cv.setOrder_price(iv.getPrice());
            cv.setCount(quantity);
            cv.setOrigin_price(iv.getPrice());
            os.makeCart(cv);
        }

        // 주문이 성공적으로 추가되거나 업데이트된 후 주문 페이지로 리다이렉트
        mav.setViewName("redirect:/order/cart");

        return mav;
    }


}