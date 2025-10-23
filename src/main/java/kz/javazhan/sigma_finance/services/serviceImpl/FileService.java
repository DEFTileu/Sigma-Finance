package kz.javazhan.sigma_finance.services.serviceImpl;

import kz.javazhan.sigma_finance.components.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileStorage storage;


    public String uploadFile(String path, byte[] data){
        return storage.store(path, data);
    }

    public byte[] downloadFile(String path){
        return storage.load(path);
    }

    public void removeFile(String key){
        storage.delete(key);
    }
}
