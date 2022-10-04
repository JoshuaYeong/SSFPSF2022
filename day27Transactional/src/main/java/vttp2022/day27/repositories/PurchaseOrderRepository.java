package vttp2022.day27.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import vttp2022.day27.models.PurchaseOrder;

import static vttp2022.day27.repositories.SQL.*;

@Repository
public class PurchaseOrderRepository {

    @Autowired
    private JdbcTemplate template;
    
    public Integer insertPurchaseOrder(final PurchaseOrder po){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(conn -> {
            // To get the auto incremental ord_id
            PreparedStatement ps = conn.prepareStatement(SQL_INSERT_PURCHASE_ORDER, 
                Statement.RETURN_GENERATED_KEYS);
            // The SQL Query needs to have values to fill up the "?"
            ps.setString(1, po.getName());
            ps.setString(2, po.getEmail());
            // Return the ps with all the values/details
            return ps;
            // Store the values within keyHolder
        }, keyHolder);

        // Retrieve the ord_id value which is an Integer
        BigInteger bigInt = (BigInteger) keyHolder.getKey();
        return bigInt.intValue();
    }
}
