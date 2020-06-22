package com.test.app.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.test.R;
import com.test.app.model.CustomSpinnerModel;
import com.test.app.util.BaseActivity;
import com.test.app.util.BookReader;
import com.test.app.util.interfaces.OnBookReaderInitialized;
import com.test.app.view.adapter.CustomSpinnerAdapter;
import com.test.app.viewModel.MainActivityViewModel;
import com.test.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    private WebView webView;
    private Spinner chapters;
    private BookReader bookReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
            initView();
            initAction();
        } catch (NullPointerException e) {
            handleError(e.getMessage());
        } catch (Exception e) {
            handleError(e.getMessage());
        }
    }

    @Override
    public void initView() {
        webView = binding.webView;
        chapters = binding.spinnerChapters;
    }

    @Override
    public void initAction() {
        initBookReader();
    }

    private void initBookReader() {
        bookReader = new BookReader("test.epub", webView, new OnBookReaderInitialized() {
            @Override
            public void onInitialized(int chapterSize) {
                getChaptersList(chapterSize);
            }
        });
    }

    private void getChaptersList(int chapterSize) {
        viewModel.getChaptersList(chapterSize).observe(this, new Observer<List<CustomSpinnerModel>>() {
            @Override
            public void onChanged(List<CustomSpinnerModel> customSpinnerModels) {
                initSpinner(customSpinnerModels);
            }
        });
    }

    private void initSpinner(List<CustomSpinnerModel> customSpinnerModels) {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, customSpinnerModels);
        chapters.setAdapter(adapter);
        chapters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bookReader.loadBook(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}