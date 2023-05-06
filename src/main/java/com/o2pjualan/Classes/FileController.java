package com.o2pjualan.Classes;

public interface FileController {
    void saveDataCustomer(Customers customers);
    void saveDataProduct (Products products);
    void saveDataFixedBill (FixedBills fixedBills);
    void saveDataBill (Bills bills);
    void saveDataPluginManager (PluginManager pluginManager);
    Customers getCustomers();
    Products getProducts();
    Bills getBills();
    FixedBills getFixedBills();
    PluginManager getPluginManager();
    int getTotalCustomers();
    int getTotalProducts();
    int getTotalFixedBills();
}
