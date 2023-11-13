package Main;

import java.sql.Connection;
import Main.MySQLConnect;
import java.util.Scanner;
import java.text.ParseException;

public class MainClass {

    public static void main(String args[]) throws ParseException {
        Connection conn = null;
        conn = MySQLConnect.Connect();
        int danhmuc;
        int chucnang;
        boolean flag_danhmuc = true;
        boolean flag_chucnang = true;
        String luachon;
        Scanner sc = new Scanner(System.in);

        while (flag_danhmuc) {
            System.out.println("-----------------\nDanh mục quản lý\n-----------------");
            System.out.println("(1) Quản lý sản phẩm.\n(2) Quản lý loại sản phẩm.\n(3) Quản lý lô hàng.\n"
                    + "(4) Quản lý lô hàng sản phẩm.\n(5) Thoát khỏi chương trình.");
            System.out.print("Hãy chọn danh mục bạn muốn quản lý: ");
            danhmuc = sc.nextInt();
            System.out.flush();
            if (danhmuc == 1) {
                while (flag_chucnang) {
                    System.out.println("-----------------\nQuản lý Sản Phẩm\n-----------------");
                    System.out.println("(1) Thêm sản phẩm.\n(2) Sửa sản phẩm.\n(3) Xóa sản phẩm.\n"
                            + "(4) Hiển thị danh sách sản phẩm.\n(5) Thoát khỏi danh mục.\n(6) Thoát khỏi chương trình.");
                    System.out.print("Hãy chọn chức năng bạn muốn thực hiện: ");
                    chucnang = sc.nextInt();

                    if (chucnang == 1) {
                        SanPhamClass.them_sanpham(conn);
                    } else if (chucnang == 2) {
                        SanPhamClass.sua_sanpham(conn);
                    } else if (chucnang == 3) {
                        SanPhamClass.xoa_sanpham(conn);
                    } else if (chucnang == 4) {
                        SanPhamClass.hienthi_sanpham(conn);
                        System.out.print("Hãy nhập kí tự bất kì nếu bạn đã xem xong: ");
                        sc.nextLine();
                        luachon = sc.nextLine();
                    } else if (chucnang == 5) {
                        flag_chucnang = false;
                    } else if (chucnang == 6) {
                        flag_chucnang = false;
                        flag_danhmuc = false;
                    } else {
                        System.out.println("Chức năng bạn chọn không tồn tại. Hãy chọn lại.");
                    }
                }
                flag_chucnang = true;
            } else if (danhmuc == 2) {
                while (flag_chucnang) {
                    System.out.println("-----------------\nQuản lý Loại Sản Phẩm\n-----------------");
                    System.out.println("(1) Thêm loại sản phẩm.\n(2) Sửa loại sản phẩm.\n(3) Xóa loại sản phẩm.\n"
                            + "(4) Hiển thị danh sách loại sản phẩm.\n(5) Thoát khỏi danh mục.\n(6) Thoát khỏi chương trình.");
                    System.out.print("Hãy chọn chức năng bạn muốn thực hiện: ");
                    chucnang = sc.nextInt();

                    if (chucnang == 1) {
                        LoaiSanPhamClass.them_loaisanpham(conn);
                    } else if (chucnang == 2) {
                        LoaiSanPhamClass.sua_loaisanpham(conn);
                    } else if (chucnang == 3) {
                        LoaiSanPhamClass.xoa_loaisanpham(conn);
                    } else if (chucnang == 4) {
                        LoaiSanPhamClass.hienthi_loaisanpham(conn);
                        System.out.print("Hãy nhập kí tự bất kì nếu bạn đã xem xong: ");
                        sc.nextLine();
                        luachon = sc.nextLine();
                    } else if (chucnang == 5) {
                        flag_chucnang = false;
                    } else if (chucnang == 6) {
                        flag_chucnang = false;
                        flag_danhmuc = false;
                    } else {
                        System.out.println("Chức năng bạn chọn không tồn tại. Hãy chọn lại.");
                    }
                }
                flag_chucnang = true;
            } else if (danhmuc == 3) {
                while (flag_chucnang) {
                    System.out.println("-----------------\nQuản lý Lô Hàng\n-----------------");
                    System.out.println("(1) Thêm lô hàng.\n(2) Sửa lô hàng.\n(3) Xóa lô hàng.\n"
                            + "(4) Hiển thị danh sách lô hàng.\n(5) Thoát khỏi danh mục.\n(6) Thoát khỏi chương trình.");
                    System.out.print("Hãy chọn chức năng bạn muốn thực hiện: ");
                    chucnang = sc.nextInt();

                    if (chucnang == 1) {
                        LoHangClass.them_lohang(conn);
                    } else if (chucnang == 2) {
                        LoHangClass.sua_lohang(conn);
                    } else if (chucnang == 3) {
                        LoHangClass.xoa_lohang(conn);
                    } else if (chucnang == 4) {
                        LoHangClass.hienthi_lohang(conn);
                        System.out.print("Hãy nhập kí tự bất kì nếu bạn đã xem xong: ");
                        sc.nextLine();
                        luachon = sc.nextLine();
                    } else if (chucnang == 5) {
                        flag_chucnang = false;
                    } else if (chucnang == 6) {
                        flag_chucnang = false;
                        flag_danhmuc = false;
                    } else {
                        System.out.println("Chức năng bạn chọn không tồn tại. Hãy chọn lại.");
                    }
                }
                flag_chucnang = true;
            } else if (danhmuc == 4) {
                while (flag_chucnang) {
                    System.out.println("-----------------\nQuản lý Lô Hàng Sản Phẩm\n-----------------");
                    System.out.println("(1) Thêm sản phẩm cho lô hàng.\n(2) Xóa sản phẩm lô hàng.\n"
                            + "(3) Hiển thị danh sách sản phẩm lô hàng.\n(4) Thoát khỏi danh mục.\n(5) Thoát khỏi chương trình.");
                    System.out.print("Hãy chọn chức năng bạn muốn thực hiện: ");
                    chucnang = sc.nextInt();

                    if (chucnang == 1) {
                        LoHangSanPhamClass.them_lohangsanpham(conn);
                    } else if (chucnang == 2) {
                        LoHangSanPhamClass.xoa_lohangsanpham(conn);
                    } else if (chucnang == 3) {
                        LoHangSanPhamClass.hienthi_lohangsanpham(conn);
                        System.out.print("Hãy nhập kí tự bất kì nếu bạn đã xem xong: ");
                        sc.nextLine();
                        luachon = sc.nextLine();
                    } else if (chucnang == 4) {
                        flag_chucnang = false;
                    } else if (chucnang == 5) {
                        flag_chucnang = false;
                        flag_danhmuc = false;
                    } else {
                        System.out.println("Chức năng bạn chọn không tồn tại. Hãy chọn lại.");
                    }
                }
                flag_chucnang = true;
            } else if (danhmuc == 5) {
                flag_danhmuc = false;
            } else {
                System.out.println("Danh mục bạn chọn không tồn tại. Hãy chọn lại.");
            }
        }
    }
    
}
