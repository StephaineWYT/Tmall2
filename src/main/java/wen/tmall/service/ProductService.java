package wen.tmall.service;

import wen.tmall.pojo.Category;
import wen.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product p);

    void delete(int id);

    void update(Product p);

    Product get(int id);

    List list(int cid);

    void setFirstProductImage(Product p);

    /* 为多个分类填充产品集合 */
    void fill(List<Category> cs);

    /* 为分类填充产品集合 */
    void fill(Category c);

    /* 每个分类下的产品集合，8个为一行，拆成多行 */
    void fillByRow(List<Category> cs);

    /* 为产品设置销量和评价数量 */
    void setSaleAndReviewNumber(Product p);

    void setSaleAndReviewNumber(List<Product> ps);
}
