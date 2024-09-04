package org.example.grip_demo.profileimage;

import lombok.RequiredArgsConstructor;
import org.example.grip_demo.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
    private final ProfileImageRepository profileImageRepository;

    public ProfileImage saveProfileURL(User user, String url){
        ProfileImage profileImage = new ProfileImage();
        profileImage.setUser(user);
        profileImage.setPath(url);
        profileImageRepository.save(profileImage);

        return profileImage;
    }
    @Transactional
    public Optional<ProfileImage> findProfileImgByUserId(Long userId){
        Optional<ProfileImage> profileImage= profileImageRepository.findByUserId(userId);
        return profileImage;
    }
    @Transactional
    public void deleteProfileImageByUserId(Long userId){
        profileImageRepository.deleteByUserId(userId);
    }
    @Transactional
    public boolean isProfileImgExists(Long userId){
        Optional<ProfileImage> profileImage = findProfileImgByUserId(userId);
        return profileImage.isPresent();

    }
}
