package kz.javazhan.sigma_finance.components;

public interface FileStorage {

    String store(String path, byte[] data);

    byte[] load(String path);

    void delete(String path);
}
