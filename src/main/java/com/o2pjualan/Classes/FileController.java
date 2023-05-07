package com.o2pjualan.Classes;

public interface FileController {
    void saveDataCustomer(Customers customers);
    void saveDataProduct (Products products);
    void saveDataFixedBill (FixedBills fixedBills);
    void saveDataBill (Bills bills);
    void saveDataPlugins(Plugins plugins);
    Customers getCustomers();
    Products getProducts();
    Bills getBills();
    FixedBills getFixedBills();
    Plugins getPlugins();
    int getTotalCustomers();
    int getTotalProducts();
    int getTotalFixedBills();
}
