package io.prep.infrastructure.filestorage;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.prep.core.util.UniqueIdentityGenerator;
import io.prep.infrastructure.exception.ErrorCode;
import io.prep.infrastructure.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AwsS3StorageAdapter implements FileStorage {

    private static final String S3_FILE_URL_FORMAT = "https://%s.s3.%s.amazonaws.com/%s";
    private final AmazonS3 s3Client;
    @Value("${aws.prep.infrastructure.bucket}")
    private String bucket;

    private final static Logger LOGGER = LoggerFactory.getLogger(AwsS3StorageAdapter.class);

    @Override
    public String upload(MultipartFile file) {
        // TODO: unique한 이름을 사용할 지, 이걸 기준으로 버전관리를 할지 판단해야함.
        String uniqueFileName = UniqueIdentityGenerator.generate().toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            s3Client.putObject(new PutObjectRequest(bucket,
                                                    uniqueFileName,
                                                    file.getInputStream(),
                                                    objectMetadata));

            String fileUrl = S3_FILE_URL_FORMAT.formatted(bucket,
                                                          s3Client.getRegionName(),
                                                          uniqueFileName);
            return fileUrl;
        } catch (IOException | SdkClientException exception) {
            LOGGER.error(exception.getMessage());
            throw new InfrastructureException(ErrorCode.FAILED_SAVE);
        }
    }
}
