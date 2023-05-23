package tv;

import tv.util.SqlBuilder;

public class Test {
    public static void main(String[] args) {
        System.out.println(new SqlBuilder().update("test").set("id", "name").where("id").buildUpdate());
        System.out.println(new SqlBuilder().insert("test").setInsert("id", "name").buildInsert());
        System.out.println(new SqlBuilder().delete("test").where("id").buildDelete());
        System.out.println(new SqlBuilder().select("id", "name").from("test").where("!id", "AND", "name").buildSelect());
        String sqlToInsert = new SqlBuilder().insert("nft.nft_auction").
                setInsert("nftId", "highest_bid", "end_time", "highest_bidder").
                buildInsert();
        System.out.println(sqlToInsert);
    }
}
