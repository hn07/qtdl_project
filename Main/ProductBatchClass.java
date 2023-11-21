
package Main;

import static Main.ProductTypeClass.displayProductType;
import static Main.ProductTypeClass.productTypeExist;
import static Main.ProductClass.displayProduct;
import static Main.ProductClass.productExist;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class ProductBatchClass {
    public static void displayProductBatch(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM lohangsanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("======================================================");
            System.out.println(" Mã lô hàng\tMã sản phẩm\tGiá nhập vào\tSố lượng");
            System.out.println("======================================================");
            while (rs.next()) {
                int rsBatchId = rs.getInt("ma_lohang");
                int rsProductId = rs.getInt("ma_sanpham");
                int rsInputPrice = rs.getInt("gia_nhapvao");
                int rsQuantity = rs.getInt("so_luong");
                System.out.printf("%-5s %-14d %-10d %-15d %d\n", ' ', rsBatchId, rsProductId, rsInputPrice, rsQuantity);

            }
            System.out.println("=======================================================");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void addProductBatch(Connection conn) {
        displayProductBatch(conn);
        Scanner sc = new Scanner(System.in);
        BatchClass.displayBatch(conn);
        System.out.print("Nhập mã lô hàng: ");
        int batchId = sc.nextInt();
        ProductClass.displayProduct(conn);
        System.out.print("Nhập mã sản phẩm: ");
        int productId = sc.nextInt();

        boolean check = false;
        check = ProductClass.productExist(conn, productId);
        boolean check2 = false;
        check2 = BatchClass.existBatch(conn, batchId);

        if (check == true && check == true) {

            System.out.print("Nhập giá nhập sản phẩm trong lô hàng: ");
            int inputPrice = sc.nextInt();
            System.out.print("Nhập số lượng sản phẩm trong lô hàng: ");
            int quantity = sc.nextInt();

            CallableStatement cstmt = null;

            try {
                String sql = "{call them_lohangsanpham(?,?,?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, batchId);
                cstmt.setInt(2, productId);
                cstmt.setInt(3, inputPrice);
                cstmt.setInt(4, quantity);
                cstmt.executeQuery();
                System.out.println("Đã thêm sản phẩm thành công");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Lô hàng hoặc sản phẩm không tồn tại");
        }
    }

    public static void deleteProductBatch(Connection conn) {
        displayProductBatch(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã lô hàng muốn xóa: ");
        int batchId = sc.nextInt();
        System.out.print("Nhập mã sản phẩm muốn xóa: ");
        int productId = sc.nextInt();

        boolean check = false;
        check = ProductClass.productExist(conn, productId);
        boolean check2 = false;
        check2 = BatchClass.existBatch(conn, batchId);

        if (check == true && check2 == true) {
            System.out.println("=================");
            System.out.println("Lô hàng sản phẩm vừa chọn ");
            displayProductBatch(conn);
            System.out.println("Xác nhận");
            System.out.println("Có[1]\t Không[0]");
            int t = sc.nextInt();

            if (t == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_lohangsanpham(?,?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, batchId);
                    cstmt.setInt(2, productId);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa lô hàng sản phẩm thành công");
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Lô hàng không tồn tại");
        }
    }
}
