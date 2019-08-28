package wen.tmall.controller;

import org.springframework.web.bind.annotation.RequestParam;
import wen.tmall.pojo.Category;
import wen.tmall.pojo.User;
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

}
