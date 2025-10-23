package kz.javazhan.sigma_finance.components;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class S3Client {
    private final Map<String, byte[]> storage = new HashMap<>();

    public String putObject(String bucket, String key, byte[] data){
        storage.put(bucket+"/"+key, data);
        return "https://tikow.s3.example.com/"+bucket+"/"+key;
    }

    public byte[] getObject(String bucket, String key){
        return storage.get(bucket+"/"+key);
    }

    public void removeObject(String bucket, String key){
        storage.remove(bucket+"/"+key);
    }
}
