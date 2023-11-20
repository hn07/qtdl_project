package Main;

import static Main.ProductTypeClass.displayProductType;
import static Main.ProductTypeClass.tontai_loaisanpham;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class BatchClass {

    public static void displayBatch(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM lohang";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("===========================================");
            System.out.println(" Mã lô hàng\tNgày nhập");
            System.out.println("===========================================");
            while (rs.next()) {
                int rsBatchId = rs.getInt("ma_lohang");
                Date rsInputDate = rs.getDate("dateInput");
                System.out.printf("%-4s %-11d %s \n", ' ', rsBatchId, rsInputDate);
            }
            System.out.println("===========================================");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void displayBatch(Connection conn, int batchId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM lohang WHERE ma_lohang = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, batchId);
            rs = pstmt.executeQuery();

            System.out.println("===================");
            while (rs.next()) {
                int rsProductTypeId = rs.getInt("ma_lohang");
                Date rsInputDate = rs.getDate("dateInput");
                System.out.println("Mã lô hàng: " + rsProductTypeId);
                System.out.println("Ngày nhập: " + rsInputDate);
                System.out.println("======================");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void hienthi_thongtinsanpham(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM loaisanpham inner join sanpham on loaisanpham.ma_loaisanpham=sanpham.ma_loaisanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println(
                    "==============================================================================================================");
            System.out.println(
                    " Mã sản phẩm\t\tTên sản phẩm\t\t  Đơn vị sản phẩm\tMã loại sản phẩm\tTên loại sản phẩm\tMô tả loại sản phẩm");
            System.out.println(
                    "==============================================================================================================");
            while (rs.next()) {
                int rsProductId = rs.getInt("ma_sanpham");
                String rsProductName = rs.getString("ten_sanpham");
                String rsProductUnit = rs.getString("donvi_sanpham");
                int rsProductTypeId = rs.getInt("ma_loaisanpham");
                String rsProductTypeName = rs.getString("ten_loaisanpham");
                String rsProductDescription = rs.getString("mota_loaisanpham");
                System.out.print("        " + rsProductId + "\t\t");
                System.out.print("" + rsProductName + "\t\t");
                System.out.print("" + rsProductUnit + "\t\t");
                System.out.print("        " + rsProductTypeId + "\t\t");
                System.out.print("   " + rsProductTypeName + "\t\t");
                System.out.println("     " + rsProductDescription);
            }
            System.out.println(
                    "==============================================================================================================");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void addBatch(Connection conn) throws ParseException {
        displayBatch(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập năm: ");
        int year = sc.nextInt();
        System.out.print("Nhập tháng: ");
        int month = sc.nextInt();
        System.out.print("Nhập ngày: ");
        int date = sc.nextInt();

        Date dateInput = Date.valueOf(year + "-" + month + "-" + date);
        System.out.println(dateInput);

        CallableStatement cstmt = null;

        try {
            String sql = "{call them_lohang(?)}";
            cstmt = conn.prepareCall(sql);

            cstmt.setDate(1, (java.sql.Date) dateInput);
            cstmt.executeQuery();
            System.out.println("Thêm lô hàng thành công.");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static boolean existBatch(Connection conn, int batchId) {
        PreparedStatement pstmt_check = null;
        ResultSet rs_check = null;
        boolean check = false;

        try {
            String sql_check = "select tontai_lohang(?) tontai_lohang";
            pstmt_check = conn.prepareStatement(sql_check);
            pstmt_check.setInt(1, batchId);
            rs_check = pstmt_check.executeQuery();

            while (rs_check.next()) {
                check = rs_check.getBoolean("tontai_lohang");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return check;
    }

    public static void updateBatch(Connection conn) {
        Scanner sc = new Scanner(System.in);
        displayBatch(conn);
        System.out.print("Nhập ID: ");
        int batchId = sc.nextInt();
        sc.nextLine();

        boolean check = false;
        check = existBatch(conn, batchId);

        if (check == true) {
            System.out.println("======================");
            System.out.println("Mã lô hàng đã chọn ");
            displayBatch(conn, batchId);
            System.out.print("Nhập vào năm: ");
            int year = sc.nextInt();
            System.out.print("Nhập vào tháng: ");
            int month = sc.nextInt();
            System.out.print("Nhập vào ngày: ");
            int date = sc.nextInt();

            Date dateInput = Date.valueOf(year + "-" + month + "-" + date);

            CallableStatement cstmt = null;

            try {
                String sql = "{call sua_lohang(?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, batchId);
                cstmt.setDate(2, dateInput);
                cstmt.executeQuery();
                System.out.println("Đã chỉnh sửa lô hàng thành công");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Lô hàng không tồn tại");
        }
    }

    public static void deleteBatch(Connection conn) {
        Scanner sc = new Scanner(System.in);
        displayBatch(conn);
        System.out.print("Nhập mã lô hàng muốn xóa: ");
        int batchId = sc.nextInt();

        boolean check = false;
        check = existBatch(conn, batchId);

        if (check == true) {
            System.out.println("======================");
            System.out.println("Mã lô hàng vừa được chọn ");
            displayBatch(conn, batchId);
            System.out.println("Xác nhận:");
            System.out.println("Có(1)\t Không(0)");
            int d = sc.nextInt();

            if (d == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_lohang(?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, batchId);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa lô hàng thành công");
                } catch (SQLException ex) {
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Lô hàng không tồn tại");
        }
    }

}
