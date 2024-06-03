package itbank.pethub.model;

import itbank.pethub.vo.CartVO;
import itbank.pethub.vo.ItemVO;
import itbank.pethub.vo.OrderVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDAO {
    @Insert("INSERT INTO Delivery_Status(name) Values(DEFAULT)")
    public String makedelivery_status();

    @Insert("INSERT INTO Order_Status(name) Values(DEFAULT)")
    String makeOrder_status();

    @Select("SELECT id FROM Delivery_Status ORDER BY id DESC FETCH FIRST 1 ROWS ONLY")
    int getdelivery_status_id();

    @Insert("Insert into Delivery(address, post, status_id) values (#{add}, #{port} #{deliveryId})")
    int makedelivery(String add, int port, int deliveryId);

    @Select("SELECT id from Delivery ORDER BY desc fetch first 1 rows only")
    int getdelivery_id();

    @Select("select id from Order_Status order by desc fetch first 1 rows only")
    int getOrder_status();

    @Insert("Insert Into Order(member_id, delivery_id, order_status) values (#{memberId}, #{deliveryId}, #{orderStatus})")
    OrderVO makeOrder(int memberId, int deliveryId, int orderStatus);

    @Select("select * from Item where id=#{productId}")
    ItemVO getItem(int productId);

    @Select("select id from Order order by desc fetch first 1 rows only")
    int getorderid();

    @Insert("insert into Cart (order_id, order_item, order_price, count, origin_price) values (#{orderid}, #{productId}, #{price}, #{quantity}, #{price1})")
    int makecart(int orderid, int productId, int price, int quantity, int price1);

    @Update("UPDATE Cart " +
            "SET count = count + #{count} " +
            "WHERE order_id = #{order_id} " +
            "AND order_id IN ( " +
            "        SELECT o.id " +
            "        FROM Order o " +
            "        WHERE o.order_status_id = ( " +
            "        SELECT os.id " +
            "        FROM order_status os " +
            "        WHERE os.name = '주문 접수' " +
            "))")
    int countup(CartVO cartVO);




    @Select("SELECT * FROM Cart WHERE order_id IN (SELECT id FROM `Order` WHERE member_id = #{memberId})")
    public List<CartVO> getCarts(int memberId);

    @Select("select * from Item order by id desc")
    List<ItemVO> selectAll();

    @Select("select * from Item where id = #{id}")
    ItemVO selectOne(int id);

    @Select("SELECT id FROM Cart WHERE order_id IN (SELECT id FROM `Order` WHERE member_id=#{memberId} and order_status in (select id from Order_Status where name ='주문 접수')) AND order_item=#{id}")
    @ResultType(Integer.class)
    Integer getExistingOrderId(@Param("memberId") int memberId, @Param("id") int id);

    @Select("select * from Cart where id=#{id}")
    CartVO selectCart(int id);

    @Delete("Delete From Cart where order_id=#{orderId}")
    int deleteCart(int orderId);

    @Select("select order_status from Order where id=#{orderId}")
    int getOrder_status_id(int orderId);

    @Select("select delivery_id from Order where order_status=#{osId}")
    int getDeli_id(int osId);

    @Select("select status_id from Delivery where id=#{dId}")
    int getDeli_st_id(int dId);

    @Delete("DELETE from Order whhere id=#{orderId}")
    int deleteOrder(int orderId);

    @Delete("DELETE from Order_Status where id=#{osId}")
    int deleteOrderStatus(int osId);

    @Delete("DELETE from Delivery where id=#{dId}")
    int deleteDelivery(int dId);

    @Delete("Delete from Delivery_Status where id=#{dsId}")
    int deleteDeliveryStatus(int dsId);
}