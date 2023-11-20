package Main;

import java.sql.Connection;
import Main.MySQLConnect;
import java.util.Scanner;
import java.text.ParseException;

public class MainClass {

    public static void main(String args[]) throws ParseException {
        Connection conn = null;
        conn = MySQLConnect.Connect();
        int category;
        int option;
        boolean flag_category = true;
        boolean flag_option = true;
        String choose;
        Scanner sc = new Scanner(System.in);

        while (flag_category) {
            System.out.println("===============\nDanh mục\n===============");
            System.out.println("[1] Sản phẩm.\n[2] Loại sản phẩm.\n[3] Lô hàng.\n"
                    + "[4] Lô hàng sản phẩm.\n[5] Thoát chương trình.");
            System.out.print("Chọn danh mục muốn quản lý: ");
            category = sc.nextInt();
            System.out.flush();
            if (category == 1) {
                while (flag_option) {
                    System.out.println("===============\nSản Phẩm\n================");
                    System.out.println("[1] Thêm sản phẩm.\n[2] Sửa sản phẩm.\n[3] Xóa sản phẩm.\n"
                            + "[4] Liệt kê sản phẩm.\n[5] Thoát danh mục.\n[6] Thoát chương trình.");
                    System.out.print("Chọn chức năng muốn thực hiện: ");
                    option = sc.nextInt();

                    if (option == 1) {
                        ProductClass.addProduct(conn);
                    } else if (option == 2) {
                        ProductClass.updateProduct(conn);
                    } else if (option == 3) {
                        ProductClass.deleteProduct(conn);
                    } else if (option == 4) {
                        ProductClass.displayProduct(conn);
                        System.out.print("Nhập kí tự bất kì: ");
                        sc.nextLine();
                        choose = sc.nextLine();
                    } else if (option == 5) {
                        flag_option = false;
                    } else if (option == 6) {
                        flag_option = false;
                        flag_category = false;
                    } else {
                        System.out.println("Chức năng Không tồn tại. Vui lòng chọn lại.");
                    }
                }
                flag_option = true;
            } else if (category == 2) {
                while (flag_option) {
                    System.out.println("===============\nLoại Sản Phẩm\n================");
                    System.out.println("[1] Thêm loại sản phẩm.\n[2] Sửa loại sản phẩm.\n[3] Xóa loại sản phẩm.\n"
                            + "[4[] Liệt kê loại sản phẩm.\n[5] Thoát danh mục.\n[6] Thoát chương trình.");
                    System.out.print("Chọn chức năng muốn thực hiện: ");
                    option = sc.nextInt();

                    if (option == 1) {
                        ProductTypeClass.addProductType(conn);
                    } else if (option == 2) {
                        ProductTypeClass.updateProductType(conn);
                    } else if (option == 3) {
                        ProductTypeClass.deleteProductType(conn);
                    } else if (option == 4) {
                        ProductTypeClass.displayProductType(conn);
                        System.out.print("Nhập kí tự bất kì: ");
                        sc.nextLine();
                        choose = sc.nextLine();
                    } else if (option == 5) {
                        flag_option = false;
                    } else if (option == 6) {
                        flag_option = false;
                        flag_category = false;
                    } else {
                        System.out.println("Chức năng không tồn tại. Vui lòng chọn lại.");
                    }
                }
                flag_option = true;
            } else if (category == 3) {
                while (flag_option) {
                    System.out.println("===============\nLô Hàng\n================");
                    System.out.println("[1] Thêm lô hàng.\n[2] Sửa lô hàng.\n[3] Xóa lô hàng.\n"
                            + "[4] Liệt kê danh sách lô hàng.\n[5] Thoát danh mục.\n[6] Thoát chương trình.");
                    System.out.print("Chọn chức năng muốn thực hiện: ");
                    option = sc.nextInt();

                    if (option == 1) {
                        BatchClass.addBatch(conn);
                    } else if (option == 2) {
                        BatchClass.updateBatch(conn);
                    } else if (option == 3) {
                        BatchClass.deleteBatch(conn);
                    } else if (option == 4) {
                        BatchClass.displayBatch(conn);
                        System.out.print("Nhập kí tự bất kì: ");
                        sc.nextLine();
                        choose = sc.nextLine();
                    } else if (option == 5) {
                        flag_option = false;
                    } else if (option == 6) {
                        flag_option = false;
                        flag_category = false;
                    } else {
                        System.out.println("Chức năng không tồn tại. Vui lòng chọn lại.");
                    }
                }
                flag_option = true;
            } else if (category == 4) {
                while (flag_option) {
                    System.out.println("===============\nLô Hàng Sản Phẩm\n================");
                    System.out.println("[1] Thêm sản phẩm cho lô hàng.\n[2] Xóa sản phẩm lô hàng.\n"
                            + "[3] Liệt kê danh sách sản phẩm lô hàng.\n[4] Thoát danh mục.\n[5] Thoát chương trình.");
                    System.out.print("Chọn chức năng muốn thực hiện: ");
                    option = sc.nextInt();

                    if (option == 1) {
                        ProductBatchClass.addProductBatch(conn);
                    } else if (option == 2) {
                        ProductBatchClass.deleteProductBatch(conn);
                    } else if (option == 3) {
                        ProductBatchClass.displayProductBatch(conn);
                        System.out.print("Nhập kí tự bất kì: ");
                        sc.nextLine();
                        choose = sc.nextLine();
                    } else if (option == 4) {
                        flag_option = false;
                    } else if (option == 5) {
                        flag_option = false;
                        flag_category = false;
                    } else {
                        System.out.println("Chức năng không tồn tại. Vui lòng chọn lại.");
                    }
                }
                flag_option = true;
            } else if (category == 5) {
                flag_category = false;
            } else {
                System.out.println("Danh mục không tồn tại. Vui lòng chọn lại.");
            }
        }
    }
    
}
