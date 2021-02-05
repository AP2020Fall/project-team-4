package Controller.TypeAdapters.Account;

import Model.AccountRelated.Account;
import Model.AccountRelated.Admin;
import Model.AccountRelated.Gamer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class AccountAdapter extends TypeAdapter<Account> {

	@Override
	public void write (JsonWriter writer, Account account) throws IOException {
		System.out.println("fuck "+account.getUsername());
		writer.beginObject();
		writer.name("firstName");
		writer.value(account.getFirstName());
		writer.name("lastName");
		writer.value(account.getLastName());
		writer.name("username");
		writer.value(account.getUsername());
		writer.name("password");
		writer.value(account.getPassword());
		writer.name("userID");
		writer.value(account.getUserID());
		writer.name("email");
		writer.value(account.getEmail());
		writer.name("phoneNum");
		writer.value(account.getPhoneNum());
		writer.name("pfpUrl");
		writer.value(account.getPfpUrl());
		if (account instanceof Gamer) {
			Gamer gamer = (Gamer) account;
			writer.name("awardsFromEvents");
			writer.value(gamer.getAwardsFromEvents());
			writer.name("money");
			writer.value(gamer.getMoney());
			writer.name("accountStartDate");
			writer.value(gamer.getAccountStartDate().format(DateTimeFormatter.BASIC_ISO_DATE));
			writer.name("frnds");
			System.out.println("fuck " + gamer.getUsername());
			writer.beginArray();
			for (String frnd : gamer.getFrnds()) {
				System.out.println("fuck frnd of " + gamer.getUsername());
				writer.value(frnd);
			}
			writer.endArray();
			writer.name("faveGames");
			writer.beginArray();
			for (String faveGame : gamer.getFaveGames()) {
				System.out.println("fuck faveGame of " + gamer.getUsername());
				writer.value(faveGame);
			}
			writer.endArray();
		}
		writer.endObject();
	}

	@Override
	public Account read (JsonReader reader) throws IOException {
		reader.beginObject();
		Account account;

		if (reader.peek().toString().equals(Admin.class.getSimpleName()))
			account = new Admin();
		else
			account = new Gamer();

		String fieldName = null;

		while (reader.hasNext()) {
			JsonToken token = reader.peek();

			if (token.equals(JsonToken.NAME))
				fieldName = reader.nextName();

			if ("firstName".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setFirstName(reader.nextString());
			}

			if ("lastName".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setLastName(reader.nextString());
			}

			if ("username".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setUsername(reader.nextString());
			}

			if ("password".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setPassword(reader.nextString());
			}

			if ("userID".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setUserID(reader.nextString());
			}

			if ("email".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setEmail(reader.nextString());
			}

			if ("phoneNum".equals(fieldName)) {
				//move to next token
				token = reader.peek();
				account.setPhoneNum(reader.nextString());
			}

			if (account instanceof Gamer) {
				if ("awardsFromEvents".equals(fieldName)) {
					//move to next token
					token = reader.peek();
					((Gamer) account).setAwardsFromEvents(reader.nextInt());
				}

				if ("money".equals(fieldName)) {
					//move to next token
					token = reader.peek();
					((Gamer) account).setMoney(reader.nextDouble());
				}

				if ("accountStartDate".equals(fieldName)) {
					//move to next token
					token = reader.peek();
					((Gamer) account).setAccountStartDate(LocalDate.parse(reader.nextString(), DateTimeFormatter.BASIC_ISO_DATE));
				}

				if ("frnds".equals(fieldName)) {
					//move to next token
					token = reader.peek();
					LinkedList<String> frnds = new LinkedList<>();
					while (reader.peek() != JsonToken.END_ARRAY)
						frnds.add(reader.nextString());
					((Gamer) account).setFrnds(frnds);
				}

				if ("faveGames".equals(fieldName)) {
					//move to next token
					token = reader.peek();
					LinkedList<String> faveGames = new LinkedList<>();
					while (reader.peek() != JsonToken.END_ARRAY)
						faveGames.add(reader.nextString());
					((Gamer) account).setFaveGames(faveGames);
				}
			}
		}

		reader.endObject();
		return account;
	}
}
