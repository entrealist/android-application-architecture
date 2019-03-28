package com.rosberry.sample.rxsearch.ui;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rosberry.sample.rxsearch.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

/**
 * A search UI widget that implements a floating search box also called persistent
 * search.
 */
public class SearchBar extends FrameLayout {

    private final static String TAG = SearchBar.class.getSimpleName();

    private final static long CLEAR_BTN_FADE_ANIM_DURATION = 500;

    private Activity mHostActivity;

    private View mMainLayout;
    private boolean mIsFocused;
    private OnFocusChangeListener mFocusChangeListener;

    private OnSearchListener mSearchListener;
    private SearchInputView mSearchInput;
    private String mTitleText;
    private boolean mIsTitleSet;
    private String mOldQuery = "";
    private OnQueryChangeListener mQueryListener;
    private ImageView mClearButton;
    private boolean mSkipQueryFocusChangeEvent;
    private boolean mSkipTextChangeEvent;

    private OnClearSearchActionListener mOnClearSearchActionListener;

    /**
     * Interface for implementing a listener to listen
     * to state changes in the query text.
     */
    public interface OnQueryChangeListener {

        /**
         * Called when the query has changed. It will
         * be invoked when one or more characters in the
         * query was changed.
         *
         * @param oldQuery the previous query
         * @param newQuery the new query
         */
        void onSearchTextChanged(String oldQuery, String newQuery);
    }

    /**
     * Interface for implementing a listener to listen
     * to when the current search has completed.
     */
    public interface OnSearchListener {
        /**
         * Called when the current search has completed
         * as a result of pressing search key in the keyboard.
         * <p/>
         *
         * @param currentQuery the text that is currently set in the query TextView
         */
        void onSearchAction(String currentQuery);
    }


    /**
     * Interface for implementing a listener to listen
     * to for focus state changes.
     */
    public interface OnFocusChangeListener {

        /**
         * Called when the search bar has gained focus
         * and listeners are now active.
         */
        void onFocus();

        /**
         * Called when the search bar has lost focus
         * and listeners are no more active.
         */
        void onFocusCleared();
    }

    /**
     * Interface for implementing a callback to be
     * invoked when the clear search text action button
     * (the x to the right of the text) is clicked.
     */
    public interface OnClearSearchActionListener {

        /**
         * Called when the clear search text button
         * was clicked.
         */
        void onClearSearchClicked();
    }

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mHostActivity = getHostActivity(getContext());

        mMainLayout = inflate(getContext(), R.layout.l_search_bar, this);

        mClearButton = findViewById(R.id.clearImg);
        mSearchInput = findViewById(R.id.searchEditText);

        setupQueryBar();
    }

    private void setupQueryBar() {
        if (!isInEditMode() && mHostActivity != null) {
            mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        mClearButton.setVisibility(View.INVISIBLE);
        mClearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchInput.setText("");
                if (mOnClearSearchActionListener != null) {
                    mOnClearSearchActionListener.onClearSearchClicked();
                }
            }
        });

        mSearchInput.addTextChangedListener(new TextWatcherAdapter() {

            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (mSkipTextChangeEvent || !mIsFocused) {
                    mSkipTextChangeEvent = false;
                } else {
                    if (mSearchInput.getText().toString().length() != 0 &&
                            mClearButton.getVisibility() == View.INVISIBLE) {
                        mClearButton.setAlpha(0.0f);
                        mClearButton.setVisibility(View.VISIBLE);
                        ViewCompat.animate(mClearButton).alpha(1.0f).setDuration(CLEAR_BTN_FADE_ANIM_DURATION).start();
                    } else if (mSearchInput.getText().toString().length() == 0) {
                        mClearButton.setVisibility(View.INVISIBLE);
                    }

                    if (mQueryListener != null && mIsFocused && !mOldQuery.equals(mSearchInput.getText().toString())) {
                        mQueryListener.onSearchTextChanged(mOldQuery, mSearchInput.getText().toString());
                    }

                }

                mOldQuery = mSearchInput.getText().toString();
            }

        });

        mSearchInput.setOnFocusChangeListener(new TextView.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (mSkipQueryFocusChangeEvent) {
                    mSkipQueryFocusChangeEvent = false;
                } else if (hasFocus != mIsFocused) {
                    setSearchFocusedInternal(hasFocus);
                }
            }
        });

        mSearchInput.setOnKeyboardDismissedListener(new SearchInputView.OnKeyboardDismissedListener() {
            @Override
            public boolean onKeyboardDismissed() {
                setSearchFocusedInternal(false);
                return true;
            }
        });

        mSearchInput.setOnSearchKeyListener(new SearchInputView.OnKeyboardSearchKeyClickListener() {
            @Override
            public void onSearchKeyClicked() {
                if (mSearchListener != null) {
                    mSearchListener.onSearchAction(getQuery());
                }
                mSkipTextChangeEvent = true;

                if (mIsTitleSet) {
                    setSearchBarTitle(getQuery());
                } else {
                    setSearchText(getQuery());
                }
                setSearchFocusedInternal(false);
            }
        });
    }

    /**
     * Sets the title for the search bar.
     * <p/>
     * Note that after the title is set, when
     * the search gains focus, the title will be replaced
     * by the search hint.
     *
     * @param title the title to be shown when search
     *              is not focused
     */
    public void setSearchBarTitle(CharSequence title) {
        this.mTitleText = title.toString();
        mIsTitleSet = true;
        mSearchInput.setText(title);
    }

    /**
     * Sets the search text.
     * <p/>
     * Note that this is the different from
     * {@link #setSearchBarTitle(CharSequence title) setSearchBarTitle} in
     * that it keeps the text when the search gains focus.
     *
     * @param text the text to be set for the search
     *             input.
     */
    public void setSearchText(CharSequence text) {
        mIsTitleSet = false;
        setQueryText(text);
    }

    /**
     * Returns the current query text.
     *
     * @return the current query
     */
    public String getQuery() {
        return mOldQuery;
    }

    public void clearQuery() {
        mSearchInput.setText("");
    }

    private void setQueryText(CharSequence text) {
        mSearchInput.setText(text);
        //move cursor to end of text
        mSearchInput.setSelection(mSearchInput.getText().length());
    }

    public void clickOnOutside() {
        if (mIsFocused) {
            setSearchFocusedInternal(false);
        }
    }

    /**
     * Sets whether the search is focused or not.
     *
     * @param focused true, to set the search to be active/focused.
     * @return true if the search was focused and will now become not focused. Useful for
     * calling super.onBackPress() in the hosting activity only if this method returns false
     */
    public boolean setSearchFocused(final boolean focused) {
        boolean updatedToNotFocused = !focused && this.mIsFocused;

        if (focused != this.mIsFocused) {
            setSearchFocusedInternal(focused);
        }

        return updatedToNotFocused;
    }


    public boolean isSearchBarFocused() {
        return mIsFocused;
    }

    private void setSearchFocusedInternal(final boolean focused) {
        this.mIsFocused = focused;

        if (focused) {
            mSearchInput.requestFocus();
            UIUtil.showKeyboard(getContext(), mSearchInput);
            if (mIsTitleSet) {
                mSkipTextChangeEvent = true;
                mSearchInput.setText("");
            } else {
                mSearchInput.setSelection(mSearchInput.getText().length());
            }
            mSearchInput.setLongClickable(true);
            mClearButton.setVisibility((mSearchInput.getText().toString().length() == 0) ?
                    View.INVISIBLE : View.VISIBLE);
            if (mFocusChangeListener != null) {
                mFocusChangeListener.onFocus();
            }
        } else {
            mMainLayout.requestFocus();
            mClearButton.setVisibility(View.GONE);
            if (mHostActivity != null) {
                UIUtil.hideKeyboard(mHostActivity);
            }
            if (mIsTitleSet) {
                mSkipTextChangeEvent = true;
                mSearchInput.setText(mTitleText);
            }
            mSearchInput.setLongClickable(false);
            if (mFocusChangeListener != null) {
                mFocusChangeListener.onFocusCleared();
            }
        }
    }

    /**
     * Sets the listener that will listen for query
     * changes as they are being typed.
     *
     * @param listener listener for query changes
     */
    public void setOnQueryChangeListener(OnQueryChangeListener listener) {
        this.mQueryListener = listener;
    }

    /**
     * Sets the listener that will be called when
     * an action that completes the current search
     * session has occurred and the search lost focus.
     * <p/>
     * <p>When called, a client would ideally grab the
     * search or suggestion query from the callback parameter or
     * from {@link #getQuery() getquery} and perform the necessary
     * query against its data source.</p>
     *
     * @param listener listener for query completion
     */
    public void setOnSearchListener(OnSearchListener listener) {
        this.mSearchListener = listener;
    }

    /**
     * Sets the listener that will be called when the focus
     * of the search has changed.
     *
     * @param listener listener for search focus changes
     */
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        this.mFocusChangeListener = listener;
    }

    /**
     * Sets the listener that will be called when the
     * clear search text action button (the x to the right
     * of the search text) is clicked.
     *
     * @param listener
     */
    public void setOnClearSearchActionListener(OnClearSearchActionListener listener) {
        this.mOnClearSearchActionListener = listener;
    }

    public static Activity getHostActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}