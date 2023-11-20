package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.Scanner;

public class ProductTypeClass {

    public static void displayProductType(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM loaisanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("================================================================");
            System.out.println(" Mã loại sản phẩm\tTên loại sản phẩm\tMô tả loại sản phẩm");
            System.out.println("================================================================");
            while (rs.next()) {
                int rsProductTypeId = rs.getInt("ma_loaisanpham");
                String rsProductTypeName = rs.getString("ten_loaisanpham");
                String rsProductTypeDescription = rs.getString("mota_loaisanpham");
                System.out.printf("%-7s %-15d %-23s %s\n", ' ', rsProductTypeId, rsProductTypeName, rsProductTypeDescription);
            }
            System.out.println("================================================================");
        } catch (SQLException ex) { 
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void displayProductType(Connection conn, int productTypeId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM loaisanpham WHERE ma_loaisanpham = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productTypeId);
            rs = pstmt.executeQuery();

            System.out.println("=======================");
            while (rs.next()) {
                int rsProductTypeId = rs.getInt("ma_loaisanpham");
                String rsProductTypeName = rs.getString("ten_loaisanpham");
                String rsProductTypeDescription = rs.getString("mota_loaisanpham");
                System.out.println("Mã loại sản phẩm: " + rsProductTypeId);
                System.out.println("Tên loại sản phẩm: " + rsProductTypeName);
                System.out.println("Mô tả loại sản phẩm: " + rsProductTypeDescription);
                System.out.println("=======================");
            }
        } catch (SQLException ex) { 
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void addProductType(Connection conn) {
        displayProductType(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên loại sản phẩm: ");
        String productTypeName = sc.nextLine();
        System.out.print("Nhập mô tả loại sản phẩm: ");
        String productTypeDescription = sc.nextLine();

        CallableStatement cstmt = null;

        try {
            String sql = "{call them_loaisanpham(?,?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, productTypeName);
            cstmt.setString(2, productTypeDescription);
            cstmt.executeQuery();
            System.out.println("Đã thêm loại sản phẩm thành công");
        } catch (SQLException ex) { 
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void updateProductType(Connection conn) {
        Scanner sc = new Scanner(System.in);
        displayProductType(conn);
        System.out.print("Nhập mã loại sản phẩm muốn chỉnh sửa: ");
        int productTypeId = sc.nextInt();
        sc.nextLine();

        boolean flag = false;
        flag = productTypeExist(conn, productTypeId);

        if (flag == true) {
            System.out.println("=======================");
            System.out.println("Loại sản phẩm vừa chọn ");
            displayProductType(conn, productTypeId);
            System.out.print("Nhập tên loại sản phẩm muốn chỉnh sửa thành: ");
            String productTypeName = sc.nextLine();
            System.out.print("Nhập mô tả loại sản phẩm muốn chỉnh sửa thành: ");
            String productTypeDescription = sc.nextLine();

            CallableStatement cstmt = null;

            try {
                String sql = "{call sua_loaisanpham(?,?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, productTypeId);
                cstmt.setString(2, productTypeName);
                cstmt.setString(3, productTypeDescription);
                cstmt.executeQuery();
                System.out.println("Đã chỉnh sửa loại sản phẩm thành công");
            } catch (SQLException ex) { 
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Loại sản phẩm không tồn tại");
        }
    }

    public static void deleteProductType(Connection conn) {
        Scanner sc = new Scanner(System.in);
        displayProductType(conn);
        System.out.print("Nhập mã loại sản phẩm muốn xóa: ");
        int productTypeId = sc.nextInt();

        boolean flag = false;
        flag = productTypeExist(conn, productTypeId);

        if (flag == true) {
            System.out.println("=======================");
            System.out.println("Loại sản phẩm vừa chọn ");
            displayProductType(conn, productTypeId);
            System.out.println("Bạn có chắn chắn sẽ xóa loại sản phẩm vừa chọn không ?");
            System.out.println("Có[1]\t Không[0]");
            int confirm = sc.nextInt();

            if (confirm == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_loaisanpham(?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, productTypeId);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa loại sản phẩm thành công");
                } catch (SQLException ex) { 
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Loại sản phẩm không tồn tại");
        }
    }

    public static boolean productTypeExist(Connection conn, int productTypeId) {
        PreparedStatement pstmt_check = null;
        ResultSet rs_check = null;
        boolean flag = false;

        try {
            String sql_check = "select tontai_loaisanpham(?) tontai_loaisanpham";
            pstmt_check = conn.prepareStatement(sql_check);
            pstmt_check.setInt(1, productTypeId);
            rs_check = pstmt_check.executeQuery();

            while (rs_check.next()) {
                flag = rs_check.getBoolean("tontai_loaisanpham");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return flag;
    }
}
