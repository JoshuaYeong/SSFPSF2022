package vttp2022.day27.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.day27.exceptions.OrderTooLargeException;
import vttp2022.day27.models.LineItem;
import vttp2022.day27.models.PurchaseOrder;
import vttp2022.day27.repositories.LineItemRepository;
import vttp2022.day27.repositories.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {
    
    @Autowired
    private PurchaseOrderRepository poRepo;

    @Autowired
    private LineItemRepository liRepo;

    @Transactional(rollbackFor = OrderTooLargeException.class)
    public Integer createPurchaseorder(final PurchaseOrder po) throws OrderTooLargeException{
        final Integer orderId = poRepo.insertPurchaseOrder(po);
        double totalUnitPrice = 0d;
        for(LineItem li:po.getLineItems()){
            totalUnitPrice = li.getQuantity()*li.getUnitPrice();
            if(totalUnitPrice > 200){
                System.out.println(totalUnitPrice);
                OrderTooLargeException ex = 
                    new OrderTooLargeException("Order exceed SGD200: %,.2f".formatted(totalUnitPrice));
                ex.setPo(po);
                throw ex;
            }
        }
        
        liRepo.addLineItem(orderId, po.getLineItems());
        return orderId;
    }
}
