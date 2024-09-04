package org.example.grip_demo.profileimage;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProfileImageController {
    private final AwsFileService awsFileService;
    private final UserService userService;
    private final ProfileImageService profileImageService;

    @PostMapping("/upload")
    public String uploadProfileImage(@RequestParam("nickName") String nickName,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam("id") Long userId) {
        try {
            //S3 bucket에 이미 profile 이미지가 있는지 확인
            //존재할 경우 파일 삭제
            if (profileImageService.isProfileImgExists(userId)) {
                profileImageService.deleteProfileImageByUserId(userId);
                awsFileService.deleteFileFromS3Bucket(nickName);

            }

            //존재하지 않을 경우
            // S3 서비스에서 생성자에 닉네임을 전달하고 파일을 업로드.
            String url = awsFileService.uploadFileToS3Bucket(file, nickName);
            User user = userService.findUserById(userId).orElse(null);
            if(user == null){
                throw new RuntimeException();
            }
            profileImageService.saveProfileURL(user, url);

            return "redirect:userinfo/"+ userId;
        } catch (Exception e) {
            return "Upload failed: " + e.getMessage();
        }
    }
}
