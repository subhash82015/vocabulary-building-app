// Generated by view binder compiler. Do not edit!
package com.app.vocabulary.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.app.vocabulary.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final AppCompatButton btnAddUser;

  @NonNull
  public final AppCompatEditText etEmail;

  @NonNull
  public final AppCompatEditText etFName;

  @NonNull
  public final AppCompatEditText etMobile;

  @NonNull
  public final AppCompatEditText etPassword;

  @NonNull
  public final LinearLayoutCompat llFeesAmount;

  @NonNull
  public final LinearLayout llHead;

  private ActivityRegisterBinding(@NonNull NestedScrollView rootView,
      @NonNull AppCompatButton btnAddUser, @NonNull AppCompatEditText etEmail,
      @NonNull AppCompatEditText etFName, @NonNull AppCompatEditText etMobile,
      @NonNull AppCompatEditText etPassword, @NonNull LinearLayoutCompat llFeesAmount,
      @NonNull LinearLayout llHead) {
    this.rootView = rootView;
    this.btnAddUser = btnAddUser;
    this.etEmail = etEmail;
    this.etFName = etFName;
    this.etMobile = etMobile;
    this.etPassword = etPassword;
    this.llFeesAmount = llFeesAmount;
    this.llHead = llHead;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAddUser;
      AppCompatButton btnAddUser = ViewBindings.findChildViewById(rootView, id);
      if (btnAddUser == null) {
        break missingId;
      }

      id = R.id.etEmail;
      AppCompatEditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.etFName;
      AppCompatEditText etFName = ViewBindings.findChildViewById(rootView, id);
      if (etFName == null) {
        break missingId;
      }

      id = R.id.etMobile;
      AppCompatEditText etMobile = ViewBindings.findChildViewById(rootView, id);
      if (etMobile == null) {
        break missingId;
      }

      id = R.id.etPassword;
      AppCompatEditText etPassword = ViewBindings.findChildViewById(rootView, id);
      if (etPassword == null) {
        break missingId;
      }

      id = R.id.llFeesAmount;
      LinearLayoutCompat llFeesAmount = ViewBindings.findChildViewById(rootView, id);
      if (llFeesAmount == null) {
        break missingId;
      }

      id = R.id.llHead;
      LinearLayout llHead = ViewBindings.findChildViewById(rootView, id);
      if (llHead == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((NestedScrollView) rootView, btnAddUser, etEmail, etFName,
          etMobile, etPassword, llFeesAmount, llHead);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
