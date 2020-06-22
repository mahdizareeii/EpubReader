package com.test.app.util;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.test.R;
import com.test.app.util.interfaces.OnBookReaderInitialized;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class BookReader {

    private String bookName;
    private WebView webView;
    private OnBookReaderInitialized onBookReaderInitialized;

    private Book book;
    private List<TOCReference> tocReferences;
    private Resource resource;

    public BookReader(String bookName, WebView webView, OnBookReaderInitialized onBookReaderInitialized) {
        this.bookName = bookName;
        this.webView = webView;
        this.onBookReaderInitialized = onBookReaderInitialized;
        initWebView();
    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        firstInitialize();
    }

    private void firstInitialize() {
        try {
            book = getBook(bookName);
            tocReferences = getTocReferences(book);
            onBookReaderInitialized.onInitialized(tocReferences.size());
        } catch (NullPointerException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    public void loadBook(int chapter) {
        try {
            resource = getResources(tocReferences, chapter);
            loadHtmlData(new String(resource.getData()));
        } catch (NullPointerException e) {
            showError(e.getMessage());
        } catch (IOException e) {
            showError(e.getMessage());
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private Book getBook(String bookName) throws IOException {
        InputStream epubInputStream = webView.getContext().getAssets().open(bookName);
        return (new EpubReader()).readEpub(epubInputStream);
    }

    private List<TOCReference> getTocReferences(Book book) {
        return book.getTableOfContents().getTocReferences();
    }

    private Resource getResources(List<TOCReference> tocReferences, int chapter) {
        return tocReferences.get(chapter).getResource();
    }

    private void loadHtmlData(String htmlString) {
        webView.loadData(htmlString, "text/html", "utf-8");
    }

    private void showError(String message) {
        Toast.makeText(webView.getContext(), webView.getContext().getResources().getString(R.string.chapter_not_found), Toast.LENGTH_SHORT).show();
        Log.i(AppConstants.ERROR_TAG, message);
    }

}
