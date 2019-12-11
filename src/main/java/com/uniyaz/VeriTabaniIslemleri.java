package com.uniyaz;

import java.sql.*;

public class VeriTabaniIslemleri {

    final private String JDBC_CONNECTION_STR = "jdbc:mysql://127.0.0.1:3306/jdbc";
    final private String USERNAME = "root";
    final private String PASSWORD = "******"; //PAROLA
    boolean baglantiyiKontrolEt(){
        try (Connection conn = DriverManager.getConnection (JDBC_CONNECTION_STR, USERNAME, PASSWORD)) {
        if (conn != null) {
            return true;
        } else {
            System.out.println("Failed to make connection!");
        }
    } catch (SQLException e) {
        System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
    }
        return false;
    }

    public void heroKaydiOlustur(Heroes hero){

        String sql = "insert into hero (name, surname) " +
                     "values (?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, hero.getHeroAdi());
            preparedStatement.setString(2, hero.getHeroSoyadi());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void heroListele(){

        String sql = "Select * from hero";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String adi = resultSet.getString("name");
                String soyadi = resultSet.getString("surname");

                System.out.printf("%d - %s - %s \n", id, adi, soyadi);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void movieKaydiOlustur(int heroId, int movieBudget, String movieName){

        String sql = "insert into movie (hero_id, movie, budget) " +
                     "values (?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, heroId);
            preparedStatement.setString(2, movieName);
            preparedStatement.setInt(3, movieBudget);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sqlSorgusu1(){

        String sql = "select concat(h.name, ' ',h.surname) as hero, " +
                    "ifnull(sum(m.budget),0) as total_budget "+
                    "from hero h left join movie m " +
                    "on h.id = m.hero_id "+
                    "group by h.name "+
                    "order by budget desc";
        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String heroAdSoyad = resultSet.getString("hero");
                int total_budget = resultSet.getInt("total_budget");
                System.out.printf("%s - %d \n", heroAdSoyad, total_budget);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sqlSorgusu2(){

        String sql = "select concat(h.name, ' ',h.surname) as hero, "+
                     "count(m.movie) as movie_count "+
                     "from hero h inner join movie m "+
                     "on h.id = m.hero_id "+
                     "group by h.name "+
                     "order by movie_count desc";
        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String heroAdSoyad = resultSet.getString("hero");
                int movie_count = resultSet.getInt("movie_count");
                System.out.printf("%s - %d \n", heroAdSoyad, movie_count);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
