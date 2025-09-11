package com.app.ecom.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.app.ecom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
{

}
