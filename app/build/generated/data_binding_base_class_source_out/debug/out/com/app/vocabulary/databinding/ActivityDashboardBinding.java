// Generated by view binder compiler. Do not edit!
package com.app.vocabulary.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.app.vocabulary.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDashboardBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final AppCompatButton btnFavorite;

  @NonNull
  public final AppCompatButton btnMobile;

  @NonNull
  public final TextView btnOption;

  @NonNull
  public final CardView cvCard;

  @NonNull
  public final CardView cvFavorite;

  @NonNull
  public final CardView cvOfflinePast;

  @NonNull
  public final ImageView imgShare;

  @NonNull
  public final ImageView ivIcon;

  @NonNull
  public final ImageView ivNotification;

  @NonNull
  public final ImageView ivSpeak;

  @NonNull
  public final LinearLayout llNoRecord;

  @NonNull
  public final LinearLayout llTop;

  @NonNull
  public final TextView tvAntonyms;

  @NonNull
  public final TextView tvDescription;

  @NonNull
  public final AppCompatTextView tvEmail;

  @NonNull
  public final TextView tvFavoriteCount;

  @NonNull
  public final AppCompatTextView tvFullName;

  @NonNull
  public final TextView tvNoWords;

  @NonNull
  public final TextView tvOfflineCount;

  @NonNull
  public final TextView tvSynonyms;

  @NonNull
  public final TextView tvWord;

  private ActivityDashboardBinding(@NonNull NestedScrollView rootView,
      @NonNull AppCompatButton btnFavorite, @NonNull AppCompatButton btnMobile,
      @NonNull TextView btnOption, @NonNull CardView cvCard, @NonNull CardView cvFavorite,
      @NonNull CardView cvOfflinePast, @NonNull ImageView imgShare, @NonNull ImageView ivIcon,
      @NonNull ImageView ivNotification, @NonNull ImageView ivSpeak,
      @NonNull LinearLayout llNoRecord, @NonNull LinearLayout llTop, @NonNull TextView tvAntonyms,
      @NonNull TextView tvDescription, @NonNull AppCompatTextView tvEmail,
      @NonNull TextView tvFavoriteCount, @NonNull AppCompatTextView tvFullName,
      @NonNull TextView tvNoWords, @NonNull TextView tvOfflineCount, @NonNull TextView tvSynonyms,
      @NonNull TextView tvWord) {
    this.rootView = rootView;
    this.btnFavorite = btnFavorite;
    this.btnMobile = btnMobile;
    this.btnOption = btnOption;
    this.cvCard = cvCard;
    this.cvFavorite = cvFavorite;
    this.cvOfflinePast = cvOfflinePast;
    this.imgShare = imgShare;
    this.ivIcon = ivIcon;
    this.ivNotification = ivNotification;
    this.ivSpeak = ivSpeak;
    this.llNoRecord = llNoRecord;
    this.llTop = llTop;
    this.tvAntonyms = tvAntonyms;
    this.tvDescription = tvDescription;
    this.tvEmail = tvEmail;
    this.tvFavoriteCount = tvFavoriteCount;
    this.tvFullName = tvFullName;
    this.tvNoWords = tvNoWords;
    this.tvOfflineCount = tvOfflineCount;
    this.tvSynonyms = tvSynonyms;
    this.tvWord = tvWord;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDashboardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDashboardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_dashboard, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDashboardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnFavorite;
      AppCompatButton btnFavorite = ViewBindings.findChildViewById(rootView, id);
      if (btnFavorite == null) {
        break missingId;
      }

      id = R.id.btnMobile;
      AppCompatButton btnMobile = ViewBindings.findChildViewById(rootView, id);
      if (btnMobile == null) {
        break missingId;
      }

      id = R.id.btn_option;
      TextView btnOption = ViewBindings.findChildViewById(rootView, id);
      if (btnOption == null) {
        break missingId;
      }

      id = R.id.cvCard;
      CardView cvCard = ViewBindings.findChildViewById(rootView, id);
      if (cvCard == null) {
        break missingId;
      }

      id = R.id.cvFavorite;
      CardView cvFavorite = ViewBindings.findChildViewById(rootView, id);
      if (cvFavorite == null) {
        break missingId;
      }

      id = R.id.cvOfflinePast;
      CardView cvOfflinePast = ViewBindings.findChildViewById(rootView, id);
      if (cvOfflinePast == null) {
        break missingId;
      }

      id = R.id.img_share;
      ImageView imgShare = ViewBindings.findChildViewById(rootView, id);
      if (imgShare == null) {
        break missingId;
      }

      id = R.id.ivIcon;
      ImageView ivIcon = ViewBindings.findChildViewById(rootView, id);
      if (ivIcon == null) {
        break missingId;
      }

      id = R.id.ivNotification;
      ImageView ivNotification = ViewBindings.findChildViewById(rootView, id);
      if (ivNotification == null) {
        break missingId;
      }

      id = R.id.ivSpeak;
      ImageView ivSpeak = ViewBindings.findChildViewById(rootView, id);
      if (ivSpeak == null) {
        break missingId;
      }

      id = R.id.llNoRecord;
      LinearLayout llNoRecord = ViewBindings.findChildViewById(rootView, id);
      if (llNoRecord == null) {
        break missingId;
      }

      id = R.id.llTop;
      LinearLayout llTop = ViewBindings.findChildViewById(rootView, id);
      if (llTop == null) {
        break missingId;
      }

      id = R.id.tvAntonyms;
      TextView tvAntonyms = ViewBindings.findChildViewById(rootView, id);
      if (tvAntonyms == null) {
        break missingId;
      }

      id = R.id.tvDescription;
      TextView tvDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvDescription == null) {
        break missingId;
      }

      id = R.id.tvEmail;
      AppCompatTextView tvEmail = ViewBindings.findChildViewById(rootView, id);
      if (tvEmail == null) {
        break missingId;
      }

      id = R.id.tvFavoriteCount;
      TextView tvFavoriteCount = ViewBindings.findChildViewById(rootView, id);
      if (tvFavoriteCount == null) {
        break missingId;
      }

      id = R.id.tvFullName;
      AppCompatTextView tvFullName = ViewBindings.findChildViewById(rootView, id);
      if (tvFullName == null) {
        break missingId;
      }

      id = R.id.tvNoWords;
      TextView tvNoWords = ViewBindings.findChildViewById(rootView, id);
      if (tvNoWords == null) {
        break missingId;
      }

      id = R.id.tvOfflineCount;
      TextView tvOfflineCount = ViewBindings.findChildViewById(rootView, id);
      if (tvOfflineCount == null) {
        break missingId;
      }

      id = R.id.tvSynonyms;
      TextView tvSynonyms = ViewBindings.findChildViewById(rootView, id);
      if (tvSynonyms == null) {
        break missingId;
      }

      id = R.id.tvWord;
      TextView tvWord = ViewBindings.findChildViewById(rootView, id);
      if (tvWord == null) {
        break missingId;
      }

      return new ActivityDashboardBinding((NestedScrollView) rootView, btnFavorite, btnMobile,
          btnOption, cvCard, cvFavorite, cvOfflinePast, imgShare, ivIcon, ivNotification, ivSpeak,
          llNoRecord, llTop, tvAntonyms, tvDescription, tvEmail, tvFavoriteCount, tvFullName,
          tvNoWords, tvOfflineCount, tvSynonyms, tvWord);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
