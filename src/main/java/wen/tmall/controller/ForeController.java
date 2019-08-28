package wen.tmall.controller;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wen.tmall.pojo.*;
import wen.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;


    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    @RequestMapping("foreregister")
    public String register(Model model, User user) {

        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if (exist) {
            String m = "用户名已经被使用,不能使用";
            model.addAttribute("msg", m);
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    @RequestMapping("forelogin")
    public String login(
            @RequestParam("name") String name,
            @RequestParam("password") String password,
            Model model, HttpSession session) {

        /* 把账号通过HtmlUtils.htmlEscape进行转义 */
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (user == null) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }

        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    @RequestMapping("foreproduct")
    public String product(int pid, Model model) {

        /*  根据pid获取Product 对象p*/
        Product p = productService.get(pid);

        /* 获取这个产品对应的单个图片集合 */
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);

        /* 获取这个产品对应的详情图片集合 */
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);

        /* 获取产品的所有属性值 */
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        List<PropertyValue> pvs = propertyValueService.list(p.getId());

        /* 获取产品对应的所有的评价 */
        List<Review> reviews = reviewService.list(p.getId());

        /* 设置产品的销量和评价数量 */
        productService.setSaleAndReviewNumber(p);

        /* 把上述取值放在request属性 */
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);

        return "fore/product";
    }

    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user != null) {
            return "success";
        }
        return "fail";
    }

    /* 账号密码获取User对象
       如果User对象为空，返回fail
       不为空，将User对象放在session中，并返回"success" 字符串 */
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name,
                            @RequestParam("password") String password,
                            HttpSession session) {

        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            return "fail";
        }

        session.setAttribute("user", user);
        return "success";
    }

}
