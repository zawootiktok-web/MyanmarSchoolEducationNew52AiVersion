package com.mmschooledu.examresults.ui.fragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mmschooledu.R;
//import com.mmschooledu.examresults.BookViewerActivity;
////import com.mmschooledu.examresults.R;
//import com.mmschooledu.examresults.WebViewActivity;
//import com.mmschooledu.examresults.adapter.BookmarkAdapter;
//import com.mmschooledu.examresults.model.Bookmark;
//import com.mmschooledu.examresults.model.BookmarkDAO;
//
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link RecnetFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class RecnetFragment extends Fragment {//implements IUnityAdsInitializationListener  {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//
//////    private String unityGameID = "5634362";
//////    private Boolean testMode = false;
//////    private String adUnitId = "Interstitial_Android";
//////
//////    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
//////        @Override
//////        public void onUnityAdsAdLoaded(String placementId) {
//////
//////        }
//////
//////        @Override
//////        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
//////            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
//////        }
//////    };
//////
//////    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
//////        @Override
//////        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
//////            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
//////        }
////
////        @Override
////        public void onUnityAdsShowStart(String placementId) {
////            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
////        }
////
////        @Override
////        public void onUnityAdsShowClick(String placementId) {
////            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
////        }
////
////        @Override
////        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
////            UnityAds.load(adUnitId, loadListener);
////
////        }
////    };
//    RecyclerView rv_recent;
//    BookmarkDAO bookmarkDAO ;
//    List<Bookmark> bookmarks ;
//    BookmarkAdapter adapter;
//    View view;
//
//    public RecnetFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment RecnetFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static RecnetFragment newInstance(String param1, String param2) {
//        RecnetFragment fragment = new RecnetFragment();
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
//        // Inflate the layout for this fragment
//        view=inflater.inflate(R.layout.fragment_recnet,container,false);
//
//        prepareView();
//        actionData();
//        actionView();
//        actionClick();
////        UnityAds.initialize(requireActivity(), unityGameID, testMode, this);
//
//        return view;
//    }
//    void prepareView(){
//        rv_recent=view.findViewById(R.id.rv_recent);
//    }
//    void actionData(){
//        bookmarkDAO= new BookmarkDAO(getContext());
//        bookmarks= bookmarkDAO.getAllBookmarks();
//    }
//    void actionView(){
//
//        adapter= new BookmarkAdapter(bookmarks, new BookmarkAdapter.OnDeleteButtonClickListener() {
//            @Override
//            public void onDeleteButtonClick(int position) {
//
//               try {
//                   Bookmark bookmark = bookmarks.get(position);
//                   // Perform the delete operation, e.g., using a delete method in your BookmarkDAO
//                   bookmarkDAO.deleteBookmark(bookmark);
//                   // Remove the item from the list
//                   bookmarks.remove(position);
//                   // Notify the adapter that the data set has changed
//                   adapter.notifyItemRemoved(position);
//               }catch (Exception e) {
//
//               }
//
//            }
//        },new BookmarkAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(String title,String url) {
//                if(getFromPrefs("ad").equals("add")){
//                  //  UnityAds.show(requireActivity(), adUnitId, new UnityAdsShowOptions(), showListener);
//                }
//
//                gotoPdf(title,url);
//
//            }
//
//        });
//        rv_recent.setAdapter(adapter);
//        rv_recent.setLayoutManager(new LinearLayoutManager(getContext()));
//    } void actionClick(){
//
//    }
//    void gotoPdf(String title, String pdfurl) {
//        Intent intent = null;
//        if (pdfurl.contains("drive.google")){
//            intent=new Intent(getContext(), WebViewActivity.class);
//        }else{
//            intent=new Intent(getContext(), BookViewerActivity.class);
//        }
//        intent.putExtra("url",pdfurl );
//        intent.putExtra("year",title.split(",,")[0]);
//        intent.putExtra("title",title);
//        startActivity(intent);
//
//    }
//    @Override
//    public void onResume() {
//
//        try {
//            actionData();
//            actionView();
//        }catch (Exception e){
//
//        }
//        super.onResume();
//    }
//
////    @Override
////    public void onInitializationComplete() {
////        UnityAds.load(adUnitId, loadListener);
////    }
////
////    @Override
////    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
////
////    }
//    public String getFromPrefs(String key) {
//
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        String lastData = sharedPreferences.getString(key, "");
//        return lastData;
//    }







import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mmschooledu.R;
import com.mmschooledu.examresults.BookViewerActivity;

import com.mmschooledu.examresults.WebViewActivity;
import com.mmschooledu.examresults.adapter.BookmarkAdapter;
import com.mmschooledu.examresults.model.Bookmark;
import com.mmschooledu.examresults.model.BookmarkDAO;
//import com.unity3d.ads.IUnityAdsInitializationListener;
//import com.unity3d.ads.IUnityAdsLoadListener;
//import com.unity3d.ads.IUnityAdsShowListener;
//import com.unity3d.ads.UnityAds;
//import com.unity3d.ads.UnityAdsShowOptions;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecnetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecnetFragment extends Fragment {//implements IUnityAdsInitializationListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ////    private String unityGameID = "5634362";
////    private Boolean testMode = false;
////    private String adUnitId = "Interstitial_Android";
////
////    private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
////        @Override
////        public void onUnityAdsAdLoaded(String placementId) {
////
////        }
////
////        @Override
////        public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
////            Log.e("UnityAdsExample", "Unity Ads failed to load ad for " + placementId + " with error: [" + error + "] " + message);
////        }
////    };
////
////    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
////        @Override
////        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
////            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
////        }
//
//        @Override
//        public void onUnityAdsShowStart(String placementId) {
//            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
//        }
//
//        @Override
//        public void onUnityAdsShowClick(String placementId) {
//            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
//        }
//
//        @Override
//        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
//            UnityAds.load(adUnitId, loadListener);
//
//        }
//    };
    RecyclerView rv_recent;
    BookmarkDAO bookmarkDAO ;
    List<Bookmark> bookmarks ;
    BookmarkAdapter adapter;
    View view;

    public RecnetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecnetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecnetFragment newInstance(String param1, String param2) {
        RecnetFragment fragment = new RecnetFragment();
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
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_recnet,container,false);

        prepareView();
        actionData();
        actionView();
        actionClick();
//        UnityAds.initialize(requireActivity(), unityGameID, testMode, this);

        return view;
    }
    void prepareView(){
        rv_recent=view.findViewById(R.id.rv_recent);
    }
    void actionData(){
        bookmarkDAO= new BookmarkDAO(getContext());
        bookmarks= bookmarkDAO.getAllBookmarks();
    }
    void actionView(){

        adapter= new BookmarkAdapter(bookmarks, new BookmarkAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onDeleteButtonClick(int position) {

                try {
                    Bookmark bookmark = bookmarks.get(position);
                    // Perform the delete operation, e.g., using a delete method in your BookmarkDAO
                    bookmarkDAO.deleteBookmark(bookmark);
                    // Remove the item from the list
                    bookmarks.remove(position);
                    // Notify the adapter that the data set has changed
                    adapter.notifyItemRemoved(position);
                }catch (Exception e) {

                }

            }
        },new BookmarkAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(String title,String url) {
                if(getFromPrefs("ad").equals("add")){
                    //  UnityAds.show(requireActivity(), adUnitId, new UnityAdsShowOptions(), showListener);
                }

                gotoPdf(title,url);

            }

        });
        rv_recent.setAdapter(adapter);
        rv_recent.setLayoutManager(new LinearLayoutManager(getContext()));
    } void actionClick(){

    }
    void gotoPdf(String title, String pdfurl) {
        Intent intent = null;
        if (pdfurl.contains("drive.google")){
            intent=new Intent(getContext(), WebViewActivity.class);
        }else{
            intent=new Intent(getContext(), BookViewerActivity.class);
        }
        intent.putExtra("url",pdfurl );
        intent.putExtra("year",title.split(",,")[0]);
        intent.putExtra("title",title);
        startActivity(intent);

    }
    @Override
    public void onResume() {

        try {
            actionData();
            actionView();
        }catch (Exception e){

        }
        super.onResume();
    }

    //    @Override
//    public void onInitializationComplete() {
//        UnityAds.load(adUnitId, loadListener);
//    }
//
//    @Override
//    public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
//
//    }
    public String getFromPrefs(String key) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData = sharedPreferences.getString(key, "");
        return lastData;
    }
}