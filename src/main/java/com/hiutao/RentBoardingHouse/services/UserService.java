package com.hiutao.RentBoardingHouse.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.hiutao.RentBoardingHouse.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    public User getAuth(String username) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Thực hiện truy vấn lấy người dùng dựa trên username
        DocumentReference userDocument = dbFirestore.collection("User").document(username);
        ApiFuture<DocumentSnapshot> future = userDocument.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // Chuyển đổi document thành đối tượng User (nếu tìm thấy)
            User user = document.toObject(User.class);
            return user;
        } else {
            // Nếu không tìm thấy user với username tương ứng
            return null;
        }
    }

    public String createUser(User user) throws ExecutionException, InterruptedException {
        // Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword); // Cập nhật mật khẩu đã mã hóa vào đối tượng User

        // Lấy Firestore instance từ FirestoreClient
        Firestore dbFirestore = FirestoreClient.getFirestore();

        // Chuẩn bị dữ liệu để lưu vào Firestore
        DocumentReference docRef = dbFirestore.collection("User").document(user.getUsername());

        // Thực hiện lưu dữ liệu user vào Firestore
        ApiFuture<WriteResult> result = docRef.set(user);

        return "Update time:  "+ result.get().getUpdateTime(); // Trả về user vừa được tạo
    }
    public boolean validateUser(String username, String password) throws ExecutionException, InterruptedException {
        User user = getAuth(username); // Lấy thông tin người dùng từ Firestore

        if (user != null) {
            // So sánh mật khẩu đã nhập với mật khẩu đã mã hóa
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false; // Nếu không tìm thấy người dùng, trả về false
    }
}
