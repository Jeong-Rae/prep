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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = UniqueIdentityGenerator.generate().toString();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            String encodedFileName = URLEncoder.encode(uniqueFileName, StandardCharsets.UTF_8);
            s3Client.putObject(new PutObjectRequest(bucket,
                                                    uniqueFileName,
                                                    file.getInputStream(),
                                                    objectMetadata));

            String fileUrl = S3_FILE_URL_FORMAT.formatted(bucket,
                                                          s3Client.getRegionName(),
                                                          uniqueFileName);
            LOGGER.info("URL: {}, OriginFileName: {}", fileUrl, originalFilename);
            return fileUrl;
        } catch (IOException | SdkClientException exception) {
            LOGGER.error(exception.getMessage());
            throw new InfrastructureException(ErrorCode.FAILED_SAVE);
        }
    }
}
