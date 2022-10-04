package vttp2022.day14po.repositories;

import static vttp2022.day14po.repositories.Queries.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.day14po.models.LineItem;

@Repository
public class LineItemRepository {
    
    @Autowired
    private JdbcTemplate template;

    public boolean insertLineItem(String orderId, LineItem lineItem)
    {
        int added = template.update(SQL_INSERT_LINE_ITEM, 
            lineItem.getProdId(), orderId, lineItem.getQty());

            return 1 == added;
    }

    public boolean insertLineItems(String orderId, List<LineItem> lineItems)
    {
        for (LineItem lineItem : lineItems)
            if(!insertLineItem(orderId, lineItem))
                return false;
            return true;
    }

}
