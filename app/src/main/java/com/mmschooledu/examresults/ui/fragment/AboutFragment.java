package com.mmschooledu.examresults.ui.fragment;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.Fragment;
//
//
//import com.mmschooledu.R;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AboutFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class AboutFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    static final String ARG_PARAM1 = "param1";
//    static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    TextView tv_app_name,tv_app_description;
//    Button bt_share,bt_rate,bt_policy,bt_disc;
//
//    View view;
//
//    public AboutFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AboutFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AboutFragment newInstance(String param1, String param2) {
//        AboutFragment fragment = new AboutFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        view=inflater.inflate(R.layout.fragment_about, container, false);
//
//        prepareView();
//        actionView();
//        actionClick();
//        // Inflate the layout for this fragment
//        return view;
//    }
//    void prepareView(){
//        tv_app_name=view.findViewById(R.id.tv_app_name);
//        tv_app_description=view.findViewById(R.id.tv_app_description);
//        bt_share=view.findViewById(R.id.bt_share);
//        bt_rate=view.findViewById(R.id.bt_rate);
//        bt_policy=view.findViewById(R.id.bt_policy);
//        bt_disc=view.findViewById(R.id.bt_disc);
//    }
//    @SuppressLint("SetTextI18n")
//    void actionView(){
//
//
//        tv_app_name.setText("App name : "+getString(R.string.app_name));
//        getDataText("about.txt");
//        tv_app_description.setText("About app : "+getDataText("about.txt"));
//    }
//    void actionClick(){
//        bt_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String text ="https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_TEXT, text);
//                startActivity(Intent.createChooser(intent, getString(R.string.share_app)));
//            }
//        });
//        bt_policy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://examresults2024.blogspot.com/2024/06/privacy-and-policy.html")));
//            }
//        });
//        bt_disc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDisc();
//
//            }
//        });
//        bt_rate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
//
//            }
//        });
//    }
//    public static String unescapeUnicode(String str) {
//        StringBuffer b = new StringBuffer();
//        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
//        while (m.find())
//            b.append((char) Integer.parseInt(m.group(1), 16));
//        return b.toString();
//    }
//    public String getDataText(String DataName) {
//String readResult=null;
//        try {
//            InputStream readText = getContext().getAssets().open(DataName);
//            int size = readText.available();
//            byte[] b = new byte[size];
//            readText.read(b);
//            readText.close();
//
//            readResult = new String(b);
//        } catch (IOException e) {
//
//        }
//        return readResult;
//    }
//
//    void showDisc(){
//        final AlertDialog dialog = new AlertDialog.Builder(requireActivity()).create();
//        View parent_view = LayoutInflater.from(requireActivity()).inflate(R.layout.two_button_dia, null);
//        dialog.setView(parent_view);
//        dialog.setCancelable(true);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.show();
//        ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
//        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
//        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
//        final Button call = (Button) dialog.findViewById(R.id.bt2);
//        final Button cancel = (Button) dialog.findViewById(R.id.bt1);
//        iv.setImageResource(R.drawable.apk);
//        tv_title.setText("Disclaimer: ");
//        tv_message.setText(getString(R.string.exit_message));
//        //cancel.setText("Exit");
//        cancel.setVisibility(View.GONE);
//        call.setText("Understand");
//        call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View p1) {
//                dialog.dismiss();
//            }
//        });
//
//    }
//}

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmschooledu.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_PARAM1 = "param1";
    static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tv_app_name,tv_app_description;
    Button bt_share,bt_rate,bt_policy,bt_disc;

    View view;

    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_about, container, false);

        prepareView();
        actionView();
        actionClick();
        // Inflate the layout for this fragment
        return view;
    }
    void prepareView(){
        tv_app_name=view.findViewById(R.id.tv_app_name);
        tv_app_description=view.findViewById(R.id.tv_app_description);
        bt_share=view.findViewById(R.id.bt_share);
        bt_rate=view.findViewById(R.id.bt_rate);
        bt_policy=view.findViewById(R.id.bt_policy);
        bt_disc=view.findViewById(R.id.bt_disc);
    }
    @SuppressLint("SetTextI18n")
    void actionView(){


        tv_app_name.setText("App name : "+getString(R.string.app_name));
        getDataText("about.txt");
        tv_app_description.setText("About app : "+getDataText("about.txt"));
    }
    void actionClick(){
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text ="https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(intent, getString(R.string.share_app)));
            }
        });
        bt_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://examresults2024.blogspot.com/2024/06/privacy-and-policy.html")));
            }
        });
        bt_disc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDisc();

            }
        });
        bt_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));

            }
        });
    }
    public static String unescapeUnicode(String str) {
        StringBuffer b = new StringBuffer();
        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
        while (m.find())
            b.append((char) Integer.parseInt(m.group(1), 16));
        return b.toString();
    }
    public String getDataText(String DataName) {
        String readResult=null;
        try {
            InputStream readText = getContext().getAssets().open(DataName);
            int size = readText.available();
            byte[] b = new byte[size];
            readText.read(b);
            readText.close();

            readResult = new String(b);
        } catch (IOException e) {

        }
        return readResult;
    }

    void showDisc(){
        final AlertDialog dialog = new AlertDialog.Builder(requireActivity()).create();
        View parent_view = LayoutInflater.from(requireActivity()).inflate(R.layout.two_button_dia, null);
        dialog.setView(parent_view);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView iv = (ImageView) dialog.findViewById(R.id.icon);
        TextView tv_title = (TextView) dialog.findViewById(R.id.title);
        TextView tv_message = (TextView) dialog.findViewById(R.id.message);
        final Button call = (Button) dialog.findViewById(R.id.bt2);
        final Button cancel = (Button) dialog.findViewById(R.id.bt1);
        iv.setImageResource(R.drawable.warning_24px);
        tv_title.setText("Disclaimer: ");
        tv_message.setText(getString(R.string.disc_message));
        //cancel.setText("Exit");
        cancel.setVisibility(View.GONE);
        call.setText("Understand");
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p1) {
                dialog.dismiss();
            }
        });

    }
}