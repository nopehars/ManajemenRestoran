/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.manajemenrestoran;

/**
 *
 * @author Nova Harsanto
 * Nim : 044876398
 * Prodi : Sistem Informasi
 * UPBJJ : UT Yogyakarta
 * TUGAS # MSIM4301 Pemrograman berbasis dekstop
 */

import java.io.*;
import java.util.*;

// Abstract class MenuItem
abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public abstract void tampilMenu();
}

// Kelas Makanan
class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Makanan: " + getNama() + ", Harga: " + getHarga() + ", Jenis: " + jenisMakanan);
    }
}

// Kelas Minuman
class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Minuman: " + getNama() + ", Harga: " + getHarga() + ", Jenis: " + jenisMinuman);
    }
}

// Kelas Diskon
class Diskon extends MenuItem {
    private double diskon;

    public Diskon(String nama, double harga, double diskon) {
        super(nama, harga, "Diskon");
        this.diskon = diskon;
    }

    public double getDiskon() {
        return diskon;
    }

    @Override
    public void tampilMenu() {
        System.out.println("Diskon: " + getNama() + ", Potongan: " + diskon + "%");
    }
}

// Kelas Menu
class Menu {
    private List<MenuItem> daftarMenu = new ArrayList<>();

    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
    }

    public void tampilkanMenu() {
        System.out.println("=== Daftar Menu ===");
        for (MenuItem item : daftarMenu) {
            item.tampilMenu();
        }
    }

    public MenuItem cariItem(String nama) throws Exception {
        for (MenuItem item : daftarMenu) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        throw new Exception("Item tidak ditemukan dalam menu.");
    }

    public List<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }
}

// Kelas Pesanan
class Pesanan {
    private List<MenuItem> daftarPesanan = new ArrayList<>();

    public void tambahkanPesanan(MenuItem item) {
        daftarPesanan.add(item);
    }

    public double hitungTotal() {
        double total = 0;
        for (MenuItem item : daftarPesanan) {
            if (item instanceof Diskon) {
                total -= total * (((Diskon) item).getDiskon() / 100);
            } else {
                total += item.getHarga();
            }
        }
        return total;
    }

    public void tampilkanStruk() {
        System.out.println("=== Struk Pesanan ===");
        for (MenuItem item : daftarPesanan) {
            item.tampilMenu();
        }
        System.out.println("Total Biaya: " + hitungTotal());
    }
}

// Kelas Utama
public class ManajemenRestoran {
    private static Menu menu = new Menu();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                tampilkanMenuUtama();
                int pilihan = Integer.parseInt(scanner.nextLine());

                switch (pilihan) {
                    case 1 -> tambahMenuItem();
                    case 2 -> menu.tampilkanMenu();
                    case 3 -> buatPesanan();
                    case 4 -> simpanMenuKeFile();
                    case 5 -> muatMenuDariFile();
                    case 6 -> {
                        System.out.println("Keluar dari program.");
                        return;
                    }
                    default -> System.out.println("Pilihan tidak valid.");
                }
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private static void tampilkanMenuUtama() {
        System.out.println("=== Manajemen Restoran ===");
        System.out.println("1. Tambah Item Menu");
        System.out.println("2. Tampilkan Menu");
        System.out.println("3. Buat Pesanan");
        System.out.println("4. Simpan Menu ke File");
        System.out.println("5. Muat Menu dari File");
        System.out.println("6. Keluar");
        System.out.print("Pilihan Anda: ");
    }

    private static void tambahMenuItem() {
        System.out.println("Pilih jenis item:");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        int jenis = Integer.parseInt(scanner.nextLine());

        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Harga: ");
        double harga = Double.parseDouble(scanner.nextLine());

        switch (jenis) {
            case 1 -> {
                System.out.print("Jenis Makanan: ");
                String jenisMakanan = scanner.nextLine();
                menu.tambahItem(new Makanan(nama, harga, jenisMakanan));
            }
            case 2 -> {
                System.out.print("Jenis Minuman: ");
                String jenisMinuman = scanner.nextLine();
                menu.tambahItem(new Minuman(nama, harga, jenisMinuman));
            }
            case 3 -> {
                System.out.print("Diskon (%): ");
                double diskon = Double.parseDouble(scanner.nextLine());
                menu.tambahItem(new Diskon(nama, harga, diskon));
            }
            default -> System.out.println("Jenis tidak valid.");
        }
    }

    private static void buatPesanan() throws Exception {
        Pesanan pesanan = new Pesanan();

        while (true) {
            System.out.print("Masukkan nama item (ketik 'selesai' untuk selesai): ");
            String nama = scanner.nextLine();

            if (nama.equalsIgnoreCase("selesai")) break;

            try {
                MenuItem item = menu.cariItem(nama);
                pesanan.tambahkanPesanan(item);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        pesanan.tampilkanStruk();
    }

    private static void simpanMenuKeFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("menu.dat"))) {
            oos.writeObject(menu.getDaftarMenu());
            System.out.println("Menu berhasil disimpan.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan menu: " + e.getMessage());
        }
    }

    private static void muatMenuDariFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("menu.dat"))) {
            List<MenuItem> daftarMenu = (List<MenuItem>) ois.readObject();
            daftarMenu.forEach(menu::tambahItem);
            System.out.println("Menu berhasil dimuat.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }
    }
}

