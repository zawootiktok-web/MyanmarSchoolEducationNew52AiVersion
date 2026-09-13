package com.mmschooledu;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Objects;
//
//public class GeolocationUtils {
//    static Context context;
//    public interface GeolocationCallback {
//        void onCountryReceived(String country);
//    }
//
//    public static void checkCountryAndShowDialog(final Context context) {
//        if (isNetworkAvailable(context)) {
//            try {
//                // Request IP-based geolocation data
//                new GeolocationTask(new GeolocationCallback() {
//                    @Override
//                    public void onCountryReceived(String country) {
//                        handleGeolocationResponse(context, country);
//                    }
//                }).execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//                // Handle exceptions here
//                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            showNoInternetDialog(context);
//        }
//    }
//
//    private static void handleGeolocationResponse(final Context context, String country) {
//        try {
//            if ("mm".equals(country)) {
//                // If the country is Myanmar, show dialog to use VPN
//                showCheckDialog(context);
//            } else {
//                // If the country is not Myanmar, continue to use the current activity
//                // You can put your logic here for other actions
//                // For example, perform some operation or show a message
//                //Toast.makeText(context, "You are not in Myanmar. Continue using the app.", Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "You can continue to use the App peacefully. Thank you very much.", Toast.LENGTH_SHORT).show();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle exceptions here
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static <Activity> void showCheckDialog(final Context context) {
//        // Show a dialog encouraging the user to use a VPN
//        try {
//            final AlertDialog dialog = new AlertDialog.Builder(context).create();
//            View parent_view = LayoutInflater.from(context).inflate(R.layout.nointernet_vn, null);
//            dialog.setView(parent_view);
//            dialog.setCancelable(false);
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//
//            // Finding Views inside dialog
//            TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
//            Button button = (Button) dialog.findViewById(R.id.dialogButton1);
//            Button button2 = (Button) dialog.findViewById(R.id.dialogButton2);
//            assert tv_title != null;
//            tv_title.setText("ပိုမို\u200Bကောင်းမွန်\u200Bသော ဝန်\u200Bဆောင်မှု့များရရှိနိုင်ရန်\n ဗွီ  ပီလ်  အမ်  ကို အသုံးပြုပြီးမှ ပြန်လည်ဝင်\u200Bရောက် အသုံးပြု\u200Bပေးကြပါ...\nမခံပါက App ဝင်တိုင်း Dialog ပြ\u200Bနေပါလိမ့်မည်");
//
//            assert button != null;
//            assert button2 != null;
//            button.setText("အသုံးပြုနည်းကြည့်ရန်");
//            button2.setText("...ဟုတ်ကဲ့...");
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                   // dialog.dismiss();
//                    // finish();
//                 //  finishAffinity((Activity) context);
//                    Intent intent = new Intent(context, WelcomeActivity2.class);
//                    context.startActivity(intent);
//                }
//            });
//
//            button2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                    // dialog.dismiss();
//                    // finish();
//                    //finishAffinity((android.app.Activity) context);
//                    Intent intent = new Intent(context, SplashActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle exceptions here
//            Toast.makeText(context, "An error occurred while showing the dialog", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static void showNoInternetDialog(final Context context) {
//        // Show a dialog indicating no internet connection
//        try {
////            AlertDialog.Builder builder = new AlertDialog.Builder(context);
////            builder.setMessage("No internet connection. Please check your network settings.")
////                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialog, int which) {
////                            // You can customize the action on OK button click
////                            dialog.dismiss();
////                        }
////                    })
////                    .show();
//
//
//            final AlertDialog dialog = new AlertDialog.Builder(context).create();
//            View parent_view = LayoutInflater.from(context).inflate(R.layout.nointernet_dia, null);
//            dialog.setView(parent_view);
//            dialog.setCancelable(false);
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//
//            // Finding Views inside dialog
//            TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
//            Button button = (Button) dialog.findViewById(R.id.dialogButton1);
//
//            tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​\u200Bကျေးဇူး​ပြု၍ အင်တာနက် ဆက်သွယ်​\u200Bပေးပါ။");
//
//            button.setText("...ဟုတ်ကဲ့...");
//
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                  //  dialog.dismiss();
//                   // finish();
//                    //finishAffinity((Activity) context);
//                    Intent intent = new Intent(context, WelcomeActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle exceptions here
//            Toast.makeText(context, "An error occurred while showing the dialog", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//        }
//        return false;
//    }
//
//    private static class GeolocationTask extends AsyncTask<Void, Void, String> {
//
//        private final GeolocationCallback callback;
//
//        public GeolocationTask(GeolocationCallback callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            String response = null;
//            try {
//                // Make a request to ipinfo.io to get geolocation data
////                URL url = new URL("https://ipinfo.io/json");
//
//                  URL url = new URL("https://api.incolumitas.com");
//               // URL url = new URL("http://www.ip-api.com/json");
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//
//                    while ((line = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//
//                    bufferedReader.close();
//                    response = stringBuilder.toString();
//                } finally {
//                    urlConnection.disconnect();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            try {
//                // Parse the country from the response
//                JSONObject jsonResponse = new JSONObject(result);
//                String country = jsonResponse.getString("country");
//                callback.onCountryReceived(country);
//            } catch (JSONException e) {
//                e.printStackTrace();
//                // Handle JSON parsing errors
//           //     Toast.makeText(context, "Error parsing response", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}
//









import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

//public class GeolocationUtils {
//    static Context context;
//
//    public interface GeolocationCallback {
//        void onCountryReceived(String country);
//    }
//
//    public static void checkCountryAndShowDialog(final Context context) {
//        if (isNetworkAvailable(context)) {
//            try {
//                // Request IP-based geolocation data
//                new GeolocationTask(new GeolocationCallback() {
//                    @Override
//                    public void onCountryReceived(String country) {
//                        handleGeolocationResponse(context, country);
//                    }
//                }).execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//                // Handle exceptions here
//                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            showNoInternetDialog(context);
//        }
//    }
//
//    private static void handleGeolocationResponse(final Context context, String country) {
//        try {
//            if ("mm".equals(country)) {
//                // If the country is Myanmar, show dialog to use VPN
//                showCheckDialog(context);
//            } else {
//               // Toast.makeText(context, "You can continue to use the App peacefully. Thank you very much.", Toast.LENGTH_SHORT).show();
//               // showCheckDialog(context);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle exceptions here
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static void showCheckDialog(final Context context) {
//        try {
//            final AlertDialog dialog = new AlertDialog.Builder(context).create();
//            View parent_view = LayoutInflater.from(context).inflate(R.layout.nointernet_vn, null);
//            dialog.setView(parent_view);
//            dialog.setCancelable(false);
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//
//            // Finding Views inside dialog
//            TextView tv_title = (TextView) dialog.findViewById(R.id.dialogTextView1);
//            Button button = (Button) dialog.findViewById(R.id.dialogButton1);
//            Button button2 = (Button) dialog.findViewById(R.id.dialogButton2);
//            assert tv_title != null;
//            tv_title.setText("ပိုမို\u200Bကောင်းမွန်\u200Bသော ဝန်\u200Bဆောင်မှု့များရရှိနိုင်ရန်\n ဗွီ  ပီလ်  အမ်  ကို အသုံးပြုပြီးမှ ပြန်လည်ဝင်\u200Bရောက် အသုံးပြု\u200Bပေးကြပါ...\nမခံပါက App ဝင်တိုင်း Dialog ပြ\u200Bနေပါလိမ့်မည်");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    private static void showNoInternetDialog(Context context) {
//        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//    }
//
////    Zaw Oo Ooredoo, [20-Nov-24 8:28 PM]
//    private static class GeolocationTask extends AsyncTask<Void, Void, String> {
//        private final GeolocationCallback callback;
//
//        GeolocationTask(GeolocationCallback callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String countryCode = null;
//            try {
//                // Replace this with the new API endpoint
//                URL url = new URL("https://api.incolumitas.com");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setConnectTimeout(5000);
//                connection.setReadTimeout(5000);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                countryCode = jsonResponse.optString("country", "").toLowerCase(); // Assuming "country" contains the country code
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//            return countryCode;
//        }
//
//        @Override
//        protected void onPostExecute(String country) {
//            super.onPostExecute(country);
//            if (country != null) {
//                callback.onCountryReceived(country);
//            } else {
//                Toast.makeText(context, "Failed to retrieve geolocation data", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}



//
//
//public class GeolocationUtils {
//    static Context context;
//
//    public interface GeolocationCallback {
//        void onCountryReceived(String country);
//    }
//
//    public static void checkCountryAndShowDialog(final Context context) {
//        if (isNetworkAvailable(context)) {
//            try {
//                // Request IP-based geolocation data
//                new GeolocationTask(new GeolocationCallback() {
//                    @Override
//                    public void onCountryReceived(String country) {
//                        handleGeolocationResponse(context, country);
//                    }
//                }).execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            showNoInternetDialog(context);
//        }
//    }
//
//    private static void handleGeolocationResponse(final Context context, String country) {
//        try {
//            if ("mm".equals(country)) {
//                // If the country is Myanmar, show dialog to use VPN
//                showCheckDialog(context);
//            } else {
//                Toast.makeText(context, "You can continue to use the App peacefully. Thank you very much.", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static void showCheckDialog(final Context context) {
//        try {
//            final AlertDialog dialog = new AlertDialog.Builder(context).create();
//            View parent_view = LayoutInflater.from(context).inflate(R.layout.nointernet_vn, null);
//            dialog.setView(parent_view);
//            dialog.setCancelable(false);
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//
//            TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
//            Button button = dialog.findViewById(R.id.dialogButton1);
//            Button button2 = dialog.findViewById(R.id.dialogButton2);
//
//            assert tv_title != null;
//            tv_title.setText("ပိုမို\u200Bကောင်းမွန်\u200Bသော ဝန်\u200Bဆောင်မှု့များရရှိနိုင်ရန်\n ဗွီ  ပီလ်  အမ်  ကို အသုံးပြုပြီးမှ ပြန်လည်ဝင်\u200Bရောက် အသုံးပြု\u200Bပေးကြပါ...\nမခံပါက App ဝင်တိုင်း Dialog ပြ\u200Bနေပါလိမ့်မည်");
//
//            assert button != null;
//            assert button2 != null;
//            button.setText("အသုံးပြုနည်းကြည့်ရန်");
//            button2.setText("...ဟုတ်ကဲ့...");
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                    Intent intent = new Intent(context, WelcomeActivity2.class);
//                    context.startActivity(intent);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    private static void showNoInternetDialog(Context context) {
//        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//    }
//
//    private static class GeolocationTask extends AsyncTask<Void, Void, String> {
//        private final GeolocationCallback callback;
//
//        GeolocationTask(GeolocationCallback callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String countryCode = null;
//            try {
//                URL url = new URL("https://api.incolumitas.com");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setConnectTimeout(5000);
//                connection.setReadTimeout(5000);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                countryCode = jsonResponse.optString("countryCode", "").toLowerCase(); // Replace "countryCode" with the correct key from API
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//            return countryCode;
//        }
//
//        @Override
//        protected void onPostExecute(String country) {
//            super.onPostExecute(country);
//            if (country != null) {
//                callback.onCountryReceived(country);
//            } else {
//                Toast.makeText(context, "Failed to retrieve geolocation data", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}


//
//
//public class GeolocationUtils {
//    static Context context;
//
//    public interface GeolocationCallback {
//        void onCountryReceived(String country);
//    }
//
//    public static void checkCountryAndShowDialog(final Context context) {
//        if (isNetworkAvailable(context)) {
//            try {
//                new GeolocationTask(new GeolocationCallback() {
//                    @Override
//                    public void onCountryReceived(String country) {
//                        handleGeolocationResponse(context, country);
//                    }
//                }).execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            showNoInternetDialog(context);
//        }
//    }
//
//    private static void handleGeolocationResponse(final Context context, String country) {
//        try {
//            if ("mm".equalsIgnoreCase(country)) {
//                showCheckDialog(context); // Myanmar ဖြစ်လျှင် Dialog ပြရန်
//            } else {
//               // Toast.makeText(context, "You can continue to use the App peacefully. Thank you very much.", Toast.LENGTH_SHORT).show();
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static void showCheckDialog(final Context context) {
//        try {
//            final AlertDialog dialog = new AlertDialog.Builder(context).create();
//            View parent_view = LayoutInflater.from(context).inflate(R.layout.nointernet_vn, null);
//            dialog.setView(parent_view);
//            dialog.setCancelable(false);
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
//            dialog.show();
//
//            TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
//            Button button = dialog.findViewById(R.id.dialogButton1);
//            Button button2 = dialog.findViewById(R.id.dialogButton2);
//
//            assert tv_title != null;
//            tv_title.setText("ပိုမို\u200Bကောင်းမွန်\u200Bသော ဝန်\u200Bဆောင်မှု့များရရှိနိုင်ရန်\n ဗွီ  ပီလ်  အမ်  ကို အသုံးပြုပြီးမှ ပြန်လည်ဝင်\u200Bရောက် အသုံးပြု\u200Bပေးကြပါ...\nမခံပါက App ဝင်တိုင်း Dialog ပြ\u200Bနေပါလိမ့်မည်");
//
//            assert button != null;
//            assert button2 != null;
//            button.setText("အသုံးပြုနည်းကြည့်ရန်");
//            button2.setText("...ဟုတ်ကဲ့...");
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                    Intent intent = new Intent(context, WelcomeActivity2.class);
//                    context.startActivity(intent);
//                }
//            });
//            button2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View p1) {
//                    // dialog.dismiss();
//                    // finish();
//                    //finishAffinity((android.app.Activity) context);
//                    Intent intent = new Intent(context, SplashActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    private static void showNoInternetDialog(Context context) {
//        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//    }
//
//    private static class GeolocationTask extends AsyncTask<Void, Void, String> {
//        private final GeolocationCallback callback;
//
//        GeolocationTask(GeolocationCallback callback) {
//            this.callback = callback;
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            String countryCode = null;
//            try {
//                URL url = new URL("https://api.incolumitas.com");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setConnectTimeout(5000);
//                connection.setReadTimeout(5000);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                JSONObject asn = jsonResponse.optJSONObject("asn");
//                if (asn != null) {
//                    countryCode = asn.optString("country", "").toLowerCase();
//                }
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//            return countryCode;
//        }
//
//        @Override
//        protected void onPostExecute(String country) {
//            super.onPostExecute(country);
//            if (country != null) {
//                callback.onCountryReceived(country);
//            } else {
//                Toast.makeText(context, "Failed to retrieve geolocation data", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}


import java.lang.ref.WeakReference;
public class GeolocationUtils {

    public interface GeolocationCallback {
        void onCountryReceived(String country);
    }

    public static void checkCountryAndShowDialog(final FragmentActivity context) {
                if (context == null || context.isFinishing() || context.isDestroyed()) {
            return;
        }
        try {
            // Run the VPN/country check in the background without showing a progress UI.
            // The gate is intentionally fail-closed: no VPN/country result means no entry.
            new GeolocationTask(context, new GeolocationCallback() {
                @Override
                public void onCountryReceived(String country) {
                    handleGeolocationResponse(context, country);
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            showCheckDialog(context);
        }

    }

    private static void handleGeolocationResponse(final Context context, String country) {
        try {
            Log.d("GeoLocation", "Country code = " + country);
            if ("mm".equalsIgnoreCase(country)) {
                showCheckDialog(context); // Myanmar only
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private static void showCheckDialog(final Context context) {
        try {
            final AlertDialog dialog = new AlertDialog.Builder(context).create();
            View parent_view = LayoutInflater.from(context).inflate(R.layout.nointernet_vn, null);
            dialog.setView(parent_view);
            dialog.setCancelable(false);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
            Button button = dialog.findViewById(R.id.dialogButton1);
            Button button2 = dialog.findViewById(R.id.dialogButton2);

            if (tv_title != null) {
                tv_title.setText("ပိုမို\u200Bကောင်းမွန်\u200Bသော ဝန်\u200Bဆောင်မှု့များရရှိနိုင်ရန်\n" +
                        "ဗွီ  ပီလ်  အမ်  ကို အသုံးပြုပြီးမှ ပြန်လည်ဝင်\u200Bရောက် အသုံးပြု\u200Bပေးကြပါ...\n" +
                        "မခံပါက App ဝင်တိုင်း Dialog ပြ\u200Bနေပါလိမ့်မည်");
            }

            if (button != null) {
                button.setText("အသုံးပြုနည်းကြည့်ရန်");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, WelcomeActivity2.class));
                    }
                });
            }

            if (button2 != null) {
                button2.setText("...ဟုတ်ကဲ့...");
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, SplashActivity.class));
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm != null ? cm.getActiveNetworkInfo() : null;
        return netInfo != null && netInfo.isConnected();
    }

    private static void showNoInternetDialog(Context context) {
        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }

    private static class GeolocationTask extends AsyncTask<Void, Void, String> {
        private final WeakReference<Context> contextRef;
        private final GeolocationCallback callback;

        GeolocationTask(Context context, GeolocationCallback callback) {
            this.contextRef = new WeakReference<>(context.getApplicationContext());
            this.callback = callback;
        }

        @Override
                protected String doInBackground(Void... voids) {
            // Keep the startup decision fast and deterministic. The gate is fail-closed.
            return readCountryCode("https://ipinfo.io/json", "country");
        }

        private String readCountryCode(String endpoint, String fieldName) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(endpoint);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(2500);
                connection.setReadTimeout(2500);
                connection.setUseCaches(false);
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("User-Agent", "MSEApp/1.1.52");

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                return new JSONObject(response.toString())
                        .optString(fieldName, "")
                        .trim()
                        .toLowerCase();
            } catch (IOException | JSONException exception) {
                Log.w("GeoLocation", "Geolocation check failed");
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }


        @Override
        protected void onPostExecute(String country) {
            Context context = contextRef.get();
            if (context != null && !(context instanceof FragmentActivity
                    && (((FragmentActivity) context).isFinishing()
                    || ((FragmentActivity) context).isDestroyed()))) {

                            if (country != null && !country.isEmpty()) {
                    callback.onCountryReceived(country);
                } else {
                    // Fail closed so the user cannot bypass the VPN-required gate.
                    showCheckDialog(context);
                }

            }
        }
    }
}
