package com.mmschooledu.examresults.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.mmschooledu.R;
import com.mmschooledu.examresults.CitiesActivity;
//import com.mmschooledu.examresults.R;
import com.mmschooledu.examresults.Task.DataFetchCallback;
import com.mmschooledu.examresults.Task.DataFetcher;
import com.mmschooledu.examresults.Task.RegionDataFetchCallback;
import com.mmschooledu.examresults.Task.RegionDataFetcher;
import com.mmschooledu.examresults.model.RegionModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment2023 extends Fragment implements DataFetchCallback, RegionDataFetchCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String data_api_v1 = "https://raw.githubusercontent.com/zawoosalay2024/examresults-2024/main/main/data-api-v2023";
    private static final String TAG = ResultFragment.class.getSimpleName();
    RecyclerView rv_template;
    LinearProgressIndicator pi_loading;
    SearchView simpleSearchView;
    FloatingActionButton fab_search;
    TextView tv_results;
    Button bt_refresh;
    List<RegionModel> posts, filteredposts;
    FeedAdapter adapter;
    boolean online = true;
    String[] template_pages_names = {};
    String current_baseUrl, current_year, current_region;
    int position = 0;
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private SwipeRefreshLayout mSwipeLayout;
    private CountDownTimer countDownTimer;

    public ResultFragment2023() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_result, container, false);
        prepareView();
        actionView();
        actionClick();
        fetchData();
        return view;
    }

    void prepareView() {

        tabLayout = view.findViewById(R.id.tab_layout);
        pi_loading = view.findViewById(R.id.pi_loading);
        rv_template = view.findViewById(R.id.rv_region);
        tv_results = view.findViewById(R.id.tv_noresult);
        bt_refresh = view.findViewById(R.id.bt_refresh);
        mSwipeLayout = view.findViewById(R.id.swipeRefreshLayout);
    }

    void actionView() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        posts = new ArrayList<RegionModel>();
        filteredposts = new ArrayList<RegionModel>();
        rv_template.setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isOnline()) {
                    refresh();
                } else {

                    mSwipeLayout.setRefreshing(false);

                }
            }
        });
        bt_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

    }

    void actionClick() {

    }


    public void refresh() {
        if (isOnline()) {
            fetchData();
        } else {
            online = false;
        }
    }

    public void saveToPrefs(String key, String result) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, result);
        editor.commit();
    }

    public String getFromPrefs(String key) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String lastData = sharedPreferences.getString(key, "");
        return lastData;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    void intentWeb(String link) {

        if (link.contains("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link));
            intent.setPackage("com.android.chrome");
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                getContext().startActivity(intent);
            } else {
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(link));
                getContext().startActivity(intent1);
            }

        }


    }


    @Override
    public void onResume() {

        try {
            if (tabLayout.getTabCount() == 0) {
                tabLayout.removeAllTabs();
                for (String title : template_pages_names) {
                    TabLayout.Tab tab = tabLayout.newTab();
                    tab.setText(title.split("-")[0]);
                    tabLayout.addTab(tab);

                }
            }

        } catch (IllegalStateException e) {

        }

        super.onResume();
    }

    private void fetchData() {
        tabLayout.removeAllTabs();
        DataFetcher dataFetcher = new DataFetcher(this);
        dataFetcher.fetchDataInBackground(data_api_v1);
        mSwipeLayout.setRefreshing(true);

    }

    @Override
    public void onDataFetched(String[] titles, String[] baseUrl, String[] date, String[] autoRefresh, String[] dataUrl) {
        // Handle the fetched data
        showTabTitles(titles, baseUrl, date, autoRefresh, dataUrl);
        current_year = titles[0];
        getList(titles[0], baseUrl[0], date[0], autoRefresh[0], dataUrl[0]);
//        Toast.makeText(requireContext(), current_year, Toast.LENGTH_SHORT).show();
        setActionBarTitle("Exam Result - " + current_year);
        mSwipeLayout.setRefreshing(false);
        saveToPrefs("ad", autoRefresh[0]);
    }

    @Override
    public void onError(Exception e) {
        // Handle the error
        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show();
        mSwipeLayout.setRefreshing(false);
        bt_refresh.setVisibility(View.VISIBLE);
    }

    private void showTabTitles(String[] tabTitles, String[] baseUrls, String[] dates, String[] autoRefreshs, String[] dataUrls) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        for (String title : tabTitles) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }

        // Set an OnTabSelectedListener to handle tab click events
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                current_baseUrl = baseUrls[tab.getPosition()];
                current_year = tabTitles[tab.getPosition()];
                setActionBarTitle("Exam Result - " + current_year);
                getList(tabTitles[tab.getPosition()], baseUrls[tab.getPosition()], dates[tab.getPosition()], autoRefreshs[tab.getPosition()], dataUrls[tab.getPosition()]);

//                Toast.makeText(requireContext(), "Selected: " + selectedTitle, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselection here (if needed)
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselection here (if needed)
            }
        });
    }


    public void getList(String title, String baseurl, String date, String autoRefresh, String dataUrl) {
        current_baseUrl = baseurl;
        current_region = title;
        try {
            if (dataUrl.length() > 1) {
                RegionDataFetcher regionDataFetcher = new RegionDataFetcher(this);
                regionDataFetcher.fetchDataInBackground(dataUrl);
                mSwipeLayout.setRefreshing(true);
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                rv_template.setVisibility(View.VISIBLE);
                tv_results.setVisibility(View.GONE);
                bt_refresh.setVisibility(View.GONE);
            } else {
                TimeWaiting(date);
            }
        } catch (Exception e) {
            tv_results.setVisibility(View.VISIBLE);
            tv_results.setText(e.toString());
            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRegionDataFetched(String json) {
        parseAndHandleJson(json);
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onRegionError(Exception e) {
        // Handle the error
        Toast.makeText(requireActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        mSwipeLayout.setRefreshing(false);
    }

    private void parseAndHandleJson(String jsonString) {
        posts.clear();
        filteredposts.clear();

        try {
            JSONObject jo = new JSONObject(jsonString);
            Iterator<String> keys = jo.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                RegionModel r = new RegionModel();
                r.jsonArray = jo.getJSONArray(key).toString();
                r.title = key;
                posts.add(r);
                filteredposts.add(r);
            }
            adapter = new FeedAdapter();
            rv_template.setAdapter(adapter);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        // Parse JSON and handle accordingly
    }

    private void setActionBarTitle(String title) {
        if (getActivity() != null) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setTitle(title);
            }
        }
    }

    void TimeWaiting(String data) {
        // Set the target date and time
        String targetDateTime = data.split(",")[0];
        String zone = data.split(",")[1];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(zone));
        Date targetDate;
        try {
            targetDate = sdf.parse(targetDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        // Calculate the time difference between current time and target time
        long currentTime = System.currentTimeMillis();
        long targetTime = targetDate.getTime();
        long timeDifference = targetTime - currentTime;

        if (timeDifference <= 0) {
            tv_results.setText("Countdown Finished");
            rv_template.setVisibility(View.GONE);
            tv_results.setVisibility(View.VISIBLE);
            bt_refresh.setVisibility(View.VISIBLE);
            return;
        }
        startCountdown(timeDifference);
    }

    private void startCountdown(long timeInMillis) {
        rv_template.setVisibility(View.GONE);
        tv_results.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                // Format the countdown text
                String countdownText = String.format("%02d days %02d hours %02d minutes %02d seconds", days, hours % 24, minutes % 60, seconds % 60);
                // Update the TextView with the countdown text
                tv_results.setText(countdownText);
            }

            @Override
            public void onFinish() {
                tv_results.setText("Countdown Finished");
                bt_refresh.setVisibility(View.VISIBLE);
                refresh();
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public String EngtoMmnumber(int num) {
        String Mmnum;
        Mmnum = (num + "").replace("1", "၁").replace("2", "၂").replace("3", "၃").replace("4", "၄").replace("5", "၅").replace("6", "၆").replace("7", "၇").replace("8", "၈").replace("9", "၉").replace("0", "၀");
        return Mmnum;
    }

    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {


        public FeedAdapter() {
            super();
            filteredposts = new ArrayList<RegionModel>();
            filteredposts.addAll(posts);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup p1, int p2) {

            View v = getLayoutInflater().inflate(R.layout.custom_items_region, p1, false);
            return new ViewHolder(v);
        }

        public void filter(String charText) {
            charText = charText.toLowerCase();
            filteredposts.clear();
            if (charText.length() == 0) {
                filteredposts.addAll(posts);
            } else {
                for (RegionModel pi : posts) {
                    if ((pi.title.toLowerCase()).contains(charText)) {
                        filteredposts.add(pi);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return filteredposts.size();

        }

        @Override
        public void onBindViewHolder(final ViewHolder p1, @SuppressLint("RecyclerView") int p2) {
            if (filteredposts.get(p2).title.contains("၁")) {

            } else if (filteredposts.get(p2).title.contains("၂")) {

            } else if (filteredposts.get(p2).title.contains("၃")) {

            } else if (filteredposts.get(p2).title.contains("၄")) {

            } else if (filteredposts.get(p2).title.contains("၅")) {

            } else if (filteredposts.get(p2).title.contains("၆")) {

            } else if (filteredposts.get(p2).title.contains("၇")) {

            } else if (filteredposts.get(p2).title.contains("၈")) {

            } else if (filteredposts.get(p2).title.contains("၉")) {

            } else if (filteredposts.get(p2).title.contains("၀")) {

            } else {
                p1.title.setText(EngtoMmnumber(p2 + 1) + "။");
            }

            p1.title.append(filteredposts.get(p2).title);
            p1.cv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View p1) {
                    Intent in = new Intent(requireActivity(), CitiesActivity.class);
                    in.putExtra("year", current_year);
                    in.putExtra("region", filteredposts.get(p2).title);
                    in.putExtra("base_url", current_baseUrl);
                    in.putExtra("citiesArray", filteredposts.get(p2).jsonArray);
                    requireActivity().startActivity(in);

                }
            });

        }

        public RegionModel getItem(int pos) {
            return filteredposts.get(pos);
        }

        public void reset() {
            posts.clear();
            filteredposts.clear();
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            CardView cv_item;
            TextView title;

            public ViewHolder(View view) {
                super(view);
                cv_item = view.findViewById(R.id.cv_item);

                title = view.findViewById(R.id.tv_title);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

            }
        }
    }
}


//package com.lotee.examresults.ui.fragment;

////import android.content.Context;
////import android.content.Intent;
////import android.content.SharedPreferences;
////import android.net.ConnectivityManager;
////import android.net.NetworkInfo;
////import android.os.Bundle;
////import android.util.Log;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.TextView;
////import android.widget.Toast;
////
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////import androidx.fragment.app.Fragment;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
////
////import com.google.android.material.tabs.TabLayout;
////import com.lotee.examresults.CitiesActivity;
////import com.lotee.examresults.R;
////import com.lotee.examresults.Task.DataFetchCallback;
////import com.lotee.examresults.Task.DataFetcher;
////import com.lotee.examresults.model.RegionModel;
////
////import org.json.JSONException;
////import org.json.JSONObject;
////
////import java.util.ArrayList;
////import java.util.Iterator;
////import java.util.List;
////
////public class ResultFragment extends Fragment implements DataFetchCallback {
////
////    private static final String TAG = ResultFragment.class.getSimpleName();
////    private static final String data_api_v1 = "https://raw.githubusercontent.com/darklandadmin/examresults-2024/main/main/data-api-v1";
////
////    private TabLayout tabLayout;
////    private RecyclerView rv_template;
////    private SwipeRefreshLayout mSwipeLayout;
////
////    private View view;
////    private FeedAdapter adapter;
////    private List<RegionModel> posts;
////
////    public ResultFragment() {
////        // Required empty public constructor
////    }
////
////    public static ResultFragment newInstance() {
////        return new ResultFragment();
////    }
////
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        // Inflate the layout for this fragment
////        view = inflater.inflate(R.layout.fragment_result, container, false);
////
////        // Initialize views
////        prepareView();
////
////        // Set up TabLayout with 4 tabs
////        String[] tabTitles = {"Tab 1", "Tab 2", "Tab 3", "Tab 4"};
////        for (String title : tabTitles) {
////            tabLayout.addTab(tabLayout.newTab().setText(title));
////        }
////
////        // Tab selection listener
////        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
////            @Override
////            public void onTabSelected(TabLayout.Tab tab) {
////                // Fetch data based on the selected tab
////                int position = tab.getPosition();
////                fetchDataForTab(position);
////            }
////
////            @Override
////            public void onTabUnselected(TabLayout.Tab tab) {}
////
////            @Override
////            public void onTabReselected(TabLayout.Tab tab) {
////                // Optionally refresh data for the reselected tab
////                int position = tab.getPosition();
////                fetchDataForTab(position);
////            }
////        });
////
////        // Initialize with the first tab's data
////        fetchDataForTab(0);
////
////        return view;
////    }
////
////    private void prepareView() {
////        tabLayout = view.findViewById(R.id.tab_layout);
////        rv_template = view.findViewById(R.id.rv_region);
////        mSwipeLayout = view.findViewById(R.id.swipeRefreshLayout);
////
////        rv_template.setLayoutManager(new LinearLayoutManager(getContext()));
////        posts = new ArrayList<>();
////
////        mSwipeLayout.setOnRefreshListener(() -> {
////            // Refresh the current tab data
////            int selectedTabIndex = tabLayout.getSelectedTabPosition();
////            fetchDataForTab(selectedTabIndex);
////        });
////    }
////
////    private void fetchDataForTab(int tabIndex) {
////        String tabDataUrl = "";
////        switch (tabIndex) {
////            case 0:
////                tabDataUrl = data_api_v1 + "/tab1";
////                break;
////            case 1:
////                tabDataUrl = data_api_v1 + "/tab2";
////                break;
////            case 2:
////                tabDataUrl = data_api_v1 + "/tab3";
////                break;
////            case 3:
////                tabDataUrl = data_api_v1 + "/tab4";
////                break;
////        }
////
////        // Fetch data for the selected tab
////        DataFetcher dataFetcher = new DataFetcher(this);
////        dataFetcher.fetchDataInBackground(tabDataUrl);
////
////        mSwipeLayout.setRefreshing(true);
////    }
////
////    @Override
////    public void onDataFetched(String[] titles, String[] baseUrls, String[] dates, String[] autoRefreshs, String[] dataUrls) {
////        parseAndHandleData(titles, baseUrls);
////        mSwipeLayout.setRefreshing(false);
////    }
////
////    @Override
////    public void onError(Exception e) {
////        Toast.makeText(requireContext(), "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
////        mSwipeLayout.setRefreshing(false);
////    }
////
////    private void parseAndHandleData(String[] titles, String[] baseUrls) {
////        posts.clear();
////
////        // Parse and prepare data for RecyclerView
////        for (int i = 0; i < titles.length; i++) {
////            RegionModel model = new RegionModel();
////            model.title = titles[i];
////            model.jsonArray = baseUrls[i]; // Assuming baseUrls contains JSON data
////            posts.add(model);
////        }
////
////        // Update RecyclerView
////        if (adapter == null) {
////            adapter = new FeedAdapter();
////            rv_template.setAdapter(adapter);
////        } else {
////            adapter.notifyDataSetChanged();
////        }
////    }
////
////    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
////
////        @NonNull
////        @Override
////        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_region, parent, false);
////            return new ViewHolder(itemView);
////        }
////
////        @Override
////        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
////            RegionModel region = posts.get(position);
////            holder.title.setText(region.title);
////
////            holder.itemView.setOnClickListener(v -> {
////                // Handle item click
////                Intent intent = new Intent(requireActivity(), CitiesActivity.class);
////                intent.putExtra("region", region.title);
////                intent.putExtra("data", region.jsonArray);
////                requireActivity().startActivity(intent);
////            });
////        }
////
////        @Override
////        public int getItemCount() {
////            return posts.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            TextView title;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                title = itemView.findViewById(R.id.tv_title);
//            }
//        }
//    }
//}



//
////package com.lotee.examresults.ui.fragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.google.android.material.tabs.TabLayout;
//import com.lotee.examresults.CitiesActivity;
//import com.lotee.examresults.R;
//import com.lotee.examresults.Task.DataFetchCallback;
//import com.lotee.examresults.Task.DataFetcher;
//import com.lotee.examresults.model.RegionModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ResultFragment extends Fragment implements DataFetchCallback {
//
//    private static final String TAG = ResultFragment.class.getSimpleName();
//    private static final String BASE_API_URL = "https://raw.githubusercontent.com/darklandadmin/examresults-2024/main/main/data-api-v1";
//
//    private TabLayout tabLayout;
//    private RecyclerView rvTemplate;
//    private SwipeRefreshLayout swipeRefreshLayout;
//
//    private FeedAdapter adapter;
//    private List<RegionModel> posts;
//
//    public ResultFragment() {
//        // Required empty public constructor
//    }
//
//    public static ResultFragment newInstance() {
//        return new ResultFragment();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_result, container, false);
//
//        initializeViews(view);
//        setupTabLayout();
//
//        // Load initial data for the first tab
//        fetchDataForTab(0);
//
//        return view;
//    }
//
//    private void initializeViews(View view) {
//        tabLayout = view.findViewById(R.id.tab_layout);
//        rvTemplate = view.findViewById(R.id.rv_region);
//        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
//
//        rvTemplate.setLayoutManager(new LinearLayoutManager(getContext()));
//        posts = new ArrayList<>();
//
//        swipeRefreshLayout.setOnRefreshListener(() -> {
//            int selectedTabIndex = tabLayout.getSelectedTabPosition();
//            fetchDataForTab(selectedTabIndex);
//        });
//    }
//
//    private void setupTabLayout() {
//        String[] tabTitles = {"Tab 1", "Tab 2", "Tab 3", "Tab 4"};
//        for (String title : tabTitles) {
//            tabLayout.addTab(tabLayout.newTab().setText(title));
//        }
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                fetchDataForTab(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {}
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                fetchDataForTab(tab.getPosition());
//            }
//        });
//    }
//
//    private void fetchDataForTab(int tabIndex) {
//        String tabDataUrl = BASE_API_URL + "/tab" + (tabIndex + 1);
//        if (isNetworkAvailable()) {
//            DataFetcher dataFetcher = new DataFetcher(this);
//            dataFetcher.fetchDataInBackground(tabDataUrl);
//            swipeRefreshLayout.setRefreshing(true);
//        } else {
//            Toast.makeText(requireContext(), "No internet connection.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onDataFetched(String[] titles, String[] baseUrls, String[] dates, String[] autoRefreshs, String[] dataUrls) {
//        posts.clear();
//        for (int i = 0; i < titles.length; i++) {
//            RegionModel model = new RegionModel();
//            model.title = titles[i];
//            model.jsonArray = baseUrls[i]; // Assuming baseUrls contains JSON data
//            posts.add(model);
//        }
//
//        if (adapter == null) {
//            adapter = new FeedAdapter();
//            rvTemplate.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
//
//        swipeRefreshLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onError(Exception e) {
//        Toast.makeText(requireContext(), "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        swipeRefreshLayout.setRefreshing(false);
//    }
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
//        return activeNetwork != null && activeNetwork.isConnected();
//    }
//
//    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_region, parent, false);
//            return new ViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            RegionModel region = posts.get(position);
//            holder.title.setText(region.title);
//
//            holder.itemView.setOnClickListener(v -> {
//                Intent intent = new Intent(requireActivity(), CitiesActivity.class);
//                intent.putExtra("region", region.title);
//                intent.putExtra("data", region.jsonArray);
//                startActivity(intent);
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return posts.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            TextView title;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                title = itemView.findViewById(R.id.tv_title);
//            }
//        }
//    }
//}



//
//
////package com.lotee.examresults.ui.fragment;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import com.google.android.material.tabs.TabLayout;
//import com.lotee.examresults.CitiesActivity;
//import com.lotee.examresults.R;
//import com.lotee.examresults.Task.DataFetchCallback;
//import com.lotee.examresults.Task.DataFetcher;
//import com.lotee.examresults.model.RegionModel;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class ResultFragment extends Fragment implements DataFetchCallback {
//
//    private static final String TAG = ResultFragment.class.getSimpleName();
//
//    // UI components
//    RecyclerView rv_template;
//    TabLayout tabLayout;
//    SwipeRefreshLayout mSwipeLayout;
//    TextView tv_results;
//    Button bt_refresh;
//
//    // Data fields
//    List<RegionModel> posts;
//    FeedAdapter adapter;
//    View view;
//    boolean isDataFetched = false; // To track if Tab 1's data is fetched
//
//    public ResultFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_result, container, false);
//        prepareView();
//        actionView();
//        return view;
//    }
//
//    void prepareView() {
//        tabLayout = view.findViewById(R.id.tab_layout);
//        rv_template = view.findViewById(R.id.rv_region);
//        tv_results = view.findViewById(R.id.tv_noresult);
//        bt_refresh = view.findViewById(R.id.bt_refresh);
//        mSwipeLayout = view.findViewById(R.id.swipeRefreshLayout);
//
//        // Add 4 tabs
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
//
//        // Default selection
//        tabLayout.selectTab(tabLayout.getTabAt(0));
//
//        posts = new ArrayList<>();
//        rv_template.setLayoutManager(new LinearLayoutManager(getContext()));
//    }
//
//    void actionView() {
//        mSwipeLayout.setOnRefreshListener(() -> {
//            if (isOnline()) {
//                refresh();
//            } else {
//                mSwipeLayout.setRefreshing(false);
//            }
//        });
//
//        bt_refresh.setOnClickListener(v -> refresh());
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        // Fetch data for Tab 1
//                        if (!isDataFetched) {
//                            fetchData();
//                        } else {
//                            displayTab1Data();
//                        }
//                        break;
//                    case 1:
//                    case 2:
//                    case 3:
//                        // Display data from Tab 1
//                        displayTab1Data();
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                // No action needed
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                // Handle reselection if needed
//            }
//        });
//    }
//
//    public void refresh() {
//        if (isOnline()) {
//            fetchData();
//        } else {
//            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
//            mSwipeLayout.setRefreshing(false);
//        }
//    }
//
//    private boolean isOnline() {
//        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }
//
//    private void fetchData() {
//        DataFetcher dataFetcher = new DataFetcher(this);
//        dataFetcher.fetchDataInBackground("https://raw.githubusercontent.com/darklandadmin/examresults-2024/main/main/data-api-v1");
//        mSwipeLayout.setRefreshing(true);
//    }
//
//    @Override
//    public void onDataFetched(String[] titles, String[] baseUrl, String[] date, String[] autoRefresh, String[] dataUrl) {
//        // Assume we're only interested in displaying the first tab's data
//        String jsonString = "{ /* JSON response for Tab 1 */ }"; // Replace with actual JSON response
//        parseAndHandleJson(jsonString);
//        isDataFetched = true;
//        mSwipeLayout.setRefreshing(false);
//    }
//
//    @Override
//    public void onError(Exception e) {
//
//    }
//
//    private void parseAndHandleJson(String jsonString) {
//        posts.clear();
//        try {
//            JSONObject jo = new JSONObject(jsonString);
//            Iterator<String> keys = jo.keys();
//
//            while (keys.hasNext()) {
//                String key = keys.next();
//                RegionModel r = new RegionModel();
//                r.title = key;
//                r.jsonArray = jo.getJSONArray(key).toString();
//                posts.add(r);
//            }
//            displayTab1Data();
//        } catch (JSONException e) {
//            Log.e(TAG, e.getMessage());
//        }
//    }
//
//    private void displayTab1Data() {
//        if (adapter == null) {
//            adapter = new FeedAdapter();
//            rv_template.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    // Adapter class for RecyclerView
//    public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_items_region, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            RegionModel model = posts.get(position);
//            holder.title.setText(model.title);
//            holder.cv_item.setOnClickListener(v -> {
//                Intent intent = new Intent(getContext(), CitiesActivity.class);
//                intent.putExtra("region", model.title);
//                startActivity(intent);
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return posts.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            TextView title;
//            CardView cv_item;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                title = itemView.findViewById(R.id.tv_title);
//                cv_item = itemView.findViewById(R.id.cv_item);
//            }
//        }
//    }
//}

