package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.Scanner;
import Main.LoaiSanPhamClass;

public class SanPhamClass {

    public static void hienthi_sanpham(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM sanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            System.out.println("---------------------------------------------------------------------------------------------------------");
            System.out.println(" Mã sản phẩm\tTên sản phẩm\t\t\tĐơn vị\t\tMô tả sản phẩm\t\tMã loại sản phẩm");
            System.out.println("---------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int rs_ma_sanpham = rs.getInt("ma_sanpham");
                String rs_ten_sanpham = rs.getString("ten_sanpham");
                String rs_donvi_sanpham = rs.getString("donvi_sanpham");
                String rs_mota_sanpham = rs.getString("mota_sanpham");
                int rs_ma_loaisanpham = rs.getInt("ma_loaisanpham");
                System.out.printf("%-5s %-9d %-31s %-15s %-30s %d\n", ' ', rs_ma_sanpham, rs_ten_sanpham, rs_donvi_sanpham,rs_mota_sanpham, rs_ma_loaisanpham );
            }
            System.out.println("---------------------------------------------------------------------------------------------------------");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void hienthi_sanpham(Connection conn, int ma_sanpham) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM sanpham where ma_sanpham = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ma_sanpham);
            rs = pstmt.executeQuery();

            System.out.println("--------------------");
            while (rs.next()) {
                int rs_ma_sanpham = rs.getInt("ma_sanpham");
                String rs_ten_sanpham = rs.getString("ten_sanpham");
                String rs_donvi_sanpham = rs.getString("donvi_sanpham");
                String rs_mota_sanpham = rs.getString("mota_sanpham");
                int rs_ma_loaisanpham = rs.getInt("ma_loaisanpham");
                System.out.println("Mã sản phẩm: " + rs_ma_sanpham);
                System.out.println("Tên sản phẩm: " + rs_ten_sanpham);
                System.out.println("Đơn vị sản phẩm: " + rs_donvi_sanpham);
                System.out.println("Mô tả sản phẩm: " + rs_mota_sanpham);
                System.out.println("Mã loại sản phẩm: " + rs_ma_loaisanpham);
                System.out.println("--------------------");
            }
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }

    public static void them_sanpham(Connection conn) {
        hienthi_sanpham(conn);
        LoaiSanPhamClass.hienthi_loaisanpham(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã loại sản phẩm cho sản phẩm: ");
        int ma_loaisanpham = sc.nextInt();
        sc.nextLine();

        boolean flag = false;
        flag = LoaiSanPhamClass.tontai_loaisanpham(conn, ma_loaisanpham);

        if (flag == true) {
            System.out.print("Nhập tên sản phẩm: ");
            String ten_sanpham = sc.nextLine();
            System.out.print("Nhập đơn vị sản phẩm: ");
            String donvi_sanpham = sc.nextLine();
            System.out.print("Nhập mô tả sản phẩm: ");
            String mota_sanpham = sc.nextLine();

            CallableStatement cstmt = null;

            try {
                String sql = "{call them_sanpham(?,?,?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setString(1, ten_sanpham);
                cstmt.setString(2, donvi_sanpham);
                cstmt.setString(3, mota_sanpham);
                cstmt.setInt(4, ma_loaisanpham);
                cstmt.executeQuery();
                System.out.println("Đã thêm sản phẩm thành công");
            } catch (SQLException ex) { //xử lý ngoại lệ
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Loại sản phẩm không tồn tại");
        }
    }

    public static void sua_sanpham(Connection conn) {
        Scanner sc = new Scanner(System.in);
        hienthi_sanpham(conn);
        System.out.print("Nhập mã sản phẩm muốn chỉnh sửa: ");
        int ma_sanpham = sc.nextInt();
        sc.nextLine();

        boolean flag = false;
        flag = tontai_sanpham(conn, ma_sanpham);

        if (flag == true) {
            System.out.println("--------------------");
            System.out.println("Sản phẩm vừa chọn ");
            hienthi_sanpham(conn, ma_sanpham);
            System.out.print("Nhập tên sản phẩm muốn chỉnh sửa thành: ");
            String ten_sanpham = sc.nextLine();
            System.out.print("Nhập đơn vị sản phẩm muốn chỉnh sửa thành: ");
            String donvi_sanpham = sc.nextLine();
            System.out.print("Nhập mô tả sản phẩm muốn chỉnh sửa thành: ");
            String mota_sanpham = sc.nextLine();

            LoaiSanPhamClass.hienthi_loaisanpham(conn);
            System.out.print("Nhập mã loại sản phẩm muốn chỉnh sửa thành: ");
            int ma_loaisanpham = sc.nextInt();
            sc.nextLine();

            boolean flag2 = false;
            flag2 = LoaiSanPhamClass.tontai_loaisanpham(conn, ma_loaisanpham);

            if (flag2 == true) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call sua_sanpham(?,?,?,?,?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, ma_sanpham);
                    cstmt.setString(2, ten_sanpham);
                    cstmt.setString(3, donvi_sanpham);
                    cstmt.setString(4, mota_sanpham);
                    cstmt.setInt(5, ma_loaisanpham);
                    cstmt.executeQuery();
                    System.out.println("Đã chỉnh sửa sản phẩm thành công");
                } catch (SQLException ex) { //xử lý ngoại lệ
                    System.out.println("SQLException: " + ex.getMessage());
                }
            } else {
                System.out.println("Loại sản phẩm không tồn tại");
            }

        } else {
            System.out.println("Sản phẩm không tồn tại");
        }
    }
    
    public static void xoa_sanpham(Connection conn) {
        Scanner sc = new Scanner(System.in);
        hienthi_sanpham(conn);
        System.out.print("Nhập mã sản phẩm muốn xóa: ");
        int ma_sanpham = sc.nextInt();

        boolean flag = false;
        flag = tontai_sanpham(conn, ma_sanpham);

        if (flag == true) {
            System.out.println("--------------------");
            System.out.println("Sản phẩm vừa chọn ");
            hienthi_sanpham(conn, ma_sanpham);
            System.out.println("Bạn có chắn chắn sẽ xóa sản phẩm vừa chọn không ?");
            System.out.println("Có(1)\t Không(0)");
            int confirm = sc.nextInt();

            if (confirm == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_sanpham(?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, ma_sanpham);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa sản phẩm thành công");
                } catch (SQLException ex) { //xử lý ngoại lệ
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Sản phẩm không tồn tại");
        }
    }
    
    public static boolean tontai_sanpham(Connection conn, int ma_sanpham) {
        PreparedStatement pstmt_check = null;
        ResultSet rs_check = null;
        boolean flag = false;

        try {
            String sql_check = "select tontai_sanpham(?) tontai_sanpham";
            pstmt_check = conn.prepareStatement(sql_check);
            pstmt_check.setInt(1, ma_sanpham);
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
