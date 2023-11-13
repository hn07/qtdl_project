
package Main;

import static Main.LoaiSanPhamClass.hienthi_loaisanpham;
import static Main.LoaiSanPhamClass.tontai_loaisanpham;
import static Main.SanPhamClass.hienthi_sanpham;
import static Main.SanPhamClass.tontai_sanpham;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class LoHangSanPhamClass {
        public static void hienthi_lohangsanpham(Connection conn) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM lohangsanpham";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            System.out.println("----------------------------------------------------------");
            System.out.println(" Mã lô hàng\tMã sản phẩm\tGiá nhập vào\tSố lượng");
            System.out.println("----------------------------------------------------------");
            while (rs.next()) {
                int rs_ma_lohang = rs.getInt("ma_lohang");
                int rs_ma_sanpham = rs.getInt("ma_sanpham");
                int rs_gia_nhapvao = rs.getInt("gia_nhapvao");
                int rs_so_luong = rs.getInt("so_luong");
                System.out.printf("%-5s %-14d %-10d %-15d %d\n", ' ', rs_ma_lohang, rs_ma_sanpham, rs_gia_nhapvao, rs_so_luong);
                
            }
            System.out.println("-----------------------------------------------------------");
        } catch (SQLException ex) { //xử lý ngoại lệ
            System.out.println("SQLException: " + ex.getMessage());
        }
    }
        
        
        public static void them_lohangsanpham(Connection conn) {
        hienthi_lohangsanpham(conn);
        Scanner sc = new Scanner(System.in);
        LoHangClass.hienthi_lohang(conn);
        System.out.print("Nhập mã lô hàng: ");
        int ma_lohang = sc.nextInt();
        SanPhamClass.hienthi_sanpham(conn);
        System.out.print("Nhập mã sản phẩm: ");
        int ma_sanpham = sc.nextInt();
        

        boolean check = false;
        check = SanPhamClass.tontai_sanpham(conn, ma_sanpham);
        boolean check2=false;
        check2=LoHangClass.tontai_lohang(conn, ma_lohang);

        if (check == true && check == true) {
            
            System.out.print("Nhập giá nhập sản phẩm trong lô hàng: ");
            int gia_nhapvao = sc.nextInt();
            System.out.print("Nhập số lượng sản phẩm trong lô hàng: ");
            int so_luong = sc.nextInt();
            
            CallableStatement cstmt = null;
               
            try {
                String sql = "{call them_lohangsanpham(?,?,?,?)}";
                cstmt = conn.prepareCall(sql);
                cstmt.setInt(1, ma_lohang);
                cstmt.setInt(2, ma_sanpham);
                cstmt.setInt(3, gia_nhapvao);
                cstmt.setInt(4, so_luong);
                cstmt.executeQuery();
                System.out.println("Đã thêm sản phẩm cho lô hàng thành công");
            } catch (SQLException ex) { //xử lý ngoại lệ
                System.out.println("SQLException: " + ex.getMessage());
            }
        } else {
            System.out.println("Lô hàng hoặc sản phẩm không tồn tại");
        }
    }
        
        
      public static void xoa_lohangsanpham(Connection conn) {
        hienthi_lohangsanpham(conn);
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã lô hàng phẩm muốn xóa: ");
        int ma_lohang = sc.nextInt();
        System.out.print("Nhập mã sản phẩm muốn xóa: ");
        int ma_sanpham = sc.nextInt();

        boolean check = false;
        check = SanPhamClass.tontai_sanpham(conn, ma_sanpham);
        boolean check2=false;
        check2=LoHangClass.tontai_lohang(conn, ma_lohang);
        
        if (check == true && check2 == true) {
            System.out.println("--------------------");
//            System.out.println("Lô hàng sản phẩm vừa chọn ");
//            hienthi_lohangsanpham(conn, ma_sanpham);
            System.out.println("Xác nhận");
            System.out.println("Có(1)\t Không(0)");
            int t = sc.nextInt();

            if (t == 1) {
                CallableStatement cstmt = null;

                try {
                    String sql = "{call xoa_lohangsanpham(?,?)}";
                    cstmt = conn.prepareCall(sql);
                    cstmt.setInt(1, ma_lohang);
                    cstmt.setInt(2, ma_sanpham);
                    cstmt.executeQuery();
                    System.out.println("Đã xóa lô hàng sản phẩm thành công");
                } catch (SQLException ex) { //xử lý ngoại lệ
                    System.out.println("SQLException: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Lô hàng không tồn tại");
        }
    }
}
