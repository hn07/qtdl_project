package Main;

import static Main.LoaiSanPhamClass.hienthi_loaisanpham;
import static Main.LoaiSanPhamClass.tontai_loaisanpham;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.sql.Date;
import java.text.SimpleDateFormat;


public class LoHangClass {
    
    
    public static void hienthi_lohang(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM lohang";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("------------------------------------");
            System.out.println(" Mã lô hàng\tNgày nhập vào");
            System.out.println("------------------------------------");
            while (rs.next()) {
                int rs_ma_lohang = rs.getInt("ma_lohang");
                Date rs_ngay_nhapvao = rs.getDate("ngay_nhapvao");
                System.out.printf("%-4s %-11d %s \n", ' ', rs_ma_lohang, rs_ngay_nhapvao);
            }
            System.out.println("------------------------------------");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
    
    
    public static void hienthi_lohang(Connection conn, int ma_lohang) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM lohang WHERE ma_lohang = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ma_lohang);
            rs = pstmt.executeQuery();

            System.out.println("------------------------");
            while (rs.next()) {
                int rs_ma_loaisanpham = rs.getInt("ma_lohang");
                Date rs_ngay_nhapvao = rs.getDate("ngay_nhapvao");
                System.out.println("Mã lô hàng: " + rs_ma_loaisanpham);
                System.out.println("Ngày nhập vào: " + rs_ngay_nhapvao);              
                System.out.println("--------------------");
            }
        } catch (SQLException ex) { //xử lý ngoại lệ
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
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(" Mã sản phẩm\t\tTên sản phẩm\t\t  Đơn vị sản phẩm\tMã loại sản phẩm\tTên loại sản phẩm\tMô tả loại sản phẩm");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int rs_ma_sanpham = rs.getInt("ma_sanpham");
                String rs_ten_sanpham = rs.getString("ten_sanpham");
                String rs_donvi_sanpham = rs.getString("donvi_sanpham");
                int rs_ma_loaisanpham = rs.getInt("ma_loaisanpham");
                String rs_ten_loaisanpham = rs.getString("ten_loaisanpham");
                String rs_mota_loaisanpham = rs.getString("mota_loaisanpham");
                System.out.print("        " + rs_ma_sanpham+"\t\t");
                System.out.print("" + rs_ten_sanpham + "\t\t");
                System.out.print("" + rs_donvi_sanpham + "\t\t");
                System.out.print("        " + rs_ma_loaisanpham + "\t\t");
                System.out.print("   " + rs_ten_loaisanpham + "\t\t");
                System.out.println("     " + rs_mota_loaisanpham);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
    
 
    
   public static void them_lohang(Connection conn) throws ParseException {
        hienthi_lohang(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập vào năm: ");
        int nam = sc.nextInt();
        System.out.print("Nhập vào tháng: ");
        int thang = sc.nextInt();
        System.out.print("Nhập vào ngày: ");
        int ngay = sc.nextInt();
        
        Date ngay_nhapvao = Date.valueOf(nam+"-"+thang+"-"+ngay);
        System.out.println(ngay_nhapvao);
             
        CallableStatement cstmt = null;

        try {
            String sql = "{call them_lohang(?)}";
            cstmt = conn.prepareCall(sql);
       
            cstmt.setDate(1, (java.sql.Date) ngay_nhapvao);
            cstmt.executeQuery();
          System.out.println("Đã thêm lô hàng thành công.");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
   
   public static boolean tontai_lohang(Connection conn, int ma_lohang) {
        PreparedStatement pstmt_check = null;
        ResultSet rs_check = null;
        boolean check = false;

        try {
            String sql_check = "select tontai_lohang(?) tontai_lohang";
            pstmt_check = conn.prepareStatement(sql_check);
            pstmt_check.setInt(1, ma_lohang);
            rs_check = pstmt_check.executeQuery();

            while (rs_check.next()) {
                check = rs_check.getBoolean("tontai_lohang");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }
        return check;
    }
   
    
    public static void sua_lohang(Connection conn) {
        Scanner sc = new Scanner(System.in);
        hienthi_lohang(conn);
        System.out.print("Nhập ID lô hàng muốn chỉnh sửa: ");
        int ma_lohang = sc.nextInt();
        sc.nextLine();

        boolean check = false;
        check = tontai_lohang(conn, ma_lohang);

        if (check == true) {
            System.out.println("--------------------");
            System.out.println("Mã lô hàng được chọn ");
            hienthi_lohang(conn, ma_lohang);
            System.out.print("Nhập vào năm muốn chỉnh sửa: ");
            int nam = sc.nextInt();
            System.out.print("Nhập vào tháng muốn chỉnh sửa: ");
            int thang = sc.nextInt();
            System.out.print("Nhập vào ngày muốn chỉnh sửa: ");
            int ngay = sc.nextInt();
        
            Date ngay_nhapvao = Date.valueOf(nam+"-"+thang+"-"+ngay);

            CallableStatement cstmt = null;

            try {
                String sql = "{call sua_lohang(?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, ma_lohang);
                cstmt.setDate(2, ngay_nhapvao);
                cstmt.executeQuery();
                System.out.println("Đã chỉnh sửa lô hàng thành công");
            } catch (SQLException ex) { //xử lý ngoại lệ
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Lô hàng không tồn tại");
        }
    }
        public static void xoa_lohang(Connection conn) {
        Scanner sc = new Scanner(System.in);
        hienthi_lohang(conn);
        System.out.print("Nhập mã lô hàng muốn xóa: ");
        int ma_lohang = sc.nextInt();

        boolean check = false;
        check = tontai_lohang(conn, ma_lohang);

        if (check == true) {
            System.out.println("--------------------");
            System.out.println("Mã lô hàng vừa được chọn ");
            hienthi_lohang(conn, ma_lohang);
            System.out.println("Xác nhận:");
            System.out.println("Có(1)\t Không(0)");
            int d = sc.nextInt();

            if (d == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_lohang(?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, ma_lohang);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa lô hàng thành công");
                } catch (SQLException ex) { //xử lý ngoại lệ
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Mã lô hàng không tồn tại");
        }
    }
    
    
   } 


