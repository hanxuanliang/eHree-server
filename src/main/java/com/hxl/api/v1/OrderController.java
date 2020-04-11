package com.hxl.api.v1;

import com.hxl.bo.PageCounter;
import com.hxl.core.LocalUser;
import com.hxl.core.annotations.ScopeLevel;
import com.hxl.dto.OrderDTO;
import com.hxl.exception.NotFoundException;
import com.hxl.logic.OrderChecker;
import com.hxl.model.Order;
import com.hxl.service.OrderService;
import com.hxl.utils.CommonUtil;
import com.hxl.vo.OrderIdVO;
import com.hxl.vo.OrderPureVO;
import com.hxl.vo.OrderSimpleVO;
import com.hxl.vo.PagingVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * order api接口
 *
 * @Author: hanxuanliang
 * @Date: 2020/4/6 21:52
 */
@RestController
@RequestMapping("/order")
@Validated
public class OrderController {

    @Resource
    private OrderService orderService;

    @Value("${ehree.order.pay-time-limit}")
    private Long payTimeLimit;

    @ScopeLevel
    @PutMapping("")
    public OrderIdVO placeOrder(@RequestBody OrderDTO orderDTO) {
        Long uid = LocalUser.getLocalUser().getId();
        OrderChecker orderChecker = orderService.isOk(uid, orderDTO);

        Long orderId = orderService.placeOrder(uid, orderDTO, orderChecker);

        return new OrderIdVO(orderId);
    }

    @ScopeLevel
    @PutMapping("/status/unpaid")
    public PagingVO getUnpaid(@RequestParam(defaultValue = "0") Integer start,
                              @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getUnpaid(page.getPage(), page.getCount());
        PagingVO pagingVO = new PagingVO(orderPage, OrderSimpleVO.class);
        pagingVO.getItems().forEach(o -> ((OrderSimpleVO) o).setPeriod(payTimeLimit));
        return pagingVO;
    }

    // All PAID DELIVERED FINISHED
    @ScopeLevel
    @PutMapping("/by/status/{status}")
    public PagingVO getOrderByStatus(@PathVariable int status,
                              @RequestParam(defaultValue = "0") Integer start,
                              @RequestParam(defaultValue = "10") Integer count) {
        PageCounter page = CommonUtil.convertToPageParameter(start, count);
        Page<Order> orderPage = orderService.getOrderByStatus(status, page.getPage(), page.getCount());
        PagingVO pagingVO = new PagingVO(orderPage, OrderSimpleVO.class);
        pagingVO.getItems().forEach(o -> ((OrderSimpleVO) o).setPeriod(payTimeLimit));
        return pagingVO;
    }

    // 查询订单详情
    @ScopeLevel
    @GetMapping("/detail/{id}")
    public OrderPureVO getOrderDetail(@PathVariable(name = "id") Long oid) {
        Optional<Order> orderOptional = orderService.getDetailOfOrder(oid);
        // FIXME 在 orElseThrow() 里面不要 throw，在java8有些版本里面，检测是错的，但是IDEA是不检测的
        // 建议在这直接 new 这个错误
        return orderOptional.map(order -> new OrderPureVO(order, payTimeLimit))
                .orElseThrow(() -> new NotFoundException(50009) );
    }
}
