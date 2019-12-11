package com.uniyaz;

import java.util.Scanner;

public class jdbcApp {
    public static void main(String[] args) {

        int secim;
        boolean devam = true;
        boolean isBaglantiHazir;
        Heroes hero = new Heroes();
        VeriTabaniIslemleri dataBase = new VeriTabaniIslemleri();
        MenuSayfasi menu = new MenuSayfasi();
        Scanner scanner = new Scanner(System.in);
        do{
            isBaglantiHazir = dataBase.baglantiyiKontrolEt();
            System.out.println("Veri tabanı bağlantısı kontrol ediliyor...");

            if (!isBaglantiHazir) {
                System.out.println("Bağlantı problemi var. Lütfen kontrol edin.");
            } else {
                System.out.println("Yapmak istediğiniz işlemin kodunu giriniz: ");
                menu.menuYazdir();
                secim = scanner.nextInt();
                if (secim == 1) {
                    scanner.nextLine();
                    System.out.println("Kahramanın Adı: ");
                    String ad = scanner.nextLine();
                    System.out.println("Kahramanın Soyadı: ");
                    String soyAd = scanner.nextLine();
                    hero.setHeroAdi(ad);
                    hero.setHeroSoyadi(soyAd);
                    dataBase.heroKaydiOlustur(hero);
                } else if (secim == 2) {
                    dataBase.heroListele();
                    System.out.println("Kayıt yapılacak kahramanın kodunu Id'sini giriniz: ");
                    int heroId = scanner.nextInt();
                    System.out.println("Filmin bütçesini giriniz: ");
                    int movieBudget = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Filmin adını giriniz: ");
                    String movieName = scanner.nextLine();
                    dataBase.movieKaydiOlustur(heroId, movieBudget, movieName);

                } else if (secim == 3) {
                    dataBase.sqlSorgusu1();
                } else if (secim == 4) {
                    dataBase.sqlSorgusu2();
                } else if (secim == 5) {
                    devam = false;
                }
            }
        }while (devam);
    }
}
