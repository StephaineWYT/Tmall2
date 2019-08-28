package wen.tmall.service;

import wen.tmall.pojo.Order;
import wen.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    void add(OrderItem c);

    void delete(int id);

    void update(OrderItem c);

    OrderItem get(int id);

    List list();

    void fill(List<Order> os);

    /* 订单填充上orderItems信息 */
    void fill(Order o);

    /* 获取销售量 */
    int getSaleCount(int pid);
}
