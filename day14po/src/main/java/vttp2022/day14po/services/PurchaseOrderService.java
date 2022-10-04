package vttp2022.day14po.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.day14po.models.PurchaseOrder;
import vttp2022.day14po.repositories.LineItemRepository;
import vttp2022.day14po.repositories.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

    @Autowired
    private LineItemRepository liRepo;

    @Autowired
    private PurchaseOrderRepository poRepo;

    public Optional<String> createPurchaseOrder(PurchaseOrder purchaseOrder)
    {
        String orderId = UUID.randomUUID().toString().substring(0, 8);
        purchaseOrder.setOrderId(orderId);

        if (poRepo.insertPurchaseOrder(purchaseOrder) 
            && liRepo.insertLineItems(orderId, purchaseOrder.getLineItems()))
        return Optional.of(orderId);

            return Optional.empty();
    }
 
}
