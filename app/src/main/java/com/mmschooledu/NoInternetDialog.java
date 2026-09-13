package com.mmschooledu;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//
//public class NoInternetDialog {
//
//    static Context context;
//    private Activity activity;
//    private AlertDialog dialog;
//
//    public NoInternetDialog(Activity activity) {
//        this.activity = activity;
//    }
//
//    public void showNoInternet() {
//
//        try {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        View parent_view = LayoutInflater.from(activity).inflate(R.layout.nointernet_dia, null);
//        builder.setView(parent_view);
//        dialog = builder.create();
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//
//        // Finding Views inside dialog
//        TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
//        Button button = dialog.findViewById(R.id.dialogButton1);
//
//        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​​​ကျေးဇူးပြု၍ အင်တာနက် ဆက်သွယ်​​ပေးပါ။");
//        button.setText("..ဟုတ်ကကဲ့..");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                dialog.dismiss();
//                Intent intent = new Intent(context, SplashActivity.class);
//                context.startActivity(intent);
////                //activity.finish();
//            }
//        });
//    } catch (Exception e) {
//        e.printStackTrace();
//        // Handle exceptions here
//        Toast.makeText(context, "An error occurred while showing the dialog", Toast.LENGTH_SHORT).show();
//    }
//}
//
//    public void dismiss() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
////            Intent intent = new Intent(context, SplashActivity.class);
////            context.startActivity(intent);
//        }
//    }
//}


public class NoInternetDialog {
    private Activity activity;
    private AlertDialog dialog;

    public NoInternetDialog(Activity activity) {
        this.activity = activity;
    }

    public void showNoInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View parent_view = LayoutInflater.from(activity).inflate(R.layout.nointernet_dia, null);
        builder.setView(parent_view);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        // Finding Views inside dialog
        TextView tv_title = dialog.findViewById(R.id.dialogTextView1);
        Button button = dialog.findViewById(R.id.dialogButton1);

        tv_title.setText("အင်တာနက် ဆက်သွယ်ထား​ခြင်းမရှိပါ။ ​​​ကျေးဇူးပြု၍ အင်တာနက် ဆက်သွယ်​​ပေးပါ။");
        button.setText("..ဟုတ်ကကဲ့..");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog
                //dialog.dismiss();

                // Start the SplashActivity
                Intent intent = new Intent(activity, SplashActivity.class);
                activity.startActivity(intent);

                // Finish the current activity if needed
               // activity.finish();
            }
        });
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
