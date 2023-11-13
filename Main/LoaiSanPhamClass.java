package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.Scanner;

public class LoaiSanPhamClass {

    public static void hienthi_loaisanpham(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM loaisanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("-------------------------------------------------------------------");
            System.out.println(" Mã loại sản phẩm\tTên loại sản phẩm\tMô tả loại sản phẩm");
            System.out.println("-------------------------------------------------------------------");
            while (rs.next()) {
                int rs_ma_loaisanpham = rs.getInt("ma_loaisanpham");
                String rs_ten_loaisanpham = rs.getString("ten_loaisanpham");
                String rs_mota_loaisanpham = rs.getString("mota_loaisanpham");
                System.out.printf("%-7s %-15d %-23s %s\n", ' ', rs_ma_loaisanpham, rs_ten_loaisanpham, rs_mota_loaisanpham);
            }
            System.out.println("-------------------------------------------------------------------");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void hienthi_loaisanpham(Connection conn, int ma_loaisanpham) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM loaisanpham WHERE ma_loaisanpham = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ma_loaisanpham);
            rs = pstmt.executeQuery();

            System.out.println("--------------------");
            while (rs.next()) {
                int rs_ma_loaisanpham = rs.getInt("ma_loaisanpham");
                String rs_ten_loaisanpham = rs.getString("ten_loaisanpham");
                String rs_mota_loaisanpham = rs.getString("mota_loaisanpham");
                System.out.println("Mã loại sản phẩm: " + rs_ma_loaisanpham);
                System.out.println("Tên loại sản phẩm: " + rs_ten_loaisanpham);
                System.out.println("Mô tả loại sản phẩm: " + rs_mota_loaisanpham);
                System.out.println("--------------------");
            }
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void them_loaisanpham(Connection conn) {
        hienthi_loaisanpham(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập tên loại sản phẩm: ");
        String ten_loaisanpham = sc.nextLine();
        System.out.print("Nhập mô tả loại sản phẩm: ");
        String mota_loaisanpham = sc.nextLine();

        CallableStatement cstmt = null;

        try {
            String sql = "{call them_loaisanpham(?,?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, ten_loaisanpham);
            cstmt.setString(2, mota_loaisanpham);
            cstmt.executeQuery();
            System.out.println("Đã thêm loại sản phẩm thành công");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void sua_loaisanpham(Connection conn) {
        Scanner sc = new Scanner(System.in);
        hienthi_loaisanpham(conn);
        System.out.print("Nhập mã loại sản phẩm muốn chỉnh sửa: ");
        int ma_loaisanpham = sc.nextInt();
        sc.nextLine();

        boolean flag = false;
        flag = tontai_loaisanpham(conn, ma_loaisanpham);

        if (flag == true) {
            System.out.println("--------------------");
            System.out.println("Loại sản phẩm vừa chọn ");
            hienthi_loaisanpham(conn, ma_loaisanpham);
            System.out.print("Nhập tên loại sản phẩm muốn chỉnh sửa thành: ");
            String ten_loaisanpham = sc.nextLine();
            System.out.print("Nhập mô tả loại sản phẩm muốn chỉnh sửa thành: ");
            String mota_loaisanpham = sc.nextLine();

            CallableStatement cstmt = null;

            try {
                String sql = "{call sua_loaisanpham(?,?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, ma_loaisanpham);
                cstmt.setString(2, ten_loaisanpham);
                cstmt.setString(3, mota_loaisanpham);
                cstmt.executeQuery();
                System.out.println("Đã chỉnh sửa loại sản phẩm thành công");
            } catch (SQLException ex) { //xử lý ngoại lệ
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Loại sản phẩm không tồn tại");
        }
    }

    public static void xoa_loaisanpham(Connection conn) {
        Scanner sc = new Scanner(System.in);
        hienthi_loaisanpham(conn);
        System.out.print("Nhập mã loại sản phẩm muốn xóa: ");
        int ma_loaisanpham = sc.nextInt();

        boolean flag = false;
        flag = tontai_loaisanpham(conn, ma_loaisanpham);

        if (flag == true) {
            System.out.println("--------------------");
            System.out.println("Loại sản phẩm vừa chọn ");
            hienthi_loaisanpham(conn, ma_loaisanpham);
            System.out.println("Bạn có chắn chắn sẽ xóa loại sản phẩm vừa chọn không ?");
            System.out.println("Có(1)\t Không(0)");
            int confirm = sc.nextInt();

            if (confirm == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_loaisanpham(?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, ma_loaisanpham);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa loại sản phẩm thành công");
                } catch (SQLException ex) { //xử lý ngoại lệ
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Loại sản phẩm không tồn tại");
        }
    }

    public static boolean tontai_loaisanpham(Connection conn, int ma_loaisanpham) {
        PreparedStatement pstmt_check = null;
        ResultSet rs_check = null;
        boolean flag = false;

        try {
            String sql_check = "select tontai_loaisanpham(?) tontai_loaisanpham";
            pstmt_check = conn.prepareStatement(sql_check);
            pstmt_check.setInt(1, ma_loaisanpham);
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
