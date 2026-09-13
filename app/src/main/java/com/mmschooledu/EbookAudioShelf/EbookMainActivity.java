package com.mmschooledu.EbookAudioShelf;

import com.mmschooledu.R;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EbookMainActivity extends EbookIndexFeedActivity implements SearchView.OnQueryTextListener {

    public void _Options_Menu_Click(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about) {
            MsgBox(
                "About",
                "Apk Name- Myanmar Audio Book Apk\n\n" +
                "Version- 1.0.16\n\n" +
                "Thank you\n\n" +
                "Developed by Myanmar Developer Group"
            );
        } else if (id == R.id.navigation_item_contact) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse("email"));
            String[] s = {"surveyormanualbook@gmail.com"};
            i.putExtra(Intent.EXTRA_EMAIL, s);
            i.putExtra(Intent.EXTRA_SUBJECT, "This is Title");
            i.putExtra(Intent.EXTRA_TEXT, "This is a Email Body");
            i.setType("message/rfc822");
            Intent chooser = Intent.createChooser(i, "Launch Email");
            startActivity(chooser);
        } else if (id == R.id.navigation_item_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_TEXT, "This is a good apk. Download here... https://play.google.com/store/apps/details?id=com.md.survey");
            startActivity(i);
        } else if (id == R.id.button1) {
            startActivity(new Intent(EbookMainActivity.this, EbookBt1MainActivity.class));
        } else if (id == R.id.button2) {
            startActivity(new Intent(EbookMainActivity.this, EbookBt2MainActivity.class));
        } else if (id == R.id.button3) {
            startActivity(new Intent(EbookMainActivity.this, EbookBt3MainActivity.class));
        } else if (id == R.id.button4) {
            startActivity(new Intent(EbookMainActivity.this, EbookAudioActivityCategories.class));
        }

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search2);
        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            if (searchView != null) {
                searchView.setOnQueryTextListener(this);
                searchView.setQueryHint("စာအုပ်၊ စာရေးဆရာ ရှာရန်...");
            }
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String query = newText == null ? "" : newText.toLowerCase().trim();

        if (dhamma_adapter != null) {
            dhamma_adapter.filter(query);
        }
        if (audioBookshelf_adapter != null) {
            audioBookshelf_adapter.filter(query);
        }
        if (music_adapter != null) {
            music_adapter.filter(query);
        }
        if (book_adapter != null) {
            book_adapter.filter(query);
        }
        return true;
    }

    public void processTitleImg(String inputJson) {
        String input;
        switch (currentFont) {
            case FONT_ZAWGYI:
                input = EbookFontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input = EbookFontConverter.zg2uni(inputJson);
                break;
            default:
                input = inputJson;
                break;
        }
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals("Index")) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    String orgjson = Html.fromHtml(content).toString();

                    JSONObject obj = new JSONObject(orgjson);
                    final String img1 = obj.getString("img1");
                    final String img2 = obj.getString("img2");
                    final String img3 = obj.getString("img3");
                    final String img4 = obj.getString("img4");
                    final String img5 = obj.getString("img5");

                    showTitleImg(img1, img2, img3, img4, img5);
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void processDhamma(String inputJson, String category) {
        d_posts = new ArrayList<EbookDhammaItem>();
        String input;
        switch (currentFont) {
            case FONT_ZAWGYI:
                input = EbookFontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input = EbookFontConverter.zg2uni(inputJson);
                break;
            default:
                input = inputJson;
                break;
        }
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals("Index")) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    String orgjson = Html.fromHtml(content).toString();

                    JSONObject obj = new JSONObject(orgjson);
                    JSONArray jarr = obj.getJSONArray(category);
                    for (int j = 0; j < jarr.length(); j++) {
                        EbookDhammaItem p = new EbookDhammaItem();
                        p.thumbnail = (jarr.getJSONObject(j).getString("thumbnail"));
                        p.bname = (jarr.getJSONObject(j).getString("bname"));
                        p.wname = (jarr.getJSONObject(j).getString("wname"));
                        p.link = (jarr.getJSONObject(j).getString("link"));
                        p.category = (jarr.getJSONObject(j).getString("category"));
                        p.mb1 = (jarr.getJSONObject(j).getString("mb1"));
                        p.mb2 = (jarr.getJSONObject(j).getString("mb2"));
                        p.time = (jarr.getJSONObject(j).getString("time"));

                        addItem(p);
                    }
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void processAudioBookshelf(String inputJson, String category) {
        a_posts = new ArrayList<EbookAudioBookshelfItem>();
        String input;
        switch (currentFont) {
            case FONT_ZAWGYI:
                input = EbookFontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input = EbookFontConverter.zg2uni(inputJson);
                break;
            default:
                input = inputJson;
                break;
        }
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals("Index")) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    String orgjson = Html.fromHtml(content).toString();

                    JSONObject obj = new JSONObject(orgjson);
                    JSONArray jarr = obj.getJSONArray(category);
                    for (int j = 0; j < jarr.length(); j++) {
                        EbookAudioBookshelfItem p = new EbookAudioBookshelfItem();
                        p.thumbnail = (jarr.getJSONObject(j).getString("thumbnail"));
                        p.bname = (jarr.getJSONObject(j).getString("bname"));
                        p.wname = (jarr.getJSONObject(j).getString("wname"));
                        p.link = (jarr.getJSONObject(j).getString("link"));
                        p.category = (jarr.getJSONObject(j).getString("category"));
                        p.mb1 = (jarr.getJSONObject(j).getString("mb1"));
                        p.mb2 = (jarr.getJSONObject(j).getString("mb2"));
                        p.time = (jarr.getJSONObject(j).getString("time"));

                        addItem(p);
                    }
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void processMusic(String inputJson, String category) {
        m_posts = new ArrayList<EbookMusicItem>();
        String input;
        switch (currentFont) {
            case FONT_ZAWGYI:
                input = EbookFontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input = EbookFontConverter.zg2uni(inputJson);
                break;
            default:
                input = inputJson;
                break;
        }
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals("Index")) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    String orgjson = Html.fromHtml(content).toString();

                    JSONObject obj = new JSONObject(orgjson);
                    JSONArray jarr = obj.getJSONArray(category);
                    for (int j = 0; j < jarr.length(); j++) {
                        EbookMusicItem p = new EbookMusicItem();
                        p.thumbnail = (jarr.getJSONObject(j).getString("thumbnail"));
                        p.bname = (jarr.getJSONObject(j).getString("bname"));
                        p.wname = (jarr.getJSONObject(j).getString("wname"));
                        p.link = (jarr.getJSONObject(j).getString("link"));
                        p.category = (jarr.getJSONObject(j).getString("category"));
                        p.mb1 = (jarr.getJSONObject(j).getString("mb1"));
                        p.mb2 = (jarr.getJSONObject(j).getString("mb2"));
                        p.time = (jarr.getJSONObject(j).getString("time"));

                        addItem(p);
                    }
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void processBookStory(String inputJson, String category) {
        b_posts = new ArrayList<EbookBookItem>();
        String input;
        switch (currentFont) {
            case FONT_ZAWGYI:
                input = EbookFontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input = EbookFontConverter.zg2uni(inputJson);
                break;
            default:
                input = inputJson;
                break;
        }
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals("Index")) {
                    String content = jo2.getJSONObject("content").getString("$t");
                    String orgjson = Html.fromHtml(content).toString();

                    JSONObject obj = new JSONObject(orgjson);
                    JSONArray jarr = obj.getJSONArray(category);
                    for (int j = 0; j < jarr.length(); j++) {
                        EbookBookItem p = new EbookBookItem();
                        p.thumbnail = (jarr.getJSONObject(j).getString("thumbnail"));
                        p.bname = (jarr.getJSONObject(j).getString("bname"));
                        p.wname = (jarr.getJSONObject(j).getString("wname"));
                        p.link = (jarr.getJSONObject(j).getString("link"));
                        p.category = (jarr.getJSONObject(j).getString("category"));
                        p.mb1 = (jarr.getJSONObject(j).getString("mb1"));
                        p.mb2 = (jarr.getJSONObject(j).getString("mb2"));
                        p.time = (jarr.getJSONObject(j).getString("time"));

                        addItem(p);
                    }
                }
            }
        } catch (JSONException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getFeedAddress() {
        return "https://kgaudioshelfappindex.blogspot.com/feeds/posts/default?alt=json";
    }
}
