### 1. Luồng hoạt động tổng quát (Main Menu)

Chương trình chạy vòng lặp console với 8 chức năng chính và 1 lựa chọn thoát

1. Register customers (Đăng ký khách hàng)
2. Update customer information (Cập nhật thông tin khách hàng)
3. Search customer information by name (Tìm kiếm khách hàng theo tên)
4. Display feast menus (Hiển thị thực đơn mâm cỗ)
5. Place a feast order (Đặt mâm cỗ tiệc)
6. Update order information (Cập nhật thông tin đơn hàng)
7. Save data to file (Lưu dữ liệu ra file)
8. Display Customer or Order lists (Hiển thị danh sách khách hàng hoặc đơn hàng)

- Khác Thoát chương trình.

---

### 2. Chi tiết yêu cầu từng chức năng (Requirements)

- Function 1 Register customers
  - Nhập thông tin khách hàng mới gồm
    - Mã KH (ID) Chuỗi 5 ký tự, bắt đầu bằng `C`, `G`, hoặc `K`, theo sau là 4 chữ số (VD `C1001`, `K1234`). Phải là duy nhất.
    - Tên (Name) Không được để trống, độ dài từ 2 đến 25 ký tự.
    - Số điện thoại (Phone) Đúng 10 chữ số, thuộc các đầu số nhà mạng hợp lệ tại Việt Nam.
    - Email Đúng định dạng email tiêu chuẩn (`...@...`).
  - Lưu vào hệ thống và tự động lưu file.

- Function 2 Update customer information
  - Nhập mã KH cần sửa. Nếu không tồn tại $rightarrow$ Báo lỗi `This customer does not exist.`.
  - Cho phép nhập mới Tên, SĐT, Email (nếu để trống thì giữ nguyên thông tin cũ).
  - Validate dữ liệu nhập mới theo đúng chuẩn như Function 1.

- Function 3 Search customer information by name
  - Cho phép nhập tên hoặc một phần tên (không phân biệt hoa thường).
  - Hiển thị danh sách kết quả (nếu có nhiều người trùng tên, sắp xếp theo tên chữ cái A-Z).
  - Nếu không tìm thấy $rightarrow$ Báo đúng thông báo `No one matches the search criteria!`.

- Function 4 Display feast menus
  - Đọc file chứa thực đơn (`feastMenu.txt`).
  - Hiển thị danh sách các set menu được sắp xếp tăng dần theo giá (Price).
  - In rõ chi tiết từng set menu Mã, Tên, Giá (đã format dấu phẩy), và các món ăn chia theo nhóm (Khai vị, Món chính, Tráng miệng). Nếu file lỗikhông đọc được $rightarrow$ Báo `Cannot read data from feastMenu.txt. Please check it.`.

- Function 5 Place a feast order
  - Điều kiện bắt buộc
    - Mã KH phải tồn tại trong danh sách đã đăng ký.
    - Mã Set Menu phải có trong thực đơn (`feastMenu.txt`).
    - Số lượng bàn (`Number of tables`) phải là số nguyên $ 0$.
    - Ngày tổ chức tiệc (`Event date`) phải là ngày trong tương lai (`LocalDate.now()`).
  - Kiểm tra trùng lặp (Duplicate) Một khách hàng không được đặt trùng một Set Menu trong cùng một ngày. Nếu trùng $rightarrow$ Báo `Dupplicate data!`.
  - Xử lý Tự động sinh `Order ID` (tăng dần), tính tổng tiền (`Total cost = Price of SetMenu  Number of tables`), hiển thị thông tin phiếu đặt tiệc và lưu file.

- Function 6 Update order information
  - Nhập `Order ID`. Nếu không tồn tại $rightarrow$ Báo `This Order does not exist.`.
  - Ràng buộc thời gian Không cho phép cập nhật đơn hàng có ngày tổ chức nằm trong quá khứ (`Event date  current date`).
  - Cho phép sửa Mã Set menu mới, Số lượng bàn mới, Ngày tổ chức mới (để trống giữ nguyên). Validate lại toàn bộ và kiểm tra trùng lặp đơn hàng. Tự động tính lại giá tiền nếu đổi Set menusố bàn.

- Function 7 Save data to file
  - Thu thập toàn bộ dữ liệu hiện tại trong RAM và ghi đè vào file (`customer.txt`, `order.txt` hoặc định dạng `.dat` tùy yêu cầu cụ thể của giảng viên).
  - In thông báo thành công.

- Function 8 Display Customer or Order lists
  - Menu con cho phép chọn Xem danh sách Khách hàng (sắp xếp theo tên A-Z) hoặc Danh sách Đơn hàng (sắp xếp theo ngày tổ chức tiệc tăng dần).
  - Nếu danh sách trống $rightarrow$ Báo `No data in the system.`.

---

### Tóm lại điểm ăn tiền khi chấm bài của các thầy FPTU

1. Validation cực kỳ ngặt nghèo Thầy cô sẽ cố tình nhập sai định dạng ID, SĐT không đúng nhà mạng, ngày ở quá khứ, hoặc mã KH không tồn tại khi đặt tiệc. Code phải bắt ngoại lệ (`try-catch`) mượt mà, không được crash (văng exception đỏ lòm).
2. Thông báo lỗi (Message matching) Các câu thông báo như `This customer does not exist.`, `No one matches the search criteria!`, `Dupplicate data!` phải khớp chính xác từng ký tự vì nhiều tool chấm tự động (Auto-grader) quét chuỗi output này.
3. Sắp xếp (Sorting) Khách hàng phải sắp xếp theo tên A-Z, thực đơn và đơn hàng phải sắp xếp theo giá  ngày tháng theo đúng yêu cầu từng chức năng.

---
