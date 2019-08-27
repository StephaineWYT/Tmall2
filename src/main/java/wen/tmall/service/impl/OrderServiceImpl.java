package wen.tmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wen.tmall.mapper.OrderMapper;
import wen.tmall.pojo.Order;
import wen.tmall.pojo.OrderExample;
import wen.tmall.pojo.User;
import wen.tmall.service.OrderService;
import wen.tmall.service.UserService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Override
    public void add(Order c) {
        orderMapper.insert(c);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order c) {
        orderMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> result = orderMapper.selectByExample(example);

        setUser(result);
        return result;
    }

    /*为订单设置用户*/
    public void setUser(List<Order> os) {
        for (Order o : os)
            setUser(o);
    }

    public void setUser(Order o) {
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }

}