package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.ItemVO;
import itbank.pethub.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService os;

    @GetMapping("/cart")
    public ModelAndView cart(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        MemberVO user = (MemberVO) session.getAttribute("user");
        int memberId = user.getId();
        mav.addObject("list", os.getCarts(memberId));

        mav.setViewName("/order/cart");
        return mav;
    }
    // 상품 리스트 불러오기
    @GetMapping("/Items")
    public ModelAndView market() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("product", os.selectAll());
        mav.setViewName("/order/Items");
        return mav;
    }

    @GetMapping("/orderUpdate/{id}")
    public ModelAndView updatePage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", os.selectCart(id));
        mav.setViewName("/order/orderUpdate");
        return mav;
    }

    @GetMapping("/DetailPage/{id}")
    public ModelAndView detailPage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", os.selectOne(id));
        mav.setViewName("/order/DetailPage");
        return mav;
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
            cartVO.setOrder_id(existingOrderId);
            cartVO.setCount(quantity);
            os.countUp(cartVO);
        } else {
            os.makedelivery_status();
            os.makeOrder_status();

            //포스트 번호 - 어케 할지 몰라 일단 300으로 고정시켜둠
            int port=300;
            int delivery_status_id=os.getdelivery_status_id();
            String add=user.getAddress();

            os.makeDelivery(add, port, delivery_status_id);

            int delivery_id=os.getdelivery_id();
            int order_status=os.getOrder_status();

            os.makeOrder(memberId, delivery_id, order_status);

            ItemVO iv=os.getItem(productId);

            int orderid = os.getorderid();
            os.makeCart(orderid, productId, iv.getPrice(), quantity, iv.getPrice());
        }

        // 주문이 성공적으로 추가되거나 업데이트된 후 주문 페이지로 리다이렉트
        mav.setViewName("/order/cart");

        return mav;
    }

    // 정보 삭제
    @GetMapping("/delete/{order_id}")
    public ModelAndView delete(@PathVariable("order_id") int order_id) {
        ModelAndView mav = new ModelAndView();

        int os_id=os.getOrder_status_id(order_id);
        int d_id=os.getDeli_id(os_id);
        int ds_id=os.getDeli_st_id(d_id);

        os.deleteCart(order_id);
        os.deleteOrder(order_id);
        os.deleteOrderStatus(os_id);
        os.deleteDelivery(d_id);

        int row =os.deleteDeliveryStatus(ds_id);


        String msg = "삭제 되었습니다. ";
        if (row != 0)
            msg = "삭제 실패하였습니다.";

        mav.addObject("path", "/order/cart");
        mav.addObject("msg", msg);

        mav.setViewName("/order/Message");

        return mav;
    }

}

