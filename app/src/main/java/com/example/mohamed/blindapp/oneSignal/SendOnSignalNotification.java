package com.example.mohamed.blindapp.oneSignal;

import android.os.AsyncTask;
import android.util.Log;


import com.example.mohamed.blindapp.oneSignal.model.ButtonData;
import com.example.mohamed.blindapp.oneSignal.model.Content;
import com.example.mohamed.blindapp.oneSignal.model.Data;
import com.example.mohamed.blindapp.oneSignal.model.Heading;
import com.example.mohamed.blindapp.oneSignal.model.NotificationFilter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 11/12/2017.  time :15:42
 */

public class SendOnSignalNotification {

    public interface response{
        void onResponse(String response);
        void  onError(String error);
    }
    private static response response;
    private String rest_Key;
    private String app_id;
    private List<String> filters=new ArrayList<>();
    private String json;


    public List<String> getFilters() {
        return filters;
    }

    public String getJson() {
        return json;
    }

    public String getRest_Key() {
        return rest_Key;
    }

    public String getApp_id() {
        return app_id;
    }


    private SendOnSignalNotification(SendOnSignalNotificationBuilder builder){
        this.app_id=builder.app_id;
        this.filters=builder.filters;
        this.rest_Key=builder.rest_Key;
        this.json=builder.json;
        response=builder.response;
    }


    public static class SendOnSignalNotificationBuilder {
        private String rest_Key;
        private String app_id;
        private List<String> filters=new ArrayList<>();
        private String json;
        private   response response;

        /**
         *
         * @param rest_Key
         * @param app_id
         */
        public SendOnSignalNotificationBuilder(String rest_Key, String app_id){
            this.app_id=app_id;
            this.rest_Key=rest_Key;
            json="{";
            json+="\"app_id\" : \""+app_id+"\" ,";
        }


        /**
         *
         * @param player_ids
         * @return
         */
        public SendOnSignalNotificationBuilder setPlyerId(String... player_ids){

            String player_id=" \"include_player_ids\" :[";

            for (int i = 0; i <player_ids.length ; i++) {
                if (i==player_ids.length-1){
                    player_id += " \"" + player_ids[i] + "\" ] ,";
                }else {
                    player_id +="\"" + player_ids[i] + "\" ,";
                }
            }

            json+=player_id;
            return this;
        }


        /**
         *
         * @param operation
         * @param filter
         * @return
         */
        public SendOnSignalNotificationBuilder setFilterWithOperation(String operation, NotificationFilter... filter){

            String filterJs="";
            if (filters.size()>0){
                filterJs+=",";
            }

            for (int i = 0; i <filter.length ; i++) {
                if (i != 0) {
                    if (filter[i].getField().equalsIgnoreCase("tag")) {
                      filterJs+=",{\"operator\" :"+"\""+operation
                              +"\" } , {\"field\" : \""+filter[i].getField()
                              +"\" , \"key\" : \""+filter[i].getKey()
                              +"\" , \"relation\" : \""+filter[i].getRelation()
                              +"\" , \"value\" : \""+filter[i].getValue()
                              +"\" }";
                    } else {
                     filterJs+=",{\"operator\" :"+"\""+operation
                             +"\" } , {\"field\" : \""+filter[i].getField()
                             +"\" , \"relation\" : \""+filter[i].getRelation()
                             +"\" , \"value\" : \""+filter[i].getValue()
                             +"\" }";
                    }
                }else {
                    if (filter[i].getField().equalsIgnoreCase("tag")) {
                        filterJs+=" {\"field\" : \""+filter[i].getField()
                                +"\" , \"key\" : \""+filter[i].getKey()
                                +"\" , \"relation\" : \""+filter[i].getRelation()
                                +"\" , \"value\" : \""+filter[i].getValue()
                                +"\" }";
                    } else {
                        filterJs+=" {\"field\" : \""+filter[i].getField()
                                +"\" , \"relation\" : \""+filter[i].getRelation()
                                +"\" , \"value\" : \""+filter[i].getValue()
                                +"\" }";
                    }
                }

                if (i==filter.length-1){
                    filters.add(filterJs);
                }
            }

            return this;
        }



        /**
         *
         * @param filter
         * @return
         */
        public SendOnSignalNotificationBuilder setFilter(NotificationFilter ... filter){

            String filterJs="";
            if (filters.size()>0){
                filterJs+=",";
            }

            for (int i = 0; i <filter.length ; i++) {
                if (i != 0) {
                    if (filter[i].getField().equalsIgnoreCase("tag")) {
                        filterJs+=" , {\"field\" : \""+filter[i].getField()
                                +"\" , \"key\" : \""+filter[i].getKey()
                                +"\" , \"relation\" : \""+filter[i].getRelation()
                                +"\" , \"value\" : \""+filter[i].getValue()
                                +"\" }";
                    } else {
                        filterJs+=" , {\"field\" : \""+filter[i].getField()
                                +"\" , \"relation\" : \""+filter[i].getRelation()
                                +"\" , \"value\" : \""+filter[i].getValue()
                                +"\" }";
                    }
                }else {
                    if (filter[i].getField().equalsIgnoreCase("tag")) {
                        filterJs+=" {\"field\" : \""+filter[i].getField()
                                +"\" , \"key\" : \""+filter[i].getKey()
                                +"\" , \"relation\" : \""+filter[i].getRelation()
                                +"\" , \"value\" : \""+filter[i].getValue()
                                +"\" }";
                    } else {
                        filterJs+=" {\"field\" : \""+filter[i].getField()
                                +"\" , \"relation\" : \""+filter[i].getRelation()
                                +"\" , \"value\" : \""+filter[i].getValue()
                                +"\" }";
                    }
                }

                if (i==filter.length-1){
                    filters.add(filterJs);
                }
            }

            return this;
        }

        /**
         *
         * @param url
         * @return
         */
        public SendOnSignalNotificationBuilder setbig_picture(String url ){
            json+="\"big_picture\" : \""+url+"\" ,";
            return this;
        }

        /**
         *
         * @param url
         * @return
         */
        public SendOnSignalNotificationBuilder setUrl(String url){
            json+="\"url\" : \""+url+"\" ,";
            return this;
        }

        /**
         *
         * @param contents
         * @return
         */
        public SendOnSignalNotificationBuilder setContents(Content... contents){
            String content=" \"contents\" :{";

            for (int i = 0; i <contents.length ; i++) {
                if (i==contents.length-1){
                    content += "\"" + contents[i].getKey() + "\" : \"" + contents[i].getVal() + "\" }";
                }else {
                        content += "\"" + contents[i].getKey() + "\" : \"" + contents[i].getVal() + "\" ,";
                }
            }

            json+=content+" ,";
            return this;
        }

        /**
         *
         * @param headings
         * @return
         */
        public SendOnSignalNotificationBuilder setHeadings(Heading... headings){
            String heading=" \"headings\" :{";

            for (int i = 0; i <headings.length ; i++) {
                if (i==headings.length-1){
                    heading += "\"" + headings[i].getKey() + "\" : \"" + headings[i].getVal() + "\" }";
                }else {
                    heading += "\"" + headings[i].getKey() + "\" : \"" + headings[i].getVal() + "\" ,";
                }
            }

            json+=heading+" ,";
            return this;
        }

        /**
         *
         * @param datas
         * @return
         */
        public SendOnSignalNotificationBuilder setAddinalData(Data... datas){
            String data=" \"data\" :{";

            for (int i = 0; i <datas.length ; i++) {
                if (i==datas.length-1){
                    data += "\"" + datas[i].getKey() + "\" : \"" + datas[i].getVal() + "\" }";
                }else {
                    data += "\"" + datas[i].getKey() + "\" : \"" + datas[i].getVal() + "\" ,";
                }
            }

            json+=data+" ,";

         return this;
        }

        /**
         *
         * @param buttons
         * @return
         */
        public SendOnSignalNotificationBuilder setButtons(ButtonData... buttons){
            String button=" \"buttons\" : [";
            for (int i = 0; i <buttons.length ; i++) {
                if (i==buttons.length-1){
                    button+="{ \"id\" : \""+buttons[i].getId()+"\" , \"text\" : \""+buttons[i].getText()+"\" } ] ,";
                }else {
                    button+="{ \"id\" : \""+buttons[i].getId()+"\" , \"text\" : \""+buttons[i].getText()+"\" } ,";

                }
            }

            json+=button;
            return this;
        }

        /**
         *
         * @param segemts
         * @return
         */
        public SendOnSignalNotificationBuilder setSegemts(String... segemts){

            String segment=" \"included_segments\" :[";

            for (int i = 0; i <segemts.length ; i++) {
                if (i==segemts.length-1){
                    segment += " \"" + segemts[i] + "\" ] ,";
                }else {
                    segment +="\"" + segemts[i] + "\" ,";
                }
            }

            json+=segment;
            return this;
        }

        /**
         *
         * @return SendOnSignalNotification
         */
        public SendOnSignalNotification build(response response){
            this.response=response;
         SendOnSignalNotification  notification=new SendOnSignalNotification(this);
            String str = notification.getJson();
            if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
                str = str.substring(0, str.length() - 1);
            }

            if (filters.size()>0){

                String allFilter=", \"filters\" :[";
                for (String s:filters) {
                    allFilter+=s;
                }
                allFilter+="]";
                str+=allFilter;
            }

            str+="}";
            json=str;
            Map<String ,String> map=new HashMap<>();
         map.put("json",json);
         map.put("key","Basic "+notification.getRest_Key());
         asynsc asynsc=new asynsc();
         asynsc.execute(map);

         return notification;
        }
    }

    static class  asynsc extends AsyncTask<Map,String,String>

    {
        @Override
        protected String doInBackground(Map[] maps) {
        Map map=maps[0];
        return sendNotification(map.get("json").toString(),map.get("key").toString());
    }

        @Override
        protected void onPostExecute(String s) {
           if (s.contains("error")){
               response.onError(s);
           }else {
               response.onResponse(s);

           }
        }
    }

    private static String sendNotification(String json, String restKey){
        try {


            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", restKey);
            con.setRequestMethod("POST");

            String strJsonBody =json;


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            Log.d("httpResponse", httpResponse + "");

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            Log.d("jsonResponse", jsonResponse + "");
            return jsonResponse;

        } catch(Throwable t) {
            t.printStackTrace();
        }
        return null;
    }
}
