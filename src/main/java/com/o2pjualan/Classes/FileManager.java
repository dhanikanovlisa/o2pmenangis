package com.o2pjualan.Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.o2pjualan.Main.folderName;
import static com.o2pjualan.Main.controller;

public class FileManager {
    public static void initiateDataStore() throws IOException {
        String[] fileNames = {"customer.xml", "customer.json", "customer.ser"};
        boolean found = false;
        for (String fileName : fileNames) {
            File file = new File(folderName + fileName);
            if (file.exists()) {
                String ext = fileName.substring( fileName.lastIndexOf(".") + 1);
                controller = new Controller(ext);
                found = true;
                break;
            }
        }
        if (!found) {
            setUpFolder("json");
            controller = new Controller("json");
        }
    }

    public static void deleteCurrentFiles() {
        String folderPath = folderName.substring(0, folderName.length()-1);
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File f: files) {
                if (f.isFile()) {
                    f.delete();
                }
            }
        }
    }

    public static boolean isExtExist(String ext) {
        String folderPath = folderName.substring(0, folderName.length()-1);
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files[0] != null) {
            return (ext.equals(files[0].getName().substring(files[0].getName().lastIndexOf(".") + 1)));
        } else {
            return false;
        }
    }

    public static void setUpFolder(String ext) {
        deleteCurrentFiles();

        if (ext.equals("obj")) {
            ext = "ser";
        }

        String[] newFileNames = {"customer.", "bill.", "fixedBill.", "product.", "plugin."};
        for (int i = 0; i < newFileNames.length; i++) {
            newFileNames[i] = newFileNames[i].concat(ext);
        }
        String bill = "";
        String customer = "";
        String fixedBill = "";
        String product = "";
        String plugin = "";

        if (ext.equals("json")) {
            bill = "[ ]";
            customer = "[ ]";
            fixedBill = "[ ]";
            product = "[ ]";
            plugin = " {\"plugins\": []}";
        } else if  (ext.equals("xml")) {
            customer = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<customers></customers>";
            bill = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<bills></bills>";
            fixedBill = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<fixedBills></fixedBills>";
            product = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                    "<products></products>";
            plugin = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PluginManager></PluginManager>\n";
        }

        for (String fileName : newFileNames) {
            File file = new File(folderName + fileName);
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(folderName + fileName);
                String content = fileName.substring(0, fileName.indexOf('.'));
                System.out.println(content);
                switch (content) {
                    case "customer":
                        writer.write(customer);
                        writer.close();
                        break;
                    case "bill":
                        writer.write(bill);
                        writer.close();
                        break;
                    case "product":
                        writer.write(product);
                        writer.close();
                        break;
                    case "fixedBill":
                        writer.write(fixedBill);
                        writer.close();
                        break;
                    case "plugin":
                        writer.write(plugin);
                        writer.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //retcode 0 = success, 1 = already true, -1 = fail
    public static Integer changeExt(String ext) {
        int retcode = -1;
        if (!isExtExist(ext)) {
            Customers tempCustomers = controller.getCustomers();
            Products tempProducts = controller.getProducts();
            FixedBills tempFixedBills = controller.getFixedBills();
            Bills tempBills = controller.getBills();

            if (tempCustomers != null && tempBills != null && tempFixedBills != null && tempProducts != null) {
                setUpFolder(ext);
                try {
                    controller = new Controller(ext);
                    controller.saveDataCustomer(tempCustomers);
                    controller.saveDataProduct(tempProducts);
                    controller.saveDataFixedBill(tempFixedBills);
                    controller.saveDataBill(tempBills);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                retcode = 0;
            }
        } else {
            retcode = 1;
        }

        return retcode;
    }

    public static int changeFolder(String newFolderPath) {
        folderName = newFolderPath;
        try {
            initiateDataStore();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
