// Generated by view binder compiler. Do not edit!
package com.app.vocabulary.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.app.vocabulary.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class VocabularyItemsBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final AppCompatButton btnLogin;

  @NonNull
  public final CardView cvCard;

  @NonNull
  public final ImageView imgShare;

  @NonNull
  public final TextView tvAntonyms;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvDescription;

  @NonNull
  public final TextView tvSynonyms;

  @NonNull
  public final TextView tvWord;

  private VocabularyItemsBinding(@NonNull CardView rootView, @NonNull AppCompatButton btnLogin,
      @NonNull CardView cvCard, @NonNull ImageView imgShare, @NonNull TextView tvAntonyms,
      @NonNull TextView tvDate, @NonNull TextView tvDescription, @NonNull TextView tvSynonyms,
      @NonNull TextView tvWord) {
    this.rootView = rootView;
    this.btnLogin = btnLogin;
    this.cvCard = cvCard;
    this.imgShare = imgShare;
    this.tvAntonyms = tvAntonyms;
    this.tvDate = tvDate;
    this.tvDescription = tvDescription;
    this.tvSynonyms = tvSynonyms;
    this.tvWord = tvWord;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static VocabularyItemsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static VocabularyItemsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.vocabulary_items, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static VocabularyItemsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnLogin;
      AppCompatButton btnLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin == null) {
        break missingId;
      }

      CardView cvCard = (CardView) rootView;

      id = R.id.img_share;
      ImageView imgShare = ViewBindings.findChildViewById(rootView, id);
      if (imgShare == null) {
        break missingId;
      }

      id = R.id.tvAntonyms;
      TextView tvAntonyms = ViewBindings.findChildViewById(rootView, id);
      if (tvAntonyms == null) {
        break missingId;
      }

      id = R.id.tvDate;
      TextView tvDate = ViewBindings.findChildViewById(rootView, id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tvDescription;
      TextView tvDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvDescription == null) {
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

      return new VocabularyItemsBinding((CardView) rootView, btnLogin, cvCard, imgShare, tvAntonyms,
          tvDate, tvDescription, tvSynonyms, tvWord);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
