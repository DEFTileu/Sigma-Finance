package kz.javazhan.sigma_finance.components;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
public class S3Adapter implements FileStorage {
    private final S3Client s3Client;
    private final String bucket;

    public S3Adapter(S3Client s3Client) {
        this.s3Client = s3Client;
        this.bucket = "my-app-bucket";
    }


    @Override
    public String store(String path, byte[] data) {
        Objects.requireNonNull(path);
        return s3Client.putObject(bucket, path, data);
    }

    @Override
    public byte[] load(String path) {
        return s3Client.getObject(bucket, normalizeKey(path));
    }

    @Override
    public void delete(String path) {
        s3Client.removeObject(bucket, normalizeKey(path));
    }

    private String normalizeKey(String path){
        return path.replace("\\","/").replaceAll("^/+","");
    }
}
