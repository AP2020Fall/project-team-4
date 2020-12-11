//package Controller.TypeAdapters.Account;
//
//import Model.AccountRelated.Account;
//import Model.AccountRelated.Admin;
//import Model.AccountRelated.Gamer;
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonToken;
//import com.google.gson.stream.JsonWriter;
//
//import java.io.IOException;
//import java.time.LocalDate;
//
//public class AccountAdapter extends TypeAdapter<Account> {
//
//	@Override
//	public Account read (JsonReader reader) throws IOException {
//		reader.beginObject();
//		Account account = null;
//
//		if (reader.peek().toString().equals(Admin.class.getSimpleName()))
//			account = new Admin();
//		else
//			account = new Gamer();
//
//		String fieldName = null;
//
//		while (reader.hasNext()) {
//			JsonToken token = reader.peek();
//
//			if (token.equals(JsonToken.NAME))
//				fieldName = reader.nextName();
//
//			if ("firstName".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setFirstName(reader.nextString());
//			}
//
//			if ("lastName".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setLastName(reader.nextString());
//			}
//
//			if ("username".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setUsername(reader.nextString());
//			}
//
//			if ("password".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setPassword(reader.nextString());
//			}
//
//			if ("userID".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setUserID(reader.nextString());
//			}
//
//			if ("email".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setEmail(reader.nextString());
//			}
//
//			if ("phoneNum".equals(fieldName)) {
//				//move to next token
//				token = reader.peek();
//				account.setPhoneNum(reader.nextString());
//			}
//
//			if (account instanceof Gamer) {
//				if ("awardsFromEvents".equals(fieldName)) {
//					//move to next token
//					token = reader.peek();
//					((Gamer) account).setAwardsFromEvents(reader.nextInt());
//				}
//
//				if ("money".equals(fieldName)) {
//					//move to next token
//					token = reader.peek();
//					((Gamer) account).setMoney(reader.nextDouble());
//				}
//
//				if ("accountStartDate".equals(fieldName)) {
//					//move to next token
//					token = reader.peek();
//					((Gamer) account).setAccountStartDate(LocalDate.parse(reader.nextString()));
//				}
//
//				if ("frnds".equals(fieldName)) {
//					//move to next token
//					token = reader.peek();
//					((Gamer) account).setFrnds(reader.nextString());
//				}
//
//				if ("frnds".equals(fieldName)) {
//					//move to next token
//					token = reader.peek();
//					((Gamer) account).setFrnds(reader.nextString());
//				}
//			}
//		}
//
//		reader.endObject();
//		return account;
//	}
//
//	@Override
//	public void write (JsonWriter writer, Account account) throws IOException {
//
//	}
//}
