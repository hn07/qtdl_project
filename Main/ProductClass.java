package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.Scanner;
import Main.ProductTypeClass;

public class ProductClass {

    public static void displayProduct(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM sanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println(
                    "==========================================================================================================");
            System.out.println(" Mã sản phẩm\tTên sản phẩm\t\t\tĐơn vị\t\tMô tả sản phẩm\t\tMã loại sản phẩm");
            System.out.println(
                    "==========================================================================================================");
            while (rs.next()) {
                int rsProductId = rs.getInt("ma_sanpham");
                String rsProductName = rs.getString("ten_sanpham");
                String rsProductUnit = rs.getString("donvi_sanpham");
                String rsProductDescription = rs.getString("mota_sanpham");
                int rsProductTypeId = rs.getInt("ma_loaisanpham");
                System.out.printf("%-5s %-9d %-31s %-15s %-30s %d\n", ' ', rsProductId, rsProductName, rsProductUnit,
                        rsProductDescription, rsProductTypeId);
            }
            System.out.println(
                    "==========================================================================================================");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void displayProduct(Connection conn, int productId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM sanpham where productId = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            rs = pstmt.executeQuery();

            System.out.println("=====================");
            while (rs.next()) {
                int rsProductId = rs.getInt("ma_sanpham");
                String rsProductName = rs.getString("ten_sanpham");
                String rsProductUnit = rs.getString("donvi_sanpham");
                String rsProductDescription = rs.getString("mota_sanpham");
                int rsProductTypeId = rs.getInt("ma_loaisanpham");
                System.out.println("Mã sản phẩm: " + rsProductId);
                System.out.println("Tên sản phẩm: " + rsProductName);
                System.out.println("Đơn vị sản phẩm: " + rsProductUnit);
                System.out.println("Mô tả sản phẩm: " + rsProductDescription);
                System.out.println("Mã loại sản phẩm: " + rsProductTypeId);
                System.out.println("=====================");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void addProduct(Connection conn) {
        displayProduct(conn);
        ProductTypeClass.displayProductType(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã loại sản phẩm cho sản phẩm: ");
        int productTypeId = sc.nextInt();
        sc.nextLine();

        boolean flag = false;
        flag = ProductTypeClass.productTypeExist(conn, productTypeId);

        if (flag == true) {
            System.out.print("Nhập tên sản phẩm: ");
            String productName = sc.nextLine();
            System.out.print("Nhập đơn vị sản phẩm: ");
            String productUnit = sc.nextLine();
            System.out.print("Nhập mô tả sản phẩm: ");
            String productDescription = sc.nextLine();

            CallableStatement cstmt = null;

            try {
                String sql = "{call them_sanpham(?,?,?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setString(1, productName);
                cstmt.setString(2, productUnit);
                cstmt.setString(3, productDescription);
                cstmt.setInt(4, productTypeId);
                cstmt.executeQuery();
                System.out.println("Đã thêm sản phẩm thành công");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Loại sản phẩm không tồn tại");
        }
    }

    public static void updateProduct(Connection conn) {
        Scanner sc = new Scanner(System.in);
        displayProduct(conn);
        System.out.print("Nhập mã sản phẩm muốn chỉnh sửa: ");
        int productId = sc.nextInt();
        sc.nextLine();

        boolean flag = false;
        flag = productExist(conn, productId);

        if (flag == true) {
            System.out.println("=====================");
            System.out.println("Sản phẩm vừa chọn ");
            displayProduct(conn, productId);
            System.out.print("Nhập tên sản phẩm muốn chỉnh sửa thành: ");
            String productName = sc.nextLine();
            System.out.print("Nhập đơn vị sản phẩm muốn chỉnh sửa thành: ");
            String productUnit = sc.nextLine();
            System.out.print("Nhập mô tả sản phẩm muốn chỉnh sửa thành: ");
            String productDescription = sc.nextLine();

            ProductTypeClass.displayProductType(conn);
            System.out.print("Nhập mã loại sản phẩm muốn chỉnh sửa thành: ");
            int productTypeId = sc.nextInt();
            sc.nextLine();

            boolean flag2 = false;
            flag2 = ProductTypeClass.productTypeExist(conn, productTypeId);

            if (flag2 == true) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call sua_sanpham(?,?,?,?,?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, productId);
                    cstmt.setString(2, productName);
                    cstmt.setString(3, productUnit);
                    cstmt.setString(4, productDescription);
                    cstmt.setInt(5, productTypeId);
                    cstmt.executeQuery();
                    System.out.println("Đã chỉnh sửa sản phẩm thành công");
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            } else {
                System.out.println("Loại sản phẩm không tồn tại");
            }

        } else {
            System.out.println("Sản phẩm không tồn tại");
        }
    }

    public static void deleteProduct(Connection conn) {
        Scanner sc = new Scanner(System.in);
        displayProduct(conn);
        System.out.print("Nhập mã sản phẩm muốn xóa: ");
        int productId = sc.nextInt();

        boolean flag = false;
        flag = productExist(conn, productId);

        if (flag == true) {
            System.out.println("=====================");
            System.out.println("Sản phẩm vừa chọn ");
            displayProduct(conn, productId);
            System.out.println("Bạn có chắn chắn sẽ xóa sản phẩm vừa chọn không ?");
            System.out.println("Có[1]\t Không[0]");
            int confirm = sc.nextInt();

            if (confirm == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_sanpham(?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, productId);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa sản phẩm thành công");
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Sản phẩm không tồn tại");
        }
    }

    public static boolean productExist(Connection conn, int productId) {
        PreparedStatement pstmt_check = null;
        ResultSet rs_check = null;
        boolean flag = false;

        try {
            String sql_check = "select tontai_sanpham(?) tontai_sanpham";
            pstmt_check = conn.prepareStatement(sql_check);
            pstmt_check.setInt(1, productId);
            rs_check = pstmt_check.executeQuery();

            while (rs_check.next()) {
                flag = rs_check.getBoolean("tontai_sanpham");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return flag;
    }
}
