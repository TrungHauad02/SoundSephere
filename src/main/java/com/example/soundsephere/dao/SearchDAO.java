package com.example.soundsephere.dao;

public class SearchDAO {
    private static final String selectAllSongs = "SELECT * FROM songs";

//    public List<Song> selectAllSongsToDisplay_Recommend() {
//
//        List<Song> listBPQ = new ArrayList<>();
//
//        try (Connection connection = JDBCUtils.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(selectAllSongs);) {
//            System.out.println(preparedStatement);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                String id = rs.getString("MaPQ");
//                String title = rs.getString("MaNV");
//
//
//                listBPQ.add(new Song());
//            }
//        } catch (SQLException exception) {
//            HandleException.printSQLException(exception);
//        }
//        return listBPQ;
//    }
}
