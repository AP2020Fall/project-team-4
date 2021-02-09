package Controller;

import Model.AccountRelated.*;
import Model.GameRelated.BattleSea.BattleSea;
import Model.GameRelated.Reversi.Reversi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hildan.fxgson.FxGson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.LinkedList;

import static Controller.AccountRelated.AccountController.getInstance;

public class MyGson {
	private static Gson gson;

	public static Gson getGson () {
		if (gson == null)
			initGsonAndItsBuilder();
		return gson;
	}

	private static void initGsonAndItsBuilder () {
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.setDateFormat("yyyy-MMM-dd HH:mm:ss");
//		gsonBuilder.setPrettyPrinting();
		gsonBuilder.serializeNulls();
//		gsonBuilder.registerTypeAdapter(Account.class, new AccountAdapter());
		gson = FxGson.addFxSupport(gsonBuilder)
				.create();
	}

//	public void serializeEverything () throws IOException {
//		serializeSavedLoginInfo();
//		serializeAdmin();
//		serializeGamers();
//		serializeAdminGameRecos();
//		serializeEvents();
//		serializeFrndReqs();
//		serializeMsgs();
//		serializeBattleSeaGames();
//		serializeReversiGames();
//		serializeIDGenerator();
//	}

//	public void deserializeEverything () throws IOException {
//		initGsonAndItsBuilder();
//		Admin.setAdmin(getAdmin());
//		Gamer.setGamers(getGamers());
//		AccountController.getInstance().setCurrentAccLoggedIn(getSavedLoginInfo());
//		AdminGameReco.setRecommendations(getAdminGameRecos());
//		Event.setEvents(getEvents());
//		FriendRequest.setAllfriendRequests(getFrndReqs());
//		Message.setAllMessages(getMsgs());
//		BattleSea.setAllGames(getBattleseaGames());
//		Reversi.setAllGames(getReversiGames());
//		IDGenerator.setAllIDsGenerated(getIDGenerator());
//		try {
//			String battleSeaDetails = Game.getAllGames().stream().filter(game -> game instanceof BattleSea).findFirst().get().getDetails();
//			BattleSea.setDetailsForBattlesea(battleSeaDetails);
//
//			String reversiDetails = Game.getAllGames().stream().filter(game -> game instanceof Reversi).findFirst().get().getDetails();
//			Reversi.setDetailsForReversi(reversiDetails);
//		} catch (NoSuchElementException | NullPointerException e) {
//			//e.printStackTrace();
//		}
//	}

	public static void serializeGamers (LinkedList<Gamer> gamers) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Gamer.json"))) {
			writer.write(getGson().toJson(gamers));
		}
	}

	public static LinkedList<String> getIDGenerator () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/IDGenerator.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<String>>() {}.getType());
		}
	}

	public static LinkedList<Reversi> getReversiGames () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/GameRelated/Reversi.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<Reversi>>() {}.getType());
		}
	}

	public static LinkedList<BattleSea> getBattleseaGames () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/GameRelated/BattleSea.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<BattleSea>>() {}.getType());
		}
	}

	public static LinkedList<Message> getMsgs () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Message.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<Message>>() {}.getType());
		}
	}

	public static LinkedList<FriendRequest> getFrndReqs () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/FriendRequest.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<FriendRequest>>() {}.getType());
		}
	}

	public static LinkedList<Event> getEvents () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Event.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<Event>>() {}.getType());
		}
	}

	public static Account getSavedLoginInfo () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/SavedLoginInfo.json"))) {
			boolean forAdmin = false;
			String json = reader.readLine();
			if (reader.ready()) forAdmin = json.startsWith("Admin");

			getInstance().setSaveLoginInfo(true);
			return getGson().fromJson(json, forAdmin ? Admin.class : Gamer.class);
		}
	}

	public static LinkedList<AdminGameReco> getAdminGameRecos () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/AdminGameReco.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<AdminGameReco>>() {}.getType());
		}
	}

	public static LinkedList<Gamer> getGamers () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Gamer.json"))) {
			return getGson().fromJson(reader.readLine(), new TypeToken<LinkedList<Gamer>>() {
			}.getType());
		}
	}

	public static Admin getAdmin () throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/com/Resources/JSONs/AccountRelated/Admin.json"))) {
			return getGson().fromJson(reader.readLine(), (Type) Admin.class);
		}
	}

	public static void serializeSavedLoginInfo (Account accountToLogin) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/SavedLoginInfo.json"))) {
			if (getInstance().saveLoginInfo()) { // skip if said no to remember me
				writer.write((accountToLogin instanceof Admin ? "Admin" : "Gamer") + "  ");
				writer.write(getGson().toJson(accountToLogin));
			}
		}
	}

	public static void serializeAdmin (Admin admin) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Admin.json"))) {
			writer.write(getGson().toJson(admin));
		}
	}

	public static void serializeAdminGameRecos (LinkedList<AdminGameReco> adminGameRecos) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/AdminGameReco.json"))) {
			writer.write(getGson().toJson(adminGameRecos));
		}
	}

	public static void serializeEvents (LinkedList<Event> events) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Event.json"))) {
			writer.write(getGson().toJson(events));
		}
	}

	public static void serializeFrndReqs (LinkedList<FriendRequest> friendRequests) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/FriendRequest.json"))) {
			writer.write(getGson().toJson(friendRequests));
		}
	}

	public static void serializeMsgs (LinkedList<Message> messages) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/AccountRelated/Message.json"))) {
			writer.write(getGson().toJson(messages));
		}
	}

	public static void serializeBattleSeaGames (LinkedList<BattleSea> battleSeas) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/GameRelated/BattleSea.json"))) {
			writer.write(getGson().toJson(battleSeas));
		}
	}

	public static void serializeReversiGames (LinkedList<Reversi> reversis) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/GameRelated/Reversi.json"))) {
			writer.write(getGson().toJson(reversis));
		}
	}

	public static void serializeIDGenerator (LinkedList<String> iDGenerator) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/Resources/JSONs/IDGenerator.json"))) {
			writer.write(getGson().toJson(iDGenerator));
		}
	}
}
