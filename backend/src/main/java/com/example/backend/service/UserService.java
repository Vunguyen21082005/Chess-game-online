package com.example.backend.service;

import com.example.backend.DTO.UserDTO;
import com.example.backend.moder.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, UserDTO> pendingUsers = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public String register(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // sinh ra ma ngau nhien  6 chu so
        String otp = String.valueOf((int) (Math.random() * 90000) + 100000);

        // luu thong tin tam thoi
        otpStorage.put(dto.getEmail(), otp);
        pendingUsers.put(dto.getEmail(), dto);

        // gui email chua ma otp de xac nhan
        emailService.sendOTPEmail(dto.getEmail(), otp);

        return "OTP đã được gửi tới email của bạn. Vui lòng kiểm tra email và xác nhận.";
    }

    //validate otp
    public String verifyOTP(String email, String otp) {
        if (!otpStorage.containsKey(email)) {
            return "Không tìm thấy OTP. Vui lòng đăng ký lại.";
        }

        String savedOtp = otpStorage.get(email);
        if (!savedOtp.equals(otp)) {
            return "OTP không đúng. Vui lòng kiểm tra lại.";
        }

        // lay thong tin user tam
        UserDTO dto = pendingUsers.get(email);

        //Tao User entity va luu vao db
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone_number(dto.getPhone_number());
        userRepository.save(user);

        // Xoa thong tin tam
        otpStorage.remove(email);
        pendingUsers.remove(email);

        return "Đăng ký thành công! Tài khoản của bạn đã được kích hoạt.";
    }


}
