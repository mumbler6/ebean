package org.tests.el;

import io.ebean.BaseTestCase;
import io.ebean.DB;
import io.ebeaninternal.api.SpiEbeanServer;
import io.ebeaninternal.server.deploy.BeanDescriptor;
import io.ebeaninternal.server.deploy.BeanFkeyProperty;
import io.ebeaninternal.server.deploy.BeanProperty;
import io.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import io.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import io.ebeaninternal.server.el.ElPropertyChain;
import io.ebeaninternal.server.el.ElPropertyDeploy;
import org.tests.model.basic.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestELBasic extends BaseTestCase {

  @Test
  public void testEl() {

    SpiEbeanServer server = (SpiEbeanServer) DB.getDefault();
    BeanDescriptor<Customer> descriptor = server.getBeanDescriptor(Customer.class);

    ElPropertyDeploy elId = descriptor.getElPropertyDeploy("id");
    assertTrue(elId instanceof BeanProperty);

    ElPropertyDeploy elBillAddress = descriptor.getElPropertyDeploy("billingAddress");
    assertTrue(elBillAddress instanceof BeanPropertyAssocOne<?>);

    ElPropertyDeploy elBillAddressId = descriptor.getElPropertyDeploy("billingAddress.id");
    assertTrue(elBillAddressId instanceof BeanFkeyProperty);
    assertEquals("billing_address_id", elBillAddressId.dbColumn());
    assertEquals("billingAddress.id", elBillAddressId.name());
    assertNull(elBillAddressId.elPrefix());


    ElPropertyDeploy elBillAddressCity = descriptor.getElPropertyDeploy("billingAddress.city");
    assertTrue(elBillAddressCity instanceof ElPropertyChain);
    assertEquals("billingAddress", elBillAddressCity.elPrefix());
    assertEquals("city", elBillAddressCity.name());
    assertEquals("${billingAddress}city", elBillAddressCity.elPlaceholder(false));
    assertEquals("city", elBillAddressCity.dbColumn());

//		ElPropertyDeploy elBillAddressCountry = descriptor.getElPropertyDeploy("billingAddress.country");


    ElPropertyDeploy elOrders = descriptor.getElPropertyDeploy("orders");
    assertTrue(elOrders instanceof BeanPropertyAssocMany<?>);

    ElPropertyDeploy elOrderStatus = descriptor.getElPropertyDeploy("orders.status");
    assertTrue(elOrderStatus instanceof ElPropertyChain);
    assertEquals("orders", elOrderStatus.elPrefix());
    assertEquals("status", elOrderStatus.name());
    assertEquals("${orders}status", elOrderStatus.elPlaceholder(false));
    assertEquals("status", elOrderStatus.dbColumn());

    ElPropertyDeploy elOrderCust = descriptor.getElPropertyDeploy("orders.customer");
    assertTrue(elOrderCust instanceof ElPropertyChain);

    ElPropertyDeploy elOrderDetails = descriptor.getElPropertyDeploy("orders.details");
    assertTrue(elOrderDetails instanceof ElPropertyChain);

    ElPropertyDeploy elOrderDetailsId = descriptor.getElPropertyDeploy("orders.details.id");
    assertTrue(elOrderDetailsId instanceof ElPropertyChain);

  }


}
