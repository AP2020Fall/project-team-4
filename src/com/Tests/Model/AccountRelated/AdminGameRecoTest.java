//package Model.AccountRelated;
//
//import Model.GameRelated.BattleSea.BattleSea;
//import Model.GameRelated.Reversi.Reversi;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedList;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assumptions.assumeTrue;
//
//public class AdminGameRecoTest {
//	@AfterAll
//	@BeforeAll
//	public static void clearRecos () {
//		AdminGameReco.setRecommendations(new LinkedList<>());
//		Account.setAccounts(new LinkedList<>());
//	}
//
//	@Test
//	public void addAdminGameRecoTest () {
//		Account.addAccount(Gamer.class, null, "1", "1", "1", "1", "1@gmail.com", "00011122233", 1);
//		assumeTrue(Account.accountExists("1"));
//		assumeTrue(Account.getAccount("1") instanceof Gamer);
//		Gamer gamer = (Gamer) Account.getAccount("1");
//		AdminGameReco.addReco(Reversi.class.getSimpleName(), gamer);
//
//		assumeTrue(AdminGameReco.recommendationExists(gamer, Reversi.class.getSimpleName()));
//
//		assertEquals(1, AdminGameReco.getRecommendations().size());
//		assertEquals(1, AdminGameReco.getRecommendations(gamer).size());
//		assertEquals(Reversi.class.getSimpleName(), AdminGameReco.getRecommendations(gamer).get(0).getGameName());
//
//		AdminGameReco.addReco(BattleSea.class.getSimpleName(), gamer);
//
//		assumeTrue(AdminGameReco.recommendationExists(gamer, BattleSea.class.getSimpleName()));
//
//		assertEquals(2, AdminGameReco.getRecommendations().size());
//		assertEquals(2, AdminGameReco.getRecommendations(gamer).size());
//		assertEquals(BattleSea.class.getSimpleName(), AdminGameReco.getRecommendations(gamer).get(0).getGameName());
//		assertEquals(Reversi.class.getSimpleName(), AdminGameReco.getRecommendations(gamer).get(1).getGameName());
//
//		Account.addAccount(Gamer.class, null, "2", "2", "2", "2", "2@gmail.com", "00011122233", 1);
//
//		assumeTrue(Account.accountExists("2"));
//		assumeTrue(Account.getAccount("2") instanceof Gamer);
//
//		Gamer gamer2 = (Gamer) Account.getAccount("2");
//		AdminGameReco.addReco(Reversi.class.getSimpleName(), gamer2);
//
//		assumeTrue(AdminGameReco.recommendationExists(gamer2, Reversi.class.getSimpleName()));
//
//		assertEquals(3, AdminGameReco.getRecommendations().size());
//		assertEquals(1, AdminGameReco.getRecommendations(gamer2).size());
//		assertEquals(Reversi.class.getSimpleName(), AdminGameReco.getRecommendations(gamer2).get(0).getGameName());
//		assertTrue(AdminGameReco.recommendationExists(gamer, BattleSea.class.getSimpleName()));
//		assertTrue(AdminGameReco.recommendationExists(gamer, Reversi.class.getSimpleName()));
//		assertTrue(AdminGameReco.recommendationExists(gamer2, Reversi.class.getSimpleName()));
//	}
//
//	@Test
//	public void removeAdminGameRecoTest () {
//		assumeTrue(Account.accountExists("1"));
//		Gamer gamer = (Gamer) Account.getAccount("1");
//		assumeTrue(AdminGameReco.recommendationExists(gamer, BattleSea.class.getSimpleName()));
//		AdminGameReco.removeReco(AdminGameReco.getRecommendation(gamer, BattleSea.class.getSimpleName()).getRecoID());
//		assertFalse(AdminGameReco.recommendationExists(gamer, BattleSea.class.getSimpleName()));
//
//		assumeTrue(AdminGameReco.recommendationExists(gamer, Reversi.class.getSimpleName()));
//		AdminGameReco.removeReco(AdminGameReco.getRecommendation(gamer, Reversi.class.getSimpleName()).getRecoID());
//		assertFalse(AdminGameReco.recommendationExists(gamer, Reversi.class.getSimpleName()));
//
//		assumeTrue(Account.accountExists("2"));
//		gamer = (Gamer) Account.getAccount("2");
//		assumeTrue(AdminGameReco.recommendationExists(gamer, Reversi.class.getSimpleName()));
//		AdminGameReco.removeReco(AdminGameReco.getRecommendation(gamer, Reversi.class.getSimpleName()).getRecoID());
//		assertFalse(AdminGameReco.recommendationExists(gamer, Reversi.class.getSimpleName()));
//
//		assertEquals(0, AdminGameReco.getRecommendations().size());
//	}
//}
