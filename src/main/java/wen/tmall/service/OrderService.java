package wen.tmall.service;

import wen.tmall.pojo.Order;
import wen.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    /* 订单属性 */
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order c);

    void delete(int id);

    void update(Order c);

    Order get(int id);

    List list();

    float add(Order o, List<OrderItem> ois);

    List list(int uid, String excludedStatus);
}
