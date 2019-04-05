package com.udacity.wallet.ui.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.udacity.wallet.R;
import com.udacity.wallet.data.model.User;
import com.udacity.wallet.databinding.ProfileFragmentBinding;

import static com.udacity.wallet.shared.Globals.getApplicationContext;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private ProfileViewModel mProfileViewModel;
    private ProfileFragmentBinding mBinding;
    private SharedPreferences.Editor mEditorPreference;
    private SharedPreferences mSharedPreferences;

    //FOR DATA
    private static final int SIGN_OUT_TASK = 10;
    private int mBudgetValue = 0;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        mBinding = DataBindingUtil.bind(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        mEditorPreference = mSharedPreferences.edit();

        mProfileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel

        mBinding.profileActivityButtonSignOut.setOnClickListener(this);
        mBinding.profileActivityButtonUpdateBudget.setOnClickListener(this);

        updateUIWhenCreating();
    }

    // --------------------
    // REST REQUESTS
    // --------------------

    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnSuccessListener(getActivity(), this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    // --------------------
    // UI
    // --------------------

    private void updateUIWhenCreating(){

        if (mProfileViewModel.getCurrentUser() != null){

            //Get picture URL from Firebase
            if (mProfileViewModel.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(mProfileViewModel.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(mBinding.profileActivityImageviewProfile);
            }

            //Get email & username from Firebase
            String email = TextUtils.isEmpty(mProfileViewModel.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : mProfileViewModel.getCurrentUser().getEmail();

            //Update views with data
            mBinding.profileActivityTextViewEmail.setText(email);

            // 5 - Get additional data from Firestore
            UserHelper.getUser(mProfileViewModel.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    mBinding.profileActivityEditTextUsername.setText(username);
                }
            });
        }
    }

    protected OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
            }
        };
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        getActivity().finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == mBinding.profileActivityButtonSignOut){
            signOutUserFromFirebase();
        }else if(v == mBinding.profileActivityButtonUpdateBudget){
            mBudgetValue = Integer.parseInt(mBinding.profileActivityEditTextBudget.getText().toString());
            mEditorPreference.putInt("user_budget", mBudgetValue);
            mEditorPreference.apply();
            Toast.makeText(getApplicationContext(), getString(R.string.budget_updated), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mBudgetValue = mSharedPreferences.getInt("user_budget", 0);
        if(mBudgetValue == 0){
            mBudgetValue = 1000;
        }

        mBinding.profileActivityEditTextBudget.setText(""+mBudgetValue);
    }
}
