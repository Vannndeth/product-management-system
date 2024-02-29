package controller;

import model.Product;
import service.ProductService;
import util.PaginationUtil;
import util.Singleton;
import validation.InputValidation;
import view.ProductView;

import java.util.List;
import java.util.Scanner;

import static validation.InputValidation.validateInteger;
import static validation.InputValidation.validateString;

public class ProductController {
    private final Scanner scanner;
    private final ProductView productView;
    private final ProductService productService;
    private final PaginationUtil paginationUtil;
    public ProductController(){
        productView = Singleton.getProductView();
        productService = Singleton.getProductService();
        scanner = Singleton.getScanner();
        paginationUtil = Singleton.getPaginationUtil();
    }

    public void startProgram(){
        productService.start("Loading", 96, 20);
    }
    public void random(){
        Long count = (long) validateInteger(scanner, "Enter Number of Record: ");
        String confirm = InputValidation.validateString(scanner, "Are you sure to random "+count+" product?(Y/N): ");
        if(confirm.equalsIgnoreCase("YES") || confirm.equalsIgnoreCase("Y")){
            productService.random(count);
        }else {
            System.out.println("You're canceled random!");
        }
    }
    public void display(){
        int rowPerPage = 10;
        int currentPage = 1;
        List<Product> products = productService.display();
        productView.displayProduct(products, rowPerPage, currentPage);
        do {
            productView.paginationOption();
            String option = validateString(scanner, "-> B)ack or Navigate page: ");
            switch (option.toUpperCase()){
                case "F" -> {
                    currentPage = paginationUtil.first(products, rowPerPage, currentPage);
                }
                case "P" -> {
                    currentPage = paginationUtil.previous(products, rowPerPage, currentPage);
                }
                case "G" -> {
                    currentPage = paginationUtil.goTo(products, rowPerPage, currentPage, scanner);
                }
                case "N" -> {
                    currentPage = paginationUtil.next(products, rowPerPage, currentPage);
                }
                case "L" -> {
                    currentPage = paginationUtil.last(products, rowPerPage, currentPage);
                }
                case "S" -> {
                    rowPerPage = setRecord();
                    productView.displayProduct(products, rowPerPage, currentPage);
                }
                case "B" -> {
                    return;
                }
                default -> {
                    System.out.println("Please choose option above!");
                }
            }
        }while (true);
    }
    public void write(){
        Product product = new Product();
        String confirm = productView.write(product, scanner);
        productService.write(product,confirm);
    }
    public void read() {
        System.out.print("-> Enter Product's Code: ");
        String code = String.valueOf(scanner.nextLine());
        Product product = productService.read(code);
        if (product != null) {
            System.out.printf("# Product detail of %s\n", code);
            productView.read(productService.read(code));
        } else {
            System.out.println("Product with code " + code + " not found.");
        }
    }
    public void update(){
        System.out.print("Enter product code to update: ");
        String productCode = String.valueOf(scanner.nextLine());
        Product product = productService.read(productCode);
        if (product != null) {
            System.out.printf("# Product detail of %s\n", productCode);
            productView.read(productService.read(productCode));
            System.out.println("# Select option to update:");
            System.out.println("1. Update All");
            System.out.println("2. Update name");
            System.out.println("3. Update price");
            System.out.println("4. Update quantity");
            System.out.println("5. Back to menu");
            int choice = validateInteger(scanner, "Enter your choice: ");
            switch (choice) {
                case 1 -> {
                    productService.update(productCode, true, true, true);
                }
                case 2 -> {
                    productService.update(productCode, true, false, false);
                }
                case 3 -> {
                    productService.update(productCode, false, true, false);
                }

                case 4 -> {
                    productService.update(productCode, false, false, true);
                }
                case 5 -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid option selected.");
                }
            }
        } else {
            System.out.println("Product with code " + productCode + " not found!");
        }
    }
    public void delete(){
        System.out.print("Enter product's code to delete: ");
        String code = String.valueOf(scanner.nextLine());
        Product product = productService.read(code);
        if(product != null){
            System.out.printf("# Product detail of %s\n", code);
            productView.read(productService.read(code));
            String confirm = validateString(scanner, "Are you sure you want to delete?(Y/N):");
            if(confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("YES")){
                productService.delete(code);
                System.out.printf("Product's with %s has been deleted...!",code);
            }else {
                System.out.println("You're canceled delete!");
            }
        }else {
            System.out.printf("Product with code %s not found!\n",code);
        }

    }
    public void search(){
        int rowPerPage = 10;
        int currentPage = 1;
        System.out.print("-> Enter Product's Name: ");
        String name = String.valueOf(scanner.nextLine());
        List<Product> products = productService.search(name);
        if (!products.isEmpty()) {
            productView.displayProduct(products, rowPerPage, currentPage);
            do {
                productView.paginationOption();
                String option = validateString(scanner, "-> B)ack or Navigate page: ");
                switch (option.toUpperCase()){
                    case "F" -> {
                        currentPage = paginationUtil.first(products, rowPerPage, currentPage);
                    }
                    case "P" -> {
                        currentPage = paginationUtil.previous(products, rowPerPage, currentPage);
                    }
                    case "G" -> {
                        currentPage = paginationUtil.goTo(products, rowPerPage, currentPage, scanner);
                    }
                    case "N" -> {
                        currentPage = paginationUtil.next(products, rowPerPage, currentPage);
                    }
                    case "L" -> {
                        currentPage = paginationUtil.last(products, rowPerPage, currentPage);
                    }
                    case "S" -> {
                        rowPerPage = setRecord();
                        productView.displayProduct(products, rowPerPage, currentPage);
                    }
                    case "B" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Please choose option above!");
                    }
                }
            }while (true);
        } else {
            System.out.println("No products with name containing " + name + " found.");
        }
    }
    public int setRecord(){
        return paginationUtil.setRecord();
    }
    public void commit(){
        productService.commit();
    }
    public void backup(){
        productService.backup();
    }
    public void restore(){
        productService.restore();
    }
    public void help(){
        productView.helpView();
    }
    public void exit(){
        productService.exit();
    }
}
