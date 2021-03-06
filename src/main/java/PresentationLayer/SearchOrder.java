package PresentationLayer;

import FunctionLayer.Item;
import FunctionLayer.LogicFacade;
import FunctionLayer.LoginSampleException;
import FunctionLayer.Order;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchOrder extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws LoginSampleException {
      
        int orderid = Integer.parseInt(request.getParameter( "orderid" ));
        if (LogicFacade.getOrder(orderid) != null){
            Order order = LogicFacade.getOrder(orderid);
            
            ArrayList<Item> itemList = LogicFacade.getItemList(orderid);
            int totalPrice = LogicFacade.calcTotalPrice(itemList);
            order.setItemlist(itemList);
            order.setTotalPrice(totalPrice);
            
            request.setAttribute("order", order);

            return "viewsingleorder";
        }
        else {
            ArrayList<Order> orders = LogicFacade.getAllOrdersByOrderid(1);
            int count = LogicFacade.countOrders();
            int totalPages = (int) Math.ceil((double)count / 10);
            request.setAttribute("totalpages", totalPages);
            request.setAttribute("count", count);
            request.setAttribute("page", 1);
            request.setAttribute("orderby", "date");
            request.setAttribute("list", orders);
            request.setAttribute("searcherror", "notfound");
            return "vieworders";
        }
    }
    
}
