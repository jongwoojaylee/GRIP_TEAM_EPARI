package org.example.grip_demo.profileimage;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class AwsFileService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String PROFILE_IMG_DIR = "profile/";

    @Transactional
    public String uploadFileToS3Bucket(MultipartFile file, String nickName) throws IOException {
        String fileName = PROFILE_IMG_DIR+"profile_"+nickName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    @Transactional
    public void deleteFileFromS3Bucket(String nickName){
        String fileName = PROFILE_IMG_DIR+"profile_"+nickName;
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
