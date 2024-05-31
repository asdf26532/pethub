package itbank.pethub.controller;


import itbank.pethub.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mall/shop")
public class ShopController {

    @Autowired
    private MarketService ms;

    // 상품 리스트 불러오기
    @GetMapping("/items")
    public ModelAndView market() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", ms.selectAll());
        mav.setViewName("/mall/shop/items");
        return mav;
    }
    // 상세페이지 불러오기
    @GetMapping("/DetailPage/{id}")
    public ModelAndView detailPage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("product", ms.selectOne(id));
        mav.setViewName("/mall/shop/DetailPage");

        return mav;
    }

}
