package co.ke.ikocare.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class TokenPrefUtil {

    /**

     * Storing API Key in shared preferences to

     * add it in header part of every retrofit request

     *

     */

    private static SharedPreferences.Editor editor;

    public TokenPrefUtil() {

    }



    private static SharedPreferences getSharedPreferences(Context context) {

        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);

    }



    public static void storeApiKey(Context context, String apiKey) {

        editor = getSharedPreferences(context).edit();

        editor.putString("API_KEY", apiKey);

        editor.apply();

    }

    public static void storeUserId(Context context, String user_id){

        editor = getSharedPreferences(context).edit();

        editor.putString("USER_ID", user_id);

        editor.apply();

    }

    public static void storeId(Context context, String id){

        editor = getSharedPreferences(context).edit();

        editor.putString("ID", id);

        editor.apply();

    }

    public static void storeName(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("NAME", name);

        editor.apply();

    }

    public static String getName(Context context){
        return getSharedPreferences(context).getString("NAME",null);
    }

    public static void storeHouse(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("HOUSE", name);

        editor.apply();

    }

    public static String getHouse(Context context){
        return getSharedPreferences(context).getString("HOUSE",null);
    }

    public static void storeAccNo(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("ACCOUNT", name);

        editor.apply();

    }

    public static String getAccNo(Context context){
        return getSharedPreferences(context).getString("ACCOUNT",null);
    }

    public static void storeAccountBalance(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("BALANCE", name);

        editor.apply();

    }

    public static String getAccountBalance(Context context){
        return getSharedPreferences(context).getString("BALANCE",null);
    }



    public static String getApiKey(Context context) {

        return getSharedPreferences(context).getString("API_KEY", null);

    }

    public static String getUserId(Context context){

        return getSharedPreferences(context).getString("USER_ID", null);


    }

    public static String getId(Context context){

        return getSharedPreferences(context).getString("ID", null);


    }


    public static void storeRent(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("RENT", name);

        editor.apply();

    }

    public static String getRent(Context context){
        return getSharedPreferences(context).getString("RENT",null);
    }

    public static void storeBuilding(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("BUILDING", name);

        editor.apply();

    }

    public static String getBuilding(Context context){
        return getSharedPreferences(context).getString("BUILDING",null);
    }

    public static void storePayBillNumber(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("PAYBILL", name);

        editor.apply();

    }

    public static String getPayBillNumber(Context context){
        return getSharedPreferences(context).getString("PAYBILL",null);
    }

    public static void storeUnit(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("UNIT", name);

        editor.apply();

    }

    public static String getUnit(Context context){
        return getSharedPreferences(context).getString("UNIT",null);
    }

    public static void storeDescriptions(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("DESCRIPTIONS", stringBuilder.toString());
        editor.apply();

    }

    public static String getDescriptions( Context context){

        return getSharedPreferences(context).getString("DESCRIPTIONS",null);

    }

    public static void storeSubTitle(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("SUBTITLE", stringBuilder.toString());
        editor.apply();

    }

    public static String getSubTitle( Context context){

        return getSharedPreferences(context).getString("SUBTITLE",null);

    }

    public static void storeInvoiceAmount(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("INVOICEAMOUNT", stringBuilder.toString());
        editor.apply();

    }

    public static String getInvoiceAmount( Context context){

        return getSharedPreferences(context).getString("INVOICEAMOUNT",null);

    }

    public static void storeTransactionAmount(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("TRANSACTIONAMOUNT", stringBuilder.toString());
        editor.apply();

    }

    public static String getTransactionAmount( Context context){

        return getSharedPreferences(context).getString("TRANSACTIONAMOUNT",null);

    }

    public static void storeTransactionDate(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("TRANSACTIONDATE", stringBuilder.toString());
        editor.apply();

    }

    public static String getTransactionDate( Context context){

        return getSharedPreferences(context).getString("TRANSACTIONDATE",null);

    }


    public static void storeTransactionTitle(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("TRANSACTIONTITLE", stringBuilder.toString());
        editor.apply();

    }

    public static String getTransactionTitle( Context context){

        return getSharedPreferences(context).getString("TRANSACTIONTITLE",null);

    }

    public static void storeTicketTitle(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("TICKETTITLE", stringBuilder.toString());
        editor.apply();

    }

    public static String getTicketTitle( Context context){

        return getSharedPreferences(context).getString("TICKETTITLE",null);

    }

    public static void storeTicketDate(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("TICKETDATE", stringBuilder.toString());
        editor.apply();

    }

    public static String getTicketDate( Context context){

        return getSharedPreferences(context).getString("TICKETDATE",null);

    }

    public static void storeTicketState(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("TICKETSTATE", stringBuilder.toString());
        editor.apply();

    }
    public static String getTicketState( Context context){

        return getSharedPreferences(context).getString("TICKETSTATE",null);

    }


    public static void storeTenantChatMessage(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("CHATMESSAGE", stringBuilder.toString());
        editor.apply();

    }
    public static String getTenantChatMessage( Context context){

        return getSharedPreferences(context).getString("CHATMESSAGE",null);

    }

    public static void storeTenantChatDate(List<String> list){

        StringBuilder stringBuilder = new StringBuilder();

        for (String s:list){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }

        editor.putString("CHATDATE", stringBuilder.toString());
        editor.apply();

    }
    public static String getTenantChatDate( Context context){

        return getSharedPreferences(context).getString("CHATDATE",null);

    }


    public static void storeEmail(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("EMAIL", name);

        editor.apply();

    }

    public static String getEmail(Context context){
        return getSharedPreferences(context).getString("EMAIL",null);
    }

    public static void storePhone(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("PHONE", name);

        editor.apply();

    }
    public static String getPhone(Context context){
        return getSharedPreferences(context).getString("PHONE",null);
    }

    public static void storeDob(Context context, String name){

        editor = getSharedPreferences(context).edit();

        editor.putString("DOB", name);

        editor.apply();

    }
    public static String getDob(Context context){
        return getSharedPreferences(context).getString("DOB",null);
    }



//    public static void cleaId(){
//
//        editor.clear();
//
//        editor.apply();
//
//    }

    public static void clearAll(){

        editor.clear();

        editor.apply();

    }


}
