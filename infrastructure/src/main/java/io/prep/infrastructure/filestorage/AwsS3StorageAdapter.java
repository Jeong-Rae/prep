package io.prep.infrastructure.filestorage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.prep.infrastructure.exception.ErrorCode;
import io.prep.infrastructure.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AwsS3StorageAdapter implements FileStorage {

    private static final String S3_FILE_URL_FORMAT = "https://%s.s3.%s.amazonaws.com/%s";
    private final AmazonS3 s3Client;
    @Value("${aws.prep.infra.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            s3Client.putObject(new PutObjectRequest(bucket,
                                                    fileName,
                                                    file.getInputStream(),
                                                    objectMetadata).withCannedAcl(
                    CannedAccessControlList.PublicRead));

            String fileUrl = S3_FILE_URL_FORMAT.formatted(bucket,
                                                          s3Client.getRegionName(),
                                                          fileName);
            return fileUrl;
        } catch (IOException | SdkClientException exception) {
            throw new InfrastructureException(ErrorCode.FAILED_SAVE);
        }
    }
}
