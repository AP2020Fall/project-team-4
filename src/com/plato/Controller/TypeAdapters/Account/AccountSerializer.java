//package Controller.TypeAdapters.Account;
//
//import Model.AccountRelated.Account;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//public class AccountSerializer {
//	public static AccountSerializer accountSerializer;
//
//	public Gson initGson (GsonBuilder builder, Gson gson) {
//		builder = new GsonBuilder();
//		builder.registerTypeAdapter(Account.class, new AccountAdapter());
//		builder.setPrettyPrinting();
//		gson = builder.create();
//		return gson;
//	}
//
//	public static AccountSerializer getInstance () {
//		if (accountSerializer == null)
//			accountSerializer = new AccountSerializer();
//		return accountSerializer;
//	}
//}
