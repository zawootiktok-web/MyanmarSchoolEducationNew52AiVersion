//package mm.pndaza.thupyadictionary.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.activity.OnBackPressedCallback;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.text.HtmlCompat;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import mm.pndaza.thupyadictionary.R;
//import mm.pndaza.thupyadictionary.utils.MDetect;
//
//public class InfoFragment extends Fragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.title_info_mm)));
//        return inflater.inflate(R.layout.fragment_info, container, false);
//    }
//
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        TextView textView = view.findViewById(R.id.tv_info);
//        String htmlContent = MDetect.getDeviceEncodedText(getInfo());
////        markwon.setMarkdown(textView, htmlContent);
//        textView.setText(HtmlCompat.fromHtml(htmlContent, HtmlCompat.FROM_HTML_MODE_LEGACY));
////        textView.setText(htmlContent);
//    }
//
//    private String getInfo() {
//        StringBuilder info = new StringBuilder();
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("about.html")));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                info.append(line);
//            }
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return info.toString();
//    }
//
//}
package com.mmschooledu.MyanmarSpelling.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        requireActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.title_info_mm)));
        return inflater.inflate(R.layout.mmfragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webView = view.findViewById(R.id.webview_info);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        String htmlContent = getHtmlContent();
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    private String getHtmlContent() {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(requireContext().getAssets().open("about.html")));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
