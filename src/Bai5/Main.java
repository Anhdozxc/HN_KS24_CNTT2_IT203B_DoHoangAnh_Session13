package Bai5;
import java.util.Scanner;
public class Main {
    /*Phân tích rủi ro & bẫy lỗi
- Nhập sai kiểu dữ liệu
  Ví dụ nhập chữ "năm trăm" vào tiền sẽ gây lỗi chương trình
- Nhập mã giường không tồn tại
  Khi update sẽ không có dòng nào bị ảnh hưởng -> dữ liệu sai
- Chọn giường đã có người
  Nếu không kiểm tra sẽ bị trùng giường
- Thêm bệnh nhân thành công nhưng update giường bị lỗi
  Gây mất đồng bộ dữ liệu
- Mất kết nối database giữa chừng
  Dữ liệu có thể bị dang dở
=> Cần validate dữ liệu và dùng transaction + rollback
-- Thiết kế kiến trúc và luồng xử lý (Không bắt buộc vẽ nên em gạch đầu dòng)
- Nhập thông tin bệnh nhân từ bàn phím
- Kiểm tra dữ liệu hợp lệ
- Kết nối database
- Tắt auto commit để bắt đầu transaction
- Kiểm tra trạng thái giường
- Thêm bệnh nhân vào bảng Patients
- Cập nhật trạng thái giường thành DA_CO_NGUOI
- Thêm dữ liệu vào bảng Finance
- Nếu tất cả thành công thì commit
- Nếu có lỗi thì rollback
- Đóng kết nối database
Cấu trúc database
- Bảng Patients
  id, name, age, bed_id
- Bảng Beds
  bed_id, status
- Bảng Finance
  id, patient_id, amount
*/
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BenhNhanController controller = new BenhNhanController();

        while (true) {
            System.out.println("1. Xem giường trống");
            System.out.println("2. Tiếp nhận bệnh nhân");
            System.out.println("3. Thoát");

            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.println("Chức năng này chưa làm");
            }

            else if (choice.equals("2")) {

                try {
                    System.out.print("Tên: ");
                    String name = sc.nextLine();

                    System.out.print("Tuổi: ");
                    int age = Integer.parseInt(sc.nextLine());

                    System.out.print("Mã giường: ");
                    int bed = Integer.parseInt(sc.nextLine());

                    System.out.print("Tiền: ");
                    double money = Double.parseDouble(sc.nextLine());

                    controller.tiepNhan(name, age, bed, money);

                } catch (Exception e) {
                    System.out.println("Nhập sai dữ liệu, vui lòng nhập lại");
                }
            }
            else if (choice.equals("3")) {
                System.out.println("Thoát chương trình");
                break;
            }
        }
    }
}