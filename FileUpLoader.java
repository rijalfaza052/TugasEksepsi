/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tugas3;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author faza
 */
public class FileUpLoader {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // ganti dengan IP server
        int port = 5000;                    // port server
        String filePath = "data.txt";       // file lokal yang akan diunggah

        // ✅ Versi dengan try-with-resources (lebih direkomendasikan)
        try (Socket socket = new Socket(serverAddress, port);
             FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             OutputStream os = socket.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            System.out.println("Mengunggah file ke server...");

            while ((bytesRead = bis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.flush();
            System.out.println("Upload selesai!");

        } catch (FileNotFoundException e) {
            System.err.println("File tidak ditemukan: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan jaringan atau I/O: " + e.getMessage());
        }
        // Tidak perlu finally karena try-with-resources otomatis menutup resource

        // ✅ Alternatif dengan try-catch-finally (lebih manual)
        FileInputStream fis = null;
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, port);
            fis = new FileInputStream(filePath);
            OutputStream os = socket.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            System.out.println("Upload selesai!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (fis != null) fis.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Gagal menutup resource: " + e.getMessage());
            }
        }
    }
}
