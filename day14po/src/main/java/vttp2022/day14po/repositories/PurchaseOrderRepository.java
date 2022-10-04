package vttp2022.day14po.repositories;

import static vttp2022.day14po.repositories.Queries.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.day14po.models.PurchaseOrder;

@Repository
public class PurchaseOrderRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean insertPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        int added = template.update(SQL_INSERT_PURCHASE_ORDER, 
            purchaseOrder.getOrderId(), purchaseOrder.getName(), purchaseOrder.getDate());

            return 1 == added;
    }
    
}
